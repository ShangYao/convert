package com.jinanlongen.sparrow.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.jinanlongen.sparrow.domain.Merchandise;
import com.jinanlongen.sparrow.repository.MerchandiseRep;
import com.jinanlongen.sparrow.util.DateUtils;

/**
 * 
 * @description
 * @author shangyao
 * @date 2018年3月22日
 */
@Service
public class RefinedAllService {
  @Autowired
  private MerchandiseRep mcdRep;

  public Merchandise queryAll(final Merchandise merchandise) {
    merchandise
        .setPages(mcdRep.findAll(getSpecificationAll(merchandise), merchandise.getPageable()));
    return merchandise;
  }

  private Specification<Merchandise> getSpecificationAll(Merchandise merchandise) {
    return new Specification<Merchandise>() {
      @Override
      public Predicate toPredicate(Root<Merchandise> root, CriteriaQuery<?> query,
          CriteriaBuilder cb) {
        List<Predicate> lstPredicates = new ArrayList<Predicate>();
        // if (0 != merchandise.getStoreId()) {
        // lstPredicates.add(cb.equal(root.get("storeId").as(Long.class),
        // merchandise.getStoreId()));
        // } else {
        // List<Long> idlist = merchandise.getStoreList().stream().map(i -> i.getId())
        // .collect(Collectors.toList());
        // if (null == idlist || idlist.size() == 0) {
        // lstPredicates.add(cb.equal(root.get("storeId").as(Long.class), 0l));
        // } else {
        // Expression<Long> exp = root.get("storeId").as(Long.class);
        // lstPredicates.add(exp.in(idlist));
        // }
        //
        // }
        if (0 != merchandise.getOwnerId()) {
          lstPredicates.add(cb.equal(root.get("ownerId").as(Long.class), merchandise.getOwnerId()));
        }
        if (StringUtils.isNotBlank(merchandise.getQueryString())) {
          // if (StringUtils.isNumeric(merchandise.getQueryString())) {
          // lstPredicates.add(cb.like(root.get("itemId").as(String.class),
          // "%" + merchandise.getQueryString() + "%"));
          // } else {
          lstPredicates.add(cb.like(cb.upper(root.get("title").as(String.class)),
              "%" + merchandise.getQueryString().toUpperCase() + "%"));
          // }
        }
        if (StringUtils.isNotBlank(merchandise.getState())) {
          lstPredicates.add(cb.equal(root.get("state").as(String.class), merchandise.getState()));
        } else {
          lstPredicates.add(cb.notEqual(root.get("state").as(String.class), "草稿"));
          lstPredicates.add(cb.notEqual(root.get("state").as(String.class), "回收站"));
        }
        if (StringUtils.isNotBlank(merchandise.getBeginCreated())) {
          lstPredicates.add(cb.greaterThanOrEqualTo(root.get("createdAt").as(Date.class),
              DateUtils.getDate(merchandise.getBeginCreated() + " 00-00-00")));
        }
        if (StringUtils.isNotBlank(merchandise.getEndCreated())) {
          lstPredicates.add(cb.lessThanOrEqualTo(root.get("createdAt").as(Date.class),
              DateUtils.getDate(merchandise.getEndCreated() + " 23-59-59")));
        }
        if (StringUtils.isNotBlank(merchandise.getEndUpdated())) {
          lstPredicates.add(cb.lessThanOrEqualTo(root.get("updatedAt").as(Date.class),
              DateUtils.getDate(merchandise.getEndUpdated() + " 23-59-59")));
        }
        if (StringUtils.isNotBlank(merchandise.getBeginUpdated())) {
          lstPredicates.add(cb.greaterThanOrEqualTo(root.get("updatedAt").as(Date.class),
              DateUtils.getDate(merchandise.getBeginUpdated() + " 00-00-00")));
        }
        Predicate[] arrayPredicates = new Predicate[lstPredicates.size()];
        return cb.and(lstPredicates.toArray(arrayPredicates));
      }
    };
  }

}
