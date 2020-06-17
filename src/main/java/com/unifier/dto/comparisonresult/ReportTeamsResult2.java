package com.unifier.dto.comparisonresult;

import com.unifier.dto.ReportInstanceData;
import io.vavr.Tuple2;

import java.util.*;
import java.util.stream.Collectors;

public class ReportTeamsResult2 {
    private List<Map<String, Object>> originalValue;
    private MigratedSizeResult migratedResult;
    private String failureReason;
    private short calculated = 0;
    private boolean equalsResult;

    public ReportTeamsResult2(List<Map<String, Object>> originalValue, MigratedSizeResult migratedResult) {
        this.originalValue = originalValue;
        this.migratedResult = migratedResult;
    }


    public boolean isEquals() {
        if (calculated == 0) {
            //List<SizeResultData> onDemandReports = migratedResult.getOnDemandReports();
            //List<SizeResultData> scheduledReports = migratedResult.getScheduledReports();

            Map<String, List<SizeResultData>> onDemandReportData = migratedResult.getOnDemandReportData();
            Map<String, List<SizeResultData>> scheduledReportData = migratedResult.getScheduledReportData();

            Map<String, List<ReportInstanceData>> migratedMap = toMap(onDemandReportData, scheduledReportData);
            //Map<String, List<ReportInstanceData>> reportMap = toMap(onDemandReports, scheduledReports);
            Map<String, List<OriginalReportData>> originalMap = toMap(originalValue);

            /*if (reportMap.size() != originalMap.size())
                failureReason = "Report team ids are not the same size";
            else {*/
            Set<String> originalReportNames = originalMap.keySet();
            for (String reportName : originalReportNames) {
                List<OriginalReportData> originalReportData = originalMap.get(reportName);
                List<ReportInstanceData> migratedReportData = migratedMap.get(reportName);
                if (originalReportData != null && migratedReportData != null) {
                    if (originalReportData.size() == migratedReportData.size()) {
                        Tuple2<Boolean, String> tuple = compareExecutions(originalReportData, migratedReportData);
                        if (!tuple._1) {
                            failureReason = "ReportName '" + reportName + "' " + tuple._2;
                            break;
                        }
                        else
                            equalsResult = true;
                    } else {
                        failureReason = "ReportName '" + reportName + "' has diff size regarding # of executions, original:"
                                + originalReportData.size() + ", migrated:" + migratedReportData.size();
                        break;
                    }
                } else {
                    if (migratedReportData == null)
                        failureReason = "ReportName '" + reportName + "' not found in migrated data";
                    else
                        failureReason = "ReportName '" + reportName + "' not found";
                    break;
                }
            }
        }

        calculated = 1;
        //}
        return equalsResult;
    }


    private Map<String, List<ReportInstanceData>> toMap(Map<String, List<SizeResultData>> onDemandReportData,
                                                        Map<String, List<SizeResultData>> scheduledReportData) {
        Map<String, List<ReportInstanceData>> result = new LinkedHashMap<>();
        onDemandReportData.forEach((reportName, sizeResultData) -> {
            List<ReportInstanceData> reportInstanceData = sizeResultData.stream().map(sizeResultData1 -> sizeResultData1.getReportInstanceData())
                    .reduce(new ArrayList<>(), (list, data) -> {
                        list.addAll(data);
                        return list;
                    });
            result.put(reportName, reportInstanceData);
        });
        scheduledReportData.forEach((reportName, sizeResultData) -> {
            List<ReportInstanceData> reportInstanceData = sizeResultData.stream().map(sizeResultData1 -> sizeResultData1.getReportInstanceData())
                    .reduce(new ArrayList<>(), (list, data) -> {
                        list.addAll(data);
                        return list;
                    });
            result.put(reportName, reportInstanceData);
        });
        /*onDemandReportData.forEach(sizeResultData -> {
            result.put(sizeResultData.getName(), sizeResultData.getReportInstanceData());
        });
        scheduledReportData.forEach(sizeResultData -> {
            result.put(sizeResultData.getName(), sizeResultData.getReportInstanceData());
        });*/
        return result;
    }

