package com.unifier.migrationreader.operation.schedule;

import com.unifier.migrated.service.MigratedService;
import com.unifier.migrationreader.ScheduleMigrationResult;
import com.unifier.original.service.OriginalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
abstract class ScheduleMigrationOperation {

    @Autowired
    protected MigratedService migratedService;

    @Autowired
    protected OriginalService originalService;

    abstract public ScheduleMigrationResult process(String line);
}
