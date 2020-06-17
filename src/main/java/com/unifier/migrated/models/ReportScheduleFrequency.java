package com.unifier.migrated.models;

/**
 * Created by bplies on 12/14/17.
 */
public enum ReportScheduleFrequency {
    ONE_TIME("One-time"),
    ONCE("Once"),
    DAILY("Daily"),
    WEEKLY("Weekly"),
    MONTHLY("Monthly");

    private String label;

    ReportScheduleFrequency(String label){
        this.label = label;
    }

    public String getLabel(){
        return this.label;
    }
}
