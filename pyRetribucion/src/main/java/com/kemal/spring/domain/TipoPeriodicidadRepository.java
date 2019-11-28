/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kemal.spring.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

	

/**
 *
 * @author consultor_jti07
 */
public interface TipoPeriodicidadRepository  extends JpaRepository<TipoPeriodicidad, Integer> {
	
     List<TipoPeriodicidad> findBySEstado(String sEstado);
}
