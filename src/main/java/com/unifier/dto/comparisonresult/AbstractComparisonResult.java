package com.unifier.dto.comparisonresult;

import io.vavr.Function2;

public abstract class AbstractComparisonResult<T> implements ComparisonResult<T> {

    private final T originalValue;
    private final T migratedValue;
    protected String failureReason;

    public AbstractComparisonResult(T originalValue, T migratedValue) {
        this.originalValue = originalValue;
        this.migratedValue = migratedValue;
    }


    @Override
    public T getOriginalValue() {
        return originalValue;
    }

    @Override
    public T getMigratedValue() {
        return migratedValue;
    }

    public Boolean isEquals() {
        if (originalValue != null && migratedValue != null) {
            Function2<T, T, Boolean> predicate = getIsEqualsPredicate();
            if (predicate != null)
                return predicate.apply(originalValue, migratedValue);
            else
                return false;
        } else
            return false;
    }

    protected abstract Function2<T, T, Boolean> getIsEqualsPredicate();

    public String getFailureReason() {
        return failureReason;
    }
}
