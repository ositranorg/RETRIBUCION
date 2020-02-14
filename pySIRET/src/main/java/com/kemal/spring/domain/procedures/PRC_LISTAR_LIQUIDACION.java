package com.kemal.spring.domain.procedures;




import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.MappedSuperclass;
import javax.persistence.SqlResultSetMapping;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
@SqlResultSetMapping(name = "ListarLiquidacionResult", 
		classes = @ConstructorResult(
				targetClass = PRC_LISTAR_LIQUIDACION.class, 
				columns = { 
						@ColumnResult(name = "nOrden", type = int.class),
						@ColumnResult(name = "nCodigo", type = int.class), 
						@ColumnResult(name = "dFechaRegistro", type = String.class),
						@ColumnResult(name = "dFechaModifica", type = String.class),
						@ColumnResult(name = "dHoraRegistro", type = String.class),
						@ColumnResult(name = "dHoraModifica", type = String.class),
						@ColumnResult(name = "nAnio", type = int.class),
						@ColumnResult(name = "nIdTipoDocumento", type = int.class),
						@ColumnResult(name = "nPeriodo", type = int.class),
						@ColumnResult(name = "nCodigo", type = int.class),
						@ColumnResult(name = "sDocumento", type = String.class),
						@ColumnResult(name = "sEstado", type = String.class),
						
}))
public class PRC_LISTAR_LIQUIDACION {	
	private int nOrden;
	private int nCodigo;
	private String dFechaRegistro;
	private String dFechaModifica;
	private String dHoraRegistro;
	private String dHoraModifica;
	private int nAnio;
	private int nIdTipoDocumento;
	private int nPeriodo;
	private String sDocumento;
	private String sEstado;

	
}
