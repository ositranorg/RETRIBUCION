package com.kemal.spring.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ContratoRepository   extends JpaRepository<Contrato, Integer> {
	public Contrato findByCalendarioAndContribuyente(TipoPeriodicidad calendario,Contribuyente contribuyente);
	public List<Contrato> findByContribuyente(Contribuyente contribuyente);
}
