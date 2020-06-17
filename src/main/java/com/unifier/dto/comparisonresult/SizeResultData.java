package com.unifier.dto.comparisonresult;

import com.unifier.dto.ReportInstanceData;

import java.util.List;

public class SizeResultData {

    private String name;
    private Long reportTemplateid;
    private int instancesSize;
    private String type;
    private List<ReportInstanceData> reportInstanceData;

    public SizeResultData(String name, Long reportTemplateid, int instancesSize, String type, List<ReportInstanceData> reportInstanceData) {
        this.name = name;
        this.reportTemplateid = reportTemplateid;
        this.instancesSize = instancesSize;
        this.type = type;
        this.reportInstanceData = reportInstanceData;
    }

    public List<ReportInstanceData> getReportInstanceData() {
        return reportInstanceData;
    }

    public Long getReportTemplateId() {
        return reportTemplateid;
    }

    public String getType() {
        return type;
    }

    public int getInstancesSize() {
        return instancesSize;
    }

    public String getName() {
        return name;
    }

}
