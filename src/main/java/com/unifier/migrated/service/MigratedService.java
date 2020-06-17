package com.unifier.migrated.service;

import com.unifier.migrated.dao.ReportFileMigratedRepository;
import com.unifier.migrated.dao.ReportMigratedRepository;
import com.unifier.migrated.dao.ReportMigratedScheduleRepository;
import com.unifier.migrated.models.ReportInstance;
import com.unifier.migrated.models.ReportTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MigratedService {

    @Autowired
    private ReportFileMigratedRepository reportFileMigratedRepository;

    @Autowired
    private ReportMigratedRepository reportMigratedRepository;

    @Autowired
    private ReportMigratedScheduleRepository reportMigratedScheduleRepository;

    //@Transactional("customReportsMigratedTransactionManager")
    public Optional<ReportTemplate> getReportTemplateById(Long id) {
        return reportMigratedScheduleRepository.findById(id);
    }

    public Optional<ReportInstance> getReportInstance(Long reportId){
        return reportMigratedRepository.findById(reportId);
    }

    //@Transactional("customReportsMigratedTransactionManager")
    public List<ReportInstance> getReportsFromSchedule(Long scheduleId) {
        return reportMigratedRepository.findReportInstancesByReportTemplateIdEquals(scheduleId);
    }

    public List<ReportInstance> getNonDeletedReportsFromSchedule(Long scheduleId) {
        return reportMigratedRepository.findReportInstancesByReportTemplateIdEqualsAndDateDeletedIsNull(scheduleId);
    }
}
