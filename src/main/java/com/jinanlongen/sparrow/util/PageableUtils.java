package com.jinanlongen.sparrow.util;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jinanlongen.sparrow.domain.BaseDomain;

/**
 * 
 * @description
 * @author shangyao
 * @date 2018年2月23日
 */
public class PageableUtils {
  @JsonIgnore
  private int page;
  @JsonIgnore
  private int pageSize = 10;
  @JsonIgnore
  private int totalPages;
  @JsonIgnore
  private long totleElements;
  @JsonIgnore
  private Sort sort;
  @JsonIgnore
  private Page<? extends BaseDomain> pages;
  @JsonIgnore
  private List<? extends BaseDomain> queryList;

  public long getTotleElements() {
    return totleElements;
  }

  public void setTotleElements(long totleElements) {
    this.totleElements = totleElements;
  }

  public List<? extends BaseDomain> getQueryList() {
    return queryList;
  }

  public void setQueryList(List<? extends BaseDomain> queryList) {
    this.queryList = queryList;
  }

  public Page<? extends BaseDomain> getPages() {
    return pages;
  }

  public int getTotalPages() {
    return totalPages;
  }

  public void setTotalPages(int totalPages) {
    this.totalPages = totalPages;
  }

  public int getPage() {
    return page;
  }

  public void setPage(int page) {
    this.page = (page == 0 ? 0 : page - 1);
  }

  public void setPages(Page<? extends BaseDomain> pages) {
    this.pages = pages;
    this.queryList = pages.getContent();
    this.totalPages = pages.getTotalPages();
    this.page = pages.getNumber() + 1;
    this.totleElements = pages.getTotalElements();
  }

  public int getPageSize() {
    return pageSize;
  }

  public void setPageSize(int pageSize) {
    this.pageSize = pageSize;
  }

  public Sort getSort() {
    return new Sort(Direction.DESC, "id");
  }

  public void setSort(String name) {
    this.sort = new Sort(Direction.ASC, name);
  }

  @JsonIgnore
  public Pageable getPageable() {
    return new PageRequest(page, pageSize, sort);
  }
}
