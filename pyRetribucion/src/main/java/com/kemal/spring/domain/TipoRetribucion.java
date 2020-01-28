package com.kemal.spring.domain;

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

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "SRET_TIPORETRIBUCION")
public class TipoRetribucion {
	@Id
	 @JsonProperty("NCODIGO")
	private Integer id;
	@Column(name = "SDESCRIPCION")
	private String sDescripcion;
	@Column(name = "SESTADO")
	private String sEstado="1";
	
	 @OneToMany(mappedBy="tipoRetribucionOrigen", fetch = FetchType.EAGER)
	    private Set<Credito> origen=new HashSet<>();;

	    @OneToMany(mappedBy="tipoRetribucionDestino", fetch = FetchType.EAGER)
	    private Set<Credito> destino=new HashSet<>();;
	    

	
}
