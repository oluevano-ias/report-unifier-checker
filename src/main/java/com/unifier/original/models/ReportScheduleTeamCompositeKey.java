package com.unifier.original.models;

import java.io.Serializable;
import java.util.Objects;

public class ReportScheduleTeamCompositeKey implements Serializable {
    private Long reportSchedule;
    private Long teamId;

    public ReportScheduleTeamCompositeKey() {

    }

    public ReportScheduleTeamCompositeKey(Long reportId, Long teamId) {
        this.reportSchedule = reportId;
        this.teamId = teamId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReportScheduleTeamCompositeKey that = (ReportScheduleTeamCompositeKey) o;
        return reportSchedule.equals(that.reportSchedule) &&
                teamId.equals(that.teamId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reportSchedule, teamId);
    }
}
