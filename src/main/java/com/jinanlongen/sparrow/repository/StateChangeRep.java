package com.jinanlongen.sparrow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.jinanlongen.sparrow.domain.StateChange;

/**
 * 
 * @description
 * @author shangyao
 * @date 2018年3月3日
 */
public interface StateChangeRep extends JpaRepository<StateChange, Long>, JpaSpecificationExecutor<StateChange> {

	List<StateChange> findLast10ByMerchandiseIdOrderByCreatedAtDesc(long id);

	@Query(value = "SELECT CREATED_AT FROM STATE_CHANGES WHERE MERCHANDISE_ID=? AND OLD_STATE='待审核' AND NEW_STATE='已发布' LIMIT 1", nativeQuery = true)
	String find(long mid);
}
