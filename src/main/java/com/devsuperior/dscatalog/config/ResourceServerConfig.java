package com.devsuperior.dscatalog.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
	
	@Autowired
	private Environment environment; // Objeto do ambiente de execução da aplicação 
	
	@Autowired
	private JwtTokenStore jwtTokenStore; 
	
	private static final String[] PUBLIC = {"/oauth/token", "/h2-console/**"};
	private static final String[] OPERADOR_OR_ADMIN = {"/products/**", "/categories/**"};
	private static final String[] ADMIN = {"/users/**"};

	@Override // verificar se o token é válido
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		
		resources.tokenStore(jwtTokenStore);
	}

	@Override// Configurando as rotas
	public void configure(HttpSecurity http) throws Exception {
		
		// H2 liberando frames
		if(Arrays.asList(environment.getActiveProfiles()).contains("test")) {
			http.headers().frameOptions().disable();
		}
		
		http.authorizeRequests()
		.antMatchers(PUBLIC).permitAll()
		.antMatchers(HttpMethod.GET, OPERADOR_OR_ADMIN).permitAll() // liberando apenas o 
														// get para todo mundo no vetor de operador e admin
		.antMatchers(OPERADOR_OR_ADMIN).hasAnyRole("OPERATOR", "ADMIN")
		.antMatchers(ADMIN).hasAnyRole("ADMIN")
		.anyRequest().authenticated();
		
		
	}

	
}
