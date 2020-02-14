package com.kemal.spring.domain;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface CondicionBCRepository extends CrudRepository<CondicionBC, Integer> {
	public Page<CondicionBC> findByNCodigoCnsAndSEstadoNotOrderBySEstadoAscDfecRegistroDesc(Integer nCodigoCns, String sEstado, Pageable pageable);

	
	
	
	public CondicionBC findByNCodigoCnsAndSEstado(Integer nCodigoCns,String sEstado);
	public List<CondicionBC> findByNCodigoCnsAndSEstadoOrderByNCodigoCns(Integer nCodigoCns,String sEstado);
}
