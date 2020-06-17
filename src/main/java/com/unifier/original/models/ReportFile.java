package com.unifier.original.models;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

/**
 * Created by bplies on 9/6/17.
 */
@Entity
@Table(name = "REPORT_FILE")
@EntityListeners(AuditingEntityListener.class)
public class ReportFile  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    //@NotNull
    private Long id;

    @Column(name = "CREATED_ON", columnDefinition = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    @CreatedDate
    private Date createdOn;

    @Column(name = "DATE_DELETED", columnDefinition = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateDeleted;

    @Column(name = "TYPE", nullable = false)
    @Enumerated(EnumType.STRING)
    private ReportFileType type;

    @Column(name = "LOCATION")
    private String location;

    @Column(name = "RECORDS")
    private Integer records;

    @Column(name = "SIZE")
    private BigInteger size;

    @ManyToOne(optional = false)
    @JoinColumn(name = "REPORT_ID", nullable = false, referencedColumnName = "ID")
    private Report report;

    @Column(name = "FILE_UUID")
    private String fileUuid;

    @Column(name = "FILE_UUID_HASH")
    private String fileUuidHash;

    @Column(name = "AUTHENTICATED_DOWNLOADS_COUNT")
    @PositiveOrZero
    private Integer authenticatedDownloadsCount = 0;

    @Column(name = "UNAUTHENTICATED_DOWNLOADS_COUNT")
    @PositiveOrZero
    private Integer unauthenticatedDownloadsCount = 0;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Date getDateDeleted() {
        return dateDeleted;
    }

    public void setDateDeleted(Date dateDeleted) {
        this.dateDeleted = dateDeleted;
    }

    public ReportFileType getType() {
        return type;
    }

    public void setType(ReportFileType type) {
        this.type = type;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getRecords() {
        return records;
    }

    public void setRecords(Integer records) {
        this.records = records;
    }

    public BigInteger getSize() {
        return size;
    }

    public void setSize(BigInteger size) {
        this.size = size;
    }

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }

    public String getFileUuid() {
        return fileUuid;
    }

    public void setFileUuid(String fileUuid) {
        this.fileUuid = fileUuid;
    }

    public String getFileUuidHash() {
        return fileUuidHash;
    }

    public void setFileUuidHash(String fileUuidHash) {
        this.fileUuidHash = fileUuidHash;
    }

    public Integer getAuthenticatedDownloadsCount() {
        return authenticatedDownloadsCount;
    }

    public void setAuthenticatedDownloadsCount(Integer authenticatedDownloadsCount) {
        this.authenticatedDownloadsCount = authenticatedDownloadsCount;
    }

    public Integer getUnauthenticatedDownloadsCount() {
        return unauthenticatedDownloadsCount;
    }

    public void setUnauthenticatedDownloadsCount(Integer unauthenticatedDownloadsCount) {
        this.unauthenticatedDownloadsCount = unauthenticatedDownloadsCount;
    }
}
