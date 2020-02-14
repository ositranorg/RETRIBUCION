package com.kemal.spring.web.controllers.restApiControllers;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.kemal.spring.service.TipoDocumentoService;

@RestController
@RequestMapping("api/tipo-documento")
@Scope("session")
public class TipoDocumentoRestController {
	@Autowired
	TipoDocumentoService tipoDocumentoService;
	@ResponseBody
	@PostMapping(value = "listar-tipo-documento", consumes = "application/json",produces =  { "application/json" })
	public ResponseEntity<?> listarTipoDocumento() {
		HashMap<String, Object> res = new HashMap<String, Object>();
		res.put("resultado", 1);
		res.put("lista", tipoDocumentoService.findAll());
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
}
