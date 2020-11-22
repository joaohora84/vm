package com.vm.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter{
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/img/**", "/css/**", "/js/**", "/sass/**", "/materialize/**", "/fullcalendar/**", "/index/**")
		.addResourceLocations("classpath:/static/img/", "classpath:/static/css/",
				"classpath:/static/js/", "classpath:/static/materialize/",
				"classpath:/static/sass/", "classpath:/static/fullcalendar/", "classpath:/templates/index/");
	}

}
