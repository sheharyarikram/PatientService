package com.dialoguemd.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages={"com.dialoguemd.*"})
@ComponentScan({"com.dialoguemd.*"})
@EntityScan({"com.dialoguemd.*"})
@EnableJpaRepositories({"com.dialoguemd.*"})
public class DialoguemdApplication {
	/*
	 * To check if app is up, try: http://localhost:8080/actuator/health
	 */

	public static void main(String[] args) {
		SpringApplication.run(DialoguemdApplication.class, args);
	}
	
	//TODO - Implement https (SSL)
	//TODO - Add authentication

}
