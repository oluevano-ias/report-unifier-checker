package com.unifier.migrationreader.operation.ondemand;

import com.unifier.migrationreader.OnDemandMigrationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OnDemandMigrationOperationFacade {

    @Autowired
    NewReportInstance newReportInstance;

    public OnDemandMigrationResult processLine(String line) {
        OnDemandMigrationResult result = OnDemandMigrationResult.noResult();
        String[] split = line.replaceFirst("\\t", "").split(":");
        switch (split[0]) {
            case "newReportInstance":
                result = newReportInstance.process(line);
                break;
        }
        return result;
    }
}
