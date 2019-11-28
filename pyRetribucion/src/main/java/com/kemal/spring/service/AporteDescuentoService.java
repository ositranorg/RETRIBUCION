package com.kemal.spring.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kemal.spring.domain.Aporte;
import com.kemal.spring.domain.AporteDescuento;
import com.kemal.spring.domain.AporteDescuentoRepository;
import com.kemal.spring.domain.AporteLiberacion;
import com.kemal.spring.domain.Archivo;
import com.kemal.spring.domain.ArchivoRepository;

@Service
public class AporteDescuentoService {

	@Autowired
	AporteDescuentoRepository aporteDescuentoRepository;
	
	@Autowired
	ArchivoRepository archivodao;
	
	public List<AporteDescuento> getListaSaldo(Integer idAporte) {

		try {
			return aporteDescuentoRepository.getListaSaldo(idAporte, "1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	@Transactional(readOnly = false)
	public void save(AporteDescuento otrosDescuentos, List<Archivo> archivos) {
		aporteDescuentoRepository.save(otrosDescuentos);
		if (null != archivos&&archivos.size()>0) {
			archivos.stream().forEach((m) -> {
				m.setCodigoPadre(otrosDescuentos.getId());
				archivodao.save(m);
			});
		}

	}

	@Transactional(readOnly = true)
	public Optional<AporteDescuento> findById(Integer id) {
		return aporteDescuentoRepository.findById(id);

	}
	
	
	 public List<AporteDescuento> findByNcodigoAp(int codAporte){
	    	
		 try {
			 Aporte aporte=new Aporte();
			 aporte.setId(codAporte);
			return aporteDescuentoRepository.findByAporteAndSEstado(aporte,"1");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	 
	 
}
