package com.kemal.spring.domain;

import java.math.BigDecimal;
import java.util.Date;

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
@Table(name = "SRET_CREDITO")
public class Credito {
	 @Id
	    @Column(name = "NCODIGO", unique = true, nullable = false)
	    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "id_SequenceCREDITO")
	    @SequenceGenerator(name = "id_SequenceCREDITO", sequenceName = "SQ_RET_CREDITO", allocationSize= 1)
	    private Integer id;	 
	    
	    @Column(name = "SDOCUMENTO")
	    private String sDocumento;	   

	    
	    @Column(name = "ncodigoAporte")
		private Integer ncodigoAporte;
	    
	    @Column(name = "DFECHAREGISTRO")
	    private Date dfecRegistro;
	    
	    
	    @Column(name = "DFECHAAPLICADO")
	    private Date dfecAplicado;
	    
	    
	    
	    @Column(name = "NIMPORTE")
	    private BigDecimal importe;
	    
	    @Column(name = "SESTADO",length = 1)
	    private String sEstado="1";
	    
	    @Column(name = "NCODIGOCNS")
	    private Integer nCodigoCns;
	    
	    
	    @Column(name = "NTIPO_PERIODICIDAD_ORIGEN")
	    private String tipoPeriodicidadOrigen;
	    @Column(name = "NTIPO_RETRIBUCION_ORIGEN")
		private String tipoRetribucionOrigen;
	    @Column(name = "SMESPERIODO_ORIGEN")
		private String mesRetribucionOrigen;
	    @Column(name = "SANIOPERIODO_ORIGEN")
		private String anioRetribucionOrigen;
		
	    @Column(name = "NTIPO_PERIODICIDAD_DESTINO")
	    private String tipoPeriodicidadDestino;
	    @Column(name = "NTIPO_RETRIBUCION_DESTINO")
		private String tipoRetribucionDestino;
	    @Column(name = "SMESPERIODO_DESTINO")
		private String mesRetribucionDestino;
	    @Column(name = "SANIOPERIODO_DESTINO")
		private String anioRetribucionDestino;		
		
}
