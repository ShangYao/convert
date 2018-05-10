package com.jinanlongen.sparrow.domain;

import java.util.Date;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * 
 * @description
 * @author shangyao
 * @date 2018年2月10日
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "alert_rules")
public class AlertRule extends BaseDomain {
  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue
  private long id;
  private String name;
  private String alertRuleType;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "AlertsUsers", joinColumns = {@JoinColumn(name = "alertId")},
      inverseJoinColumns = {@JoinColumn(name = "uid")})
  private Set<User> users;

  private boolean enable;
  private float lowALertPrice;
  private float middleAlertPrice;
  private float highALertPrice;
  private boolean highALertStock;
  private boolean lowAlertStock;
  @CreatedDate
  private Date createdAt;
  @LastModifiedDate
  private Date updatedAt;
  private String createdUser;
  @Transient
  private long[] storeId;
  @Transient
  private long[] userId;

  public long[] getStoreId() {
    return storeId;
  }

  public void setStoreId(long[] storeId) {
    this.storeId = storeId;
  }

  public long[] getUserId() {
    return userId;
  }

  public void setUserId(long[] userId) {
    this.userId = userId;
  }

  public boolean isEnable() {
    return enable;
  }

  public void setEnable(boolean enable) {
    this.enable = enable;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAlertRuleType() {
    return alertRuleType;
  }

  public void setAlertRuleType(String alertRuleType) {
    this.alertRuleType = alertRuleType;
  }

  public Set<User> getUsers() {
    return users;
  }

  public void setUsers(Set<User> users) {
    this.users = users;
  }



  public float getLowALertPrice() {
    return lowALertPrice;
  }

  public void setLowALertPrice(float lowALertPrice) {
    this.lowALertPrice = lowALertPrice;
  }

  public float getMiddleAlertPrice() {
    return middleAlertPrice;
  }

  public void setMiddleAlertPrice(float middleAlertPrice) {
    this.middleAlertPrice = middleAlertPrice;
  }

  public float getHighALertPrice() {
    return highALertPrice;
  }

  public void setHighALertPrice(float highALertPrice) {
    this.highALertPrice = highALertPrice;
  }

  public boolean isHighALertStock() {
    return highALertStock;
  }

  public void setHighALertStock(boolean highALertStock) {
    this.highALertStock = highALertStock;
  }

  public boolean isLowAlertStock() {
    return lowAlertStock;
  }

  public void setLowAlertStock(boolean lowAlertStock) {
    this.lowAlertStock = lowAlertStock;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public Date getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
  }

  public String getCreatedUser() {
    return createdUser;
  }

  public void setCreatedUser(String createdUser) {
    this.createdUser = createdUser;
  }

  public static long getSerialversionuid() {
    return serialVersionUID;
  }

}
