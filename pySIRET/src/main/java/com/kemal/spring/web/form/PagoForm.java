package com.kemal.spring.web.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PagoForm {
	private Integer fbtipoPeriodicidad;
	private Integer fbtipoRetribucion;
	private String fbmesRetribucion;
	private String fbanioRetribucion;
	private String fbdfecpagovouDesde;
	private String fbdfecpagovouHasta;
	
	private String ftipoPeriodicidad;
	private String ftipoRetribucion;
	private String fmesRetribucion;
	private String fanioRetribucion;
	private String fbanco;
	private String fdfecpagovou;
	private String fnroOperacion;
	private String fobservacion;
	private String fsDocumento;
	private String fnImporte;
	
	

	
	private String fid;
	private String accion;	
	
}
