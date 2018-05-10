package com.jinanlongen.sparrow.controller;

import org.springframework.security.core.context.SecurityContextHolder;

import com.jinanlongen.sparrow.domain.User;

/**
 * 
 * @description 需要获取当前用户信息可继承此类
 * @author shangyao
 * @date 2018年2月2日
 */
public class BaseController {

	/**
	 * 
	 * @return 当前用户
	 */
	protected User getUser() {
		return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

	/**
	 * 
	 * @return 当前用户id
	 */
	protected Long getUserId() {
		Long id = 0l;
		User user = getUser();
		if (user != null) {
			id = user.getId();
		}
		return id;
	}

	/**
	 * 
	 * @return 当前用户姓名
	 */
	protected String getUserName() {
		String userName = "unknown";
		User user = getUser();
		if (user != null) {
			userName = user.getUserName();
		}
		return userName;
	}

	/**
	 * 
	 * @return 当前用户编码
	 */
	protected String getUserCode() {
		String userCode = "unknown";
		User user = getUser();
		if (user != null) {
			userCode = user.getCode();
		}
		return userCode;
	}
}
