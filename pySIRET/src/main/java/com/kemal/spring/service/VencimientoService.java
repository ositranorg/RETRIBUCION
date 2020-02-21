package com.kemal.spring.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kemal.spring.domain.Concepto;
import com.kemal.spring.domain.Concesionario;
import com.kemal.spring.domain.Vencimiento;
import com.kemal.spring.domain.VencimientoRepository;
import com.kemal.spring.web.dto.CalendarioDto;
import com.kemal.spring.web.dto.Util;

@Service
public class VencimientoService {
	@Autowired
    VencimientoRepository dao;
	@Autowired
	Util util;
	@Transactional
    public void save(Vencimiento vencimiento) {
    	dao.save(vencimiento);
    }
	@Transactional
    public void deleteall() {
    	dao.deleteAll();
    }
    public List<Vencimiento> findAll() {
		return null;// dao.findBysEstadoOrderById("1");
	}
    
  
    
    public List<CalendarioDto> findByConceptoOrderById(Concesionario contribuyente) {
		return null;// util.fromLstToCalendarioDto(dao.findByContribuyenteOrderById(contribuyente));
	}
    public List<Vencimiento> findByConcesionarioAndConceptoAndSAnioPeriodoAndSEstado(Concesionario  t,Integer idConcepto,String anio) {
    	Concepto concepto=new Concepto(idConcepto);
		return  dao.findByConcesionarioAndConceptoAndSAnioPeriodoAndSEstado(t,concepto,anio,"1");
	}
    public Vencimiento findById(Integer id) {
		return dao.findById(id).get();// dao.findBysEstadoOrderById("1");
	}
    
}
