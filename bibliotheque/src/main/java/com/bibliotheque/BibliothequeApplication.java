package com.bibliotheque;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BibliothequeApplication {

	@Bean
	public ModelMapper modelMapper()
	{
		return new ModelMapper();
	}




	public static void main(String[] args) {
		SpringApplication.run(BibliothequeApplication.class, args);
	}

}
