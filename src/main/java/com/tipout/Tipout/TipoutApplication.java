package com.tipout.Tipout;

import com.tipout.Tipout.security.RSAKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(RSAKeyProperties.class)
public class TipoutApplication {

	public static void main(String[] args) {
		SpringApplication.run(TipoutApplication.class, args);
	}

}
