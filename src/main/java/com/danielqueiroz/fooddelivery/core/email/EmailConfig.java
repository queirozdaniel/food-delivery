package com.danielqueiroz.fooddelivery.core.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.danielqueiroz.fooddelivery.core.email.EmailProperties.Implementacao;
import com.danielqueiroz.fooddelivery.domain.service.EnvioEmailService;
import com.danielqueiroz.fooddelivery.infrastructure.service.email.FakeEnvioEmailService;
import com.danielqueiroz.fooddelivery.infrastructure.service.email.SmtpEnvioEmailService;

@Configuration
public class EmailConfig {

	@Autowired
	private EmailProperties emailProperties;
	
	@Bean
	public EnvioEmailService envioEmailService() {
		if (Implementacao.FAKE.equals(emailProperties.getImpl())) {
			return new FakeEnvioEmailService();
		} else {
			return new SmtpEnvioEmailService();
		}
		
	}
	
}
