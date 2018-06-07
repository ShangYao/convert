package com.jinanlongen.sparrow.domain.es;

import com.jinanlongen.sparrow.roc.domain.Gender;

public class EsGender {
  private String code;
  private String name;


  public EsGender(String code, String name) {
    super();
    this.code = code;
    this.name = name;
  }

  public EsGender() {

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

  public EsGender generate(Gender gender) {
    this.code = gender.getCode();
    this.name = gender.getName();
    return this;
  }

}
