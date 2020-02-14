package com.kemal.spring.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MonedaRepository   extends JpaRepository<Moneda, Integer> {
	
	public List<Moneda> findBySEstadoOrderByIdAsc(String sestado);
   
}
