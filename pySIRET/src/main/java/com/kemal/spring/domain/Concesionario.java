package com.kemal.spring.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "T_CONCESIONARIO",schema ="SCINVGSF")
public class Concesionario {
	@Id
	@Column(name = "CNC_ID")
	private Integer id;
	
	@Column(name="CNC_NOMBRE")
	private String snombre;
	@Column(name="CNC_DESCRIPCION")
	private String sDescripcion;
	@Column(name="CNC_SIGLAS")
	private String sSiglas;	
	
	
	@Column(name="CNC_DIRECCION")
	private String sDireccion;	
	
	@Column(name="CNC_NRO_DOCUMENTO")
	private String sruc;
	
	@Column(name="CNC_TELEFONO")
	private String sTelefono;
	@Column(name="CNC_CORREO")
	private String sCorreo;
	@Column(name="CNC_ESTADO")
	private String sestado = "1";

	@OneToMany(mappedBy = "concesionario",
			cascade = CascadeType.ALL,
			fetch = FetchType.LAZY)	
	@Column(nullable = true)
	@JsonBackReference
	private Set<Representante> representante = new HashSet<>();
	/*@Column(length = 1)
	private String supervporOSITRAN="1";*/
	
	
	
	@OneToMany(mappedBy = "concesionario",
			cascade = CascadeType.ALL,
			fetch = FetchType.LAZY)	
	@Column(nullable = true)
	@JsonIgnore
    private Set<Vencimiento> lstVencimiento = new HashSet<>(); 
	
	public Concesionario(Integer id) {
		this.id=id;
		
	}
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "concesionario")  
    private List<ConcesionarioTipoVencimiento> concesionarioTipoVencimiento = new ArrayList<>();

	
	
}
