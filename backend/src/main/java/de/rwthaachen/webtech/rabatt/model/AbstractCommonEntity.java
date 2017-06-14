package de.rwthaachen.webtech.rabatt.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;

@MappedSuperclass
public abstract class AbstractCommonEntity extends AbstractEntityId {

  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "created_at")
  private Date createdAt;

  @UpdateTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "updated_at")
  private Date updatedAt;

  @JsonIgnore
  public Date getCreatedAt() {
    return createdAt;
  }

  @JsonSetter
  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  @JsonIgnore
  public Date getUpdatedAt() {
    return updatedAt;
  }

  @JsonSetter
  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
  }

}
