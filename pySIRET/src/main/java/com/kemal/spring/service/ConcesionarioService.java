package com.kemal.spring.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.kemal.spring.domain.Concesionario;
import com.kemal.spring.domain.ConcesionarioRepository;

@Service
public class ConcesionarioService {
	@Autowired
	ConcesionarioRepository dao;
	public Concesionario findById(Integer id) {
			return dao.findById(id).get();
	}
	public void deleteAll() {
		dao.deleteAll();
	}
	public void save(Concesionario contribuyente) {
		dao.save(contribuyente);
	}

	public List<Concesionario> listaContribuyente() {
		return (List<Concesionario>) dao.findAll();
	}

	public Concesionario obtenerContribuyente(Integer id) {
		return dao.findById(id).get();
	}
	public HashMap<String, Object> listaContribuyentePaginacion(int pagina, int totalRegistrosPorPagina) {
		Pageable pageable = PageRequest.of(pagina-1, totalRegistrosPorPagina);
		Page<Concesionario> pagedResult = dao.findAll(pageable);

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
	public HashMap<String,Object> actualizarContribuyenteByRuc(Concesionario contribuyente){
		Concesionario cont = dao.findBySruc(contribuyente.getSruc());
		cont.setSCorreo(contribuyente.getSCorreo());
		cont.setSTelefono(contribuyente.getSTelefono());
		dao.save(cont);
		HashMap<String, Object> rest = new HashMap<>();
		rest.put("resultado", 1);
		rest.put("mensaje", "Se actualizó correctamente");
		return rest;
	}

}
