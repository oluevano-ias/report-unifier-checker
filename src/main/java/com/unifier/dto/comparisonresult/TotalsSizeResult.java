package com.unifier.dto.comparisonresult;

import java.util.List;
import java.util.Map;

public class TotalsSizeResult {
    private int originalSize;
    private int migratedSize = -1;
    private MigratedSizeResult migratedResult;
    private short calculated = 0;
    private boolean equalsResult;

    public TotalsSizeResult(int originalSize, MigratedSizeResult migratedResult) {
        this.originalSize = originalSize;
        this.migratedResult = migratedResult;
    }

    public int getMigratedSize() {
        if (migratedSize == -1) {
            List<SizeResultData> onDemandReports = migratedResult.getOnDemandReports();
            List<SizeResultData> scheduledReports = migratedResult.getScheduledReports();

            Map<String, List<SizeResultData>> onDemandReportData = migratedResult.getOnDemandReportData();
            Map<String, List<SizeResultData>> scheduledReportData = migratedResult.getScheduledReportData();

            int totalOnDemandReports = calculateTotals(onDemandReportData); //calculateTotals(onDemandReports);
            int totalScheduledReports = calculateTotals(scheduledReportData); //calculateTotals(scheduledReports);
            migratedSize = totalOnDemandReports + totalScheduledReports;
        }
        return migratedSize;
    }

    private int calculateTotals(Map<String, List<SizeResultData>> data) {
        Integer total = data.values().stream()
                .flatMap(list -> list.stream().map(sizeResultData -> sizeResultData.getInstancesSize()))
                .reduce(0, (accumulator, currentSize) -> {
                    accumulator += currentSize;
                    return accumulator;
                });
        return total;
    }

    public int getOriginalSize() {
        return originalSize;
    }

    public MigratedSizeResult getMigratedResult() {
        return migratedResult;
    }

    public boolean isEquals() {
        if (calculated == 0) {
            equalsResult = originalSize == getMigratedSize();
            calculated = 1;
        }
        return equalsResult;
    }

    private int calculateTotals(List<SizeResultData> data) {
        int accumulator = 0;
        return data.stream()
                .map(sizeResultData -> sizeResultData.getInstancesSize())
                .reduce(accumulator, (total, currentSize) -> total += currentSize);
    }

    public String getFailureReason() {
        if (!equalsResult)
            return "Total reports size is not the same";
        else
            return null;
    }
}
