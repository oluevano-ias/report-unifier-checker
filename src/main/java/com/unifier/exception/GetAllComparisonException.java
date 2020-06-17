package com.unifier.exception;

public class GetAllComparisonException extends RuntimeException {
    private final Long teamId;

    public GetAllComparisonException(Long teamId, Throwable cause) {
        super(cause);
        this.teamId = teamId;
    }

    public GetAllComparisonException(Long teamId, String cause) {
        super(cause);
        this.teamId = teamId;
    }

    public Long getTeamId() {
        return teamId;
    }
}
