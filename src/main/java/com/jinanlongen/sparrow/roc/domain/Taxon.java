package com.jinanlongen.sparrow.roc.domain;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @description
 * @author shangyao
 * @date 2018年5月9日
 */
@Entity
@Table(name = "taxons")
public class Taxon {
  @Id
  private long id;
  private String name;
  private String code;
  private String ancestry;
  private Date createdAt;
  private Date updatedAt;
  private String sizeTableCode;
  private String widthTableCode;
  private boolean hasGender;

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

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getAncestry() {
    return ancestry;
  }

  public void setAncestry(String ancestry) {
    this.ancestry = ancestry;
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

  public String getSizeTableCode() {
    return sizeTableCode;
  }

  public void setSizeTableCode(String sizeTableCode) {
    this.sizeTableCode = sizeTableCode;
  }

  public String getWidthTableCode() {
    return widthTableCode;
  }

  public void setWidthTableCode(String widthTableCode) {
    this.widthTableCode = widthTableCode;
  }

  public boolean isHasGender() {
    return hasGender;
  }

  public void setHasGender(boolean hasGender) {
    this.hasGender = hasGender;
  }

}
