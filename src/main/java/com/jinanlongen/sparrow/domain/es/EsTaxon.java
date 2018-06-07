package com.jinanlongen.sparrow.domain.es;

import com.jinanlongen.sparrow.roc.domain.Taxon;

public class EsTaxon {
  private String code;
  private String name;

  public EsTaxon(String code, String name) {
    super();
    this.code = code;
    this.name = name;
  }

  public EsTaxon() {

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

  public EsTaxon generate(Taxon taxon) {
    this.code = taxon.getCode();
    this.name = taxon.getName();
    return this;
  }

}
