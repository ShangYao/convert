package com.jinanlongen.sparrow.domain;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jinanlongen.sparrow.util.PageableUtils;

/**
 * 类说明
 * 
 * @author shangyao
 * @date 2018年1月1日
 */
public abstract class BaseDomain extends PageableUtils implements Serializable {
	private static final long serialVersionUID = 1L;
	@JsonIgnore
	private String alertMessage;
	private long group1;
	private long group2;
	private long group3;

	public long getGroup1() {
		return group1;
	}

	public void setGroup1(long group1) {
		this.group1 = group1;
	}

	public long getGroup2() {
		return group2;
	}

	public void setGroup2(long group2) {
		this.group2 = group2;
	}

	public long getGroup3() {
		return group3;
	}

	public void setGroup3(long group3) {
		this.group3 = group3;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getAlertMessage() {
		return alertMessage;
	}

	public void setAlertMessage(String alertMessage) {
		this.alertMessage = alertMessage;
	}

}
