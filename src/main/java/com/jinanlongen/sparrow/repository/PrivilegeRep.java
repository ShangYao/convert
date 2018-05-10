package com.jinanlongen.sparrow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jinanlongen.sparrow.domain.Privilege;

/**
 * 类说明
 * 
 * @author shangyao
 * @date 2018年1月25日
 */
public interface PrivilegeRep extends JpaRepository<Privilege, Long> {

	@Query(value = "select * from privileges where id in (select distinct a.privilege_id from roles_privileges a,users_roles b where a.role_id=b.role_id and b.uid=?) ", nativeQuery = true)
	List<Privilege> findByUserId(long userId);
}
