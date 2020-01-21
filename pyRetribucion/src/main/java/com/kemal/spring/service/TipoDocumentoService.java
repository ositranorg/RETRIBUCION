package com.kemal.spring.service;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.kemal.spring.domain.TipoDocumento;
import com.kemal.spring.domain.TipoDocumentoRepository;

@Service
public class TipoDocumentoService {
	private TipoDocumentoRepository tipoDocumentoRepository;
	public TipoDocumentoService(TipoDocumentoRepository tipoDocumentoRepository) {
		this.tipoDocumentoRepository = tipoDocumentoRepository;
	}
	public List<TipoDocumento> findAll(){
		return tipoDocumentoRepository.findAllTipoDocumento(Sort.by(Sort.Direction.ASC, "id"));
	}
}
