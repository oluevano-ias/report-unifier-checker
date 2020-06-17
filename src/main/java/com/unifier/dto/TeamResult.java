package com.unifier.dto;

import com.unifier.dto.comparisonresult.NamesResult2;
import com.unifier.dto.comparisonresult.ReportTeamsResult;
import com.unifier.dto.comparisonresult.ReportTeamsResult2;
import com.unifier.dto.comparisonresult.TotalsSizeResult;

import java.util.ArrayList;
import java.util.List;

public class TeamResult {

    private TotalsSizeResult sizeResult;
    private NamesResult2 namesResult;
    private ReportTeamsResult2 reportTeamsResult;
    private Long teamId;
    public TeamResult(Long teamId) {
        this.teamId = teamId;
    }

    public NamesResult2 getNamesResult() {
        return namesResult;
    }

    public void setNamesResult(NamesResult2 namesResult2) {
        this.namesResult = namesResult2;
    }

    public TotalsSizeResult getSizeResult() {
        return sizeResult;
    }

    public void setSizeResult(TotalsSizeResult sizeResult) {
        this.sizeResult = sizeResult;
    }

    public Long getTeamId() {
        return teamId;
    }

    public ReportTeamsResult2 getReportTeamsResult() {
        return reportTeamsResult;
    }

    public void setReportTeamsResult(ReportTeamsResult2 reportTeamsResult) {
        this.reportTeamsResult = reportTeamsResult;
    }

    public boolean isEqual() {
        return sizeResult.isEquals() && namesResult.isEquals() && reportTeamsResult.isEquals();
    }

    public List<String> getFailureReasons() {
        ArrayList<String> errors = new ArrayList<>();
        addIfNotNullError(errors, "sizeResult: ", sizeResult.getFailureReason());
        addIfNotNullError(errors, "namesResult: ", namesResult.getFailureReason());
        addIfNotNullError(errors, "reportTeamsResult: ", reportTeamsResult.getFailureReason());
        return errors;
    }

    public boolean checkTeam() {
        return isEqual();
    }

    private void addIfNotNullError(List<String> holder, String op, String error) {
        if (error != null)
            holder.add(op + error);
    }
}
