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
import com.inthebytes.accountservice.service.LoginDetailsService;
import com.inthebytes.accountservice.service.LogoutService;

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
    	security
		.csrf()
			.disable()
	 	.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)

	 	// Login
		.and()
	 	    .addFilter(new AuthenticationFilter(authenticationManager()))
			.authorizeRequests()
			.antMatchers(HttpMethod.POST, "/login").permitAll()
			
		// End Point Security	
			.antMatchers("/public").permitAll()
	        .antMatchers("/authticated").authenticated()
	        .antMatchers("/admin").hasRole("ADMIN")
	        .antMatchers("/user").hasAnyRole("ADMIN","USER")
	        .and().httpBasic()

		// Logout
		.and()
			.logout()
			.logoutUrl("/logout")
			.addLogoutHandler(logoutHandler)
			.logoutSuccessUrl("/")
			.invalidateHttpSession(true)
			.deleteCookies("remove");
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