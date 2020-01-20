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

}
