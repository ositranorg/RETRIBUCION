package com.kemal.spring.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kemal.spring.domain.PagoSinAporte;
import com.kemal.spring.domain.PagoSinAporteRepository;

@Service
public class PagoService {
	@Autowired
	PagoSinAporteRepository dao;
	
	
	public List<PagoSinAporte> findBySEstadoAndNCodigoCnsLib(Integer nCodigoCns){
		return dao.findBySEstadoAndNCodigoCns("2",nCodigoCns);
	}
	 public Page<PagoSinAporte> findByNCodigoCnsAndSEstado(
			 Integer sTipoPeriodicidad,
			 Integer sTipoRetribucion,
			 String sMesPeriodo,
			 String sAnioPeriodo ,
			 Date dfecReconocimientoDesde,
			 Date dfecReconocimientoHasta,
			 Integer nCodigoCns,
			 String sEstado,
			 Pageable pageable){
	    
		 try {
				return dao.getListaPagos(
						sTipoPeriodicidad, 
						sTipoRetribucion,
						sMesPeriodo, 
						sAnioPeriodo, 
						dfecReconocimientoDesde,
						dfecReconocimientoHasta,
						nCodigoCns, 						
						sEstado, 				
						pageable);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
	}
	 
	 public List<PagoSinAporte> getListaPago(	
			 Integer sTipoPeriodicidad,
			 Integer sTipoRetribucion,
			 String sMesPeriodo,
			 String sAnioPeriodo ,
			 Integer nCodigoCns,
			 String sEstado){
	    
		 try {
				return dao.getListaPago(
						sTipoPeriodicidad, 
						sTipoRetribucion,
						sMesPeriodo, 
						sAnioPeriodo, 	
						nCodigoCns, 						
						sEstado);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
	}
	 
	 
	 @Transactional(readOnly = false)
	 public void save(PagoSinAporte pago) {
		 dao.save(pago);
		 
	 }
	 
		@Transactional(readOnly = true)
		public Optional<PagoSinAporte> findById(Integer id) {
			return dao.findById(id);

		}
}
