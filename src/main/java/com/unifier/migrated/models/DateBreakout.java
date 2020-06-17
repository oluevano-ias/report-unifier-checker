package com.unifier.migrated.models;

/**
 * Created by pgupta on 3/14/18.
 */
public enum DateBreakout {
    PERIOD("period"),
    BY_DAY("by day"),
    BY_WEEK("by week"),
    BY_MONTH("by month");

    private String format;

    DateBreakout(String format) {
        this.format = format;
    }

    public String getFormat() {
        return format;
    }
}