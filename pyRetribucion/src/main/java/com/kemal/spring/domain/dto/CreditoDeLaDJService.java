package com.kemal.spring.domain.dto;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreditoDeLaDJService {
	@Autowired
	CreditoDeLaDJRepository creditoDeLaDJRepository;

	public List<CreditoDeLaDJ>  listar(int concesionario,int page){
		return creditoDeLaDJRepository.listar(concesionario,page);
	}
}
