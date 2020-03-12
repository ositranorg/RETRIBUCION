package com.kemal.spring.web.controllers.restApiControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kemal.spring.domain.procedures.PRC_TipoDeclaracion;
import com.kemal.spring.web.controllers.restApiControllers.dto.DeclaracionDto;
@Controller
@Scope("session")
public class DeclaracionRestController {
	@Autowired
	PRC_TipoDeclaracion prc_TipoDeclaracion;
	
	@ResponseBody
	@PostMapping(value = "/gettipodeclaracion", consumes = "application/json",produces =  { "application/json" })
	public ResponseEntity<?> gettipodeclaracion(@RequestBody DeclaracionDto tipoDeclaracionDto) {
		Integer s=prc_TipoDeclaracion.getTipoDeclaracion(
				tipoDeclaracionDto.getIdConcesionario(),
				tipoDeclaracionDto.getMesRetribucion(), 
				tipoDeclaracionDto.getAnioRetribucion()
				);
		System.out.println("--->"+s);
		return new ResponseEntity<>(s, HttpStatus.OK);
	}
}
