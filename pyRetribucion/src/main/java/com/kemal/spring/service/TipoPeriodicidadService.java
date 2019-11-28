/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kemal.spring.service;


import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kemal.spring.domain.TipoPeriodicidad;
import com.kemal.spring.domain.TipoPeriodicidadRepository;

/**
 *
 * @author consultor_jti07
 */
@Service
public class TipoPeriodicidadService  {
	@Autowired
    TipoPeriodicidadRepository dao;

    public List<TipoPeriodicidad> findAll() {
        return dao.findBySEstado("1");
    }
    
    public void save(TipoPeriodicidad calendario) {
    	dao.save(calendario);
    }
    public TipoPeriodicidad findById(Integer id){
		Optional<TipoPeriodicidad> todoResult = dao.findById(id);
		 return todoResult.orElseThrow(() -> new EntityNotFoundException());
	}
}
