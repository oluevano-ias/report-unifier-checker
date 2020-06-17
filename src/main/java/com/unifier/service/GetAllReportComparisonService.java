package com.unifier.service;

import ch.qos.logback.classic.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.unifier.dto.GetAllResponseResult;
import com.unifier.dto.ReportInstanceData;
import com.unifier.dto.TeamResult;
import com.unifier.dto.comparisonresult.*;
import com.unifier.exception.GetAllComparisonException;
import com.unifier.migrated.models.ReportInstance;
import com.unifier.migrated.service.MigratedService;
import io.vavr.Function2;
import io.vavr.Tuple2;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.unifier.util.EitherUtil.liftAndMapLeft;

@Component
public class GetAllReportComparisonService {

    private static Logger logger = (Logger) LoggerFactory.getLogger(GetAllReportComparisonService.class);
    final String scheduledPathFilter = "$.[*][?(@.frequency != null && @.frequency != 'ONE_TIME')]['id', 'reportName']";
    final String nonScheduledPathFilter = "$.[*][?(@.frequency == null || @.frequency == 'ONE_TIME')]['id', 'reportName']";
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    private AuthTokenService authTokenService;
    @Value("${endpoint.migrated.fw.url}")
    private String migratedFwEndpoint;
    @Value("${endpoint.original.fw.url}")
    private String originalFwEndpoint;
    @Value("${endpoint.migrated.pub.url}")
    private String migratedPubEndpoint;
    @Value("${endpoint.original.pub.url}")
    private String originalPubEndpoint;
    @Value("${output.path}")
    private String outputPath;

    @Autowired
    private MigratedService migratedService;
    private Function2<Long, Throwable, GetAllComparisonException> throwableToComparisonException =
            (teamId, throwable) -> new GetAllComparisonException(teamId, throwable);

    public Either<GetAllComparisonException, TeamResult> compareSingleTeam(String product, long teamId) {
        Try<Tuple2<String, String>> endpointsTry = getEndpoints(product, teamId);
        if (endpointsTry.isFailure())
            return Either.left(new GetAllComparisonException(teamId, endpointsTry.getCause()));

        Tuple2<String, String> endpoints = endpointsTry.get();
        Either<GetAllComparisonException, String> originalRequest = liftRequest(endpoints._1, teamId);
        Either<GetAllComparisonException, String> migratedRequest = liftRequest(endpoints._2, teamId);

        return originalRequest.flatMap(originalResponse ->
                migratedRequest.map(migratedResponse -> {
                    //filtered only works because i modified api-crb
                    // in dev/api-crb-rollback/nemo-api-custom-report-builder-19.3.45
                    String filteredResponse = filteroutDeletedScheduledReports(originalResponse);
                    return new GetAllResponseResult(teamId, filteredResponse, migratedResponse);
                }))
                .flatMap(this::createResult);
    }

    private Try<Tuple2<String, String>> getEndpoints(String product, long teamId) {
        if (product.equals("fw"))
            return Try.success(new Tuple2<>(originalFwEndpoint, migratedFwEndpoint));
        else if (product.equals("pub"))
            return Try.success(new Tuple2<>(originalPubEndpoint, migratedPubEndpoint));
        else return Try.failure(new RuntimeException("Only 'fw' or 'pub' are valid products"));
    }

    public PerProductResult2 compareMultipleTeams(Map<String, List<Long>> data) {
        PerProductResult2 perProductResult = new PerProductResult2();
        List<Long> pubTeamIds = data.get("pub");
        List<Long> fwTeamIds = data.get("fw");
        if (pubTeamIds != null && fwTeamIds != null) {
            MultipleTeamsResult2 pubResult = createProductResult("pub", pubTeamIds);
            MultipleTeamsResult2 fwResult = createProductResult("fw", fwTeamIds);
            perProductResult.setPublisherReports(pubResult);
            perProductResult.setFirewallReports(fwResult);
            return perProductResult;
        } else throw new IllegalArgumentException("No report product specified");
    }

