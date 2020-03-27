package com.kemal.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kemal.spring.domain.ConcesionarioTipoVencimiento;
import com.kemal.spring.domain.ConcesionarioTipoVencimientoRepository;

@Service
public class ConcesionarioTipoVencimientoService {
	@Autowired
	ConcesionarioTipoVencimientoRepository dao;
	public ConcesionarioTipoVencimiento findById(Integer idConcesionario,Integer idTipoVencimiento) {
			return dao.findByID(idConcesionario, idTipoVencimiento);
	}
	public void deleteAll() {
		dao.deleteAll();
	}
	public void save(ConcesionarioTipoVencimiento	 contribuyente) {
		dao.save(contribuyente);
	}

	public List<ConcesionarioTipoVencimiento> listaContribuyente() {
		return (List<ConcesionarioTipoVencimiento>) dao.findAll();
	}

	public ConcesionarioTipoVencimiento obtenerContribuyente(Integer id) {
		return dao.findById(id).get();
	}
	

}
