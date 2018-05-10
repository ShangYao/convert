package com.jinanlongen.sparrow.repository;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

import java.util.List;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.AuthenticationSource;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.ldap.query.LdapQuery;
import org.springframework.stereotype.Repository;

import com.jinanlongen.sparrow.domain.User;

/**
 * 类说明
 * 
 * @author shangyao
 * @date 2018年1月24日
 */
@Repository
public class LDAPRep {
	private static final Logger logger = LoggerFactory.getLogger(LDAPRep.class);
	private static final LdapTemplate ldapTemplate;
	static {
		LdapContextSource cs = new LdapContextSource();
		cs.setCacheEnvironmentProperties(false);
		cs.setUrl("ldap://192.168.200.125:389");
		cs.setBase("dc=longen,dc=com");
		cs.setAuthenticationSource(new AuthenticationSource() {
			@Override
			public String getCredentials() {
				return "meiguogou5.com";
			}

			@Override
			public String getPrincipal() {
				return "cn=admin,dc=longen,dc=com";
			}
		});
		cs.afterPropertiesSet();
		ldapTemplate = new LdapTemplate(cs);
	}

	/**
	 * 
	 * @param name
	 *            员工工号
	 * @param password
	 *            密码
	 * @return boolean
	 */
	public boolean check(String name, String password) {
		boolean result = false;
		try {
			ldapTemplate.authenticate(
					query().attributes("sn").where("objectClass").is("employedPerson").and("cn").is(name), password);
			result = true;
		} catch (Exception e) {
			logger.error("代号{}的用户，登录验证失败！", name);
		}
		return result;

	}

	/**
	 * 
	 * @param name
	 *            员工工号
	 * @return 员工姓名
	 */
	public List<String> findSnByCn(String name) {
		LdapQuery query = query().attributes("sn").where("objectClass").is("employedPerson").and("cn").is(name);
		return ldapTemplate.search(query, new AttributesMapper<String>() {
			@Override
			public String mapFromAttributes(Attributes attrs) throws NamingException {
				return (String) attrs.get("sn").get();
			}
		});

	}

	/**
	 * 
	 * @return 所有的员工
	 */
	public List<User> getAllPerson() {
		return ldapTemplate.search(query().where("objectClass").is("employedPerson"), new AttributesMapper<User>() {
			@Override
			public User mapFromAttributes(Attributes attributes) throws NamingException {
				User user = new User();
				user.setCode(attributes.get("cn").get().toString());
				user.setUserName(attributes.get("sn").get().toString());
				return user;
			}
		});
	}
}
