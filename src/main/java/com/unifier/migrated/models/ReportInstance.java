package com.unifier.migrated.models;

import com.unifier.migrated.models.converters.ReportPropertiesConverter;
import com.unifier.migrated.models.converters.StringSetConverter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "REPORT_INSTANCE")
@EntityListeners(AuditingEntityListener.class)
@NamedEntityGraph(name = "report-instance-entity-graph",
        attributeNodes = {
                //@NamedAttributeNode("files"),
                @NamedAttributeNode("reportTeams"),
                @NamedAttributeNode("reportTemplate")
        }
)
public class ReportInstance implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "VERSION", nullable = false)
    @Version
    @NotNull
    private Long version = 0L;

    @Column(name = "REPORT_NAME", nullable = false)
    @NotNull
    private String name;

    @Column(name = "CREATED_ON", columnDefinition = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createdOn;

    @Column(name = "LAST_MODIFIED", columnDefinition = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date lastModified;

    @Column(name = "DATE_DELETED", columnDefinition = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateDeleted;

    @Column(name = "STATUS", nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull
    private ReportStatus status = ReportStatus.PENDING;

    @Column(name = "TYPE", nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull
    private ReportType type;

    @Column(name = "TEAM_ID", nullable = false)
    @NotNull
    private Long teamId;

    @Column(name = "CREATED_BY", nullable = true)
    private Long createdByUserId;

    @NotNull
    @Column(name = "CREATED_BY_USERNAME", nullable = false)
    private String createdByUsername;

    @Column(name = "REPORT_PERIOD", nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull
    private ReportPeriod period;

    @Column(name = "REPORT_START", columnDefinition = "DATE", nullable = false)
    @Temporal(TemporalType.DATE)
    @NotNull
    private Date reportStart;

    @Column(name = "REPORT_END", columnDefinition = "DATE", nullable = false)
    @Temporal(TemporalType.DATE)
    @NotNull
    private Date reportEnd;

    //    JSON Object describing the report configuration
    @Column(name = "REPORT_PROPERTIES", length = Integer.MAX_VALUE, nullable = false) // Must avoid default of 255
    @NotNull
    @Size(min = 1)
    @Convert(converter = ReportPropertiesConverter.class)
    private List<ReportProperties> reportProperties;

    @Column(name = "REPORT_PROPERTIES_VERSION", nullable = false)
    @NotNull
    private String reportPropertiesVersion = "2.0"; // Use current version as default

    @Column(name = "TIME_IN_QUEUE_MS")
    private Long timeInQueueMillis;

    @Column(name = "DATA_PROCESSING_STARTED", columnDefinition = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataProcessingStarted;

    @Column(name = "TIME_DATA_PROCESSING_MS")
    private Long timeDataProcessingeMillis;

    @Column(name = "POST_PROCESSING_STARTED", columnDefinition = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date postProcessingStarted;

    @Column(name = "TIME_POST_PROCESSING_MS")
    private Long timePostProcessingeMillis;

    @Column(name = "DATE_READY", columnDefinition = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateReady;

    @Column(name = "DATE_COMPLETED", columnDefinition = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCompleted;

    @Column(name = "ERROR_CODE")
    private Integer errorCode;

    @Column(name = "ERROR_DESC")
    private String errorDescription;

    /*@OneToMany(mappedBy = "report", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<ReportFile> files = new HashSet<>();
    */
    // OneToOne should not have cascade type defined to avoid errors when saving report Template and Instance relationship
    @JoinColumn(name = "REPORT_TEMPLATE_ID")
    @OneToOne
    private ReportTemplate reportTemplate;

    @Column(name = "WORKER_RECEIVES_COUNT", nullable = false)
    private Integer workerReceivesCount = 0;

    @Column(name = "READY_RECEIVES_COUNT", nullable = false)
    private Integer readyReceivesCount = 0;

    @Column(name = "FREQUENCY")
    @Enumerated(EnumType.STRING)
    private ReportScheduleFrequency frequency;

    @Column(name = "EMAIL_NOTIFICATIONS", length = 65535, nullable = false)
    // Must avoid default of 255, TEXT is up to 64KB
    @Convert(converter = StringSetConverter.class)
    private Set<String> emailNotifications;

    @Column(name = "REPORT_PRODUCT", nullable = false)
    @Enumerated(EnumType.STRING)
    private ReportProduct reportProduct;

    @Column(name="FILE_TYPE")
    @Enumerated(EnumType.STRING)
    private FileType fileType;

    @Column(name = "IS_AUTO")
    private boolean isAutoReport;

    @Column(name = "EMAIL_CAMPAIGN_CONTACTS")
    private boolean isEmailCampaignContacts;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "reportInstance", fetch = FetchType.EAGER)
    private Set<ReportTeam> reportTeams = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public Date getDateDeleted() {
        return dateDeleted;
    }

    public void setDateDeleted(Date dateDeleted) {
        this.dateDeleted = dateDeleted;
    }

    public ReportStatus getStatus() {
        return status;
    }

    public void setStatus(ReportStatus status) {
        this.status = status;
    }

    public ReportType getType() {
        return type;
    }

    public void setType(ReportType type) {
        this.type = type;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public Long getCreatedByUserId() {
        return createdByUserId;
    }

    public void setCreatedByUserId(Long createdByUserId) {
        this.createdByUserId = createdByUserId;
    }

    public String getCreatedByUsername() {
        return createdByUsername;
    }

    public void setCreatedByUsername(String createdByUsername) {
        this.createdByUsername = createdByUsername;
    }

    public ReportPeriod getPeriod() {
        return period;
    }

    public void setPeriod(ReportPeriod period) {
        this.period = period;
    }

    public Date getReportStart() {
        return reportStart;
    }

    public void setReportStart(Date reportStart) {
        this.reportStart = reportStart;
    }

    public Date getReportEnd() {
        return reportEnd;
    }

    public void setReportEnd(Date reportEnd) {
        this.reportEnd = reportEnd;
    }

    public List<ReportProperties> getReportProperties() {
        return reportProperties;
    }

    public void setReportProperties(List<ReportProperties> reportProperties) {
        this.reportProperties = reportProperties;
    }

    public String getReportPropertiesVersion() {
        return reportPropertiesVersion;
    }

    public void setReportPropertiesVersion(String reportPropertiesVersion) {
        this.reportPropertiesVersion = reportPropertiesVersion;
    }

    public Long getTimeInQueueMillis() {
        return timeInQueueMillis;
    }

    public void setTimeInQueueMillis(Long timeInQueueMillis) {
        this.timeInQueueMillis = timeInQueueMillis;
    }

    public Date getDataProcessingStarted() {
        return dataProcessingStarted;
    }

    public void setDataProcessingStarted(Date dataProcessingStarted) {
        this.dataProcessingStarted = dataProcessingStarted;
    }

    public Long getTimeDataProcessingeMillis() {
        return timeDataProcessingeMillis;
    }

    public void setTimeDataProcessingeMillis(Long timeDataProcessingeMillis) {
        this.timeDataProcessingeMillis = timeDataProcessingeMillis;
    }

    public Date getPostProcessingStarted() {
        return postProcessingStarted;
    }

    public void setPostProcessingStarted(Date postProcessingStarted) {
        this.postProcessingStarted = postProcessingStarted;
    }

    public Long getTimePostProcessingeMillis() {
        return timePostProcessingeMillis;
    }

    public void setTimePostProcessingeMillis(Long timePostProcessingeMillis) {
        this.timePostProcessingeMillis = timePostProcessingeMillis;
    }

    public Date getDateReady() {
        return dateReady;
    }

    public void setDateReady(Date dateReady) {
        this.dateReady = dateReady;
    }

    public Date getDateCompleted() {
        return dateCompleted;
    }

    public void setDateCompleted(Date dateCompleted) {
        this.dateCompleted = dateCompleted;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    /*public Set<ReportFile> getFiles() {
        return files;
    }

    public void setFiles(Set<ReportFile> files) {
        this.files = files;
    }*/

    public ReportTemplate getReportTemplate() {
        return reportTemplate;
    }

    public void setReportTemplate(ReportTemplate reportTemplate) {
        this.reportTemplate = reportTemplate;
    }

    public Integer getWorkerReceivesCount() {
        return workerReceivesCount;
    }

    // PUB-2166: workerReceivesCount must only be the maximum of any value ever received. 2 set(1) = 2
    public void setWorkerReceivesCount(Integer workerReceivesCount) {
        this.workerReceivesCount = Math.max(this.workerReceivesCount, workerReceivesCount);
    }

    public Integer getReadyReceivesCount() {
        return readyReceivesCount;
    }

    public void setReadyReceivesCount(Integer readyReceivesCount) {
        this.readyReceivesCount = readyReceivesCount;
    }

    public void incrementReadyReceives() {
        this.readyReceivesCount++;
    }

    public ReportScheduleFrequency getFrequency() {
        return frequency;
    }

    public void setFrequency(ReportScheduleFrequency frequency) {
        this.frequency = frequency;
    }

    public Set<String> getEmailNotifications() {
        return emailNotifications;
    }

    public void setEmailNotifications(Set<String> emailNotifications) {
        this.emailNotifications = emailNotifications;
    }

    public ReportProduct getReportProduct() {
        return reportProduct;
    }

    public void setReportProduct(ReportProduct reportProduct) {
        this.reportProduct = reportProduct;
    }

    public FileType getFileType() {
        return fileType;
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }

    public boolean isAutoReport() {
        return isAutoReport;
    }

    public void setAutoReport(boolean autoReport) {
        isAutoReport = autoReport;
    }

    public boolean isEmailCampaignContacts() {
        return isEmailCampaignContacts;
    }

    public void setEmailCampaignContacts(boolean isEmailCampaignContacts) {
        this.isEmailCampaignContacts = isEmailCampaignContacts;
    }

    public Set<ReportTeam> getReportTeams() {
        return reportTeams;
    }

    public void setReportTeams(Set<ReportTeam> reportTeams) {
        this.reportTeams = reportTeams;
    }

    @Override
    public String toString() {
        return "ReportInstance{" +
                "id=" + id +
                ", version=" + version +
                ", name='" + name + '\'' +
                ", createdOn=" + createdOn +
                ", lastModified=" + lastModified +
                ", dateDeleted=" + dateDeleted +
                ", status=" + status +
                ", type=" + type +
                ", teamId=" + teamId +
                ", createdByUserId=" + createdByUserId +
                ", createdByUsername='" + createdByUsername + '\'' +
                ", period=" + period +
                ", reportStart=" + reportStart +
                ", reportEnd=" + reportEnd +
                ", reportProperties=" + reportProperties +
                ", reportPropertiesVersion='" + reportPropertiesVersion + '\'' +
                ", timeInQueueMillis=" + timeInQueueMillis +
                ", dataProcessingStarted=" + dataProcessingStarted +
                ", timeDataProcessingeMillis=" + timeDataProcessingeMillis +
                ", postProcessingStarted=" + postProcessingStarted +
                ", timePostProcessingeMillis=" + timePostProcessingeMillis +
                ", dateReady=" + dateReady +
                ", dateCompleted=" + dateCompleted +
                ", errorCode=" + errorCode +
                ", errorDescription='" + errorDescription + '\'' +
                //", files=" + files +
                ", reportTemplate=" + reportTemplate +
                ", workerReceivesCount=" + workerReceivesCount +
                ", readyReceivesCount=" + readyReceivesCount +
                ", frequency=" + frequency +
                ", emailNotifications=" + emailNotifications +
                ", reportProduct=" + reportProduct +
                ", fileType=" + fileType +
                ", ReportTeams=" + reportTeams +
                '}';
    }
}
