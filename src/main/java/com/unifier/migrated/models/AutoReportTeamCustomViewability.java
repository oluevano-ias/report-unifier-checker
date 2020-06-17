package com.unifier.migrated.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "AUTO_REPORT_TEAM_CUSTOM_VIEWABILITY")
@IdClass(AutoReportTeamCustomViewabilityCompositeKey.class)
public class AutoReportTeamCustomViewability implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "FW_VIEWABILITY_ID")
    private Long fwViewabilityId = -1L;

    @Column(name = "MONITOR_VIEWABILITY_ID")
    private Long monitorViewabilityId = -1L;

    @Id
    @Column(name = "TEAM_ID")
    private Long teamId;

    @ManyToOne
    @JoinColumn(name = "TEAM_ID", nullable = false, insertable = false, updatable = false)
    private AutoReportTeam reportTeam;

    public AutoReportTeamCustomViewability() {
    }

    public AutoReportTeamCustomViewability(Long teamId, Long fwViewabilityId, Long monitorViewabilityId) {
        this.teamId = teamId;
        this.fwViewabilityId = fwViewabilityId;
        this.monitorViewabilityId = monitorViewabilityId;
    }

    public Long getFwViewabilityId() {
        return fwViewabilityId;
    }

    public void setFwViewabilityId(Long fwViewabilityId) {
        this.fwViewabilityId = fwViewabilityId;
    }

    public Long getMonitorViewabilityId() {
        return monitorViewabilityId;
    }

    public void setMonitorViewabilityId(Long monitorViewabilityId) {
        this.monitorViewabilityId = monitorViewabilityId;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public String toString() {
        return "AutoReportTeamCustomViewability{fwViewabilityId=" + fwViewabilityId +
                ", monitorViewabilityId=" + monitorViewabilityId +
                ", teamId=" + teamId +
                "}";
    }
}