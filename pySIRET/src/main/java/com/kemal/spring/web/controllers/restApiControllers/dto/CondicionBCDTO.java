package com.kemal.spring.web.controllers.restApiControllers.dto;

import com.fasterxml.jackson.annotation.JsonRootName;

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
@JsonRootName("condicionBC")
public class CondicionBCDTO {
private Integer codigoBuenControbuyente;
private String sBuenContribuyente;
}
