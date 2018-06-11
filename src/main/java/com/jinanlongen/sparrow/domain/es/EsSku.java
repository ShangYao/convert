package com.jinanlongen.sparrow.domain.es;

import com.jinanlongen.sparrow.domain.LineItem;

public class EsSku {
  private String id;
  private String color_id;
  private String size_id;
  private String brand_code;
  private float desired_price;
  private String album_id;
  private int stock;
  private String mpn;

  public String getMpn() {
    return mpn;
  }

  public void setMpn(String mpn) {
    this.mpn = mpn;
  }

  public String getBrand_code() {
    return brand_code;
  }

  public void setBrand_code(String brand_code) {
    this.brand_code = brand_code;
  }

  public int getStock() {
    return stock;
  }

  public void setStock(int stock) {
    this.stock = stock;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getColor_id() {
    return color_id;
  }

  public void setColor_id(String color_id) {
    this.color_id = color_id;
  }

  public String getSize_id() {
    return size_id;
  }

  public void setSize_id(String size_id) {
    this.size_id = size_id;
  }



  public float getDesired_price() {
    return desired_price;
  }

  public void setDesired_price(float desired_price) {
    this.desired_price = desired_price;
  }

  public String getAlbum_id() {
    return album_id;
  }

  public void setAlbum_id(String album_id) {
    this.album_id = album_id;
  }

  public EsSku generate(LineItem item) {
    this.id = item.getId() + "";
    this.color_id = item.getColorId() + "";
    this.size_id = item.getSizeId() + "";
    this.album_id = item.getAlbumId() == 0 ? null : (item.getAlbumId() + "");
    this.desired_price = item.getPrice();
    this.stock = item.getStock();
    this.brand_code = item.getBrand_code();
    this.mpn = item.getMpn();
    return this;
  }

}
