package com.kemal.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kemal.spring.domain.TipoVencimiento;
import com.kemal.spring.domain.TipoVencimientoRepository;

@Service
public class TipoVencimientoService {
	@Autowired
	TipoVencimientoRepository dao;

	

	public void save(TipoVencimiento tipoVencimiento) {
		dao.save(tipoVencimiento);
	}
	

}
