package com.lisophoria.projectTracker;

import com.lisophoria.projectTracker.auth.AuthenticationService;
import com.lisophoria.projectTracker.auth.RegisterRequest;
import com.lisophoria.projectTracker.user.Role;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ProjectTracker {

	public static void main(String[] args) {
		SpringApplication.run(ProjectTracker.class, args);
	}

}
