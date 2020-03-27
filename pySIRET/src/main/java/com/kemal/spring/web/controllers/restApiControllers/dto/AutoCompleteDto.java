package com.kemal.spring.web.controllers.restApiControllers.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.kemal.spring.domain.Concesionario;
import com.kemal.spring.domain.TipoPeriodicidad;
import com.kemal.spring.domain.TipoRetribucion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonRootName("autoCompleteDto")
public class AutoCompleteDto {
	private List<TipoPeriodicidad> listaTPDto;
	private List<TipoRetribucion> listaTRDto;
	private List<ContribuyenteDto> listaTCDto;
}
