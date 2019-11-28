package com.kemal.spring.domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "SRET_PAGO")
public class Pago {
	 @Id
	    @Column(name = "NCODIGO", unique = true, nullable = false)
	    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "id_SequencePago")
	    @SequenceGenerator(name = "id_SequencePago", sequenceName = "SQ_RET_PAGO", allocationSize= 1)
	    private Integer id;
	 	@DateTimeFormat(pattern = "dd/MM/yyyy")
	 	@Column(name = "DFECPAGOVOUCHER")
	    private Date dfecPagoVoucher;

	 	@ManyToOne
	 	@JoinColumn(name="sTipoRetribucion")
		private TipoRetribucion tipoRetribucion; 
	 	
	 	@ManyToOne
	 	@JoinColumn(name="sTipoPeriodicidad")
		private TipoPeriodicidad tipoPeriodicidad;
	 	
	 	
	 	/*@Column(name = "TIPOPERIODICIDAD",length = 100)
	    private String sTipoPeriodicidad;
	    
	    @Column(name = "TIPORETRIBUCION",length = 100)
	    private String sTipoRetribucion;
	    */
	    @Column(name = "MESRETRIBUCION",length = 100)
	    private String sMesRetribucion;
	    
	    @Column(name = "ANIORETRIBUCION",length = 100)
	    private String sAnioRetribucion;
	    
	    
	    @Column(name = "SDOCUMENTO",length = 2000)
	    private String sDocumento;
	    
	    
	    @Column(name = "NIMPORTE")
		private BigDecimal nImporte;

	    @Column(name = "NROOPERACION",length = 200)
		private String nroOperacion;
	    
	    @Column(name = "OBSERVACION")
	    private String observacion;
	    
	    @Column(name = "DFECHAREGISTRO")
	    private Date dfecRegistro;
	     
	    @Column(name = "SESTADO",length = 1)
	    private String sEstado="1";
	    
	    @Column(name = "NCODIGOCNS")
	    private Integer nCodigoCns;
	    
	    @ManyToOne(fetch = FetchType.LAZY,targetEntity = Banco.class)
	    @JoinColumn(name = "NCODIGOBANCO")
	    private Banco banco ;
	    
	    @ManyToOne(fetch = FetchType.LAZY,targetEntity = Moneda.class)
	    @JoinColumn(name = "NCODIGOMONEDA")
	    private Moneda moneda ;  
}
