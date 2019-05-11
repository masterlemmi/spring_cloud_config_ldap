package com.example.demo.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@SpringBootApplication
@EnableConfigServer
@RestController
public class ConfigServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConfigServerApplication.class, args);
	}

	@GetMapping("/user")
	public Object user(Principal principal) {
		if (principal != null && principal instanceof  UsernamePasswordAuthenticationToken){
			return ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
		}
		return principal;
	}

}
