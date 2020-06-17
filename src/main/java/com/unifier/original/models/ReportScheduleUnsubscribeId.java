package com.unifier.original.models;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ReportScheduleUnsubscribeId implements Serializable {

    @Column(name = "REPORT_SCHEDULE_ID", nullable = false)
    private Long reportScheduleId;

    @Column(name = "EMAIL", nullable = false)
    private String email;

    public Long getReportScheduleId() {
        return reportScheduleId;
    }

    public String getEmail() {
        return email;
    }

    public ReportScheduleUnsubscribeId() {
    }

    public ReportScheduleUnsubscribeId(Long reportScheduleId, String email) {
        this.reportScheduleId = reportScheduleId;
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReportScheduleUnsubscribeId that = (ReportScheduleUnsubscribeId) o;
        return reportScheduleId.equals(that.reportScheduleId) &&
                email.equals(that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reportScheduleId, email);
    }

    @Override
    public String toString() {
        return "ReportScheduleUnsubscribeId{" +
                "reportScheduleId=" + reportScheduleId +
                ", email='" + email + '\'' +
                '}';
    }
}
