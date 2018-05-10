package com.jinanlongen.sparrow.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;

import com.jinanlongen.sparrow.security.SparrowAuthenticationProvider;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private SessionRegistry sessionRegistry;
	@Autowired
	private SparrowAuthenticationProvider provider;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests().antMatchers("/logout").permitAll().antMatchers("/api/**").permitAll()
				.antMatchers("/merchandise/index").permitAll().antMatchers("/images/**").permitAll()
				.antMatchers("/js/**").permitAll().antMatchers("/css/**").permitAll().antMatchers("/fonts/**")
				.permitAll().antMatchers("/favicon.ico").permitAll().antMatchers("/").permitAll().anyRequest()
				.authenticated().and().formLogin().loginPage("/login").defaultSuccessUrl("/merchandise/mine/index")
				.failureUrl("/login?error").permitAll().and().sessionManagement().maximumSessions(1)
				.sessionRegistry(sessionRegistry).and().and().logout().invalidateHttpSession(true)
				.clearAuthentication(true).and().httpBasic();

	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(provider);
	}

	@Bean
	public SessionRegistry getSessionRegistry() {
		SessionRegistry sessionRegistry = new SessionRegistryImpl();
		return sessionRegistry;
	}
}