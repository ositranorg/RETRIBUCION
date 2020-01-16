package com.kemal.spring.domain.dto;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreditoDeLaDJService {
	@Autowired
	CreditoDeLaDJRepository creditoDeLaDJRepository;
	public long countListar(int concesionario) {
		return creditoDeLaDJRepository.countListar(concesionario);
	}
	public List<CreditoDeLaDJ>  listar(int concesionario,int ini,int max){
		return creditoDeLaDJRepository.listar(concesionario,ini,max);
	}
}
