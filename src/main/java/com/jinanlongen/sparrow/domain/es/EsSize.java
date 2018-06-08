package com.jinanlongen.sparrow.domain.es;

import com.jinanlongen.sparrow.domain.Size;

public class EsSize {
  private String id;
  private String name;
  private String album_id;
  private String memo;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAlbum_id() {
    return album_id;
  }

  public void setAlbum_id(String album_id) {
    this.album_id = album_id;
  }

  public String getMemo() {
    return memo;
  }

  public void setMemo(String memo) {
    this.memo = memo;
  }

  public EsSize generate(Size size) {
    this.id = size.getId() + "";
    this.name = size.getName();
    this.memo = size.getDescription();
    this.album_id = size.getAlbumId() == 0 ? null : (size.getAlbumId() + "");
    return this;
  }

}
