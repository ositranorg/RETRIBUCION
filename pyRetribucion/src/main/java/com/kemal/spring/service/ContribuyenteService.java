package com.kemal.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kemal.spring.domain.Contribuyente;
import com.kemal.spring.domain.ContribuyenteRepository;

@Service
public class ContribuyenteService {
	@Autowired
	ContribuyenteRepository dao;

	public void save(Contribuyente contribuyente) {
		dao.save(contribuyente);
	}

	
}
