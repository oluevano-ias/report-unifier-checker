package com.unifier.migrated.models;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ReportScheduleUnsubscribeId implements Serializable {

    @Column(name = "REPORT_TEMPLATE_ID", nullable = false)
    private Long reportTemplateId;

    @Column(name = "EMAIL", nullable = false)
    private String email;

    public Long getReportScheduleId() {
        return reportTemplateId;
    }

    public String getEmail() {
        return email;
    }

    public ReportScheduleUnsubscribeId() {
    }

    public ReportScheduleUnsubscribeId(Long reportScheduleId, String email) {
        this.reportTemplateId = reportScheduleId;
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReportScheduleUnsubscribeId that = (ReportScheduleUnsubscribeId) o;
        return reportTemplateId.equals(that.reportTemplateId) &&
                email.equals(that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reportTemplateId, email);
    }

    @Override
    public String toString() {
        return "ReportScheduleUnsubscribeId{" +
                "reportTemplateId=" + reportTemplateId +
                ", email='" + email + '\'' +
                '}';
    }
}
