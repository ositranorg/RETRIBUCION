package com.kemal.spring.web.controllers.restApiControllers;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.kemal.spring.service.ContribuyenteService;
import com.kemal.spring.web.controllers.restApiControllers.dto.ContribuyenteDto;

@RestController
@RequestMapping("api/contribuyente")
@Scope("session")
public class ContribuyenteRestController {
	@Autowired
	ContribuyenteService contribuyenteService;
	
	@Value("${total-registro-por-pagina}")
	private int totalRegistroPorPagina;
	
	@ResponseBody
	@PostMapping(value = "listar-contribuyente", consumes = "application/json",produces =  { "application/json" })
	public ResponseEntity<?> listarContribuyente() {
		HashMap<String, Object> rest = new HashMap<String, Object>();
		rest.put("lista", contribuyenteService.listaContribuyente());
		rest.put("resultado", 1);
		return new ResponseEntity<>(rest, HttpStatus.OK);
	}
	@ResponseBody
	@PostMapping(value = "listar-contribuyente-paginacion", consumes = "application/json",produces =  { "application/json" })
	public ResponseEntity<?> listarContribuyentePaginacion(@RequestBody ContribuyenteDto contribuyenteDto) {
		HashMap<String, Object> rest = new HashMap<String, Object>();
		rest.put("lista", contribuyenteService.listaContribuyentePaginacion(contribuyenteDto.getPagina(), totalRegistroPorPagina));
		rest.put("resultado", 1);
		return new ResponseEntity<>(rest, HttpStatus.OK);
	}
}
