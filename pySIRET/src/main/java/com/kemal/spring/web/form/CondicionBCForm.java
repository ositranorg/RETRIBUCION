package com.kemal.spring.web.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CondicionBCForm {
	private String dfecInicio;
	private String dfecExclusion;
	private String sBuenContribuyente;
	
	private String idEliminar;
	
	
	private String fsDocumento;
}
