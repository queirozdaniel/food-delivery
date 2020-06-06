package com.danielqueiroz.fooddelivery.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.danielqueiroz.fooddelivery.domain.exception.EntidadeEmUsoException;
import com.danielqueiroz.fooddelivery.domain.exception.FormaPagamentoNaoEncontradaException;
import com.danielqueiroz.fooddelivery.domain.model.FormaPagamento;
import com.danielqueiroz.fooddelivery.domain.repository.FormaPagamentoRepository;

@Service
public class FormaPagamentoService {

	private static final String MSG_FORMA_PAGAMENTO_EM_USO = "Forma de pagamento de código %d não pode ser removida, pois está em uso";

	@Autowired
	private FormaPagamentoRepository formaPagamentoRepository;

	@Transactional
	public FormaPagamento salvar(FormaPagamento formaPagamento) {
		return formaPagamentoRepository.save(formaPagamento);
	}

	@Transactional
	public void deletar(Long id) {
		try {
			formaPagamentoRepository.deleteById(id);
			formaPagamentoRepository.flush();

		} catch (EmptyResultDataAccessException e) {
			throw new FormaPagamentoNaoEncontradaException(id);

		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_FORMA_PAGAMENTO_EM_USO, id));
		}
	}

	public List<FormaPagamento> buscarTodos() {
		return formaPagamentoRepository.findAll();
	}

	public FormaPagamento buscarPorId(Long id) {
		return formaPagamentoRepository.findById(id).orElseThrow(() -> new FormaPagamentoNaoEncontradaException(id));
	}

}
