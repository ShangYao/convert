package com.jinanlongen.sparrow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jinanlongen.sparrow.domain.Role;

/**
 * 类说明
 * 
 * @author shangyao
 * @date 2018年1月26日
 */
public interface RoleRep extends JpaRepository<Role, Long> {
	@Query(value = "SELECT COUNT(*) FROM USERS_ROLES WHERE ROLE_ID=?", nativeQuery = true)
	int findUserRole(Long id);

	@Query(value = "SELECT COUNT(*) FROM ROLES WHERE NAME=?", nativeQuery = true)
	int countRoleName(String name);

	@Query(value = "SELECT COUNT(*) FROM ROLES WHERE NAME=? AND ID!=?", nativeQuery = true)
	int countName(String name, long id);
}
