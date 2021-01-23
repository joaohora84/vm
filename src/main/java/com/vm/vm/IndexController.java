package com.vm.vm;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
	
	@RequestMapping("/")
	public String indexcatalogo() {
		return "/indexcatalogo";
	}
	
	@RequestMapping("/index")
	public String index() {
		return "/index";
	}
	
	@RequestMapping("/indexcatalogo")
	public String indexCatalogo() {
		return "/indexcatalogo";
	}
	
	@RequestMapping("/erro")
	public String erro() {
		
		return "erro";
		
	}
	
	
}
