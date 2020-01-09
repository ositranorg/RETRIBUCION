package com.kemal.spring.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "SRET_LIQUIDACION")
public class Liquidacion {
	@Id
	@Column(name = "NCODIGO", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_SequenceLiquidacion")
	@SequenceGenerator(name = "id_SequenceLiquidacion", sequenceName = "SQ_RET_LIQUIDACION", allocationSize=1)
	private Long id;
	
	
	@Column(name = "NPERIODO")
	private Integer nPeriodo;
	
	@Column(name = "NIDTIPODOCUMENTO")
	private Integer nIdTipoDocumento;
	
	@Column(name = "NANIO")
	private Integer nAnio;
	
	@Column(name = "SDOCUMENTO", length = 4000)
	private String sDocumento;
	
	
	@Column(name = "DFECHAREGISTRO")
	@CreationTimestamp	 
	private Date dfecRegistro;
	
	@Column(name = "SUSUREGISTRA")
	private String sUsuRegistra;

	@Column(name = "DFECHAMODIFICA")
	private Date dfecModifica;
	@Column(name = "SUSUMODIFICA")
	private String sUsuModifica;
	
	@Column(name = "SESTADO", length = 1)
	private String sEstado = "1";
}
