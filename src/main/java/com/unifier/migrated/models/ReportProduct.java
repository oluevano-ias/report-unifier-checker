package com.unifier.migrated.models;


import com.unifier.exception.NotFoundException;

import java.util.Arrays;

/**
 * Created by bplies on 12/14/17.
 */
public enum ReportProduct {
    PUB("pub", "/publisher/reports"),
    FW("fw", "/custom-reports/reports");

    private String source;

    private String domainPath;

    ReportProduct(String source, String domainPath) {
        this.source = source;
        this.domainPath = domainPath;
    }

    public String getSource() {
        return source;
    }

    public String getDomainPath() {
        return domainPath;
    }

    public static ReportProduct toReportProduct(String source) {
        return Arrays.stream(ReportProduct.values()).filter(r -> r.source.equals(source)).findAny().orElseThrow(NotFoundException::new);
    }

}
