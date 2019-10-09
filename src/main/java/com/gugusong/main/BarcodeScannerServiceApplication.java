package com.gugusong.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.gugusong.barcode_scanner_service")
public class BarcodeScannerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BarcodeScannerServiceApplication.class, args);
	}

}
