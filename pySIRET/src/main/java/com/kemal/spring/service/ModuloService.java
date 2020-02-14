package com.kemal.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kemal.spring.domain.Modulo;
import com.kemal.spring.domain.ModuloRepository;

@Service
public class ModuloService {
	@Autowired
	ModuloRepository dao;
	
	 public List<Modulo> findAll() {
	        return dao.findAll();
	    }
	    
	    public void save(Modulo calendario) {
	    	dao.save(calendario);
	    }
}
