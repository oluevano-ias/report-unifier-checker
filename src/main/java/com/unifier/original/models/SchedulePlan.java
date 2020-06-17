package com.unifier.original.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Represents the entire plan for executions of a Report.  Contains a collection of SchedulePlanIteration that
 * will detail
 * 1. The Report Start Date
 * 2. The Report End Date
 * 3. When to run the Report
 *
 * Created by bplies on 4/14/16.
 */
@Entity
@Table(name = "SCHEDULE_PLAN")
public class SchedulePlan implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @OneToMany(mappedBy = "schedulePlan", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @OrderBy("runAfterDate")
    private List<SchedulePlanIteration> iterations = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public List<SchedulePlanIteration> getIterations() {
        return iterations;
    }

    /**
     * Adds an iteration to the Plan
     * @param start
     * @param end
     * @param run
     */
    public void addIteration(Date start, Date end, Date run) {
        SchedulePlanIteration iter = new SchedulePlanIteration(this, start, end, run);
        this.iterations.add(iter);
        Collections.sort(this.iterations);
    }

    public void deleteIteration(SchedulePlanIteration iter) {
        this.iterations.remove(iter);
    }

    @PreRemove
    public void clear() {
        for (SchedulePlanIteration itr : this.iterations) {
            itr.setSchedulePlan(null);
        }
        this.iterations.clear();
    }
}
