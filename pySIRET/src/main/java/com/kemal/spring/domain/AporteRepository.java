package com.kemal.spring.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AporteRepository  extends JpaRepository<Aporte, Integer>{
	public List<Aporte> findBysEstadoOrderById(String sEstado);
	public Aporte findByTipoPeriodicidadAndTipoRetribucionAndSMesPeriodoAndSAnioPeriodoAndAporteTipoAndContribuyente(TipoPeriodicidad tipoPeriodicidad,TipoRetribucion tipoRetribucion,String SMesPeriodo,String SanioPeriodo,AporteTipo aporteTipo,Concesionario contribuyente);

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
