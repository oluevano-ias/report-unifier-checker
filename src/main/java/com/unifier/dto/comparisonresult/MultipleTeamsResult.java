package com.unifier.dto.comparisonresult;

import com.unifier.dto.TeamResult;

import java.util.ArrayList;
import java.util.List;

public class MultipleTeamsResult {
    List<TeamWithException> errors = new ArrayList<>();
    List<TeamResult> success = new ArrayList<>();

    public List<TeamWithException> getErrors() {
        return errors;
    }

    public List<TeamResult> getSuccess() {
        return success;
    }

    public void addTeamWithException(TeamWithException teamWithException) {
        errors.add(teamWithException);
    }

    public void addTeamResult(TeamResult teamResult) {
        success.add(teamResult);
    }
}
