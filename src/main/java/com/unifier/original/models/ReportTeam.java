package com.unifier.original.models;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "REPORT_TEAM")
@EntityListeners(AuditingEntityListener.class)
@IdClass(ReportTeamCompositeKey.class)
public class ReportTeam implements Serializable {
    @Id
    @ManyToOne
    @JoinColumn(name = "REPORT_ID", referencedColumnName = "ID", nullable = false)
    private Report report;

    @Column(name = "TEAM_ID", nullable = false)
    @NotNull
    @Id
    private Long teamId;

    public ReportTeam() {

    }

    public ReportTeam(Report report, Long teamId) {
        this.report = report;
        this.teamId = teamId;
    }

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
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
                "reportId=" + (report != null ? report.getId() : "") +
                ", teamId=" + teamId +
                '}';
    }
}