package com.jinanlongen.sparrow.domain;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 
 * @description
 * @author shangyao
 * @date 2018年5月8日
 */
@Entity
public class Album extends BaseDomain {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue
  private long id;
  private long merchandiseId;
  private String name;
  private Date createdAt;
  private Date updteadAt;



  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getMerchandiseId() {
    return merchandiseId;
  }

  public void setMerchandiseId(long merchandiseId) {
    this.merchandiseId = merchandiseId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public Date getUpdteadAt() {
    return updteadAt;
  }

  public void setUpdteadAt(Date updteadAt) {
    this.updteadAt = updteadAt;
  }

  public static long getSerialversionuid() {
    return serialVersionUID;
  }


}
