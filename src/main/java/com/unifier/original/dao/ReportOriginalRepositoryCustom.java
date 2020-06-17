package com.unifier.original.dao;

import javax.persistence.LockModeType;

public interface ReportOriginalRepositoryCustom<T> {

    void refresh(T entity);

    void lock(T entity, LockModeType lockMode);

    void clear();
}
