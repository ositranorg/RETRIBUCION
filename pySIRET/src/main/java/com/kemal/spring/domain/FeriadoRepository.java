package com.kemal.spring.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FeriadoRepository   extends JpaRepository<Feriado, Integer>{
		public Feriado findByFerAnyoAndFerMesAndFerDia(Integer FerAnyo,Integer FerMes,Integer FerDia);
}
