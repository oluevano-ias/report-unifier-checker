package com.unifier.migrated.dao;

import javax.persistence.LockModeType;

public interface ReportMigratedRepositoryCustom<T> {

    void refresh(T entity);

    void lock(T entity, LockModeType lockMode);

    void clear();
}
