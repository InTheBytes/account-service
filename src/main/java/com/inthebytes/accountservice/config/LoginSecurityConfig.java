package com.inthebytes.accountservice.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.inthebytes.accountservice.service.LoginDetailsService;

@EnableWebSecurity
@Configuration
public class LoginSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private LoginDetailsService loginDetailService;
	
	@Autowired
	private DataSource dataSource;
	
	protected void configure(AuthenticationManagerBuilder auth) {
		auth.authenticationProvider(authenticationProvider());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		 http.authorizeRequests()
		 	.antMatchers("/login").permitAll();
	}
	
	@Bean
    PasswordEncoder passwordEncoder(){
        return  new BCryptPasswordEncoder();
    }
	
	@Bean
	DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider dap = new DaoAuthenticationProvider();
		dap.setPasswordEncoder(passwordEncoder());
		dap.setUserDetailsService(loginDetailService);
		return dap;
	}
	
	

}
