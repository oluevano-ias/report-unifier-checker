package com.unifier.migrated.dao;

import com.unifier.migrated.models.ReportProduct;
import com.unifier.migrated.models.ReportTemplate;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by bplies on 12/15/17.
 */
public interface ReportMigratedScheduleRepository extends CrudRepository<ReportTemplate, Long>, JpaSpecificationExecutor<ReportTemplate> {
    List<ReportTemplate> findByTeamIdAndReportProductAndDateDeletedIsNullOrderByCreatedOnDesc(Long teamId, ReportProduct reportProduct);

    List<ReportTemplate> findByIdInAndReportProductAndDateDeletedIsNullOrderByCreatedOnDesc(List<Long> reportScheduleIds, ReportProduct reportProduct);

    @Query("FROM ReportTemplate WHERE reportProduct = :reportProduct and nextRunAfter <= CURRENT_TIMESTAMP() ORDER BY nextRunAfter")
    List<ReportTemplate> getSchedulesToRun(@Param("reportProduct") ReportProduct reportProduct, Pageable page);

}
