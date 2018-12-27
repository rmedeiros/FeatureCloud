package com.onekin.tagcloud.config;

import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class MvcConfig implements WebMvcConfigurer {

	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler(
				"/fonts/**",
				"/img/**",
				"/css/**",
				"/js/**").addResourceLocations(
						"classpath:/static/fonts/",
						"classpath:/static/img/",
						"classpath:/static/css/",
						"classpath:/static/js/");
	}
}
