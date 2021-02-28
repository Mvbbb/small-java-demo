package com.xxxx.springsecuritydemo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 登录
 *
 * @author zhoubin
 * @since 1.0.0
 */
@Controller
public class LoginController {

	// /**
	//  * 登录
	//  * @return
	//  */
	// @RequestMapping("/login")
	// public String login(){
	// 	return "redirect:main.html";
	// }


	/**
	 * 页面跳转
	 * @return
	 */
	// @Secured("ROLE_abC")
	//PreAuthorize允许角色以ROLE_开头，也可以不以ROLE_开头，但是配置类不允许以ROLE_开头
	@PreAuthorize("hasRole('ROLE_abc')")
	@RequestMapping("/toMain")
	public String main(){
		return "redirect:main.html";
	}


	/**
	 * 页面跳转
	 * @return
	 */
	@RequestMapping("/toError")
	public String error(){
		return "redirect:error.html";
	}


	/**
	 * 页面跳转
	 * @return
	 */
	@GetMapping("/demo")
	public String demo(){
		return "demo";
	}



	/**
	 * 页面跳转
	 * @return
	 */
	@GetMapping("/showLogin")
	public String showLogin(){
		return "login";
	}

}