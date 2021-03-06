package com.unifier.original.dao;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;

public class ReportOriginalRepositoryCustomImpl<T> implements ReportOriginalRepositoryCustom<T> {

    @PersistenceContext(unitName = "customReportsOriginal")
    @Qualifier("customReportsOriginalEntityManagerFactory")
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
