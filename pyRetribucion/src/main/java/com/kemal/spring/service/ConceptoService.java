package com.kemal.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kemal.spring.domain.Concepto;
import com.kemal.spring.domain.ConceptoRepository;

@Service
public class ConceptoService {
	@Autowired
	ConceptoRepository dao;

	public List<Concepto> findAll() {
		return dao.findAll();
	}

	public void save(Concepto concepto) {
		dao.save(concepto);
	}
	
	public List<Concepto>  findConceptosBaseImponible(){
		return dao.findConceptosBaseImponible();
	}
}
