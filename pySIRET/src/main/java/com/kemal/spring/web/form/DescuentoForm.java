package com.kemal.spring.web.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DescuentoForm {
	private Integer fbtipoPeriodicidad;
	private Integer fbtipoRetribucion;
	private String fbmesRetribucion;
	private String fbanioRetribucion;
	private String fbdfecReconocimientoDesde;
	private String fbdfecReconocimientoHasta;
	
	private String fsDocumento;
	private String fnImporte;
	private String fsDescripcion;
	private Integer monedaRetribucion;
	
	private String fid;
	private String accion;	
	
}
