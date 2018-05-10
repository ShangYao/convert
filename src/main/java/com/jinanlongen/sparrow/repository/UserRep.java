package com.jinanlongen.sparrow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.jinanlongen.sparrow.domain.User;

/**
 * 类说明
 * 
 * @author shangyao
 * @date 2018年1月23日
 */
public interface UserRep extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

	@Query(value = "select * from users where code=? limit 1", nativeQuery = true)
	User findByCode(String code);

	@Query(value = "select distinct uid from users_roles where role_id=? ", nativeQuery = true)
	List<Long> findUserIdByRoleId(long id);

	@Query(value = "select distinct uid from groups_users where group_id=? ", nativeQuery = true)
	List<Long> findUserIdByGroupId(long id);

	@Query(value = "select * from users where has_refined=true ", nativeQuery = true)
	List<User> findByHasRefinedTrue();

	@Transactional
	@Modifying
	@Query(value = "update users set has_refined='true'  where id=? ", nativeQuery = true)
	void update(long id);

}
