package com.example.spireforjava.config;

import com.example.spireforjava.interceptor.PayInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebAppConfig implements WebMvcConfigurer {

	@Bean
	public PayInterceptor LoginInterceptor() {
		System.out.println("luojiwen");
		return new PayInterceptor();
	}
	

    
}
