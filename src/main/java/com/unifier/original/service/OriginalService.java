package com.unifier.original.service;

import com.unifier.original.dao.ReportFileOriginalRepository;
import com.unifier.original.dao.ReportOriginalRepository;
import com.unifier.original.dao.ReportOriginalScheduleRepository;
import com.unifier.original.models.Report;
import com.unifier.original.models.ReportSchedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class OriginalService {

    @Autowired
    private ReportFileOriginalRepository reportFileOriginalRepository;

    @Autowired
    private ReportOriginalRepository reportOriginalRepository;

    @Autowired
    private ReportOriginalScheduleRepository reportOriginalScheduleRepository;

    //@Transactional("customReportsOriginalTransactionManager")
    public Optional<ReportSchedule> getReportScheduleById(Long id){
        return reportOriginalScheduleRepository.findById(id);
    }

    public Optional<Report> getReport(Long reportId){
        return reportOriginalRepository.findById(reportId);
    }

    //@Transactional("customReportsOriginalTransactionManager")
    public List<Report> getReportsFromSchedule(Long scheduleId){
        return reportOriginalRepository.findReportsByReportScheduleIdEquals(scheduleId);
    }

}
