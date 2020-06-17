package com.unifier.util;

import com.unifier.migrated.models.ReportInstance;
import com.unifier.migrated.models.ReportTemplate;
import com.unifier.original.models.Report;
import com.unifier.original.models.ReportFile;
import com.unifier.original.models.ReportSchedule;
import com.unifier.original.models.ReportTeam;
import io.vavr.Function2;
import io.vavr.collection.Seq;
import io.vavr.control.Validation;

import java.math.BigInteger;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class MigrationUtil {

    private static <T1, T2> Validation<String, T1> equals3(T1 t1, T2 t2, String failureMsg) {
        if (t1 == null && t2 == null)
            return Validation.valid(t1);
        else if (t1 != null && t2 != null)
            return t1.equals(t2) == true ? Validation.valid(t1) : Validation.invalid(failureMsg);
        else
            return Validation.invalid(failureMsg);
    }

    private static <T1, T2, T3> Validation<String, T3> equals3(T1 t1, T2 t2, Function2<T1, T2, Validation<String, T3>> next, String failureMsg) {
        if (t1 == null && t2 == null)
            return Validation.valid(null);
        else if (t1 != null && t2 != null)
            return next.apply(t1, t2);
        else
            return Validation.invalid(failureMsg);
    }

    public static Validation<Seq<String>, Validation<Seq<String>, Boolean>> compareReportSchedules(Optional<ReportSchedule> optionalReportSchedule,
                                                                                                   Optional<ReportTemplate> optionalReportTemplate) {
        Validation<String, ReportSchedule> v1 = optionalReportSchedule.isPresent() == true ?
                Validation.valid(optionalReportSchedule.get()) : Validation.invalid("original reportSchedule not found");
        Validation<String, ReportTemplate> v2 = optionalReportTemplate.isPresent() == true ?
                Validation.valid(optionalReportTemplate.get()) : Validation.invalid("migrated reportSchedule not found");
        return v1.combine(v2).ap((reportSchedule, reportTemplate) -> {
            Validation<String, Date> v3 = equals3(reportSchedule.getCreatedOn(), reportTemplate.getCreatedOn(), "createdOn not equal");
            Validation<String, Date> v4 = equals3(reportSchedule.getLastModified(), reportTemplate.getLastModified(), "LastModified not equal");
            Validation<String, Date> v5 = equals3(reportSchedule.getDateDeleted(), reportTemplate.getDateDeleted(), "DateDeleted not equal");
            Validation<String, Long> v6 = equals3(reportSchedule.getCreatedByUserId(), reportTemplate.getCreatedByUserId(), "CreatedByUserId not equal");
            Validation<String, String> v7 = equals3(reportSchedule.getCreatedByUsername(), reportTemplate.getCreatedByUsername(), "CreatedByUsername not equal");
            Validation<String, Long> v8 = equals3(reportSchedule.getSchedulePlan(), reportTemplate.getSchedulePlan(),
                    MigrationUtil::compareSchedulePlan, "SchedulePlanId not equal");
            return Validation.combine(v3, v4, v5, v6, v7, v8)
                    .ap((d3, d4, d5, d6, d7, d8) -> true);
        });
    }

    public static Validation<String, Long> compareSchedulePlan(com.unifier.original.models.SchedulePlan originalSchedulePlan,
                                                               com.unifier.migrated.models.SchedulePlan migratedSchedulePlan) {
        if (originalSchedulePlan.getId().equals(migratedSchedulePlan.getId()))
            return Validation.valid(originalSchedulePlan.getId());
        else
            return Validation.invalid("SchedulePlanId is not equal");
    }

    public static Validation<Seq<String>, Boolean> areReportsEqual(List<Report> reports, List<ReportInstance> reportInstances) {
        Validation<String, Integer> v3 = reports.size() == reportInstances.size() == true ?
                Validation.valid(reports.size()) : Validation.invalid("original reports size != migrated reports size");
        int allEqualCtr = 0;
        for (int x = 0; x < reports.size(); x++) {
            Validation<Seq<String>, Boolean> vx = compareReports(reports.get(x), reportInstances.get(x));
            if (vx.isValid())
                allEqualCtr++;
        }
        Validation<String, Integer> v4 = allEqualCtr == reports.size() ?
                Validation.valid(allEqualCtr) : Validation.invalid("at least one report is not equal");
        return Validation.combine(v3, v4).ap((d1, d2) -> true);
    }

    public static Validation<Seq<String>, Boolean> compareReports(Report report, ReportInstance reportInstance) {
        Validation<String, Long> v1 = equals3(report.getVersion(), reportInstance.getVersion(), "report version is not equal");
        Validation<String, String> v2 = equals3(report.getName(), reportInstance.getName(), "report name is not equal");
        Validation<String, String> v3 = equals3(report.getType().toString(), reportInstance.getType().toString(), "report type is not equal");
        Validation<String, Long> v4 = equals3(report.getCreatedByUserId(), reportInstance.getCreatedByUserId(), "report createdByUserId is not equal");
        Validation<String, String> v5 = equals3(report.getCreatedByUsername(), reportInstance.getCreatedByUsername(), "report createdByUsername is not equal");
        //Validation<String, Long> v6 = equals3(report.getId(), oldReportId, "oldReport id is not [" + oldReportId +"]");
        //Validation<String, Long> v7 = equals3(reportInstance.getId(), newReportInstanceId, "reportInstance id is not [" + newReportInstanceId +"]");
        Set<ReportFile> reportFiles = report.getFiles();
        Set<com.unifier.migrated.models.ReportFile> reportInstanceFiles = null;//reportInstance.getFiles();

        Set<ReportTeam> originalReportTeams = report.getReportTeams();
        Set<com.unifier.migrated.models.ReportTeam> migratedReportTeams = reportInstance.getReportTeams();

        Validation<String, Integer> v6 = reportFiles.size() == reportInstanceFiles.size() == true ?
                Validation.valid(reportFiles.size()) : Validation.invalid("original report files size != migrated report files size");

        Validation<String, Integer> v7 = originalReportTeams.size() == migratedReportTeams.size() ?
                Validation.valid(originalReportTeams.size()) : Validation.invalid("original report teams size != migrated report teams size");

        if (v6.isValid()) {
            int allFilesEqualCtr = 0;
            Iterator<ReportFile> it1 = reportFiles.iterator();
            Iterator<com.unifier.migrated.models.ReportFile> it2 = reportInstanceFiles.iterator();
            while (it1.hasNext() && it2.hasNext()) {
                Validation<Seq<String>, Boolean> reportFilesEqual = compareReportsFiles(it1.next(), it2.next());
                if (reportFilesEqual.isValid())
                    allFilesEqualCtr++;
            }
            Validation<String, String> v8 = allFilesEqualCtr == reportFiles.size() == true ?
                    Validation.valid("report files are ok") : Validation.invalid("At least one report file is not equal");
            return Validation.combine(v1, v2, v3, v4, v5, v6, v7, v8).ap((d1, d2, d3, d4, d5, d6, d7, d8) -> true);
        } else
            return Validation.combine(v1, v2, v3, v4, v5, v6, v7).ap((d1, d2, d3, d4, d5, d6, d7) -> true);
    }

    public static Validation<Seq<String>, Validation<Seq<String>, Boolean>> compareReports(Optional<Report> optionalReport, Optional<ReportInstance> optionalReportInstance) {
        Validation<String, Report> v1 = optionalReport.isPresent() == true ?
                Validation.valid(optionalReport.get()) : Validation.invalid("original report not found");
        Validation<String, ReportInstance> v2 = optionalReportInstance.isPresent() == true ?
                Validation.valid(optionalReportInstance.get()) : Validation.invalid("migrated ReportInstance not found");
        return Validation.combine(v1, v2).ap((report, reportInstance) -> compareReports(report, reportInstance));
    }

    private static Validation<Seq<String>, Boolean> compareReportsFiles(ReportFile oldReport, com.unifier.migrated.models.ReportFile newReportFile) {
        Validation<String, BigInteger> sizeOk = equals3(oldReport.getSize(), newReportFile.getSize(), "old report size != new report size");
        Validation<String, String> uuidOk = equals3(oldReport.getFileUuid(), newReportFile.getFileUuid(), "old report UUID != new report UUID");
        return Validation.combine(sizeOk, uuidOk).ap((v1, v2) -> true);
    }
}
