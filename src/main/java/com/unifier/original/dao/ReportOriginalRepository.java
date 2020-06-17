package com.unifier.original.dao;

import com.unifier.original.models.Report;
import com.unifier.original.models.ReportProduct;
import com.unifier.original.models.ReportStatus;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by bplies on 9/7/17.
 */
public interface ReportOriginalRepository extends CrudRepository<Report, Long>, ReportOriginalRepositoryCustom<Report>, JpaSpecificationExecutor<Report> {
    List<Report> findByTeamIdAndReportProductAndDateDeletedIsNullOrderByDateCompletedDesc(Long teamId, ReportProduct reportProduct);

    List<Report> findByIdInAndReportProductAndDateDeletedIsNullOrderByDateCompletedDesc(List<Long> reportIds, ReportProduct reportProduct);

    default List<Report> findByIdInAndReportProductAndStatusIsNotCompleteOrderByCreatedOnAsc(List<Long> reportIds, ReportProduct reportProduct) {
        return findByIdInAndReportProductAndIsNotReportStatusOrderByCreatedOnAsc(reportIds, reportProduct, ReportStatus.COMPLETE);
    }

    @Query("FROM Report WHERE id IN :reportIds AND reportProduct = :reportProduct AND status != :reportStatus ORDER By createdOn")
    List<Report> findByIdInAndReportProductAndIsNotReportStatusOrderByCreatedOnAsc(@Param("reportIds") List<Long> reportIds,
                                                                                   @Param("reportProduct") ReportProduct reportProduct,
                                                                                   @Param("reportStatus") ReportStatus reportStatus);

    Report findByIdAndReportProduct(Long id, ReportProduct reportProduct);

    List<Report> findReportsByReportScheduleIdEquals(Long scheduleId);
}
