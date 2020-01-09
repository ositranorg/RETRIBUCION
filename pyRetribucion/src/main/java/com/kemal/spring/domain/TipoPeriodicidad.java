/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kemal.spring.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 *
 * @author consultor_jti07
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "SRET_TIPOPERIODICIDAD")
public class TipoPeriodicidad implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Id
    @Column(name = "NTIPO_PERIODICIDAD", unique = true, nullable = false)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "id_SequenceTIPOPERIODICIDAD")
    @SequenceGenerator(name = "id_SequenceTIPOPERIODICIDAD", sequenceName = "SQ_RET_TIPOPERIODICIDAD", allocationSize= 1)
    private Integer id;


    @Column(name = "SDESCRIPCION", nullable = false)
    private String sDescripcion;

    @Column(name = "SESTADO")
	private String sEstado="1";
    

    @Column(name = "ORDERBY")
    private Integer orden; 
    
    
    
    
    
    
    
}
