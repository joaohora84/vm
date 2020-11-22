package com.vm.vm;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
	
	@RequestMapping("/")
	public String index() {
		return "/index";
	}
	
	@RequestMapping("/erro")
	public String erro() {
		
		return "erro";
		
	}
	
	
}
