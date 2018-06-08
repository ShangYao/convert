package com.jinanlongen.sparrow.domain.es;

import com.jinanlongen.sparrow.domain.Color;

public class EsColor {
  private String id;
  private String name;
  private String album_id;
  private String memo;

  @Override
  public String toString() {
    return "EsColor [id=" + id + ", name=" + name + ", album_id=" + album_id + ", memo=" + memo
        + "]";
  }



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

  public String getMemo() {
    return memo;
  }

  public void setMemo(String memo) {
    this.memo = memo;
  }

  public String getAlbum_id() {
    return album_id;
  }

  public void setAlbum_id(String album_id) {
    this.album_id = album_id;
  }

  public EsColor generate(Color color) {
    this.id = color.getId() + "";
    this.name = color.getName();
    this.memo = color.getDescription();
    this.album_id = color.getAlbumId() == 0 ? null : (color.getAlbumId() + "");
    return this;
  }

}
