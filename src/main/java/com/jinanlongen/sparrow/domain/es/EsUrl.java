package com.jinanlongen.sparrow.domain.es;

import com.jinanlongen.sparrow.domain.SourceUrl;

public class EsUrl {
  private String id;
  private String url;
  private String state;
  private String mpn;
  private String brand_code;
  private long created_at;
  private long updated_at;

  public String getBrand_code() {
    return brand_code;
  }

  public void setBrand_code(String brand_code) {
    this.brand_code = brand_code;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getUrl() {
    return url;
  }

  public String getMpn() {
    return mpn;
  }

  public void setMpn(String mpn) {
    this.mpn = mpn;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public long getCreated_at() {
    return created_at;
  }

  public void setCreated_at(long created_at) {
    this.created_at = created_at;
  }

  public long getUpdated_at() {
    return updated_at;
  }

  public void setUpdated_at(long updated_at) {
    this.updated_at = updated_at;
  }

  public EsUrl generate(SourceUrl url) {
    this.id = url.getId() + "";
    this.url = url.getUrl();
    this.state = (url.getState() == 0) ? "abnormal" : "normal";
    this.created_at = url.getCreatedAt().getTime();
    this.updated_at = url.getUpdatedAt().getTime();
    this.mpn = url.getMpn();
    return this;
  }

}
