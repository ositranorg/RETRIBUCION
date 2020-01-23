package com.kemal.spring.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kemal.spring.bd.view.VWNuevoCredito;
import com.kemal.spring.bd.view.VWNuevoCreditoRepository;
import com.kemal.spring.domain.Credito;
import com.kemal.spring.domain.CreditoRepository;

@Service
public class CreditoService {

	@Autowired
	VWNuevoCreditoRepository vwNuevoCreditoRepository;
	@Autowired
	CreditoRepository creditoRepository;
	@Transactional(readOnly = false)
	public HashMap<String,Object> registrarCredito(List<Credito> lstCredito,Integer idcn) {
		
		
		lstCredito.stream().forEach((a) -> {
			creditoRepository.save(a);
		});
		
	
		HashMap<String, Object> rest=new HashMap<String, Object>();
		rest.put("lista", listarNuevosCreditos(idcn));
		rest.put("resultado", 1);
		rest.put("mensaje", "Se registró correctamente");
		return rest;
	}
	
	public List<VWNuevoCredito> listarNuevosCreditos(Integer idcn){
		List<VWNuevoCredito> pagedResult = vwNuevoCreditoRepository.findByNCodigocn(idcn);
		return pagedResult;
	}
	
	
}
