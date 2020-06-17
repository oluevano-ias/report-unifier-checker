package com.unifier.dto.comparisonresult;

import io.vavr.Function2;


public class SizeResult extends AbstractComparisonResult<Integer> {
    Function2<Integer, Integer, Boolean> predicate = (originalValue, migratedValue) -> {
        if (!originalValue.equals(migratedValue)) {
            failureReason = "Total reports size is not the same";
            return false;
        } else
            return true;
    };

    public SizeResult(Integer originalValue, Integer migratedValue) {
        super(originalValue, migratedValue);
        predicate = predicate.memoized();
    }

    @Override
    protected Function2<Integer, Integer, Boolean> getIsEqualsPredicate() {
        return predicate;
    }
}
