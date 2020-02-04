package com.kemal.spring.bd.view;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface VWCreditoRegistradoRepository extends CrudRepository< VWCreditoRegistrado, Long> {
	public Page<VWCreditoRegistrado> findByNCodigocn(Integer nCodigoCn,Pageable pageable);
	public List<VWCreditoRegistrado> findByNCodigocnAndNcodigoEstadoNotIn(Integer nCodigoCn,Integer ncodigoEstado);
	
}