package com.kemal.spring.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "SRET_TIPO_DOCUMENTO",
uniqueConstraints={@UniqueConstraint(columnNames={"sNombre"})})
public class TipoDocumento {
	@Id
	@Column(name = "NCODIGO", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_SequenceTIPODOCUMENTO")
	@SequenceGenerator(name = "id_SequenceTIPODOCUMENTO", sequenceName = "SQ_RET_TIPODOCUMENTO", allocationSize= 1)
	private Integer id;
	
	@Column(unique = true)
	private String sNombre;
	
	/*
	@JsonIgnore
	@OneToOne(mappedBy = "tipoDocumento", cascade = CascadeType.ALL, orphanRemoval = true)
	*/
	/*
    @OneToOne(mappedBy = "tipoDocumento", cascade = CascadeType.ALL,
              fetch = FetchType.LAZY, optional = false)
    */
    //@OneToOne(fetch = FetchType.LAZY)
    //@MapsId
    
	@OneToMany(mappedBy = "tipoDocumento", 
    	cascade = CascadeType.ALL,
    	fetch = FetchType.LAZY)
	@Column(nullable = true)
	@JsonBackReference
    private Set<Representante> representantes = new HashSet<>();
}