    private MultipleTeamsResult2 createProductResult(String product, List<Long> teamIds) {
        logger.info("{} teams processing started", product);
        ObjectMapper mapper = new ObjectMapper();
        File outputDir = new File(outputPath, product);
        if (!outputDir.exists())
            outputDir.mkdirs();
        //MultipleTeamsResult teamsResult = new MultipleTeamsResult();
        MultipleTeamsResult2 teamsResult = new MultipleTeamsResult2();
        int counter = 1;
        int total = teamIds.size();
        for (Long teamId : teamIds) {
            logger.info("processing {} team {} [{}/{}]", product, teamId, counter++, total);
            Either<GetAllComparisonException, TeamResult> teamResult = compareSingleTeam(product, teamId);
            if (teamResult.isLeft()) {
                GetAllComparisonException ex = teamResult.getLeft();
                teamsResult.addTeamWithException(new TeamWithException(ex.getTeamId(), ex.getMessage()));
                writeTeamWithException(ex, mapper, outputDir);
            } else {
                TeamResult teamResult1 = teamResult.get();
                //logger.info("\tprocessed team {}", teamResult1.getTeamId());
                if (teamResult1.isEqual()) {
                    teamsResult.addEqualTeam(teamResult1);
                    writeTeam(mapper, outputDir, teamResult1, "equal-Team");
                }
                else {
                    teamsResult.addNotEqualTeam(teamResult1);
                    writeTeam(mapper, outputDir, teamResult1, "notEqual-Team");
                }
            }
        }
        logger.info("{} teams processing finished", product);
        return teamsResult;
    }

