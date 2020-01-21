package com.kemal.spring.bd.dto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface VWNuevoCreditoRepository extends CrudRepository< VWNuevoCredito, Long> {
	public Page<VWNuevoCredito> findByNCodigocn(Integer nCodigoCns, Pageable pageable);
}
