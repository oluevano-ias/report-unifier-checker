package com.unifier.dto.comparisonresult;

public interface ComparisonResult<T> {

    T getOriginalValue();

    T getMigratedValue();

    Boolean isEquals();
}
