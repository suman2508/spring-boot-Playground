package com.bleedcode.practical_1;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.bleedcode.practical_1.baker.CakeBaker;

@SpringBootApplication
public class Practical1Application {

	public static void main(String[] args) {
		SpringApplication.run(Practical1Application.class, args);
	}

	@Bean
	CommandLineRunner run(CakeBaker cakeBaker){
		return args -> {
			cakeBaker.bakeCake();	
		};
	}

}
