package com.jinanlongen.sparrow.domain.es;

import com.jinanlongen.sparrow.roc.domain.Brand;

public class EsBrand {
  private String code;
  private String name;



  public EsBrand(String code, String name) {
    super();
    this.code = code;
    this.name = name;
  }

  public EsBrand() {

  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public EsBrand generate(Brand brand) {
    this.code = brand.getCode();
    this.name = brand.getName();
    return this;
  }

}
