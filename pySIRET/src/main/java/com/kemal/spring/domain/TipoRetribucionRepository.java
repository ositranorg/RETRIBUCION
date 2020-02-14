package com.kemal.spring.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoRetribucionRepository extends JpaRepository<TipoRetribucion, Integer> {
	public List<TipoRetribucion> findBySEstado(String sestado);
}
