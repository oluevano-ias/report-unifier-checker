package com.unifier.controller;

import com.unifier.dto.MultipleTeamsRequest;
import com.unifier.dto.TeamResult;
import com.unifier.dto.comparisonresult.MultipleTeamsResult;
import com.unifier.dto.comparisonresult.PerProductResult;
import com.unifier.dto.comparisonresult.PerProductResult2;
import com.unifier.exception.GetAllComparisonException;
import com.unifier.service.GetAllReportComparisonService;
import com.unifier.service.UnifierCheckerService;
import io.vavr.control.Either;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/")
public class UnifierController extends BaseController {

    @Autowired
    UnifierCheckerService unifierCheckerService;

    @Autowired
    GetAllReportComparisonService comparisonService;

    @GetMapping(value = "checkSchedule", produces = "application/json")
    public String checkSchedule(){
        return unifierCheckerService.checkSchedule();
    }

    @GetMapping(value = "checkOnDemand", produces = "application/json")
    public String checkOnDemand(){
        return unifierCheckerService.checkOnDemand();
    }

    @GetMapping(value = "compareGetAll/{product}/{teamId}", produces = "application/json")
    public TeamResult compareGetAll(@PathVariable String product, @PathVariable Long teamId){
        return comparisonService.compareSingleTeam(product, teamId).getOrElseThrow(e -> e);
    }

    @PostMapping(value = "compareGetAll/", produces = "application/json")
    public PerProductResult2 compareGetAll(@RequestBody Map<String, List<Long>> data){
        return comparisonService.compareMultipleTeams(data);
    }
}
