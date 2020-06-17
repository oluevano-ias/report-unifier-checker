package com.unifier.dto;

import java.util.Date;
import java.util.Set;

public class ReportInstanceData {
    private Set<Long> teamIds;
    private Date createdOn;

    public ReportInstanceData(Set<Long> teamIds, Date createdOn) {
        this.teamIds = teamIds;
        this.createdOn = createdOn;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public Set<Long> getTeamIds() {
        return teamIds;
    }
}
