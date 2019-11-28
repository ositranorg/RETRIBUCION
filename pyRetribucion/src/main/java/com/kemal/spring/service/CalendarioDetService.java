package com.kemal.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kemal.spring.domain.TipoPeriodicidadDet;
import com.kemal.spring.domain.TipoPeriodicidadDetRepository;
import com.kemal.spring.domain.Concepto;
import com.kemal.spring.domain.Contrato;
import com.kemal.spring.web.dto.CalendarioDto;
import com.kemal.spring.web.dto.Util;

@Service
public class CalendarioDetService {
	@Autowired
    TipoPeriodicidadDetRepository dao;
	@Autowired
	Util util;
    public void save(TipoPeriodicidadDet calendarioDet) {
    	dao.save(calendarioDet);
    }
    
    public List<TipoPeriodicidadDet> findAll() {
		return dao.findBysEstadoOrderById("1");
	}
    
  
    
    public List<CalendarioDto> getCalendariosxContratoAndConceptoOrderById(Contrato contrato,Concepto concepto) {
		return util.fromLstToCalendarioDto(dao.findByContratoAndConceptoOrderById(contrato,concepto));
	}
}
