package com.kemal.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kemal.spring.domain.AportePorcentaje;
import com.kemal.spring.domain.AportePorcentajeRepository;
import com.kemal.spring.domain.Concesionario;
import com.kemal.spring.domain.TipoRetribucion;
@Service
public class AportePorcentajeService {
	@Autowired
	AportePorcentajeRepository dao;

	public List<AportePorcentaje> findAll(int idcontribuyente){	
		
		Concesionario x=new Concesionario();
		x.setId(idcontribuyente);
		return dao.findByContribuyenteAndSEstado(x,"1");
	}
	
	public AportePorcentaje findByContribuyenteAndTipoRetribucionAndSEstado(int idcontribuyente,int idTipoRetribucion){	
		
		Concesionario x=new Concesionario();
		x.setId(idcontribuyente);
		TipoRetribucion t=new TipoRetribucion();
		t.setId(idTipoRetribucion);
		return dao.findByContribuyenteAndTipoRetribucionAndSEstado(x,t,"1");
	}
	
	
	
	public void save(AportePorcentaje aportePorcentaje) {
		dao.save(aportePorcentaje);
	}
}
