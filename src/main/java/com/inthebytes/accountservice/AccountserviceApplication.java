package com.inthebytes.accountservice;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.inthebytes.stacklunch.StackLunchApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@EnableJpaRepositories
@OpenAPIDefinition(info =
	@Info(title = "Account Service API", version = "1.0", description = "Documentation Account Service API v1.0")
)
public class AccountserviceApplication {

	public static void main(String[] args) {
		StackLunchApplication.run(AccountserviceApplication.class, args);
	}

}