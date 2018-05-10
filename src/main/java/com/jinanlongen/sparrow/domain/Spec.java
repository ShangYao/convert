package com.jinanlongen.sparrow.domain;

/**
 * 
 * @description
 * @author shangyao
 * @date 2018年5月8日
 */
public class Spec extends BaseDomain {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private String key;
  private String value;

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public static long getSerialversionuid() {
    return serialVersionUID;
  }

}
