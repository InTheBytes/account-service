package com.inthebytes.accountservice;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.inthebytes.stacklunch.UniversalStackLunchConfiguration;

@Configuration
@Import({UniversalStackLunchConfiguration.class, 
	AccountServiceSecurityConfig.class,
	AccountserviceApplication.class})
public class AccountServiceTestConfig {

}
