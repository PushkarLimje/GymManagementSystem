package com.Gym.GymMembershipManagementSystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class GymMembershipManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(GymMembershipManagementSystemApplication.class, args);
	}

}
