package com.kemal.spring.domain;

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

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "SRET_TIPO_DOCUMENTO")
public class TipoDocumento {
	@Id
	@Column(name = "NCODIGO", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_SequenceTIPODOCUMENTO")
	@SequenceGenerator(name = "id_SequenceTIPODOCUMENTO", sequenceName = "SQ_RET_TIPODOCUMENTO", allocationSize= 1)
	private Integer id;
	
	@Column(unique = true)
	private String sNombre;
	
	
    /*
	@OneToMany(mappedBy = "tipoDocumento", 
    	cascade = CascadeType.ALL,
    	fetch = FetchType.LAZY)
	@Column(nullable = true)
	@JsonBackReference
    private Set<Representante> representantes = new HashSet<>();*/
}
