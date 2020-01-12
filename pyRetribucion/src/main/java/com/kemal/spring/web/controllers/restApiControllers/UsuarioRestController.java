package com.kemal.spring.web.controllers.restApiControllers;

import java.util.HashMap;
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

import com.kemal.spring.domain.nonentity.Resultado;
import com.kemal.spring.service.UserService;
import com.kemal.spring.web.controllers.restApiControllers.dto.UsuarioDto;

@RestController
@RequestMapping("api/usuario")
@Scope("session")
public class UsuarioRestController {
	
	@Autowired
	UserService userService; 
	@PostMapping(value = "cambiar-clave", consumes = "application/json",produces =  { "application/json" })
	@ResponseBody
	public ResponseEntity<?> cambiarClave(@RequestBody UsuarioDto usuarioDto) {
		/*
		System.out.println("clave anterior: " + usuarioDto.getClaveAnterior());
		System.out.println("clave nueva: " + usuarioDto.getNuevaClave());
		System.out.println("clave confirmar: " + usuarioDto.getConfirmarClave());
		*/
		Resultado res = userService.cambiarClave(usuarioDto.getClaveAnterior(), usuarioDto.getNuevaClave(), usuarioDto.getConfirmarClave());

		return new ResponseEntity<>(res, HttpStatus.OK);
	}
}
