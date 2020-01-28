package com.kemal.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kemal.spring.domain.Estado;
import com.kemal.spring.domain.EstadoRepository;
@Service
public class EstadoService {
	@Autowired
	EstadoRepository dao;
	@Transactional(readOnly = false)
	public void save(Estado estado) {
		dao.save(estado);
	}
}
