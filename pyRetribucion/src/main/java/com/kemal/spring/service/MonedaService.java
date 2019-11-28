package com.kemal.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kemal.spring.domain.Moneda;
import com.kemal.spring.domain.MonedaRepository;

@Service
public class MonedaService {
	@Autowired
	MonedaRepository dao;
	
	 public List<Moneda> findAll() {
	        return dao.findBySEstadoOrderByIdAsc("1");
	    }
	    
	    public void save(Moneda moneda) {
	    	dao.save(moneda);
	    }
	    
		public Moneda findById(Integer id) {
		        return dao.findById(id).get();
		}
}
