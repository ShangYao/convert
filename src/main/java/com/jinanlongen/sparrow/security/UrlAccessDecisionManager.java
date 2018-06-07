package com.jinanlongen.sparrow.security;

import java.util.Collection;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Service;

@Service
public class UrlAccessDecisionManager implements AccessDecisionManager {
  private Logger logger = LoggerFactory.getLogger(UrlAccessDecisionManager.class);

  @Override
  public void decide(Authentication authentication, Object object,
      Collection<ConfigAttribute> configAttributes)
      throws AccessDeniedException, InsufficientAuthenticationException {
    HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();

    String url;

    if (matchers("/images/**", request) || matchers("/js/**", request)
        || matchers("/api/**", request) || matchers("/css/**", request)
        || matchers("/fonts/**", request) || matchers("/", request)
        || matchers("/refined/index", request) || matchers("/favicon.ico", request)
        || matchers("/login", request)) {
      return;
    } else {
      if (null == authentication.getAuthorities()) {
        throw new AccessDeniedException("此用户无任何权限");
      }
      for (GrantedAuthority ga : authentication.getAuthorities()) {
        if (ga instanceof UrlGrantedAuthority) {
          UrlGrantedAuthority urlGrantedAuthority = (UrlGrantedAuthority) ga;
          url = urlGrantedAuthority.getPermissionUrl();
          if (matchers(url, request)) {
            // logger.info("用户{}正在访问：{}", authentication.getName(),
            // request.getRequestURL().toString());
            return;
          }
        }
      }

    }
    logger.info("用户{}无权限访问：{}", authentication.getName(), request.getRequestURL().toString());
    throw new AccessDeniedException("无访问权限");
  }

  @Override
  public boolean supports(ConfigAttribute attribute) {
    return true;
  }

  @Override
  public boolean supports(Class<?> clazz) {
    return true;
  }

  private boolean matchers(String url, HttpServletRequest request) {
    AntPathRequestMatcher matcher = new AntPathRequestMatcher(url);
    if (matcher.matches(request)) {
      return true;
    }
    return false;
  }
}
