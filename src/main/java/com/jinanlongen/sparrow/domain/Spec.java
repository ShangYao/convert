package com.jinanlongen.sparrow.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 
 * @description
 * @author shangyao
 * @date 2018年5月8日
 */
@Entity
@Table(name = "specs")
@JsonIgnoreProperties({"merchandiseId"})
public class Spec extends BaseDomain {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "m_part")
  @SequenceGenerator(name = "m_part", sequenceName = "par_seq", allocationSize = 1)
  private long id;
  private String key;
  private String value;
  private long merchandiseId;

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

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public static long getSerialversionuid() {
    return serialVersionUID;
  }

}
