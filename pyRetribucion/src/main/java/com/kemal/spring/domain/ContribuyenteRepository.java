package com.kemal.spring.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContribuyenteRepository   extends PagingAndSortingRepository<Contribuyente, Long> {
	Page<Contribuyente> findAll(Pageable pageable);	
}
