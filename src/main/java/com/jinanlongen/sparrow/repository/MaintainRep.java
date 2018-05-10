package com.jinanlongen.sparrow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jinanlongen.sparrow.domain.Maintain;

/**
 * 
 * @description
 * @author shangyao
 * @date 2018年3月20日
 */
public interface MaintainRep extends JpaRepository<Maintain, Long> {
	List<Maintain> findLast10ByMidOrderByCreatedAtDesc(long id);
}
