package com.kemal.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kemal.spring.domain.Feriado;
import com.kemal.spring.domain.FeriadoRepository;

@Service
public class FeriadoService {
	@Autowired
	FeriadoRepository dao;

	public Feriado findByFerAnyoAndFerMesAndFerDia(Integer FerAnyo,Integer FerMes,Integer FerDia){
		return dao.findByFerAnyoAndFerMesAndFerDia(FerAnyo,FerMes,FerDia);
	}
}
