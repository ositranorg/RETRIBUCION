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
@Table(name = "SRET_APORTEESTADODJ")
public class AporteEstadoDJ {
	@Id
	@Column(name = "NCODIGO",length = 1, unique = true, nullable = false)	
	private String id;
	@Column(name = "SDESCRIPCION",length = 20)
	private String sDescripcion;
	@Column(name="SESTADO",length = 1)
	private String sEstado="1";
}
