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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "SRET_CONCESIONARIO")
public class Concesionario {
	@Id
	@Column(name = "NCODIGO", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_SequenceCONCESIONARIO")
	@SequenceGenerator(name = "id_SequenceCONCESIONARIO", sequenceName = "SQ_RET_CONCESIONARIO", allocationSize= 1)
	private Integer id;
	@Column(length = 11)
	private String sruc;
	@Column(length = 500)
	private String snombre;
	private String sTelefono;
	@Column(length = 800)
	private String sCorreo;
	private String sestado = "1";

	@OneToMany(mappedBy = "concesionario",
			cascade = CascadeType.ALL,
			fetch = FetchType.LAZY)	
	@Column(nullable = true)
	@JsonBackReference
	private Set<Representante> representante = new HashSet<>();
	@Column(length = 1)
	private String supervporOSITRAN="1";
	
	
	
	@OneToMany(mappedBy = "concesionario",
			cascade = CascadeType.ALL,
			fetch = FetchType.LAZY)	
	@Column(nullable = true)
	@JsonIgnore
    private Set<Vencimiento> lstVencimiento = new HashSet<>(); 
	public Concesionario(Integer id) {
		this.id=id;
		
	}
}
