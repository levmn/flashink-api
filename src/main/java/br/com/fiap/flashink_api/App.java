package br.com.fiap.flashink_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@EnableCaching
@OpenAPIDefinition(
	info = @Info(
		title = "API FlashInk", 
		version = "v1", 
		description = "API do SaaS de artes flash de tattoos",
		contact = @Contact(name = "Levi Magni | Allan Moreira", email = "flashink@email.com")
	)
)
public class App {

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

}
