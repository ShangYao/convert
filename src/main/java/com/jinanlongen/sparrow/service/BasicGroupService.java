package com.jinanlongen.sparrow.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jinanlongen.sparrow.domain.Group;
import com.jinanlongen.sparrow.repository.GroupRep;

/**
 * 
 * @description
 * @author shangyao
 * @date 2018年2月3日
 */
@Service
public class BasicGroupService {
	@Autowired
	private GroupRep groupRep;

	public Group findGroupById(Long id) {
		if (0 == id) {
			return new Group();
		}
		return groupRep.findOne(id);

	}
}
