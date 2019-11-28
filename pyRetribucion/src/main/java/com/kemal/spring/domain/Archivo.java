package com.kemal.spring.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"bytes"})
@Entity
@Table(name = "SRET_ARCHIVO")
public class Archivo {
	@Id
	@Column(name = "NCODIGO", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_SequenceARCHIVO")
	@SequenceGenerator(name = "id_SequenceARCHIVO", sequenceName = "SQ_RET_ARCHIVO", allocationSize= 1)
	private Integer id;
    private String fileName;
   
    private String modulo;
    private Integer codigoPadre;
    private String tipoDocumento;
    private String sEstado="1";
         //setters & getters
}
