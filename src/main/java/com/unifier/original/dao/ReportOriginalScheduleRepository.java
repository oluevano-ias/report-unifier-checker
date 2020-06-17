package com.unifier.original.dao;

import com.unifier.original.models.ReportProduct;
import com.unifier.original.models.ReportSchedule;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by bplies on 12/15/17.
 */
public interface ReportOriginalScheduleRepository extends CrudRepository<ReportSchedule, Long>, JpaSpecificationExecutor<ReportSchedule> {
    List<ReportSchedule> findByTeamIdAndReportProductAndDateDeletedIsNullOrderByCreatedOnDesc(Long teamId, ReportProduct reportProduct);

    List<ReportSchedule> findByIdInAndReportProductAndDateDeletedIsNullOrderByCreatedOnDesc(List<Long> reportScheduleIds, ReportProduct reportProduct);

    @Query("FROM ReportSchedule WHERE reportProduct = :reportProduct and nextRunAfter <= CURRENT_TIMESTAMP() ORDER BY nextRunAfter")
    List<ReportSchedule> getSchedulesToRun(@Param("reportProduct") ReportProduct reportProduct, Pageable page);

}
