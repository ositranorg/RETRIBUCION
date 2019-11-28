package com.kemal.spring.domain;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface CreditoRepository extends CrudRepository<Credito, Integer> {

	public List<Credito> findBySEstado(String sEstado);
	
	public Page<Credito> findAllBySEstado(String sEstado,Pageable pageable);	
}
