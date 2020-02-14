package com.kemal.spring.domain;

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
@Table(name = "SRET_TIPOVENCIMIENTO")
public class TipoVencimiento {
	 @Id
	    @Column(name = "NCODIGO", unique = true, nullable = false)
	    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "id_SequenceTipoVencimiento")
	    @SequenceGenerator(name = "id_SequenceTipoVencimiento", sequenceName = "SQ_RET_TIPOVENCIMIENTO", allocationSize= 1)
	    private Integer id;


	    @Column(name = "SDESCRIPCION", nullable = false)
	    private String sDescripcion;

	    @Column(name = "SESTADO")
		private String sEstado="1";
}
