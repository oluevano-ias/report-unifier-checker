package com.unifier.migrated.models;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "REPORT_TEAM")
@EntityListeners(AuditingEntityListener.class)
@IdClass(ReportTeamCompositeKey.class)
public class ReportTeam implements Serializable {
    @Id
    @ManyToOne
    @JoinColumn(name = "REPORT_INSTANCE_ID", referencedColumnName = "ID", nullable = false)
    private ReportInstance reportInstance;

    @Column(name = "TEAM_ID", nullable = false)
    @NotNull
    @Id
    private Long teamId;

    public ReportTeam() {

    }

    public ReportTeam(ReportInstance report, Long teamId) {
        this.reportInstance = report;
        this.teamId = teamId;
    }

    public ReportInstance getReport() {
        return reportInstance;
    }

    public void setReport(ReportInstance report) {
        this.reportInstance = report;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    @Override
    public String toString() {
        return "ReportTeam{" +
                "reportInstance=" + (reportInstance != null ? reportInstance.getId() : "") +
                ", teamId=" + teamId +
                '}';
    }
}