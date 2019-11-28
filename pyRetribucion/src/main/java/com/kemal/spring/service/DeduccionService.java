package com.kemal.spring.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kemal.spring.domain.Archivo;
import com.kemal.spring.domain.ArchivoRepository;
import com.kemal.spring.domain.Deduccion;
import com.kemal.spring.domain.DeduccionRepository;
import com.kemal.spring.domain.LiberacionPago;
import com.kemal.spring.domain.Descuento;

@Service
public class DeduccionService {
	@Autowired
	ArchivoRepository archivodao;
	@Autowired
	DeduccionRepository dao;

	public Page<Deduccion> findByNCodigoCnsAndSEstado( Integer nCodigoCns, String sEstado, Pageable pageable) {

		try {
			return dao.getListaDeducciones( nCodigoCns,
					sEstado, pageable);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public List<Deduccion> getListaSaldo(Integer nCodigoCns, Integer idMoneda, String sEstado) {

		try {
			return dao.getListaSaldo(nCodigoCns, idMoneda, sEstado);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@Transactional(readOnly = false)
	public void save(Deduccion deducciones, List<Archivo> archivos) {
		dao.save(deducciones);
		if (null != archivos&&archivos.size()>0) {
			archivos.stream().forEach((m) -> {
				m.setCodigoPadre(deducciones.getId());
				archivodao.save(m);
			});
		}
	}

	@Transactional(readOnly = true)
	public Optional<Deduccion> findById(Integer id) {
		return dao.findById(id);

	}
}
