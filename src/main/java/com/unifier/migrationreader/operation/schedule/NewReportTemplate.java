package com.unifier.migrationreader.operation.schedule;

import com.unifier.migrated.models.ReportInstance;
import com.unifier.migrated.models.ReportTemplate;
import com.unifier.migrationreader.ScheduleMigrationResult;
import com.unifier.original.models.Report;
import com.unifier.original.models.ReportSchedule;
import com.unifier.util.MigrationUtil;
import com.unifier.util.ValidationUtil;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static com.unifier.util.ValidationUtil.OK;

@Component
class NewReportTemplate extends ScheduleMigrationOperation {

    public ScheduleMigrationResult process(String line) {
        ScheduleMigrationResult smr = ScheduleMigrationResult.create(line);
        String[] fields = line.split(":");
        Long reportTemplateId = Long.parseLong(fields[1]);
        Long reportScheduleId = Long.parseLong(fields[2]);
        //Long newReportInstanceId = Long.parseLong(fields[3]);
        //Long oldReportId = Long.parseLong(fields[4]);
        Optional<ReportSchedule> reportSchedule = originalService.getReportScheduleById(reportScheduleId);
        Optional<ReportTemplate> reportTemplate = migratedService.getReportTemplateById(reportTemplateId);

        String scheduleResult = ValidationUtil.getResultAsString(MigrationUtil.compareReportSchedules(reportSchedule, reportTemplate));
        smr.setScheduleResult(scheduleResult);
        if (scheduleResult.equals(OK)) {
            List<Report> o1 = originalService.getReportsFromSchedule(reportScheduleId);
            List<ReportInstance> m1 = migratedService.getReportsFromSchedule(reportTemplateId);
            String reportResult = ValidationUtil.getResultAsString2(MigrationUtil.areReportsEqual(o1, m1));
            smr.setReportsResult(reportResult);
        }
        return smr;
    }
}
