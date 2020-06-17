package com.unifier.migrated.models;

import java.io.Serializable;
import java.util.Objects;

public class ReportScheduleTeamCompositeKey implements Serializable {
    private Long reportTemplate;
    private Long teamId;

    public ReportScheduleTeamCompositeKey() {

    }

    public ReportScheduleTeamCompositeKey(Long reportId, Long teamId) {
        this.reportTemplate = reportId;
        this.teamId = teamId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReportScheduleTeamCompositeKey that = (ReportScheduleTeamCompositeKey) o;
        return reportTemplate.equals(that.reportTemplate) &&
                teamId.equals(that.teamId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reportTemplate, teamId);
    }
}
