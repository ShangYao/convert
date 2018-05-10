package com.jinanlongen.sparrow;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.jinanlongen.sparrow.repository.LDAPRep;

/**
 * 类说明
 * 
 * @author shangyao
 * @date 2018年1月27日
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class LDAPRepTests {
	@Autowired
	private LDAPRep ldap;

	@Test
	public void checkTest() {
		List<String> l = new ArrayList<String>();
		l.add("2");
		Assert.assertTrue("未通过验证", ldap.check("1210", "lejm2016"));
	}
}
