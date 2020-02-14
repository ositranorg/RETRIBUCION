package com.kemal.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kemal.spring.domain.Banco;
import com.kemal.spring.domain.BancoRepository;

@Service
public class BancoService {
	@Autowired
	BancoRepository dao;
	
	 	public List<Banco> findAll() {
	        return dao.findBySEstado("1");
	    }
	    
	    public void save(Banco banco) {
	    	dao.save(banco);
	    }
}
