package com.kemal.spring.web.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CalendarioDto {
private Integer id;
private String sMesPeriodo;
private Date dFechaVenc;
}
