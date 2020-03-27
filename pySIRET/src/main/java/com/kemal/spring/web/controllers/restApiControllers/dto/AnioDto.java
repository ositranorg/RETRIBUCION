package com.kemal.spring.web.controllers.restApiControllers.dto;

import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonRootName("anioDto")
public class AnioDto {
	private String id;
private String sDescripcion;
}
