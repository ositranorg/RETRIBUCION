package com.kemal.spring.domain;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BancoRepository extends JpaRepository<Banco, Integer> {

	public List<Banco> findBySEstado(String sEstado);
	public Page<Banco> findBySEstado(String sEstado,Pageable pageable);
	
}
