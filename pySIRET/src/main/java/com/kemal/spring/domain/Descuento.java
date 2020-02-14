package com.kemal.spring.domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
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
@Table(name = "SRET_DESCUENTO")
public class Descuento {
	 @Id
	    @Column(name = "NCODIGO", unique = true, nullable = false)
	    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "id_SequenceOTROSDESCUENTO")
	    @SequenceGenerator(name = "id_SequenceOTROSDESCUENTO", sequenceName = "SQ_RET_OTRODESCUENTO", allocationSize= 1)
	    private Integer id;

	  @Column(name = "SDESCRIPCION",length = 4000)
	 	private String sDescripcion;
	    
	    
	    
	    @Column(name = "NIMPORTE")
		private BigDecimal nImporte;

	    @Column(name = "NSALDO")
		private BigDecimal nSaldo;
		
		@ManyToOne
		private Moneda moneda;
		
		
		@Column(name = "DFECHAREGISTRO")
	    private Date dfecRegistro;
		@Column(name = "SUSUREGISTRA")
	    private String sUsuRegistra;
		
		
		@Column(name = "DFECHAMODIFICA")
	    private Date dfecModifica;
		@Column(name = "SUSUMODIFICA")
	    private String sUsuModifica;
	    
	    @Column(name = "SESTADO",length = 1)
	    private String sEstado="1";
	    
	    @Column(name = "NCODIGOCNS")
	    private Integer nCodigoCns;
	    

}
