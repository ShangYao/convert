package com.jinanlongen.sparrow.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.jinanlongen.sparrow.domain.StateChange;
import com.jinanlongen.sparrow.repository.MerchandiseRep;
import com.jinanlongen.sparrow.repository.StateChangeRep;
import com.jinanlongen.sparrow.util.DateUtils;

/**
 * 
 * @description
 * @author shangyao
 * @date 2018年3月16日
 */
@Service
public class StateChangeService {
	@Autowired
	private StateChangeRep scRep;
	@Autowired
	private MerchandiseRep mcRep;

	public StateChange query(StateChange stateChange) {
		stateChange.setPages(scRep.findAll(getSpecification(stateChange), stateChange.getPageable()));
		return stateChange;
	}

	private Specification<StateChange> getSpecification(StateChange stateChange) {
		return new Specification<StateChange>() {
			@Override
			public Predicate toPredicate(Root<StateChange> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> lstPredicates = new ArrayList<Predicate>();

				if (0 != stateChange.getMerchandiseId()) {
					lstPredicates
							.add(cb.equal(root.get("merchandiseId").as(Long.class), stateChange.getMerchandiseId()));
				} else {
					List<Long> idlist = mcRep.findId();
					Expression<Long> exp = root.get("merchandiseId").as(Long.class);
					lstPredicates.add(exp.in(idlist));

				}
				if (0 != stateChange.getCreatedUserId()) {
					lstPredicates
							.add(cb.equal(root.get("createdUserId").as(Long.class), stateChange.getCreatedUserId()));
				}
				if (StringUtils.isNotBlank(stateChange.getItemId())) {

					long id = mcRep.findIdByItemId(stateChange.getItemId());

					lstPredicates.add(cb.equal(root.get("merchandiseId").as(Long.class), id));
				}
				if (StringUtils.isNotBlank(stateChange.getMid())) {
					if (NumberUtils.isDigits(stateChange.getMid())) {
						lstPredicates.add(cb.equal(root.get("merchandiseId").as(Long.class),
								Long.parseLong(stateChange.getMid())));

					} else {
						lstPredicates.add(cb.equal(root.get("merchandiseId").as(Long.class), 0l));

					}

				}
				if (StringUtils.isNotBlank(stateChange.getBeginCreated())) {
					lstPredicates.add(cb.greaterThanOrEqualTo(root.get("createdAt").as(Date.class),
							DateUtils.getDate(stateChange.getBeginCreated() + " 00-00-00")));
				}
				if (StringUtils.isNotBlank(stateChange.getEndCreated())) {
					lstPredicates.add(cb.lessThanOrEqualTo(root.get("createdAt").as(Date.class),
							DateUtils.getDate(stateChange.getEndCreated() + " 23-59-59")));
				}
				lstPredicates.add(cb.equal(root.get("oldState").as(String.class), "待审核"));
				Predicate[] arrayPredicates = new Predicate[lstPredicates.size()];
				return cb.and(lstPredicates.toArray(arrayPredicates));
			}
		};
	}

}
