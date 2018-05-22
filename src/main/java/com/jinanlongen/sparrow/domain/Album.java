package com.jinanlongen.sparrow.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * 
 * @description
 * @author shangyao
 * @date 2018年5月8日
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Album extends BaseDomain {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "m_album")
  @SequenceGenerator(name = "m_album", sequenceName = "album_seq", allocationSize = 1)
  private long id;
  private long merchandiseId;
  private String name;
  @CreatedDate
  private Date createdAt;
  @LastModifiedDate
  private Date updteadAt;
  @Transient
  private List<Image> images = new ArrayList<Image>();


  public List<Image> getImages() {
    return images;
  }

  public void setImages(List<Image> images) {
    this.images = images;
  }

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
