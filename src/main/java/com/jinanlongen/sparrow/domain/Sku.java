package com.jinanlongen.sparrow.domain;

public class Sku extends BaseDomain {
  private static final long serialVersionUID = 1L;
  private long id;
  private long mId;
  private String color;
  private String size;
  private float price;
  private int stock;
  private long albumId;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getmId() {
    return mId;
  }

  public void setmId(long mId) {
    this.mId = mId;
  }

  public String getColor() {
    return color;
  }

  public void setColor(String color) {
    this.color = color;
  }

  public String getSize() {
    return size;
  }

  public void setSize(String size) {
    this.size = size;
  }

  public float getPrice() {
    return price;
  }

  public void setPrice(float price) {
    this.price = price;
  }

  public int getStock() {
    return stock;
  }

  public void setStock(int stock) {
    this.stock = stock;
  }

  public long getAlbumId() {
    return albumId;
  }

  public void setAlbumId(long albumId) {
    this.albumId = albumId;
  }

  public static long getSerialversionuid() {
    return serialVersionUID;
  }

}
