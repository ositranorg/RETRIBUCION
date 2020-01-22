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
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "SRET_CONTRIBUYENTE")
public class Contribuyente {
	@Id
	@Column(name = "NCODIGO", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_SequenceCONTRIBUYENTE")
	@SequenceGenerator(name = "id_SequenceCONTRIBUYENTE", sequenceName = "SQ_RET_CONTRIBUYENTE", allocationSize= 1)
	private Integer id;
	@Column(length = 11)
	private String sruc;
	@Column(length = 500)
	private String snombre;
	private String sTelefono;
	@Column(length = 800)
	private String sCorreo;
	private String sestado = "1";

	@OneToMany(mappedBy = "contribuyente",
			cascade = CascadeType.ALL,
			fetch = FetchType.LAZY)	
	@Column(nullable = true)
	@JsonBackReference
	private Set<Representante> representante = new HashSet<>();
	

}
