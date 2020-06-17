package com.unifier.original.models;

/**
 * Created by bplies on 9/6/17.
 */
public enum ReportPeriod {
    YESTERDAY("Yesterday"),
    TWO_DAYS_AGO("Two Days Ago"),
    THIS_WEEK("This Week"),
    LAST_WEEK("Last Week"),
    THIS_MONTH("This Month"),
    LAST_MONTH("Last Month"),
    THIS_QUARTER("This Quarter"),
    LAST_QUARTER("Last Quarter"),
    LAST_7_DAYS("Last 7 Days"),
    LAST_30_DAYS("Last 30 Days"),
    CUSTOM("Custom");

    private String label;

    ReportPeriod(String label){
        this.label = label;
    }

    public String getLabel(){
        return this.label;
    }
}
