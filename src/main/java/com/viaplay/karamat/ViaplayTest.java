package com.viaplay.karamat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ViaplayTest {

	public static void main(String[] args) {
		SpringApplication.run(ViaplayTest.class, args);
	}
}
