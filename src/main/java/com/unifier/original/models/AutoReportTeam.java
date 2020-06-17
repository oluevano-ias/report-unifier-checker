package com.unifier.original.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

@Table(name = "AUTO_REPORT_TEAM")
@Entity
public class AutoReportTeam implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "TEAM_ID", nullable = false)
    private Long teamId;

    @Column(name = "SCHEDULED_REPORT_ID", nullable = false)
    private Long scheduledReportId;

    @Column(name = "DATE_CREATED", nullable = false)
    private LocalDateTime dateCreated;

    @Column(name = "LAST_MODIFIED")
    private LocalDateTime lastModified;

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public Long getScheduledReportId() {
        return scheduledReportId;
    }

    public void setScheduledReportId(Long scheduledReportId) {
        this.scheduledReportId = scheduledReportId;
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

    public String toString() {
        return "AutoReportTeam{ " +
                "teamId=" + teamId +
                ", scheduledReportId=" + scheduledReportId +
                ", dateCreated=" + dateCreated +
                ", lastModified=" + lastModified +
                "}";
    }
}