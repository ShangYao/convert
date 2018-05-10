package com.jinanlongen.sparrow.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jinanlongen.sparrow.domain.Group;

/**
 * 
 * @description
 * @author shangyao
 * @date 2018年2月1日
 */
public interface GroupRep extends JpaRepository<Group, Long> {
	@Transactional
	void deleteByParentId(String id);

	List<Group> findByParentIdIsNull();

	List<Group> findByParentId(String id);

	@Query(value = "SELECT COUNT(*) FROM GROUPS_USERS WHERE GROUP_ID=?", nativeQuery = true)
	int findGroupUser(long id);

	@Query(value = "SELECT COUNT(*) FROM GROUPS_STORES WHERE GROUP_ID=?", nativeQuery = true)
	int findGroupStore(long id);

	@Query(value = "SELECT COUNT(*) FROM GROUPS WHERE NAME=?", nativeQuery = true)
	int countGroupName(String name);

	@Query(value = "SELECT COUNT(*) FROM GROUPS WHERE NAME=? AND ID!=?", nativeQuery = true)
	int countGroupName2(String name, Long id);
}
