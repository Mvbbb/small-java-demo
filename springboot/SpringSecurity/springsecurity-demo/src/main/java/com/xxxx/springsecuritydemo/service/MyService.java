package com.xxxx.springsecuritydemo.service;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zhoubin
 * @since 1.0.0
 */
public interface MyService {

	boolean hasPermission(HttpServletRequest request, Authentication authentication);
}
