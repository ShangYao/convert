package com.jinanlongen.sparrow.domain;

import org.springframework.stereotype.Component;

/**
 * 
 * @description
 * @author shangyao
 * @date 2018年3月5日
 */
@Component
public class Statistics {
	private String name;
	private String count;
	private String count2;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getCount2() {
		return count2;
	}

	public void setCount2(String count2) {
		this.count2 = count2;
	}

}
