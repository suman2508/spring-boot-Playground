package com.bleedcode.bean_concepts_demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.bleedcode.bean_concepts_demo.components.PaymentProcessor;
import com.bleedcode.bean_concepts_demo.config.ScopeTester;

@SpringBootApplication
public class BeanConceptsDemoApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(BeanConceptsDemoApplication.class, args);

		PaymentProcessor processor = context.getBean(PaymentProcessor.class);
		processor.processPayment(1500.00);
		
		ScopeTester tester = context.getBean(ScopeTester.class);
		tester.testScopes();
	}

}
