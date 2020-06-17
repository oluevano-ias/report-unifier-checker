package com.unifier.dto.comparisonresult;

import io.vavr.Function2;
import io.vavr.Lazy;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class ReportTeamsResult extends AbstractComparisonResult<List<Map<String, Object>>> {

    Function2<List<Map<String, Object>>, List<Map<String, Object>>, Boolean> predicate = (originalValue, migratedValue) -> {
        if (originalValue.size() == migratedValue.size()) {
            boolean isEquals = false;
            for (int x = 0; x < originalValue.size(); x++) {
                Map<String, Object> originalMap = originalValue.get(x);
                Map<String, Object> migratedMap = migratedValue.get(x);
                String originalReportName = originalMap.get("reportName") == null ? "" : originalMap.get("reportName").toString();
                String migratedReportName = migratedMap.get("reportName") == null ? "" : migratedMap.get("reportName").toString();
                if (originalReportName.equals(migratedReportName) && !originalReportName.equals("")) {
                    List<Long> originalTeamIds = (List<Long>) originalMap.get("reportTeamIds");
                    List<Long> migratedTeamIds = (List<Long>) migratedMap.get("reportTeamIds");
                    HashSet<Long> originals = new HashSet<>(originalTeamIds);
                    HashSet<Long> migratedOnes = new HashSet<>(migratedTeamIds);
                    originals.removeAll(migratedOnes);
                    isEquals = originals.size() == 0;
                    if (!isEquals) {
                        failureReason = "At least one report does not have same team ids";
                        break;
                    }
                } else {
                    failureReason = "At least one report name is not the same";
                    break;
                }
            }
            return isEquals;
        } else {
            failureReason = "Report team ids are not the same size";
            return false;
        }
    };

    public ReportTeamsResult(List<Map<String, Object>> originalValue, List<Map<String, Object>> migratedValue) {
        super(originalValue, migratedValue);
        predicate = predicate.memoized();
    }

    @Override
    protected Function2<List<Map<String, Object>>, List<Map<String, Object>>, Boolean> getIsEqualsPredicate() {
        return predicate;
    }

}
