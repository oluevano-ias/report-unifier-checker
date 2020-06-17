package com.unifier.dto.comparisonresult;

import java.util.List;
import java.util.Map;


public class MigratedSizeResult {
    private List<SizeResultData> onDemandReports;
    private List<SizeResultData> scheduledReports;
    private Map<String, List<SizeResultData>> onDemandReportData;
    private Map<String, List<SizeResultData>> scheduledReportData;

    public MigratedSizeResult(List<SizeResultData> onDemandReports, List<SizeResultData> scheduledReports) {
        this.onDemandReports = onDemandReports;
        this.scheduledReports = scheduledReports;
    }
    public MigratedSizeResult(Map<String, List<SizeResultData>> onDemandReportData, Map<String, List<SizeResultData>> scheduledReportData) {
        this.onDemandReportData = onDemandReportData;
        this.scheduledReportData = scheduledReportData;
    }

    public Map<String, List<SizeResultData>> getOnDemandReportData() {
        return onDemandReportData;
    }

    public Map<String, List<SizeResultData>> getScheduledReportData() {
        return scheduledReportData;
    }

    public List<SizeResultData> getOnDemandReports() {
        return onDemandReports;
    }

    public List<SizeResultData> getScheduledReports() {
        return scheduledReports;
    }
}
