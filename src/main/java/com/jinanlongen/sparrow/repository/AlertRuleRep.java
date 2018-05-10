package com.jinanlongen.sparrow.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jinanlongen.sparrow.domain.AlertRule;

/**
 * 
 * @description
 * @author shangyao
 * @date 2018年2月11日
 */
public interface AlertRuleRep extends JpaRepository<AlertRule, Long> {

}
