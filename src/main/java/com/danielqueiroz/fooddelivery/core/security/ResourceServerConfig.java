package com.danielqueiroz.fooddelivery.core.security;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

@Configuration
@EnableWebSecurity
public class ResourceServerConfig extends WebSecurityConfigurerAdapter{

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.anyRequest().authenticated()
			.and()
			.oauth2ResourceServer().jwt();
	}
	
	
	@Bean
	public JwtDecoder jwtDecoder() {
		var sectretKey = new SecretKeySpec("food8284nasn2161ASdd37127394nansbcoa244234593473delivery".getBytes(), "HmacSHA256");
		
		return NimbusJwtDecoder.withSecretKey(sectretKey).build();
	}
	
	
}
