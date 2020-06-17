package com.unifier.migrated.models;

import java.io.Serializable;
import java.util.Objects;

public class ReportTeamCompositeKey implements Serializable {
    private Long reportInstance;
    private Long teamId;

    public ReportTeamCompositeKey() {

    }

    public ReportTeamCompositeKey(Long reportInstance, Long teamId) {
        this.reportInstance = reportInstance;
        this.teamId = teamId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReportTeamCompositeKey that = (ReportTeamCompositeKey) o;
        return reportInstance.equals(that.reportInstance) &&
                teamId.equals(that.teamId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reportInstance, teamId);
    }
}

