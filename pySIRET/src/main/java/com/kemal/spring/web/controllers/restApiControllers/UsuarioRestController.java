package com.kemal.spring.web.controllers.restApiControllers;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.kemal.spring.domain.User;
import com.kemal.spring.domain.procedures.PkUserMapperResultado;
import com.kemal.spring.service.MessageByLocaleService;
import com.kemal.spring.service.UsuarioService;
import com.kemal.spring.web.controllers.restApiControllers.dto.UsuarioDto;
import com.kemal.spring.web.dto.Util;

@RestController
@RequestMapping("api/usuario")
@Scope("session")
public class UsuarioRestController {
	@Autowired
	Util util;
	@Autowired
	UsuarioService userService;

	@Autowired
	MessageByLocaleService messageByLocaleService;

	public static HttpSession session() {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		return attr.getRequest().getSession(); // true == allow create
	}

	@ResponseBody
	@PostMapping(value = "cambiar-clave", consumes = "application/json",produces =  { "application/json" })
	public ResponseEntity<?> cambiarClave(@RequestBody UsuarioDto usuarioDto) {
		System.out.println("clave nueva: " + usuarioDto.getNuevaClave());
		User u=(User)session().getAttribute("oUsuario");
		HashMap<String, Object>res=userService.cambiarClave(u.getId(),usuarioDto.getClaveAnterior(),usuarioDto.getNuevaClave());
	    
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@ResponseBody
	@PostMapping(value = "recuperar-clave", consumes = "application/json", produces = { "application/json" })
	public ResponseEntity<?> enviarClave(@RequestBody UsuarioDto usuarioDto) throws Exception {
		HashMap<String, Object> res = userService.recuperarClave(usuarioDto.getIdEntidadPrestadora(),usuarioDto.getCorreo());
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
}
