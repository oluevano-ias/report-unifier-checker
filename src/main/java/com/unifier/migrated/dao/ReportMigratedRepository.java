package com.unifier.migrated.dao;

import com.unifier.migrated.models.ReportInstance;
import com.unifier.migrated.models.ReportProduct;
import com.unifier.migrated.models.ReportStatus;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by bplies on 9/7/17.
 */
public interface ReportMigratedRepository extends CrudRepository<ReportInstance, Long>,
        ReportMigratedRepositoryCustom<ReportInstance>, JpaSpecificationExecutor<ReportInstance> {
    List<ReportInstance> findByTeamIdAndReportProductAndDateDeletedIsNullOrderByDateCompletedDesc(Long teamId, ReportProduct reportProduct);

    List<ReportInstance> findByIdInAndReportProductAndDateDeletedIsNullOrderByDateCompletedDesc(List<Long> reportIds, ReportProduct reportProduct);

    default List<ReportInstance> findByIdInAndReportProductAndStatusIsNotCompleteOrderByCreatedOnAsc(List<Long> reportIds, ReportProduct reportProduct) {
        return findByIdInAndReportProductAndIsNotReportStatusOrderByCreatedOnAsc(reportIds, reportProduct, ReportStatus.COMPLETE);
    }

    @Query("FROM ReportInstance WHERE id IN :reportIds AND reportProduct = :reportProduct AND status != :reportStatus ORDER By createdOn")
    List<ReportInstance> findByIdInAndReportProductAndIsNotReportStatusOrderByCreatedOnAsc(@Param("reportIds") List<Long> reportIds,
                                                                                           @Param("reportProduct") ReportProduct reportProduct,
                                                                                           @Param("reportStatus") ReportStatus reportStatus);

    ReportInstance findByIdAndReportProduct(Long id, ReportProduct reportProduct);

    List<ReportInstance> findReportInstancesByReportTemplateIdEquals(Long templateId);

    List<ReportInstance> findReportInstancesByReportTemplateIdEqualsAndDateDeletedIsNull(Long templateId);
}
