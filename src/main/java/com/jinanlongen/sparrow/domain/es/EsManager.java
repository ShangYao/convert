package com.jinanlongen.sparrow.domain.es;

import com.jinanlongen.sparrow.domain.User;

public class EsManager {
  private String code;
  private String name;

  public EsManager() {}

  public EsManager(String code, String name) {
    super();
    this.code = code;
    this.name = name;
  }

  public EsManager generate(User holder) {
    this.code = holder.getCode();
    this.name = holder.getUserName();
    return this;
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



}
