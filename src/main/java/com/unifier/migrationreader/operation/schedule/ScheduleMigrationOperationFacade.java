package com.unifier.migrationreader.operation.schedule;

import com.unifier.migrationreader.ScheduleMigrationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ScheduleMigrationOperationFacade {

    @Autowired
    NewReportTemplate newReportTemplate;

    @Autowired
    UpdateReportScheduleTeam updateReportScheduleTeam;

    @Autowired
    UpdateReportTeam updateReportTeam;


    public ScheduleMigrationResult processLine(String line) {
        ScheduleMigrationResult result = ScheduleMigrationResult.noResult();
        String[] split = line.replaceFirst("\\t", "").split(":");
        switch (split[0]) {
            case "newReportTemplate":
                result = newReportTemplate.process(line);
                break;
            case "updateReportScheduleTeam":
                result = updateReportScheduleTeam.process(line);
                break;
            case "updateReportTeam":
                result = updateReportTeam.process(line);
                break;
        }
        return result;
    }
}
