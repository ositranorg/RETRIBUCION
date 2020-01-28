package com.kemal.spring.bd.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class VWNuevoCreditoService {
	@Autowired
	VWNuevoCreditoRepository creditoRepository;
	

	public Page<VWNuevoCredito> findByNCodigocn(Integer nCodigoCns, Pageable pageable) {

		return null;
	}
}
