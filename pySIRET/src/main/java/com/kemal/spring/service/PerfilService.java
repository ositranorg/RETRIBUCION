package com.kemal.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kemal.spring.domain.Perfil;
import com.kemal.spring.domain.PerfilRepository;
@Service
public class PerfilService {
	@Autowired
	PerfilRepository dao;
	
	public void save(Perfil perfil) {
		dao.save(perfil);
	}
}
