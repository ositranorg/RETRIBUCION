package com.kemal.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kemal.spring.domain.Archivo;
import com.kemal.spring.domain.ArchivoRepository;

@Service
public class ArchivoService {
	@Autowired
	ArchivoRepository dao;
	
		
	public List<Archivo> findBysEstadoOrderById(String modulo,Integer codigoPadre,String tipoDocumento,String sEstado){
		return dao.findByModuloAndCodigoPadreAndTipoDocumentoAndSEstadoOrderById(modulo,codigoPadre,tipoDocumento,sEstado);
	}
	
	public void deshabilitar(Integer id) {
		Archivo archivo=dao.findById(id).get();
		archivo.setSEstado("0");
		dao.save(archivo);
	}
	public void save(Archivo archivo) {
		dao.save(archivo);
	}
	
}
