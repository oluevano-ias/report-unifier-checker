package com.unifier.service;

import ch.qos.logback.classic.Logger;
import com.unifier.migrationreader.MigrationFileReader;
import com.unifier.migrationreader.MigrationResult;
import com.unifier.migrationreader.operation.ondemand.OnDemandMigrationOperationFacade;
import com.unifier.migrationreader.operation.schedule.ScheduleMigrationOperationFacade;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

@Component
public class UnifierCheckerService {

    private static Logger logger = (Logger) LoggerFactory.getLogger(UnifierCheckerService.class);


    @Autowired
    MigrationFileReader migrationFileReader;

    @Autowired
    ScheduleMigrationOperationFacade scheduleMigrationOperationFacade;

    @Autowired
    OnDemandMigrationOperationFacade onDemandMigrationOperationFacade;

    public String checkSchedule() {
        String outFile = "/Users/yanoziel/dev/reportUnifierChecker/checkScheduleResult.txt";
        return checkMigrationData(outFile, migrationFileReader::getScheduleMigrationLines, scheduleMigrationOperationFacade::processLine);
        /*BufferedWriter bw = createBufferedWriter(outFile);
        try {
            Stream<String> scheduleMigrationLines = migrationFileReader.getScheduleMigrationLines();
            scheduleMigrationLines.map(scheduleMigrationOperationFacade::processLine).forEach(mr -> writeMigrationResult(mr, bw));
        } finally {
            closeBufferedWriter(bw);
        }
        return "ok";
        */
    }

    public String checkOnDemand() {
        String outFile = "/Users/yanoziel/dev/reportUnifierChecker/checkOnDemandResult.txt";
        return checkMigrationData(outFile, migrationFileReader::getOnDemandMigrationLines, onDemandMigrationOperationFacade::processLine);
        /*BufferedWriter bw = createBufferedWriter(outFile);
        try {
            Stream<String> onDemandMigrationLines = migrationFileReader.getOnDemandMigrationLines();
            onDemandMigrationLines.map(onDemandMigrationOperationFacade::processLine).forEach(mr -> writeMigrationResult(mr, bw));
        } finally {
            closeBufferedWriter(bw);
        }
        return "ok";*/
    }

    private String checkMigrationData(String outFile, Supplier<Stream<String>> supplier, Function<String, MigrationResult> consumer){
        BufferedWriter bw = createBufferedWriter(outFile);
        try {
            Stream<String> stream = supplier.get();
            stream.map(consumer).forEach(mr -> writeMigrationResult(mr, bw));
        } finally {
            closeBufferedWriter(bw);
        }
        return "ok";
    }

    private void closeBufferedWriter(BufferedWriter bw) {
        try {
            if (bw != null)
                bw.close();
        } catch (IOException ioe) {
            throw new RuntimeException("Failed to close buffered writer", ioe);
        }
    }

    private BufferedWriter createBufferedWriter(String outFile) {
        try {
            return new BufferedWriter(new FileWriter(new File(outFile)));
        } catch (IOException e) {
            throw new RuntimeException("Cant create out file!", e);
        }
    }

    private void writeMigrationResult(MigrationResult mr, BufferedWriter bw) {
        try {
            bw.write(mr.getResultAsString());
            bw.newLine();
            bw.flush();
        } catch (IOException ioe) {
            throw new RuntimeException("failed to write line to file", ioe);
        }
    }
}