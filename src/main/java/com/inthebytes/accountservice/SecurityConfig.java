package com.inthebytes.accountservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import com.inthebytes.accountservice.login.AuthenticationFilter;
import com.inthebytes.accountservice.login.AuthorizationFilter;
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
			    .antMatchers("/user/admin/**").hasRole("ADMIN")
		        .antMatchers( "/user/authenticated/**").authenticated()
			    .antMatchers("/user/public/**").permitAll()
			    .antMatchers("/user/profile").authenticated()

	    // Logout
	    .and()
		    .logout()
		    .logoutUrl("/user/logout")
		    .addLogoutHandler(logoutHandler)
		    .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler())
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