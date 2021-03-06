package com.unifier.original.models;

import com.unifier.original.models.converters.ReportPropertiesConverter;
import com.unifier.original.models.converters.StringSetConverter;
import org.hibernate.annotations.Formula;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by bplies on 12/14/17.
 */
@Entity
@Table(name = "REPORT_SCHEDULE")
@EntityListeners(AuditingEntityListener.class)
@NamedEntityGraph(name = "report-schedule-entity-graph",
        attributeNodes = {
                @NamedAttributeNode("reportScheduleTeams"),
                @NamedAttributeNode("schedulePlan"),
                @NamedAttributeNode("reports")
        }
)
public class ReportSchedule implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    //@NotNull
    private Long id;

    @Column(name = "VERSION", nullable = false)
    @Version
    @NotNull
    private Long version = 0L;


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


    @Column(name = "TEAM_ID", nullable = false)
    @NotNull
    private Long teamId;

    @Column(name = "PRODUCT", nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull
    private ReportProduct reportProduct;

    @Column(name = "REPORT_NAME", nullable = false)
    @NotNull
    private String name;

    @Column(name = "FREQUENCY", nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull
    private ReportScheduleFrequency frequency;

    @Column(name = "SCHEDULE_START", columnDefinition = "DATE", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date scheduleStart;

    @Column(name = "SCHEDULE_END", columnDefinition = "DATE", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date scheduleEnd;

    @JoinColumn(name = "SCHEDULE_PLAN_ID")
    @OneToOne(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private SchedulePlan schedulePlan;

    @Column(name = "REPORT_PERIOD", nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull
    private ReportPeriod period;

    @Column(name = "CUSTOM_REPORT_START", columnDefinition = "DATE")
    @Temporal(TemporalType.DATE)
    private Date customReportStart;

    @Column(name = "CUSTOM_REPORT_END", columnDefinition = "DATE")
    @Temporal(TemporalType.DATE)
    private Date customReportEnd;

    //    JSON Object describing the report configuration
    @Column(name = "REPORT_PROPERTIES", length = Integer.MAX_VALUE, nullable = false) // Must avoid default of 255
    @NotNull
    @Size(min = 1)
    @Convert(converter = ReportPropertiesConverter.class)
    private List<ReportProperties> reportProperties;

    @Column(name = "REPORT_PROPERTIES_VERSION", nullable = false)
    @NotNull
    private String reportPropertiesVersion = "1.0"; // Use current version as default

    @Column(name = "CREATED_BY", nullable = true)
    private Long createdByUserId;

    @Column(name = "CREATED_BY_USERNAME", nullable = false)
    @NotNull
    private String createdByUsername;

    @OneToMany(mappedBy = "reportSchedule")//, fetch = FetchType.EAGER)
    @OrderBy("createdOn")
    private List<Report> reports = new ArrayList<>();

    @Column(name = "NEXT_RUN_AFTER", columnDefinition = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date nextRunAfter;

    @Formula("(SELECT MAX(r.DATE_COMPLETED) FROM REPORT r WHERE r.REPORT_SCHEDULE_ID = id)")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastRun;

    @Column(name = "EMAIL_NOTIFICATIONS", length = 65535, nullable = false)
    // Must avoid default of 255, TEXT is up to 64KB
    @Convert(converter = StringSetConverter.class)
    private Set<String> emailNotifications;

    @Column(name = "FILE_TYPE")
    @Enumerated(EnumType.STRING)
    private FileType fileType;

    @Column(name = "IS_AUTO")
    private boolean isAutoReport;

    @Column(name = "EMAIL_CAMPAIGN_CONTACTS")
    private boolean isEmailCampaignContacts;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "reportSchedule", fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<ReportScheduleTeam> reportScheduleTeams = new HashSet<>();

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

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public ReportProduct getReportProduct() {
        return reportProduct;
    }

    public void setReportProduct(ReportProduct reportProduct) {
        this.reportProduct = reportProduct;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ReportScheduleFrequency getFrequency() {
        return frequency;
    }

    public void setFrequency(ReportScheduleFrequency frequency) {
        this.frequency = frequency;
    }

    public Date getScheduleStart() {
        return scheduleStart;
    }

    public void setScheduleStart(Date scheduleStart) {
        this.scheduleStart = scheduleStart;
    }

    public Date getScheduleEnd() {
        return scheduleEnd;
    }

    public void setScheduleEnd(Date scheduleEnd) {
        this.scheduleEnd = scheduleEnd;
    }

    public SchedulePlan getSchedulePlan() {
        return schedulePlan;
    }

    public void setSchedulePlan(SchedulePlan schedulePlan) {
        this.schedulePlan = schedulePlan;
    }

    public ReportPeriod getPeriod() {
        return period;
    }

    public void setPeriod(ReportPeriod period) {
        this.period = period;
    }

    public Date getCustomReportStart() {
        return customReportStart;
    }

    public void setCustomReportStart(Date customReportStart) {
        this.customReportStart = customReportStart;
    }

    public Date getCustomReportEnd() {
        return customReportEnd;
    }

    public void setCustomReportEnd(Date customReportEnd) {
        this.customReportEnd = customReportEnd;
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

    public List<Report> getReports() {
        return reports;
    }

    public void addReport(Report report) {
        this.reports.add(report);
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

    public Date getNextRunAfter() {
        return nextRunAfter;
    }

    public void setNextRunAfter(Date nextRunAfter) {
        this.nextRunAfter = nextRunAfter;
    }

    public Date getLastRun() {
        return lastRun;
    }

    public void setLastRun(Date lastRun) {
        this.lastRun = lastRun;
    }

    public Set<String> getEmailNotifications() {
        return emailNotifications;
    }

    public void setEmailNotifications(Set<String> emailNotifications) {
        this.emailNotifications = emailNotifications;
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

    public Set<ReportScheduleTeam> getReportScheduleTeams() {
        return reportScheduleTeams;
    }

    public void setReportScheduleTeams(Set<ReportScheduleTeam> reportScheduleTeams) {
        this.reportScheduleTeams = reportScheduleTeams;
    }
}
