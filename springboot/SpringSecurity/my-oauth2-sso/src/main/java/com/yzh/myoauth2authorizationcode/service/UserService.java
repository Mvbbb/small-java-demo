package com.yzh.myoauth2authorizationcode.service;

import com.yzh.myoauth2authorizationcode.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
	
	@Autowired
	private PasswordEncoder passwordEncoder; // SecurityConfig 中装配的 PasswordEncoder
	
	@Override
	public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
		String password = passwordEncoder.encode("123456");
		return new User("admin",password, AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
	}
}
