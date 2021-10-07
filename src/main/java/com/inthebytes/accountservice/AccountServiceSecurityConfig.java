package com.inthebytes.accountservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import com.inthebytes.accountservice.auth.AuthenticationFilter;
import com.inthebytes.accountservice.auth.LoginDetailsService;
import com.inthebytes.accountservice.auth.LogoutService;
import com.inthebytes.stacklunch.security.StackLunchSecurityConfig;

@Configuration
@EnableWebSecurity
public class AccountServiceSecurityConfig extends StackLunchSecurityConfig {
	
	@Autowired
	private LoginDetailsService loginDetailService;

	@Autowired
	private LogoutService logoutHandler;
	

	@Override
	public HttpSecurity addSecurityConfigs(HttpSecurity security) throws Exception {
		return security
				.addFilter(new AuthenticationFilter(authenticationManager()))
					.authorizeRequests()
					    .antMatchers("/user/admin/**").hasRole("ADMIN")
				        .antMatchers( "/user/authenticated/**").authenticated()
					    .antMatchers("/user/public/**").permitAll()
					    .antMatchers("/user/profile").authenticated()
			    .and()
				    .logout()
				    .logoutUrl("/user/logout")
				    .addLogoutHandler(logoutHandler)
				    .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler())
				    .invalidateHttpSession(true)
				    .deleteCookies("remove")
				.and();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) {
		auth.authenticationProvider(authenticationProvider());
	}
    
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    
    @Bean
	DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider dap = new DaoAuthenticationProvider();
		dap.setPasswordEncoder(passwordEncoder());
		dap.setUserDetailsService(loginDetailService);
		return dap;
	}
}