package com.kemal.spring.web.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LiberacionPagoForm {
	
	private String tipoPeriodicidad;
	private String tipoRetribucion;
	private String mesRetribucion;
	private String anioRetribucion;
	private String dfecReconocimiento;
	private String fsDocumento;
	private String fnImporte;
	private String fsDescripcion;

	private Integer monedaRetribucion;
	
	private String fid;
	private String accion;	
	
}
