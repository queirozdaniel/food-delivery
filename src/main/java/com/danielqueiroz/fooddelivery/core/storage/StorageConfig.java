package com.danielqueiroz.fooddelivery.core.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.danielqueiroz.fooddelivery.core.storage.StorageProperties.TipoStorage;
import com.danielqueiroz.fooddelivery.domain.service.FotoStorageService;
import com.danielqueiroz.fooddelivery.infrastructure.service.storage.LocalFotoStorageService;
import com.danielqueiroz.fooddelivery.infrastructure.service.storage.S3FotoStorageService;

@Configuration
public class StorageConfig {

	@Autowired
	private StorageProperties storageProperties;
	
	@Bean
	public AmazonS3 amazonS3() {
		var credentials = new BasicAWSCredentials(
				storageProperties.getS3().getIdChaveAcesso(), 
				storageProperties.getS3().getIdChaveAcessoSecreta());
		
		return AmazonS3ClientBuilder.standard()
				.withCredentials(new AWSStaticCredentialsProvider(credentials))
				.withRegion(storageProperties.getS3().getRegiao())
				.build();
	}

	@Bean
	public FotoStorageService fotoStorageService() {
		if (TipoStorage.S3.equals(storageProperties.getTipo())) {
			return new S3FotoStorageService();
		} else {
			return new LocalFotoStorageService();
		}
	}
	
}
