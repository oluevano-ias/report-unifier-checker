package com.unifier.original.models;

import java.io.Serializable;
import java.util.Objects;

public class ReportTeamCompositeKey implements Serializable {
    private Long report;
    private Long teamId;

    public ReportTeamCompositeKey() {

    }

    public ReportTeamCompositeKey(Long report, Long teamId) {
        this.report = report;
        this.teamId = teamId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReportTeamCompositeKey that = (ReportTeamCompositeKey) o;
        return report.equals(that.report) &&
                teamId.equals(that.teamId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(report, teamId);
    }
}

