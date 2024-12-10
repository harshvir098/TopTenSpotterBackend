package com.proyectofinal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

@Configuration
public class Config {
	@Bean
	StandardServletMultipartResolver multipartResolver() {
	    return new StandardServletMultipartResolver();
	}
}