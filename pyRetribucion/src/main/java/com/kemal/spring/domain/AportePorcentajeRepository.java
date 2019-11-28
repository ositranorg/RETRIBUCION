package com.kemal.spring.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AportePorcentajeRepository extends JpaRepository<AportePorcentaje, Integer>{
	
	 
	
	
		public List<AportePorcentaje> findByContribuyenteAndSEstado(Contribuyente contribuyente,String SEstado);
		public AportePorcentaje findByContribuyenteAndTipoRetribucionAndSEstado(Contribuyente contribuyente,TipoRetribucion tipoRetribucion,String SEstado);
		
}
