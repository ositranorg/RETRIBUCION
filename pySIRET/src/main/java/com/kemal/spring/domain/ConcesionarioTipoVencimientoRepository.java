package com.kemal.spring.domain;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConcesionarioTipoVencimientoRepository   extends CrudRepository<ConcesionarioTipoVencimiento, Integer> {
	@Query("SELECT X FROM ConcesionarioTipoVencimiento c Where c.concesionario.id=:idConcesionario and c.tipoVencimiento.id=:idTipoVencimiento")
	public ConcesionarioTipoVencimiento findByID(Integer  idConcesionario,Integer idTipoVencimiento);
}


