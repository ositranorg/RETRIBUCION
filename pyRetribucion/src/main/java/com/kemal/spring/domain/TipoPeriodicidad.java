/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kemal.spring.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

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
	 @JsonProperty("NCODIGO")
    private Integer id;


    @Column(name = "SDESCRIPCION")
    private String sDescripcion;

    @Column(name = "SESTADO")
	private String sEstado="1";
    

    @Column(name = "ORDERBY")
    private Integer orden; 
    
    
    @OneToMany(mappedBy="tipoPeriodicidadOrigen", fetch = FetchType.EAGER)
    private Set<Credito> origen=new HashSet<>();;

    @OneToMany(mappedBy="tipoPeriodicidadDestino", fetch = FetchType.EAGER)
    private Set<Credito> destino=new HashSet<>();;
    
    
    
    
}
