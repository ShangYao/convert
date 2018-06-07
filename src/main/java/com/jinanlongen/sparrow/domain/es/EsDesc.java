package com.jinanlongen.sparrow.domain.es;

import com.jinanlongen.sparrow.domain.Desc;

public class EsDesc {

  private long id;
  private String type;
  private String content;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public EsDesc generate(Desc desc) {
    this.id = desc.getId();
    this.content = desc.getContent();
    this.type = desc.getName();

    return this;
  }


}
