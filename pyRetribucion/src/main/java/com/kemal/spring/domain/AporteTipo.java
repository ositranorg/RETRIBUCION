package com.kemal.spring.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "SRET_APORTETIPO")
public class AporteTipo {
	@Id
	@Column(name = "NCODIGO",length = 1, unique = true, nullable = false)	
	private String id;
	@Column(length = 20)
	private String sDescripcion;
	@Column(length = 1)
	private String sEstado="1";
}
