package com.kemal.spring.domain.dto;

import java.util.List;

import org.springframework.stereotype.Repository;
@Repository
public interface CreditoDeLaDJRepository {
public List<CreditoDeLaDJ> listar(int concesionario,int page);	
}
