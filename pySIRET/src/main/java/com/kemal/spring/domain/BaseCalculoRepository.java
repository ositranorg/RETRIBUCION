package com.kemal.spring.domain;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface BaseCalculoRepository extends CrudRepository<BaseCalculo, Integer> {

	public List<BaseCalculo> findBySEstado(String sEstado);
	public Page<BaseCalculo> findByNcodigoApAndSEstado(Integer NcodigoAp,String sEstado,Pageable pageable);
	@Transactional
	@Modifying
	@Query("update BaseCalculo m set m.sEstado='0' where m.ncodigoAp=(:codaporte) ")
	public void updateEliminarContenedorBi(@Param("codaporte") int codaporte);
	
	public List<BaseCalculo> findByNcodigoApAndSEstado(Integer ncodigoAp,String sEstado);
}
