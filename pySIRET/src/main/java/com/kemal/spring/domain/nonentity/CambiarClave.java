package com.kemal.spring.domain.nonentity;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.MappedSuperclass;
import javax.persistence.SqlResultSetMapping;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@MappedSuperclass
@SqlResultSetMapping(name = "CambiarClaveResult", 
		classes = @ConstructorResult(
				targetClass = CambiarClave.class, 
				columns = { 
						@ColumnResult(name = "resultado", type = int.class),
						@ColumnResult(name = "mensaje", type = String.class) 
}))
public class CambiarClave {
	private int resultado;
	private String mensaje;
	
}
