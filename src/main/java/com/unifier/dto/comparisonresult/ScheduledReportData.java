package com.unifier.dto.comparisonresult;

public class ScheduledReportData {

    private String name;
    private Long id;
    private int instancesSize;

    public ScheduledReportData(String name, Long id, int instancesSize) {
        this.name = name;
        this.id = id;
        this.instancesSize = instancesSize;
    }

    public int getInstancesSize() {
        return instancesSize;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }
}
