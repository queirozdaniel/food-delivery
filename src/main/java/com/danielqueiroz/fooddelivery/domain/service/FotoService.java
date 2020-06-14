package com.danielqueiroz.fooddelivery.domain.service;

import java.io.InputStream;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.danielqueiroz.fooddelivery.domain.model.FotoProduto;
import com.danielqueiroz.fooddelivery.domain.repository.ProdutoRepository;
import com.danielqueiroz.fooddelivery.domain.service.FotoStorageService.NovaFoto;

@Service
public class FotoService {

	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private FotoStorageService fotoStorageService;
	
	@Transactional
	public FotoProduto salvar(FotoProduto foto, InputStream dadosArquivo) {
		Long restauranteId = foto.getProduto().getRestaurante().getId();
		Long produtoId = foto.getProduto().getId();
		Optional<FotoProduto> fotoExistente = produtoRepository.findFotoById(restauranteId, produtoId);

		String nomeNovoArquivo = fotoStorageService.gerarNomeArquivo(foto.getNomeArquivo());
		
		if (fotoExistente.isPresent()) {
			fotoStorageService.remover(fotoExistente.get().getNomeArquivo());
			produtoRepository.delete(fotoExistente.get());
		}
		
		foto.setNomeArquivo(nomeNovoArquivo);
		foto = produtoRepository.save(foto);
		produtoRepository.flush();
		
		NovaFoto novaFoto = NovaFoto.builder()
			.nomeArquivo(foto.getNomeArquivo())
			.inputStream(dadosArquivo)
			.build();
		
		fotoStorageService.armazenar(novaFoto);
		
		return foto; 
	}
	
}
