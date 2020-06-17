package com.unifier.migrationreader;

public class OnDemandMigrationResult implements MigrationResult {

    String line;
    String reportsResult;

    private OnDemandMigrationResult(String line) {
        this.line = line;
    }

    public static OnDemandMigrationResult noResult() {
        return new OnDemandMigrationResult("No results");
    }

    public static OnDemandMigrationResult create(String line) {
        return new OnDemandMigrationResult(line);
    }

    public void setReportsResult(String reportsResult) {
        this.reportsResult = reportsResult;
    }

    public String getResultAsString(){
        String ln = System.lineSeparator() + "\t";
        return String.format("%s:%sreportsResult:%s%s",
                this.line,ln, reportsResult, ln);
    }
}
