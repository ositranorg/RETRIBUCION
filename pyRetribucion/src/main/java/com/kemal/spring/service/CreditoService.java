package com.kemal.spring.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kemal.spring.domain.Credito;
import com.kemal.spring.domain.CreditoRepository;
import com.kemal.spring.domain.Deduccion;

@Service
public class CreditoService {

	@Autowired
	CreditoRepository dao;

	public Page<Credito> findAllBySEstado(String estado,Pageable pageable) {

		try {
			return dao.findAllBySEstado(estado, pageable);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Transactional(readOnly = false)
	public void save(Credito deducciones) {
		dao.save(deducciones);

	}

	@Transactional(readOnly = true)
	public Optional<Credito> findById(Integer id) {
		return dao.findById(id);

	}
}
