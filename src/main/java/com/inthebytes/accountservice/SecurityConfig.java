package com.inthebytes.accountservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.inthebytes.accountservice.login.AuthenticationFilter;
import com.inthebytes.accountservice.login.AuthorizationFilter;
import com.inthebytes.accountservice.service.LoginDetailsService;
import com.inthebytes.accountservice.service.LogoutService;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private LoginDetailsService loginDetailService;

	@Autowired
	private LogoutService logoutHandler;
	
	protected void configure(AuthenticationManagerBuilder auth) {
		auth.authenticationProvider(authenticationProvider());
	}

    @Override
    protected void configure(HttpSecurity security) throws Exception {

    	//TODO: Actively populate with restrictions
	    security.csrf().disable()
			    .cors()
		.and()
	 	.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)

	 	// Login
		.and()
	 	    .addFilter(new AuthenticationFilter(authenticationManager()))
	 	    .addFilter(new AuthorizationFilter(authenticationManager()))
			.authorizeRequests()
		    	.antMatchers(HttpMethod.POST, "/login").permitAll()
			    .antMatchers("/admin/**").hasRole("ADMIN")
		        .antMatchers( "/authenticated/**").authenticated()
			    .antMatchers("/public/**").permitAll()
	        
	    // End Goal for Security
//	        .antMatchers("/user").hasAnyRole("ADMIN","USER")
	        
	    //Bypass for development
	        .antMatchers("/user").permitAll()


	    // Logout
	    .and()
		    .logout()
		    .logoutUrl("/logout")
		    .addLogoutHandler(logoutHandler)
		    .logoutSuccessUrl("/")
		    .invalidateHttpSession(true)
		    .deleteCookies("remove")

        .and().httpBasic();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder(){
        return  new BCryptPasswordEncoder();
    }
    
    @Bean
	DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider dap = new DaoAuthenticationProvider();
		dap.setPasswordEncoder(passwordEncoder());
		dap.setUserDetailsService(loginDetailService);
		return dap;
	}
	
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
	    return super.authenticationManagerBean();
	    
	}
}