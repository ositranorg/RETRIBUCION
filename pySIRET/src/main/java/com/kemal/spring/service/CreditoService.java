package com.kemal.spring.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kemal.spring.bd.view.VWCreditoRegistrado;
import com.kemal.spring.bd.view.VWCreditoRegistradoRepository;
import com.kemal.spring.bd.view.VWNuevoCredito;
import com.kemal.spring.bd.view.VWNuevoCreditoRepository;
import com.kemal.spring.domain.Credito;
import com.kemal.spring.domain.CreditoRepository;
import com.kemal.spring.domain.Estado;

@Service
public class CreditoService {

	@Autowired
	VWNuevoCreditoRepository vwNuevoCreditoRepository;
	@Autowired
	CreditoRepository creditoRepository;
	
	@Autowired
	VWCreditoRegistradoRepository creditoRegistradoRepository;
	
	
	@Transactional(readOnly = false)
	public HashMap<String,Object> save(List<Credito> lstCredito,String usuario) {
		
		
		lstCredito.stream().forEach((a) -> {
			
			
			a.setSUsuRegistra(usuario);
			a.setDfecRegistro(new Date());
			Estado estado=new Estado();
			estado.setId(1);
			a.setEstado(estado);
			creditoRepository.save(a);
		});
		
	
		HashMap<String, Object> rest=new HashMap<String, Object>();
		rest.put("resultado", 1);
		rest.put("mensaje", "Se registró correctamente");
		return rest;
	}
	
	public List<VWNuevoCredito> listarNuevosCreditos(Integer idcn){
		List<VWNuevoCredito> pagedResult = vwNuevoCreditoRepository.findByNCodigocn(idcn);
		return pagedResult;
	}
	public Page<Credito> findByNCodigocn(Integer nCodigoCns, Pageable pageable) {

		try {
			Estado estado= new Estado();
			estado.setId(0);
			return creditoRepository.findByNCodigoCnAndEstadoNotIn( nCodigoCns,estado,pageable);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	public Page<VWCreditoRegistrado> findCreditosRegistrados(Integer nCodigoCn, Pageable pageable) {

		try {
			return creditoRegistradoRepository.findByNCodigocn(nCodigoCn, pageable);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public List<VWCreditoRegistrado> findCreditosRegistrados(Integer nCodigoCn) {

		try {
			return creditoRegistradoRepository.findByNCodigocnAndNcodigoEstadoNotIn(nCodigoCn,0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	@Transactional(readOnly = false)
	public Integer delete(Integer idCredito,String usuario)throws Exception {
		Credito d=creditoRepository.findById(idCredito).get();
		Estado e=new Estado();
		e.setId(0);
		d.setEstado(e);
		creditoRepository.save(d);
	
		return 1;
	}
	
	
}
