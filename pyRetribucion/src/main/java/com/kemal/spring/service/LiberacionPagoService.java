package com.kemal.spring.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kemal.spring.domain.AporteDeduccion;
import com.kemal.spring.domain.Archivo;
import com.kemal.spring.domain.ArchivoRepository;
import com.kemal.spring.domain.LiberacionPago;
import com.kemal.spring.domain.LiberacionPagoRepository;
import com.kemal.spring.domain.Descuento;

@Service
public class LiberacionPagoService {
	@Autowired
	LiberacionPagoRepository liberacionPagoRepository;
	@Autowired
	ArchivoRepository archivodao;

	public Page<LiberacionPago> findByNCodigoCnsAndSEstadox(Integer nCodigoCns, String sEstado, Pageable pageable) {
		try {
			return liberacionPagoRepository.getListaLiberacion(nCodigoCns, sEstado, pageable);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public List<LiberacionPago> getListaSaldo(Integer nCodigoCns, Integer idMoneda, String sEstado) {

		try {
			return liberacionPagoRepository.getListaSaldo(nCodigoCns, idMoneda, sEstado);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Transactional(readOnly = false)
	public void save(LiberacionPago liberacionPago, List<Archivo> archivos) {
		liberacionPagoRepository.save(liberacionPago);
		if (null != archivos && archivos.size() > 0) {
			archivos.stream().forEach((m) -> {
				m.setCodigoPadre(liberacionPago.getId());
				archivodao.save(m);
			});
		}
	}

	@Transactional(readOnly = true)
	public Optional<LiberacionPago> findById(Integer id) {
		return liberacionPagoRepository.findById(id);

	}
}
