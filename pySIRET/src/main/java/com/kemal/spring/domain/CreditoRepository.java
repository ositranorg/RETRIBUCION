package com.kemal.spring.domain;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CreditoRepository extends CrudRepository<Credito, Integer> {

	public List<Credito> findByEstado(Estado estado);
	
	public Page<Credito> findAllByEstado(Estado estado,Pageable pageable);	
	
	public Page<Credito> findByNCodigoCnAndEstadoNotIn(Integer nCodigoCn,Estado estado, Pageable pageable);
}
