package com.unifier.dto.comparisonresult;

public class TeamWithException {
    long teamId;
    String error;

    public TeamWithException(long teamId, String error) {
        this.teamId = teamId;
        this.error = error;
    }

    public long getTeamId() {
        return teamId;
    }

    public String getError() {
        return error;
    }
}
