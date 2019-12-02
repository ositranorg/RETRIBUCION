package com.kemal.spring.service;

import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.kemal.spring.domain.Liquidacion;
import com.kemal.spring.domain.LiquidacionRepository;

@Repository
public class LiquidacionService {
	@Resource
	LiquidacionRepository liquidacionRepository;
	

	
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
}
