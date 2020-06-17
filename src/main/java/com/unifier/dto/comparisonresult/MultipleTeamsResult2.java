package com.unifier.dto.comparisonresult;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.unifier.dto.TeamResult;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class MultipleTeamsResult2 {
    List<TeamWithException> errors = new ArrayList<>();
    int totalEqual = 0;
    int totalNotEqual = 0;
    List<Long> equal = new ArrayList<>();
    List<Long> notEqual = new ArrayList<>();

    public int getTotalEqual() {
        return totalEqual;
    }

    public int getTotalNotEqual() {
        return totalNotEqual;
    }

    public List<Long> getEqual() {
        return equal;
    }

    public List<Long> getNotEqual() {
        return notEqual;
    }

    public List<TeamWithException> getErrors() {
        return errors;
    }

    public void addTeamWithException(TeamWithException teamWithException) {
        errors.add(teamWithException);
    }

    public void addEqualTeam(TeamResult teamResult) {
        equal.add(teamResult.getTeamId());
        totalEqual++;
    }

    public void addNotEqualTeam(TeamResult teamResult) {
        notEqual.add(teamResult.getTeamId());
        totalNotEqual++;
    }
}
