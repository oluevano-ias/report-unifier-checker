package com.unifier.migrationreader.operation.schedule;

import com.unifier.migrationreader.ScheduleMigrationResult;
import org.springframework.stereotype.Component;

@Component
class UpdateReportScheduleTeam extends ScheduleMigrationOperation {

    public ScheduleMigrationResult process(String line) {
        /*String[] fields = line.split(":");
        Long reportTemplateId = Long.parseLong(fields[1]);
        Long reportScheduleId = Long.parseLong(fields[2]);
        Long reportScheduleTeamUpdateCount = Long.parseLong(fields[3]);

        Optional<ReportSchedule> o1 = originalService.getReportScheduleById(reportScheduleId);
        Optional<ReportTemplate> m1 = migratedService.getReportTemplateById(reportTemplateId);

        String result = ValidationUtil.getResultAsString(MigrationUtil.isReportScheduleEqual(o1, m1));
        return MigrationResult.create(line, result);
         */
        return ScheduleMigrationResult.create(line);
    }
}
