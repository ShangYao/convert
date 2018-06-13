package com.jinanlongen.sparrow.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.google.common.collect.Lists;
import com.jinanlongen.sparrow.domain.Merchandise;
import com.jinanlongen.sparrow.domain.User;
import com.jinanlongen.sparrow.repository.GroupRep;
import com.jinanlongen.sparrow.repository.MerchandiseRep;
import com.jinanlongen.sparrow.repository.UserRep;
import com.jinanlongen.sparrow.util.DateUtils;

/**
 * 
 * @description
 * @author shangyao
 * @date 2018年3月22日
 */
@Service
public class RefinedAuditService {
  @Autowired
  private MerchandiseRep mcdRep;
  @Autowired
  private GroupRep groupRep;
  @Autowired
  private UserRep userRep;

  public Merchandise auditQuery(final Merchandise merchandise) {
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
        } else {
          List<BigInteger> ownerIds = groupRep.getUidsByGid(merchandise.getReviewerId());
          List<Long> ids = ownerIds.stream().map(i -> i.longValue()).collect(Collectors.toList());
          Expression<Long> exp = root.get("ownerId").as(Long.class);
          lstPredicates.add(exp.in(ids));
        }

        if (StringUtils.isNotBlank(merchandise.getQueryString())) {
          lstPredicates.add(cb.like(cb.upper(root.get("title").as(String.class)),
              "%" + merchandise.getQueryString().toUpperCase() + "%"));
        }
        if (StringUtils.isNotBlank(merchandise.getState())) {
          lstPredicates.add(cb.equal(root.get("state").as(String.class), merchandise.getState()));
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

  public List<User> findUserBySameGroup(long reviewerId) {
    List<User> users = Lists.newArrayList();
    List<BigInteger> ownerIds = groupRep.getUidsByGid(reviewerId);
    for (BigInteger id : ownerIds) {
      users.add(userRep.findOne(id.longValue()));
    }
    return users;
  }
}
