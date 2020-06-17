package com.unifier.migrationreader.operation.schedule;

import com.unifier.migrationreader.ScheduleMigrationResult;
import org.springframework.stereotype.Component;

@Component
class UpdateReportTeam extends ScheduleMigrationOperation {

    public ScheduleMigrationResult process(String line) {
        return ScheduleMigrationResult.create(line);
    }
}
