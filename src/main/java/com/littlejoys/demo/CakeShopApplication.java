package com.littlejoys.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.littlejoys")
@EnableJpaRepositories("com.littlejoys.dao")
@EntityScan("com.littlejoys.entity")
public class CakeShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(CakeShopApplication.class, args);
	}

}
