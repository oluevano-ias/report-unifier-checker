package com.unifier.migrationreader;

public class ScheduleMigrationResult implements MigrationResult {

    String line;
    String scheduleResult;
    String reportsResult;

    private ScheduleMigrationResult(String line) {
        this.line = line;
    }

    public static ScheduleMigrationResult noResult() {
        return new ScheduleMigrationResult("No results");
    }

    public static ScheduleMigrationResult create(String line) {
        return new ScheduleMigrationResult(line);
    }

    public void setScheduleResult(String scheduleResult) {
        this.scheduleResult = scheduleResult;
    }

    public void setReportsResult(String reportsResult) {
        this.reportsResult = reportsResult;
    }

    public String getResultAsString(){
        String ln = System.lineSeparator() + "\t";
        return String.format("%s%sscheduleResult:%s%sreportsResult:%s%s",
                this.line, ln, scheduleResult, ln, reportsResult, ln);
    }
}
