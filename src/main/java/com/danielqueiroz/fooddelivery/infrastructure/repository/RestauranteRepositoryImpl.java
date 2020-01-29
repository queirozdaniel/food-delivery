package com.danielqueiroz.fooddelivery.infrastructure.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.danielqueiroz.fooddelivery.domain.model.Restaurante;
import com.danielqueiroz.fooddelivery.domain.repository.RestauranteRepository;

public class RestauranteRepositoryImpl implements RestauranteRepository{

	@Override
	public List<Restaurante> findAll() {
		return this.findAll();
	}

	@Override
	public List<Restaurante> findAll(Sort sort) {
		return this.findAll(sort);
	}

	@Override
	public List<Restaurante> findAllById(Iterable<Long> ids) {
		return this.findAllById(ids);
	}

	@Override
	public <S extends Restaurante> List<S> saveAll(Iterable<S> entities) {
		return null;
	}

	@Override
	public void flush() {
		
	}

	@Override
	public <S extends Restaurante> S saveAndFlush(S entity) {
		return null;
	}

	@Override
	public void deleteInBatch(Iterable<Restaurante> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAllInBatch() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Restaurante getOne(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Restaurante> List<S> findAll(Example<S> example) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Restaurante> List<S> findAll(Example<S> example, Sort sort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Restaurante> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Restaurante> S save(S entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Restaurante> findById(Long id) {
		return this.findById(id);
	}

	@Override
	public boolean existsById(Long id) {
		return false;
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Restaurante entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll(Iterable<? extends Restaurante> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <S extends Restaurante> Optional<S> findOne(Example<S> example) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Restaurante> Page<S> findAll(Example<S> example, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Restaurante> long count(Example<S> example) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public <S extends Restaurante> boolean exists(Example<S> example) {
		// TODO Auto-generated method stub
		return false;
	}

}
