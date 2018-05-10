package com.jinanlongen.sparrow.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import com.jinanlongen.sparrow.domain.User;
import com.jinanlongen.sparrow.repository.LDAPRep;

/**
 * 类说明
 * 
 * @author shangyao
 * @date 2018年1月25日
 */
@Component
public class SparrowAuthenticationProvider implements AuthenticationProvider {
	@Autowired
	private UrlUserService userService;
	@Autowired
	LDAPRep ldap;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		// TODO Auto-generated method stub
		authentication.getDetails();
		String name = authentication.getName();
		String pwd = (String) authentication.getCredentials();
		if (!ldap.check(name, pwd)) {
			throw new BadCredentialsException("LDAP authenticate fail !");
		}
		User user = (User) userService.loadUserByUsername(name);

		return new UsernamePasswordAuthenticationToken(user, pwd, user.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		// TODO Auto-generated method stub
		return true;
	}

}
