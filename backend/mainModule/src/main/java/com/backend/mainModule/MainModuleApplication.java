package com.backend.mainModule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class MainModuleApplication {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.configure().directory("backend/mainModule/src/main/java/com/backend/mainModule/.env").load();

		SpringApplication.run(MainModuleApplication.class, args);
	}

}
