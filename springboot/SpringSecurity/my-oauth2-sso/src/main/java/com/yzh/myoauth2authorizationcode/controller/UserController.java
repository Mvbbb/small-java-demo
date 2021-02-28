package com.yzh.myoauth2authorizationcode.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

	/**
	 * 获取当前用户
	 * @param authentication
	 * @return
	 */
	@GetMapping("/getCurrentUser")
	public Object getCurrentUser(Authentication authentication){
		return authentication.getPrincipal();
	}
}
