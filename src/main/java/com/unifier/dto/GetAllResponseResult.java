package com.unifier.dto;

public class GetAllResponseResult {

    private final Long teamId;
    private String originalResponse;
    private String migratedResponse;

    public GetAllResponseResult(Long teamId, String originalResponse, String migratedResponse) {
        this.originalResponse = originalResponse;
        this.migratedResponse = migratedResponse;
        this.teamId = teamId;
    }


    public String getOriginalResponse() {
        return originalResponse;
    }

    public String getMigratedResponse() {
        return migratedResponse;
    }

    public Long getTeamId() {
        return teamId;
    }
}