    private void writeTeamWithException(GetAllComparisonException ex, ObjectMapper mapper, File outputDir) {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(outputDir, "exceptionFailed-Team" + ex.getTeamId()), ex.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeTeam(ObjectMapper mapper, File outputDir, TeamResult teamResult, String filePrefix) {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(outputDir, filePrefix + "-" + teamResult.getTeamId()), teamResult);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Either<GetAllComparisonException, String> liftRequest(String endpoint, Long teamId) {
        return liftAndMapLeft(this::doRequest, throwableToComparisonException.apply(teamId))
                .apply(endpoint, teamId);
    }

    private String doRequest(String endpoint, Long teamId) {
        String token = authTokenService.getAuthToken().getToken();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(endpoint + teamId, HttpMethod.GET, entity, String.class);
        return response.getBody();
    }

    private Either<GetAllComparisonException, TeamResult> createResult(GetAllResponseResult responseResult) {
        Long teamId = responseResult.getTeamId();
        TeamResult result = new TeamResult(teamId);

        Function<TeamResult, Either<GetAllComparisonException, TeamResult>> checkSize =
                liftAndMapLeft(this::checkSize, throwableToComparisonException.apply(teamId))
                        .apply(responseResult);
        Function<TeamResult, Either<GetAllComparisonException, TeamResult>> checkReportNames =
                liftAndMapLeft(this::checkReportNames, throwableToComparisonException.apply(teamId))
                        .apply(responseResult);
        Function<TeamResult, Either<GetAllComparisonException, TeamResult>> checkReportTeamIds =
                liftAndMapLeft(this::checkReportTeamIds, throwableToComparisonException.apply(teamId))
                        .apply(responseResult);
        Function<TeamResult, Either<GetAllComparisonException, TeamResult>> runChecks =
                liftAndMapLeft(this::runChecks, throwableToComparisonException.apply(teamId));

        return checkSize.apply(result)
                .flatMap(checkReportNames)
                .flatMap(checkReportTeamIds)
                .flatMap(runChecks);
    }

    private TeamResult runChecks(TeamResult result) {
        result.checkTeam();
        return result;
    }

    private String filteroutDeletedScheduledReports(String response) {
        //below works because of modified api-crb used to test original data
        String onlyNonDeletedSchedulesPath = "$.[*][?(@.scheduledReportDateDeleted == null)]";
        return JsonPath.read(response, onlyNonDeletedSchedulesPath).toString();
    }

    private TeamResult checkSize(GetAllResponseResult responseResult, TeamResult teamResult) {
        String lengthPath = "$.length()";
        Integer originalSize = JsonPath.read(responseResult.getOriginalResponse(), lengthPath);
        List<Map<String, Object>> scheduledReports = JsonPath.read(responseResult.getMigratedResponse(), scheduledPathFilter);
        List<Map<String, Object>> nonScheduledReports = JsonPath.read(responseResult.getMigratedResponse(), nonScheduledPathFilter);

        Map<String, List<SizeResultData>> scheduledReportData = toMap(scheduledReports, "scheduled", migratedService::getNonDeletedReportsFromSchedule);
        Map<String, List<SizeResultData>> onDemandReportData = toMap(nonScheduledReports, "onDemand", migratedService::getReportsFromSchedule);

        MigratedSizeResult migratedSizeResult = new MigratedSizeResult(onDemandReportData, scheduledReportData);
        TotalsSizeResult totalsSizeResult = new TotalsSizeResult(originalSize, migratedSizeResult);

        teamResult.setSizeResult(totalsSizeResult);
        return teamResult;
    }

    private TeamResult checkReportNames(GetAllResponseResult responseResult, TeamResult teamResult) {
        String namesPath = "$..reportName";
        List<String> originalNames = JsonPath.read(responseResult.getOriginalResponse(), namesPath);
        //List<String> migratedNames = JsonPath.read(responseResult.getMigratedResponse(), namesPath);
        MigratedSizeResult migratedResult = teamResult.getSizeResult().getMigratedResult();
        //teamResult.setNamesResult(new NamesResult(originalNames, migratedNames));
        teamResult.setNamesResult(new NamesResult2(originalNames, migratedResult));
        return teamResult;
    }

    private TeamResult checkReportTeamIds(GetAllResponseResult responseResult, TeamResult teamResult) {
        String path = "$.[*]['id', 'reportName', 'reportTeamIds', 'createdOn']";
        List<Map<String, Object>> originalValue = JsonPath.read(responseResult.getOriginalResponse(), path);
        //List<Map<String, Object>> migratedValue = JsonPath.read(responseResult.getMigratedResponse(), path);
        MigratedSizeResult migratedResult = teamResult.getSizeResult().getMigratedResult();
        //logger.info("originalValue {}, migratedValue {}", originalValue, migratedValue);
        teamResult.setReportTeamsResult(new ReportTeamsResult2(originalValue, migratedResult));
        return teamResult;
    }

    private Map<String, List<SizeResultData>> toMap(List<Map<String, Object>> listOfMap, String reportType, Function<Long, List<ReportInstance>> instancesReaderFn) {
        Map<String, List<SizeResultData>> resultMap = new LinkedHashMap<>();
        listOfMap.stream().forEach(currentMap -> {
            String reportName = currentMap.get("reportName").toString();
            List<ReportInstanceData> reportInstanceData = Collections.emptyList();
            Long templateId = -1L;
            int instancesSize = 0;
            if (currentMap.get("id") != null)
                templateId = Long.parseLong(currentMap.get("id").toString());
            if (templateId != -1) {
                List<ReportInstance> instances = instancesReaderFn.apply(templateId);//migratedService.getReportsFromSchedule(templateId);
                reportInstanceData = instances.stream().map(reportInstance -> {
                            Set<Long> teamIds = reportInstance.getReportTeams()
                                    .stream().map(rt -> rt.getTeamId()).collect(Collectors.toSet());
                            return new ReportInstanceData(teamIds, reportInstance.getCreatedOn());
                        }
                ).collect(Collectors.toList());
                instancesSize = instances.size();
            }
            SizeResultData sizeResultData = new SizeResultData(reportName, templateId, instancesSize, reportType, reportInstanceData);
            resultMap.computeIfAbsent(reportName, k -> new ArrayList<>()).add(sizeResultData);
        });
        return resultMap;
    }
}
