package com.unifier.migrationreader.operation.ondemand;

import com.unifier.migrated.service.MigratedService;
import com.unifier.migrationreader.OnDemandMigrationResult;
import com.unifier.migrationreader.ScheduleMigrationResult;
import com.unifier.original.service.OriginalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
abstract class OnDemandMigrationOperation {

    @Autowired
    protected MigratedService migratedService;

    @Autowired
    protected OriginalService originalService;

    abstract public OnDemandMigrationResult process(String line);
}
