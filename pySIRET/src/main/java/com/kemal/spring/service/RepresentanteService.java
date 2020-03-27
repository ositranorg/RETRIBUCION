package com.kemal.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kemal.spring.bd.view.VWRepresentante;
import com.kemal.spring.domain.RepresentanteRepository;

@Service
public class RepresentanteService {
	@Autowired
	RepresentanteRepository representanteRepository;
	public List<VWRepresentante> listarRepresentantes(Integer concesionario){
		return representanteRepository.findByIdConcesionario(concesionario);
	}
}
