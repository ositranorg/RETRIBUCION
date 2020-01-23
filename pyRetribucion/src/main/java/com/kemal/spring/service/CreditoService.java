package com.kemal.spring.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kemal.spring.bd.view.VWNuevoCredito;
import com.kemal.spring.bd.view.VWNuevoCreditoRepository;

@Service
public class CreditoService {

	@Autowired
	VWNuevoCreditoRepository vwNuevoCreditoRepository;

	@Transactional
	public HashMap<String,Object> registrarRepresentante(int idcn,int pagina,int totalRegistrosPorPagina) {

		HashMap<String, Object> rest=new HashMap<String, Object>();
		//rest.put("lista", listarNuevosCreditos(idcn,pagina,totalRegistrosPorPagina));
		rest.put("resultado", 1);
		rest.put("mensaje", "Se registró correctamente");
		return rest;
	}
	
	public List<VWNuevoCredito> listarNuevosCreditos(Integer idcn){
		List<VWNuevoCredito> pagedResult = vwNuevoCreditoRepository.findByNCodigocn(idcn);
//		pagedResult.addAll(vwNuevoCreditoRepository.findByNCodigocn(idcn));
//		pagedResult.addAll(vwNuevoCreditoRepository.findByNCodigocn(idcn));
		return pagedResult;
	
	}
	public HashMap<String, Object> eliminarRepresentantesByRucPaginado(String ruc,int pagina,int totalRegistrosPorPagina,int idRepresentante){
		
		//Optional<Representante> representante = vwNuevoCreditoRepository.findById(idRepresentante);
		//representante.get().setNEstado(0);
		//representanteRepository.save(representante.get());
		
		HashMap<String, Object> rest = new HashMap<>();
		//rest.put("lista", listarRepresentantesByRucPaginado(representante.get().getContribuyente().getSruc(),pagina,totalRegistrosPorPagina));
		rest.put("resultado", 1);
		rest.put("mensaje", "Se eliminó correctamente");
		return rest;
	}
}
