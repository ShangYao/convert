package com.jinanlongen.sparrow.domain;

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


  public long getId() {
    return id;
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
