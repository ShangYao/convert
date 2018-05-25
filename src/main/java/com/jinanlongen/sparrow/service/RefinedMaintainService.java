package com.jinanlongen.sparrow.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
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
public class RefinedMaintainService {
  @Autowired
  private MerchandiseRep mcdRep;

  public Merchandise maintainQuery(final Merchandise merchandise) {
    merchandise.setSort("maintainAt");
    merchandise.setPages(mcdRep.findAll(getSpecification(merchandise), merchandise.getPageable()));
    return merchandise;
  }

  private Specification<Merchandise> getSpecification(Merchandise merchandise) {
    return new Specification<Merchandise>() {
      @Override
      public Predicate toPredicate(Root<Merchandise> root, CriteriaQuery<?> query,
          CriteriaBuilder cb) {
        List<Predicate> lstPredicates = new ArrayList<Predicate>();

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
          Expression<String> exp = root.get("state").as(String.class);
          lstPredicates.add(exp.in(Arrays.asList("已发布", "已禁用")));
          // lstPredicates.add(cb.equal(root.get("state").as(String.class),
          // merchandise.getState()));
        }

        if (StringUtils.isNotBlank(merchandise.getEndMaintain())) {
          lstPredicates.add(cb.lessThanOrEqualTo(root.get("maintainAt").as(Date.class),
              DateUtils.getDate(merchandise.getEndMaintain() + " 23-59-59")));
        }
        if (StringUtils.isNotBlank(merchandise.getBeginMaintain())) {
          lstPredicates.add(cb.greaterThanOrEqualTo(root.get("maintainAt").as(Date.class),
              DateUtils.getDate(merchandise.getBeginMaintain() + " 00-00-00")));
        }
        Predicate[] arrayPredicates = new Predicate[lstPredicates.size()];
        return cb.and(lstPredicates.toArray(arrayPredicates));
      }
    };
  }
}
