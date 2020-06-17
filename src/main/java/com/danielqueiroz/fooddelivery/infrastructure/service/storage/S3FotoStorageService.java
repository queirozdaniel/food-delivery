package com.danielqueiroz.fooddelivery.infrastructure.service.storage;

import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.danielqueiroz.fooddelivery.core.storage.StorageProperties;
import com.danielqueiroz.fooddelivery.domain.service.FotoStorageService;

public class S3FotoStorageService implements FotoStorageService{

	@Autowired
	private AmazonS3 amazonS3;
	
	@Autowired
	private StorageProperties storageProperties;
	
	@Override
	public FotoRecuperada recuperar(String nomeArquivo) {
		String caminhoArquivo = getCaminhoArquivo(nomeArquivo);
		
		URL url = amazonS3.getUrl(storageProperties.getS3().getBucket(), caminhoArquivo);
		
		return FotoRecuperada.builder().url(url.toString()).build();
	}

	@Override
	public void armazenar(NovaFoto novaFoto) {
		String caminhoArquivo = getCaminhoArquivo(novaFoto.getNomeArquivo());
		var objectMetadata = new ObjectMetadata(); 
		objectMetadata.setContentType(novaFoto.getContentType());
		objectMetadata.setContentLength(novaFoto.getContentLength());
		
		try {
			var putObjectRequest = new PutObjectRequest(
					storageProperties.getS3().getBucket(), 
					caminhoArquivo, 
					novaFoto.getInputStream(),
					objectMetadata)
					.withCannedAcl(CannedAccessControlList.PublicRead);
			
			amazonS3.putObject(putObjectRequest);
		} catch (Exception e) {
			throw new StorageException("Não foi possível enviar arquivo para Amazon S3", e);
		}
	}

	private String getCaminhoArquivo(String nomeArquivo) {
		return String.format("%s/%s", storageProperties.getS3().getDiretorioFotos(),nomeArquivo);
	}

	@Override
	public void remover(String nomeArquivo) {
		try {
	        String caminhoArquivo = getCaminhoArquivo(nomeArquivo);

	        var deleteObjectRequest = new DeleteObjectRequest(
	                storageProperties.getS3().getBucket(), caminhoArquivo);

	        amazonS3.deleteObject(deleteObjectRequest);
	    } catch (Exception e) {
	        throw new StorageException("Não foi possível excluir arquivo na Amazon S3.", e);
	    }
		
	}

}
