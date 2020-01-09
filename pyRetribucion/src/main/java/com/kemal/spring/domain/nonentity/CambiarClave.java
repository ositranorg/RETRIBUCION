package com.kemal.spring.domain.nonentity;

import java.util.List;

import javax.persistence.ColumnResult;
import javax.persistence.MappedSuperclass;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.ConstructorResult;

import com.kemal.spring.domain.Contribuyente;
import com.kemal.spring.domain.Perfil;
import com.kemal.spring.domain.Role;

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
