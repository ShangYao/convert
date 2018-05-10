package com.jinanlongen.sparrow.security;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.jinanlongen.sparrow.domain.Privilege;
import com.jinanlongen.sparrow.domain.User;
import com.jinanlongen.sparrow.repository.LDAPRep;
import com.jinanlongen.sparrow.repository.PrivilegeRep;
import com.jinanlongen.sparrow.repository.UserRep;

@Service
public class UrlUserService implements UserDetailsService {
	private static final Logger logger = LoggerFactory.getLogger(UrlUserService.class);
	@Autowired
	UserRep userRep;
	@Autowired
	PrivilegeRep privilegeRep;
	@Autowired
	private LDAPRep ldap;

	@Override
	public UserDetails loadUserByUsername(String userName) {
		User user = userRep.findByCode(userName);
		if (user != null) {
			List<Privilege> Privileges = privilegeRep.findByUserId(user.getId());
			List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
			for (Privilege privilege : Privileges) {
				if (privilege != null && privilege.getName() != null) {
					GrantedAuthority grantedAuthority = new UrlGrantedAuthority(privilege.getPermissionUrl());
					grantedAuthorities.add(grantedAuthority);
				}
			}
			user.setGrantedAuthorities(grantedAuthorities);
			return user;
		} else {
			User userAdd = new User();
			userAdd.setCode(userName);
			String realName = ldap.findSnByCn(userName).get(0);
			userAdd.setUserName(realName);
			logger.info("新用户{}登录", realName);
			userRep.save(userAdd);
			return userAdd;
		}
	}
}