package com.unifier.dto.comparisonresult;

import io.vavr.Function2;

import java.util.HashSet;
import java.util.List;

public class NamesResult extends AbstractComparisonResult<List<String>> {

    Function2<List<String>, List<String>, Boolean> predicate = (originalValue, migratedValue) -> {
        HashSet<String> originals = new HashSet<>(originalValue);
        HashSet<String> migratedOnes = new HashSet<>(migratedValue);
        originals.removeAll(migratedOnes);
        boolean namesEqual = originals.size() == 0;
        if (!namesEqual) {
            failureReason = "Report names are not the same";
            return false;
        } else
            return true;
    };

    public NamesResult(List<String> originalValue, List<String> migratedValue) {
        super(originalValue, migratedValue);
        predicate = predicate.memoized();
    }

    @Override
    protected Function2<List<String>, List<String>, Boolean> getIsEqualsPredicate() {
        return predicate;
    }
}