    private Map<String, List<OriginalReportData>> toMap(List<Map<String, Object>> originalValue) {
        Map<String, List<OriginalReportData>> result2 = new LinkedHashMap<>();
        originalValue.forEach(map -> {
            String reportName = map.get("reportName") == null ? "" : map.get("reportName").toString();
            List<Integer> teamIds = map.get("reportTeamIds") == null ? Collections.emptyList() : (List<Integer>) map.get("reportTeamIds");
            String createdOn = map.get("createdOn") == null ? "" : map.get("createdOn").toString();
            Date createdOnDate = new Date(Long.parseLong(createdOn));
            Set<Long> realTeamIds = teamIds.stream().map(integer -> new Long(integer)).collect(Collectors.toSet());
            OriginalReportData originalReportData = new OriginalReportData(reportName, realTeamIds, createdOnDate);
            result2.computeIfAbsent(reportName, key -> new ArrayList<>()).add(originalReportData);
        });
        return result2;
    }

    private Map<String, List<ReportInstanceData>> toMap
            (List<SizeResultData> onDemandReports, List<SizeResultData> scheduledReports) {
        Map<String, List<ReportInstanceData>> result = new LinkedHashMap<>();
        onDemandReports.forEach(sizeResultData -> {
            result.put(sizeResultData.getName(), sizeResultData.getReportInstanceData());
        });
        scheduledReports.forEach(sizeResultData -> {
            result.put(sizeResultData.getName(), sizeResultData.getReportInstanceData());
        });
        return result;
    }

    private Tuple2<Boolean, String> compareExecutions
            (List<OriginalReportData> originalReportData, List<ReportInstanceData> migratedReportData) {
        Map<Date, Set<Long>> originalDateSetMap = originalReportDataToMap(originalReportData);
        Map<Date, Set<Long>> migratedDateSetMap = migratedReportDataToMap(migratedReportData);
        boolean equal = false;
        String reason = null;
        for (Map.Entry<Date, Set<Long>> originalEntry : originalDateSetMap.entrySet()) {
            Date date = originalEntry.getKey();
            Set<Long> migratedTeamIds = migratedDateSetMap.get(date);
            if (migratedTeamIds != null) {
                HashSet<Long> originalTeamIds = new HashSet<>(originalEntry.getValue());
                originalTeamIds.removeAll(migratedTeamIds);
                equal = originalTeamIds.size() == 0;
            } else {
                reason = "createdOn '" + date + "' not found in migrated data";
                break;
            }
        }
        return new Tuple2(equal, reason);
    }

    private Map<Date, Set<Long>> originalReportDataToMap(List<OriginalReportData> originalReportData) {
        Map<Date, Set<Long>> result = new LinkedHashMap<>();
        originalReportData.forEach(originalReportData1 -> result.put(originalReportData1.createdOnDate, originalReportData1.teamIds));
        return result;
    }

    private Map<Date, Set<Long>> migratedReportDataToMap(List<ReportInstanceData> migratedReportData) {
        Map<Date, Set<Long>> result = new LinkedHashMap<>();
        migratedReportData.forEach(migratedReportData1 -> result.put(migratedReportData1.getCreatedOn(), migratedReportData1.getTeamIds()));
        return result;
    }

    public String getFailureReason() {
        return failureReason;
    }

    private static class OriginalReportData {
        final Set<Long> teamIds;
        final Date createdOnDate;
        final private String reportName;

        public OriginalReportData(String reportName, Set<Long> teamIds, Date createdOnDate) {
            this.reportName = reportName;
            this.teamIds = teamIds;
            this.createdOnDate = createdOnDate;
        }

        public boolean isEqualsTo(ReportInstanceData reportInstanceData) {
            Set<Long> teamIds = reportInstanceData.getTeamIds();
            HashSet<Long> thisTheamIds = new HashSet<>(this.teamIds);
            thisTheamIds.removeAll(teamIds);
            boolean sameTeamIds = thisTheamIds.size() == 0;
            //reportInstanceData.getCreatedOn().
            return sameTeamIds;
        }
    }
}
