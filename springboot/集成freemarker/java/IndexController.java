package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {
	
	@GetMapping({"","/"})
	public ModelAndView index(HttpServletRequest request){
		ModelAndView mv = new ModelAndView();
		User user=(User)request.getSession().getAttribute("user");
		if(user==null){
			mv.setViewName("redirect:/user/login");
		}else{
			mv.setViewName("/index");
			mv.addObject(user);
		}
		return mv;
	}
}
