package com.unifier.migrated.models;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

@Table(name = "REPORT_SCHEDULE_UNSUBSCRIBE")
@Entity
public class ReportScheduleUnsubscribe implements Serializable {
  private static final long serialVersionUID = 1L;

  @EmbeddedId
  private ReportScheduleUnsubscribeId unsubscribeId;


  @Column(name = "IS_UNSUBSCRIBED", nullable = false)
  private Boolean unsubscribed;

  @Column(name = "LAST_MODIFIED", nullable = false)
  private LocalDateTime lastModified;

  public ReportScheduleUnsubscribeId getId(){
    return unsubscribeId;
  }

  public void setUnsubscribeId(ReportScheduleUnsubscribeId unsubscribeId){
    this.unsubscribeId = unsubscribeId;
  }

  public Boolean getUnsubscribed() {
    return unsubscribed;
  }

  public void setUnsubscribed(Boolean unsubscribed) {
    this.unsubscribed = unsubscribed;
  }

  public LocalDateTime getLastModified() {
    return lastModified;
  }

  public void setLastModified(LocalDateTime lastModified) {
    this.lastModified = lastModified;
  }

  public String toString() {
    return "ReportUnsubscribeStatus{unsubscribeId=" + unsubscribeId +
      ", unsubscribed=" + unsubscribed +
      ", lastModified=" + lastModified +
      "}";
  }
}