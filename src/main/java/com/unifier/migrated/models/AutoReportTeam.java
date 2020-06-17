package com.unifier.migrated.models;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Table(name = "AUTO_REPORT_TEAM")
@Entity
public class AutoReportTeam implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "TEAM_ID", nullable = false)
    private Long teamId;

    @Column(name = "REPORT_TEMPLATE_ID", nullable = false)
    private Long reportTemplateId;

    @Column(name = "DATE_CREATED", nullable = false)
    private LocalDateTime dateCreated;

    @Column(name = "LAST_MODIFIED")
    private LocalDateTime lastModified;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "reportTeam", fetch = FetchType.EAGER, orphanRemoval = true)
    @OrderBy("fwViewabilityId")
    private Set<AutoReportTeamCustomViewability> customViewability = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "reportTemplate", fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<ReportScheduleTeam> reportScheduleTeams = new HashSet<>();

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public Long getScheduledReportId() {
        return reportTemplateId;
    }

    public void setScheduledReportId(Long scheduledReportId) {
        this.reportTemplateId = scheduledReportId;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDateTime getLastModified() {
        return lastModified;
    }

    public void setLastModified(LocalDateTime dateModified) {
        this.lastModified = dateModified;
    }

    public void setCustomViewability(Set<AutoReportTeamCustomViewability> customViewability) {
        this.customViewability = customViewability;
    }

    public Set<ReportScheduleTeam> getReportScheduleTeams() {
        return reportScheduleTeams;
    }

    public void setReportScheduleTeams(Set<ReportScheduleTeam> reportScheduleTeams) {
        this.reportScheduleTeams = reportScheduleTeams;
    }

    public String toString() {
        return "AutoReportTeam{ " +
                "teamId=" + teamId +
                ", reportTemplateId=" + reportTemplateId +
                ", dateCreated=" + dateCreated +
                ", lastModified=" + lastModified +
                "}";
    }
}