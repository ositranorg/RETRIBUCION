package com.kemal.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kemal.spring.domain.Aporte;
import com.kemal.spring.domain.AporteLiberacion;
import com.kemal.spring.domain.AporteLiberacionRepository;
import com.kemal.spring.domain.Archivo;
import com.kemal.spring.domain.ArchivoRepository;

@Service
public class AporteLiberacionService {
	@Autowired
	AporteLiberacionRepository aporteLiberacionRepository;
	@Autowired
	ArchivoRepository archivodao;

	
	
	public List<AporteLiberacion> getListaSaldo(Integer idAporte) {

		try {
			return aporteLiberacionRepository.getListaSaldo(idAporte, "1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}	 

	 
	 
	 @Transactional(readOnly = false)
	 public void save(AporteLiberacion liberacionPago, List<Archivo> archivos) {
		 aporteLiberacionRepository.save(liberacionPago);
			if (null != archivos&&archivos.size()>0) {
				archivos.stream().forEach((m) -> {
					m.setCodigoPadre(liberacionPago.getId());
					archivodao.save(m);
				});
			}
	 }
	 
	  public List<AporteLiberacion> findByNcodigoAp(int codAporte){
	    	
			 try {
				 Aporte aporte=new Aporte();
				 aporte.setId(codAporte);
				return aporteLiberacionRepository.findByAporteAndSEstado(aporte,"1");
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
			
		}
	 
}
