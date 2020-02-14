package com.kemal.spring.domain.procedures;

import com.kemal.spring.domain.nonentity.Resultado;

import lombok.Data;
import lombok.EqualsAndHashCode;
@Data
@EqualsAndHashCode(callSuper=true)
public class PkUserMapperResultado extends Resultado{
	private int estadoClave;
}
