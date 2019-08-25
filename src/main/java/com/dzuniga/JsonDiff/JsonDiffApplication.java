package com.dzuniga.JsonDiff;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class JsonDiffApplication {
	public static void main(String[] args) {
		SpringApplication.run(JsonDiffApplication.class, args);
	}
}
