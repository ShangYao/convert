package com.jinanlongen.sparrow.domain.es;

import com.jinanlongen.sparrow.domain.Color;

public class EsColor {
  private long id;
  private String name;
  private long album_id;
  private String memo;

  @Override
  public String toString() {
    return "EsColor [id=" + id + ", name=" + name + ", album_id=" + album_id + ", memo=" + memo
        + "]";
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

  public long getAlbum_id() {
    return album_id;
  }

  public void setAlbum_id(long album_id) {
    this.album_id = album_id;
  }

  public String getMemo() {
    return memo;
  }

  public void setMemo(String memo) {
    this.memo = memo;
  }

  public EsColor generate(Color color) {
    this.id = color.getId();
    this.name = color.getName();
    this.memo = color.getDescription();
    this.album_id = color.getAlbumId();
    return this;
  }

}
