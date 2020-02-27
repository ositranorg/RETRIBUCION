package com.kemal.spring.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kemal.spring.domain.Concepto;
import com.kemal.spring.domain.Concesionario;
import com.kemal.spring.domain.TipoPeriodicidad;
import com.kemal.spring.domain.TipoPeriodicidadRepository;
import com.kemal.spring.domain.TipoRetribucion;
import com.kemal.spring.domain.TipoRetribucionRepository;
import com.kemal.spring.domain.Vencimiento;
import com.kemal.spring.domain.VencimientoRepository;
import com.kemal.spring.web.dto.CalendarioDto;
import com.kemal.spring.web.dto.Util;

@Service
public class VencimientoService {
	@Autowired
    VencimientoRepository dao;
	@Autowired
	TipoPeriodicidadRepository daoTipoPeriodicidad;
	@Autowired
	TipoRetribucionRepository daoTipoRetribucion;
	
	@Autowired
	Util util;
	@Transactional
    public void save(Vencimiento vencimiento) {
    	dao.save(vencimiento);
    }
	@Transactional
    public void save(List<Vencimiento> vencimiento) {
		for (Vencimiento vencimiento2 : vencimiento) {
			dao.save(vencimiento2);
		}
    	
    }
	@Transactional
    public void deleteall() {
    	dao.deleteAll();
    }
    public List<Vencimiento> findAll() {
		return null;// dao.findBysEstadoOrderById("1");
	}
	@Transactional
    public void deleteSAnioPeriodoAndConcepto(String sAnioPeriodo,Integer idconcepto){
    	List<Vencimiento> s= dao.findBySAnioPeriodoAndSEstado(sAnioPeriodo, "1");
    	for (Vencimiento vencimiento : s) {
			dao.deleteById(vencimiento.getId());
		}
    }
    public List<Vencimiento> getVencimiento(Integer  idconcesion,Integer idtipoPeriodicidad,Integer idtipoRetribucion,String anio) {
		return  dao.getVencimiento
				(idconcesion,idtipoPeriodicidad,idtipoRetribucion,anio,"1");
	}
    public Vencimiento findById(Integer id) {
		return dao.findById(id).get();// dao.findBysEstadoOrderById("1");
	}
    public List<Vencimiento> findBySAnioPeriodoAndConcepto(String  sAnioPeriodo,Integer idconcepto) {
    	return dao.findBySAnioPeriodoAndSEstado(sAnioPeriodo,"1");
	}
	
}
