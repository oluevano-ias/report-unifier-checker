package com.unifier.migrated.models;

import java.io.Serializable;
import java.util.Objects;

public class AutoReportTeamCustomViewabilityCompositeKey implements Serializable {
    //@Column(name = "FW_VIEWABILITY_ID")
    private Long fwViewabilityId;

    //@Column(name = "MONITOR_VIEWABILITY_ID")
    private Long monitorViewabilityId;

    //@Column(name = "TEAM_ID", nullable = false)
    private Long teamId;

    public AutoReportTeamCustomViewabilityCompositeKey() {
    }

    public AutoReportTeamCustomViewabilityCompositeKey(Long fwViewabilityId, Long monitorViewabilityId, Long teamId) {
        this.fwViewabilityId = fwViewabilityId;
        this.monitorViewabilityId = monitorViewabilityId;
        this.teamId = teamId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AutoReportTeamCustomViewabilityCompositeKey that = (AutoReportTeamCustomViewabilityCompositeKey) o;
        return fwViewabilityId.equals(that.fwViewabilityId) &&
                monitorViewabilityId.equals(that.monitorViewabilityId) &&
                teamId.equals(that.teamId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fwViewabilityId, monitorViewabilityId, teamId);
    }

    @Override
    public String toString() {
        return "AutoReportTeamCustomViewabilityCompositeKey{" +
                "fwViewabilityId=" + fwViewabilityId +
                ", monitorViewabilityId=" + monitorViewabilityId +
                ", teamId=" + teamId +
                '}';
    }
}
