package com.inthebytes.accountservice;


import com.inthebytes.accountservice.login.JwtProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
				.allowedOriginPatterns("*")
				.allowedOriginPatterns("http://*")
				.exposedHeaders(JwtProperties.HEADER_STRING)
				.allowCredentials(true)
				.allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH");
	}
}