package com.kemal.spring.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConcesionarioRepository   extends PagingAndSortingRepository<Concesionario, Integer> {
	Page<Concesionario> findAll(Pageable pageable);
	Concesionario findBySruc(String sruc);
}


