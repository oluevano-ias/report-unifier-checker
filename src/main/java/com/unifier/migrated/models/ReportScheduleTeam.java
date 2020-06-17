package com.unifier.migrated.models;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "REPORT_SCHEDULE_TEAM")
@EntityListeners(AuditingEntityListener.class)
@IdClass(ReportScheduleTeamCompositeKey.class)
public class ReportScheduleTeam implements Serializable {
    @Id
    @ManyToOne
    @JoinColumn(name = "REPORT_TEMPLATE_ID", referencedColumnName = "ID", nullable = false)
    private ReportTemplate reportTemplate;

    @Column(name = "TEAM_ID", nullable = false)
    @NotNull
    @Id
    private Long teamId;

    public ReportScheduleTeam() {

    }

    public ReportScheduleTeam(ReportTemplate reportSchedule, Long teamId) {
        this.reportTemplate = reportSchedule;
        this.teamId = teamId;
    }

    public ReportTemplate getReportSchedule() {
        return reportTemplate;
    }

    public void setReportSchedule(ReportTemplate reportSchedule) {
        this.reportTemplate = reportSchedule;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    @Override
    public String toString() {
        return "ReportScheduleTeam{" +
                "reportTemplate=" + (reportTemplate != null ? reportTemplate.getId() : "") +
                ", teamId=" + teamId +
                '}';
    }
}