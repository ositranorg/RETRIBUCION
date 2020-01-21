package com.kemal.spring.service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.kemal.spring.domain.Contribuyente;
import com.kemal.spring.domain.ContribuyenteRepository;
import com.kemal.spring.domain.Representante;
import com.kemal.spring.domain.RepresentanteRepository;

@Service
public class RepresentanteService {
	@Autowired
	RepresentanteRepository representanteRepository;
	
	@Autowired
	ContribuyenteRepository contribuyenteRepository;
	
	@Transactional
	public HashMap<String,Object> registrarRepresentante(Representante representante,int pagina,int totalRegistrosPorPagina) {
		Contribuyente contribuyente = contribuyenteRepository.findBySruc(representante.getContribuyente().getSruc());

		System.out.println("ID REPRESNTANTE: " + representante.getId());
		representante.setId(representanteRepository.getNextSeriesId());
		representante.getContribuyente().setId(contribuyente.getId());
		System.out.println("ID CONTRIBUYENTE: " + contribuyente.getId());
		//System.out.println("ID TIPO DOCUMENTO: " + representante.getTipoDocumento().getId());
		representanteRepository.save(representante);
		HashMap<String, Object> rest = new HashMap<String, Object>();
		//rest.put("lista", listarRepresentantes(contribuyente.getId()));
		rest.put("lista", listarRepresentantesByRucPaginado(representante.getContribuyente().getSruc(),pagina,totalRegistrosPorPagina));
		rest.put("resultado", 1);
		rest.put("mensaje", "Se registró correctamente");
		return rest;
	}
	public List<Representante> listarRepresentantes(Integer idContribuyente){
		return representanteRepository.findAllByIdContribuyente(idContribuyente);
	}
	public List<Representante> listarRepresentantesByRuc(String ruc){
		Contribuyente contribuyente = contribuyenteRepository.findBySruc(ruc);
		return representanteRepository.findAllByIdContribuyente(contribuyente.getId());
	}
	public HashMap<String, Object> listarRepresentantesByRucPaginado(String ruc,int pagina,int totalRegistrosPorPagina){
		Contribuyente contribuyente = contribuyenteRepository.findBySruc(ruc);
		Pageable pageable = PageRequest.of(pagina-1, totalRegistrosPorPagina);
		Page<Representante> pagedResult = representanteRepository.findAllByIdContribuyentePaginacion(contribuyente.getId(),pageable);
		HashMap<String, Object> rest = new HashMap<>();
		if (pagedResult.hasContent()) {
			rest.put("totalRegistros", pagedResult.getTotalElements());
			rest.put("representantes", pagedResult.getContent());
			return rest;
		} else {
			rest.put("totalRegistros", 0);
			rest.put("representantes", null);
			return rest;
		}
	}
	public HashMap<String, Object> eliminarRepresentantesByRucPaginado(String ruc,int pagina,int totalRegistrosPorPagina,int idRepresentante){
		
		Optional<Representante> representante = representanteRepository.findById(idRepresentante);
		representante.get().setNEstado(0);
		representanteRepository.save(representante.get());
		
		HashMap<String, Object> rest = new HashMap<>();
		rest.put("lista", listarRepresentantesByRucPaginado(representante.get().getContribuyente().getSruc(),pagina,totalRegistrosPorPagina));
		rest.put("resultado", 1);
		rest.put("mensaje", "Se eliminó correctamente");
		return rest;
	}
}
