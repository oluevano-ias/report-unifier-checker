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
@Table(name = "REPORT_SCHEDULE_TEAM")
@EntityListeners(AuditingEntityListener.class)
@IdClass(ReportScheduleTeamCompositeKey.class)
public class ReportScheduleTeam implements Serializable {
    @Id
    @ManyToOne
    @JoinColumn(name = "REPORT_SCHEDULE_ID", referencedColumnName = "ID", nullable = false)
    private ReportSchedule reportSchedule;

    @Column(name = "TEAM_ID", nullable = false)
    @NotNull
    @Id
    private Long teamId;

    public ReportScheduleTeam() {

    }

    public ReportScheduleTeam(ReportSchedule reportSchedule, Long teamId) {
        this.reportSchedule = reportSchedule;
        this.teamId = teamId;
    }

    public ReportSchedule getReportSchedule() {
        return reportSchedule;
    }

    public void setReportSchedule(ReportSchedule reportSchedule) {
        this.reportSchedule = reportSchedule;
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
                "reportScheduleId=" + (reportSchedule != null ? reportSchedule.getId() : "") +
                ", teamId=" + teamId +
                '}';
    }
}