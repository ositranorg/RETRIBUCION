package com.kemal.spring.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kemal.spring.domain.Archivo;
import com.kemal.spring.domain.ArchivoRepository;
import com.kemal.spring.domain.CondicionBC;
import com.kemal.spring.domain.CondicionBCRepository;

@Service
public class CondicionBCService {
	@Autowired
	CondicionBCRepository dao;

	@Autowired
	ArchivoRepository archivodao;

	public Page<CondicionBC> findByNCodigoCnsAndSEstadoNotOrderBySEstadoAsc(Integer nCodigoCns, Pageable pageable) {

		try {
			return dao.findByNCodigoCnsAndSEstadoNotOrderBySEstadoAscDfecRegistroDesc(nCodigoCns, "0", pageable);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Transactional(readOnly = false)
	public void save(CondicionBC condicionBC, List<Archivo> archivos) {
		List<CondicionBC> d = dao.findByNCodigoCnsAndSEstadoOrderByNCodigoCns(condicionBC.getNCodigoCns(), "1");
		d.stream().forEach((s) -> {
			s.setSEstado("2");
			s.setStatus("ANTERIOR");
		});
		dao.saveAll(d);
		dao.save(condicionBC);
		if (null != archivos&&archivos.size()>0) {
			archivos.stream().forEach((m) -> {
				m.setCodigoPadre(condicionBC.getId());
				archivodao.save(m);
			});
		}
	}

	@Transactional(readOnly = true)
	public CondicionBC findById(Integer id) {
		Optional<CondicionBC> d = dao.findById(id);
		return d.get();

	}

	@Transactional(readOnly = true)
	public CondicionBC findByNCodigoCnsAndSEstado(Integer nCodigoCns) {
		return dao.findByNCodigoCnsAndSEstado(nCodigoCns, "1");
	}
}
