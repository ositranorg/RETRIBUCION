package com.kemal.spring.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ArchivoRepository extends JpaRepository<Archivo, Integer>{	 
	public List<Archivo> findByModuloAndCodigoPadreAndTipoDocumentoAndSEstadoOrderById(String modulo,Integer codigoPadre,String tipoDocumento,String sEstado); 
}
