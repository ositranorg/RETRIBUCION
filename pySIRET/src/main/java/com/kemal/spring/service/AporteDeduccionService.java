package com.kemal.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kemal.spring.domain.Aporte;
import com.kemal.spring.domain.AporteDeduccion;
import com.kemal.spring.domain.AporteDeduccionRepository;
import com.kemal.spring.domain.Archivo;
import com.kemal.spring.domain.ArchivoRepository;

@Service
public class AporteDeduccionService {
	@Autowired
	ArchivoRepository archivodao;
	@Autowired
	AporteDeduccionRepository dao;

	
	 
	public List<AporteDeduccion> getListaSaldo(Integer idAporte) {

		try {
			return dao.getListaSaldo(idAporte, "1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	@Transactional(readOnly = false)
	public void save(AporteDeduccion deducciones, List<Archivo> archivos) {
		dao.save(deducciones);
		if (null != archivos&&archivos.size()>0) {
			archivos.stream().forEach((m) -> {
				m.setCodigoPadre(deducciones.getId());
				archivodao.save(m);
			});
		}
	}

	public List<AporteDeduccion> findByNcodigoAp(int codAporte){
	    	
		 try {
			 Aporte aporte=new Aporte();
			 aporte.setId(codAporte);
			return dao.findByAporteAndSEstado(aporte,"1");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
}
