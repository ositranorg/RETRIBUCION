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
import com.kemal.spring.domain.TipoDocumento;
import com.kemal.spring.domain.TipoDocumentoRepository;

@Service
public class RepresentanteService {
	@Autowired
	RepresentanteRepository representanteRepository;
	
	@Autowired
	ContribuyenteRepository contribuyenteRepository;
	
	@Autowired
	TipoDocumentoRepository tipoDocumentoRepository;
	
	@Transactional
	public HashMap<String,Object> registrarRepresentante(Representante representante,int pagina,int totalRegistrosPorPagina) {
		Contribuyente contribuyente = contribuyenteRepository.findBySruc(representante.getContribuyente().getSruc());

		System.out.println("ID REPRESNTANTE: " + representante.getId());
		if(representante.getId()==null) {
			System.out.println("INGRESE INSERTAR");			
			TipoDocumento tipoDocumentoModel = tipoDocumentoRepository.findById(representante.getTipoDocumento().getId()).get();
			representante.setContribuyente(contribuyente);
			representante.setTipoDocumento(tipoDocumentoModel);			
			representante.setId(representanteRepository.getNextSeriesId());	
			representanteRepository.save(representante);
		}else {
			System.out.println("INGRESE ACTUALIZAR");
			
			Representante representanteModel = representanteRepository.findById(representante.getId()).get();
			TipoDocumento tipoDocumentoModel = tipoDocumentoRepository.findById(representante.getTipoDocumento().getId()).get();
			
			representanteModel.setContribuyente(contribuyente);
			representanteModel.setTipoDocumento(tipoDocumentoModel);
			representanteModel.setDFechaInicio(representante.getDFechaInicio());
			representanteModel.setSNombres(representante.getSNombres());
			representanteModel.setSApePaterno(representante.getSApePaterno());
			representanteModel.setSApeMaterno(representante.getSApeMaterno());
			representanteModel.setSCargo(representante.getSCargo());
			representanteModel.setSNumero(representante.getSNumero());
			representanteRepository.save(representanteModel);
		}
		
		System.out.println("ID CONTRIBUYENTE: " + contribuyente.getId());
		//System.out.println("ID TIPO DOCUMENTO: " + representante.getTipoDocumento().getId());
		
		HashMap<String, Object> rest = new HashMap<String, Object>();
		System.out.println("INGRESE ACA");
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
		System.out.println("EL RUC ES: " + ruc);
		Contribuyente contribuyente = contribuyenteRepository.findBySruc(ruc);
		Pageable pageable = PageRequest.of(pagina-1, totalRegistrosPorPagina);
		System.out.println("INGRESE A LA LISTA");
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
