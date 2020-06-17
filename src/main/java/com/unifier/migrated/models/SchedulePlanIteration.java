package com.unifier.migrated.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by bplies on 4/14/16.
 */
@Entity
@Table(name = "SCHEDULE_PLAN_ITER")
public class SchedulePlanIteration implements Comparable<SchedulePlanIteration>, Serializable {
    public SchedulePlanIteration() {}
    protected SchedulePlanIteration(SchedulePlan plan, Date start, Date end, Date run) {
        this.schedulePlan = plan;
        this.reportStartDate = start;
        this.reportEndDate = end;
        this.runAfterDate = run;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "SCHEDULE_PLAN_ID")
    private SchedulePlan schedulePlan;

    @Version
    @Column(name = "VERSION", nullable = false)
    private int version;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "REPORT_START_DATE", nullable = false)
    private Date reportStartDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "REPORT_END_DATE", nullable = false)
    private Date reportEndDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "RUN_AFTER_DATE", nullable = false)
    private Date runAfterDate;

    public int getVersion() {
        return version;
    }

    public SchedulePlan getSchedulePlan() {
        return schedulePlan;
    }

    public Date getReportStartDate() {
        return reportStartDate;
    }

    public Date getReportEndDate() {
        return reportEndDate;
    }

    public Date getRunAfterDate() {
        return runAfterDate;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public void setSchedulePlan(SchedulePlan schedulePlan) {
        this.schedulePlan = schedulePlan;
    }

    public void setReportStartDate(Date reportStartDate) {
        this.reportStartDate = reportStartDate;
    }

    public void setReportEndDate(Date reportEndDate) {
        this.reportEndDate = reportEndDate;
    }

    public void setRunAfterDate(Date runAfterDate) {
        this.runAfterDate = runAfterDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int compareTo(SchedulePlanIteration that) {
        int compare = 0;
        compare = (compare == 0) ? this.runAfterDate.compareTo(that.runAfterDate) : compare;
        compare = (compare == 0) ? this.reportStartDate.compareTo(that.reportStartDate) : compare;
        compare = (compare == 0) ? this.reportEndDate.compareTo(that.reportEndDate) : compare;
        return compare;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof SchedulePlanIteration)) return false;

        SchedulePlanIteration that = (SchedulePlanIteration) o;

        if (!reportStartDate.equals(that.reportStartDate)) return false;
        if (!reportEndDate.equals(that.reportEndDate)) return false;
        return runAfterDate.equals(that.runAfterDate);

    }

    @Override
    public int hashCode() {
        int result = runAfterDate.hashCode();
        result = 31 * result + reportEndDate.hashCode();
        result = 31 * result + reportEndDate.hashCode();
        return result;
    }
}
