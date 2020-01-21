package com.kemal.spring.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

	public List<Contribuyente> listaContribuyente() {
		return (List<Contribuyente>) dao.findAll();
	}

	public Contribuyente obtenerContribuyente(Integer id) {
		return dao.findById(id).get();
	}
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> listaContribuyentePaginacion(int pagina, int totalRegistrosPorPagina) {
		Pageable pageable = PageRequest.of(pagina-1, totalRegistrosPorPagina);
		Page<Contribuyente> pagedResult = dao.findAll(pageable);

		HashMap<String, Object> rest = new HashMap<>();
		if (pagedResult.hasContent()) {
			rest.put("totalRegistros", pagedResult.getTotalElements());
			rest.put("contribuyentes", pagedResult.getContent());
			return rest;
		} else {
			rest.put("totalRegistros", 0);
			rest.put("contribuyentes", null);
			return rest;
		}
	}
	public HashMap<String,Object> actualizarContribuyenteByRuc(Contribuyente contribuyente){
		Contribuyente cont = dao.findBySruc(contribuyente.getSruc());
		cont.setSCorreo(contribuyente.getSCorreo());
		cont.setSTelefono(contribuyente.getSTelefono());
		dao.save(cont);
		HashMap<String, Object> rest = new HashMap<>();
		rest.put("resultado", 1);
		rest.put("mensaje", "Se actualizó correctamente");
		return rest;
	}

}
