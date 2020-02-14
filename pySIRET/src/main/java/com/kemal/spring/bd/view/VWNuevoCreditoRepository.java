package com.kemal.spring.bd.view;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface VWNuevoCreditoRepository extends CrudRepository< VWNuevoCredito, Long> {
	public Page<VWNuevoCredito> findByNCodigocn(Integer nCodigoCns, Pageable pageable);
	
	public List<VWNuevoCredito> findByNCodigocn(Integer nCodigoCns);
	
}
