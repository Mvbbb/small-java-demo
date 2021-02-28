package com.xxxx.springsecuritydemo.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

/**
 * @author zhoubin
 * @since 1.0.0
 */
@Service
public class MyServiceImpl implements MyService {

	@Override
	public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
		//获取主体
		Object obj = authentication.getPrincipal();
		//判断主体是否属于UserDetails
		if (obj instanceof UserDetails){
			//获取权限
			UserDetails userDetails = (UserDetails) obj;
			Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
			//判断请求的URI是否在权限里
			return authorities.contains(new SimpleGrantedAuthority(request.getRequestURI()));
		}
		return false;
	}
}