package com.jinanlongen.sparrow.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.jinanlongen.sparrow.domain.Group;
import com.jinanlongen.sparrow.domain.User;
import com.jinanlongen.sparrow.repository.GroupRep;
import com.jinanlongen.sparrow.repository.LDAPRep;
import com.jinanlongen.sparrow.repository.UserRep;

/**
 * 类说明
 * 
 * @author shangyao
 * @date 2018年1月26日
 */
@Service
public class BasicUserService {
	private Logger logger = LoggerFactory.getLogger(BasicUserService.class);
	@Autowired
	private UserRep userRep;
	@Autowired
	private GroupRep groupRep;

	@Autowired
	private LDAPRep ldap;

	public User queryList(final User user) {
		Group group = getGroup(user);
		Specification<User> specification = new Specification<User>() {
			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> lstPredicates = new ArrayList<Predicate>();
				if (StringUtils.isNotBlank(user.getCode())) {
					lstPredicates.add(cb.like(root.get("code").as(String.class), "%" + user.getCode() + "%"));
				}
				if (StringUtils.isNotBlank(user.getUserName())) {
					lstPredicates.add(cb.like(root.get("userName").as(String.class), "%" + user.getUserName() + "%"));
				}
				if (null != user.getRoleId() && 0 != user.getRoleId()[0]) {
					List<Long> userIdList = userRep.findUserIdByRoleId(user.getRoleId()[0]);
					if (null != userIdList && userIdList.size() != 0L) {
						Expression<Long> exp = root.get("id").as(Long.class);
						lstPredicates.add(exp.in(userIdList));
					} else {
						Expression<Long> exp = root.get("id").as(Long.class);
						userIdList.add(0L);
						lstPredicates.add(exp.in(userIdList));
					}
				}
				if (null != group) {
					List<Long> userIdList = userRep.findUserIdByGroupId(group.getId());
					if (null != userIdList && userIdList.size() != 0L) {
						Expression<Long> exp = root.get("id").as(Long.class);
						lstPredicates.add(exp.in(userIdList));
					} else {
						Expression<Long> exp = root.get("id").as(Long.class);
						userIdList.add(0L);
						lstPredicates.add(exp.in(userIdList));
					}

				}
				Predicate[] arrayPredicates = new Predicate[lstPredicates.size()];
				return cb.and(lstPredicates.toArray(arrayPredicates));
			}
		};
		Page<User> page = userRep.findAll(specification, user.getPageable());
		user.setPages(page);
		return user;
	}

	private Group getGroup(User user) {
		long id = 0;
		if (user.getGroup3() != 0) {
			id = user.getGroup3();
		} else if (user.getGroup2() != 0) {
			id = user.getGroup2();
		} else if (user.getGroup1() != 0) {
			id = user.getGroup1();
		}
		if (id == 0) {
			return null;
		} else {
			return groupRep.findOne(id);
		}

	}

	// @Scheduled(cron = "0 0 22 * * ?")
	public void updateUserTableByLDAP() {
		List<User> userList = ldap.getAllPerson();
		for (User user : userList) {
			User tableUser = userRep.findByCode(user.getCode());
			if (null != tableUser) {
				logger.info("用户{}已存在", user.getUserName());
				continue;
			}
			userRep.save(user);
			logger.info("添加用户{}", user.getUserName());
		}

	}
}
