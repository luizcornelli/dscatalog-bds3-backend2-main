package com.devsuperior.dscatalog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration // Classe de configuração do spring
public class AppConfig {
	
	@Bean // o Bean é um componente do Spring, é uma anotation de metodo e não de classe
	//vai ser gerenciado pelo springboot esse componente, podendo ser injetado em outros componentes
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		
		return new BCryptPasswordEncoder();
	}
	
}
