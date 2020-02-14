package com.kemal.spring.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kemal.spring.domain.TipoRetribucion;
import com.kemal.spring.domain.TipoRetribucionRepository;

@Service
public class TipoRetribucionService {
	@Autowired
	TipoRetribucionRepository dao;
	public List<TipoRetribucion> findAll(){
		return dao.findBySEstado("1");
	}
	public void save(TipoRetribucion tipoRetribucion) {
		dao.save(tipoRetribucion);
	}
	public TipoRetribucion findById(Integer id){
		Optional<TipoRetribucion> todoResult = dao.findById(id);
		 return todoResult.orElseThrow(() -> new EntityNotFoundException());
	}
}
