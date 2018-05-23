package com.jinanlongen.sparrow.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "descs")
public class Desc extends BaseDomain {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "m_part")
  @SequenceGenerator(name = "m_part", sequenceName = "par_seq", allocationSize = 1)
  private long id;
  private String name;
  private String content;
  private long merchandiseId;

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



  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public long getMerchandiseId() {
    return merchandiseId;
  }

  public void setMerchandiseId(long merchandiseId) {
    this.merchandiseId = merchandiseId;
  }

  public static long getSerialversionuid() {
    return serialVersionUID;
  }


}
