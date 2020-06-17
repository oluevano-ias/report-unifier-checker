package com.unifier.migrated.dao;

import com.unifier.migrated.models.ReportFile;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ReportFileMigratedRepository extends CrudRepository<ReportFile, Long>, ReportMigratedRepositoryCustom<ReportFile> {

    ReportFile findByFileUuid(String fileUuid);

    @Transactional
    @Modifying
    @Query("UPDATE ReportFile reportFile SET reportFile.authenticatedDownloadsCount = reportFile.authenticatedDownloadsCount + 1 where reportFile.id = :id")
    void increaseReportFileAuthenticatedDownloadCount(@Param("id") Long id);

    @Transactional
    @Modifying
    @Query("UPDATE ReportFile reportFile SET reportFile.unauthenticatedDownloadsCount = reportFile.unauthenticatedDownloadsCount + 1 where reportFile.fileUuid = :fileUuId")
    void increaseReportFileUnauthenticatedDownloadCount(@Param("fileUuId") String fileUuId);
}
