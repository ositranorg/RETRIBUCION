package com.kemal.spring.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kemal.spring.domain.TipoPeriodicidad;
import com.kemal.spring.domain.Contrato;
import com.kemal.spring.domain.ContratoRepository;
import com.kemal.spring.domain.Contribuyente;

@Service
public class ContratoService {
	@Autowired
	ContratoRepository dao;
	public List<Contrato> findByContribuyente(Contribuyente contribuyente) {
		return dao.findByContribuyente(contribuyente);
	}
	public Contrato findByCalendarioAndContribuyente(TipoPeriodicidad calendario,Contribuyente contribuyente) {
		return dao.findByCalendarioAndContribuyente(calendario,contribuyente);
	}
	public Optional<Contrato> findById(Integer id) {
		return dao.findById(id);
	}
	public void save(Contrato Contrato) {
		dao.save(Contrato);
	}
	

}
