package com.unifier.migrated.dao;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;

public class ReportMigratedRepositoryCustomImpl<T> implements ReportMigratedRepositoryCustom<T> {

    @PersistenceContext(unitName = "customReportsMigrated")
    @Qualifier("customReportsMigratedEntityManagerFactory")
    private EntityManager em;

    @Override
    @Transactional
    public void refresh(T entity) {
        em.refresh(entity);
    }

    public void lock(T entity, LockModeType lockMode) {
        em.lock(entity, lockMode);
    }

    public void clear() {
        em.clear();
    }
}
