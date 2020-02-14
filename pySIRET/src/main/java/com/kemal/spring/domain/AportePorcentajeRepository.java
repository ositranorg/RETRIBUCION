package com.kemal.spring.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AportePorcentajeRepository extends JpaRepository<AportePorcentaje, Integer>{
	
	 
	
	
		public List<AportePorcentaje> findByContribuyenteAndSEstado(Concesionario contribuyente,String SEstado);
		public AportePorcentaje findByContribuyenteAndTipoRetribucionAndSEstado(Concesionario contribuyente,TipoRetribucion tipoRetribucion,String SEstado);
		
}
