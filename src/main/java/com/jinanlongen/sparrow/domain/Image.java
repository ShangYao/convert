package com.jinanlongen.sparrow.domain;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.web.multipart.MultipartFile;

/**
 * 
 * @description
 * @author shangyao
 * @date 2018年5月8日
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Image extends BaseDomain {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue
  private long id;
  private long albumId;
  private String name;
  private String url;
  private int code;
  @CreatedDate
  private Date createdAt;
  @LastModifiedDate
  private Date updteadAt;
  @Transient
  private MultipartFile imageFile;

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

  public long getId() {
    return id;
  }

  public MultipartFile getImageFile() {
    return imageFile;
  }

  public void setImageFile(MultipartFile imageFile) {
    this.imageFile = imageFile;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getAlbumId() {
    return albumId;
  }

  public void setAlbumId(long albumId) {
    this.albumId = albumId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public static long getSerialversionuid() {
    return serialVersionUID;
  }

}
