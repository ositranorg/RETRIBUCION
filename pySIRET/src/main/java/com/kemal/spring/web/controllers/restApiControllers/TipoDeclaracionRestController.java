package com.kemal.spring.web.controllers.restApiControllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.kemal.spring.domain.procedures.PRC_TipoDeclaracion;
import com.kemal.spring.web.controllers.restApiControllers.dto.TipoDeclaracionDto;
@RestController
@RequestMapping("api/tipodeclaracion")
@Scope("session")
public class TipoDeclaracionRestController {
	@Autowired
	PRC_TipoDeclaracion prc_TipoDeclaracion;
	
	@ResponseBody
	@PostMapping(value = "/gettipodeclaracion", consumes = "application/json",produces =  { "application/json" })
	public ResponseEntity<?> gettipodeclaracion(@RequestBody List<TipoDeclaracionDto> tipoDeclaracionDto) {
		Integer s=prc_TipoDeclaracion.getTipoDeclaracion(tipoDeclaracionDto.get(0).getIdConcesionario(), tipoDeclaracionDto.get(0).getMes(), tipoDeclaracionDto.get(0).getAnio());
		System.out.println("--->"+s);
		return new ResponseEntity<>(s, HttpStatus.OK);
	}
	
}
