package com.unifier.migrationreader.operation.ondemand;

import com.unifier.migrated.models.ReportInstance;
import com.unifier.migrationreader.OnDemandMigrationResult;
import com.unifier.original.models.Report;
import com.unifier.util.MigrationUtil;
import com.unifier.util.ValidationUtil;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
class NewReportInstance extends OnDemandMigrationOperation {

    public OnDemandMigrationResult process(String line) {
        OnDemandMigrationResult omr = OnDemandMigrationResult.create(line);
        String[] fields = line.split(":");
        Long reportTemplateId = Long.parseLong(fields[1]);
        Long oldReportId = Long.parseLong(fields[2]);
        Long newReportId = Long.parseLong(fields[3]);

        Optional<Report> report = originalService.getReport(oldReportId);
        Optional<ReportInstance> reportInstance = migratedService.getReportInstance(newReportId);


        String reportResult = ValidationUtil.getResultAsString(MigrationUtil.compareReports(report, reportInstance));
        omr.setReportsResult(reportResult);
        return omr;
    }
}
