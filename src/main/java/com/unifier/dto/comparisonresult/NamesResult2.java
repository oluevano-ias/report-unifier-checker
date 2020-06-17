package com.unifier.dto.comparisonresult;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class NamesResult2 {

    private List<String> originalNames;
    private MigratedSizeResult migratedResult;
    private boolean equalsResult;
    private short calculated = 0;

    public NamesResult2(List<String> originalNames, MigratedSizeResult migratedResult) {
        this.originalNames = originalNames;
        this.migratedResult = migratedResult;
    }

    public boolean isEquals() {
        if (calculated == 0) {
            HashSet<String> originals = new HashSet<>(originalNames);
            Set<String> migratedOnes = toSet(migratedResult);
            originals.removeAll(migratedOnes);
            boolean namesEqual = originals.size() == 0;
            if (namesEqual) {
                equalsResult = true;
            }
            calculated = 1;
        }
        return equalsResult;
    }

    private Set<String> toSet(MigratedSizeResult result) {
        List<SizeResultData> onDemandReports = result.getOnDemandReports();
        List<SizeResultData> scheduledReports = result.getScheduledReports();

        Map<String, List<SizeResultData>> onDemandReportData = result.getOnDemandReportData();
        Map<String, List<SizeResultData>> scheduledReportData = result.getScheduledReportData();

        HashSet<String> names = new HashSet<>();
        //Set<String> onDemandSet = onDemandReports.stream().map(demandReport -> demandReport.getName()).collect(Collectors.toSet());
        //Set<String> scheduledSet = scheduledReports.stream().map(scheduledReport -> scheduledReport.getName()).collect(Collectors.toSet());
        //names.addAll(onDemandSet);
        //names.addAll(scheduledSet);

        names.addAll(onDemandReportData.keySet());
        names.addAll(scheduledReportData.keySet());
        return names;
    }

    public String getFailureReason() {
        if (!equalsResult)
            return "Report names are not the same";
        else
            return null;
    }
}
