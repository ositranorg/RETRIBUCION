package com.kemal.spring.service;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.kemal.spring.domain.Liquidacion;
import com.kemal.spring.domain.LiquidacionRepository;
import com.kemal.spring.domain.procedures.PRC_LISTAR_LIQUIDACION;

@Repository
public class LiquidacionService {
	@Resource
	LiquidacionRepository liquidacionRepository;
	
	@Resource
	com.kemal.spring.domain.procedures.PRC_LISTAR_LIQUIDACIONRepository PRC_LISTAR_LIQUIDACIONRepository;
	
	@Transactional(readOnly = false)
	public void save(List<Liquidacion> liquidaciones) {
			
		liquidaciones.forEach((liquidacion)->{
			liquidacion.setId(liquidacionRepository.getNextSeriesId());
			liquidacionRepository.save(liquidacion);
		
			
		});
				
	}
	public List<Liquidacion> listarLiquidaciones(){
		return liquidacionRepository.findAll();
	}
	public  List<PRC_LISTAR_LIQUIDACION> listarLiquidacion(){
		return PRC_LISTAR_LIQUIDACIONRepository.listarLiquidacion();
	}
}
