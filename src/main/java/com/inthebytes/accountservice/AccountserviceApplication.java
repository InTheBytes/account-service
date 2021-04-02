package com.inthebytes.accountservice;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@SpringBootApplication
@EnableEncryptableProperties
public class AccountserviceApplication {
	public static void main(String[] args) {
		SpringApplication.run(AccountserviceApplication.class, args);
	}
}
