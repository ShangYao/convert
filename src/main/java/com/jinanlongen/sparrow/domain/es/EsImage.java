package com.jinanlongen.sparrow.domain.es;

import com.jinanlongen.sparrow.domain.Image;

public class EsImage {
  private String id;
  private String path;
  private String position;
  private boolean main_candidate;
  private String type;
  private String url;
  private String fingerprint;

  public EsImage generate(Image image) {
    this.id = image.getId() + "";
    this.position = image.getCode() + "";
    String imageUrl = image.getUrl();
    this.url = imageUrl;
    this.path = imageUrl.substring(imageUrl.indexOf("/"));
    this.fingerprint = image.getFingerprint();
    return this;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public String getPosition() {
    return position;
  }

  public void setPosition(String position) {
    this.position = position;
  }

  public boolean isMain_candidate() {
    return main_candidate;
  }

  public void setMain_candidate(boolean main_candidate) {
    this.main_candidate = main_candidate;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getFingerprint() {
    return fingerprint;
  }

  public void setFingerprint(String fingerprint) {
    this.fingerprint = fingerprint;
  }



}
