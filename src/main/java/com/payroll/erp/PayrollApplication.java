package com.payroll.erp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@ComponentScan(basePackages = {"com.payroll.erp"})
@SpringBootApplication
@EnableTransactionManagement
public class PayrollApplication {

	public static void main(String[] args) {
		SpringApplication.run(PayrollApplication.class, args);
	}

}
