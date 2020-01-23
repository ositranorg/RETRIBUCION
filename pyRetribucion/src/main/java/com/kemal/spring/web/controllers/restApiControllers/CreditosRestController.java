package com.kemal.spring.web.controllers.restApiControllers;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kemal.spring.bd.view.NuevoCreditoDTO;
import com.kemal.spring.domain.User;
import com.kemal.spring.service.CreditoService;
import com.kemal.spring.service.userDetails.UserDetailsImpl;
import com.kemal.spring.web.controllers.restApiControllers.dto.RepresentanteDto;
import com.kemal.spring.web.dto.DataTableObject;

@RestController
@RequestMapping("api/creditos")
@Scope("session")
public class CreditosRestController {
	@Autowired
	CreditoService creditoService;

	@Autowired
	DataTableObject dataTableObject;
	
	private ModelMapper modelMapper = new ModelMapper();
	public static HttpSession session() {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		return attr.getRequest().getSession(); // true == allow create
	}
	@ResponseBody
	@PostMapping(value = "registrar-representante", consumes = "application/json",produces =  { "application/json" })
	public ResponseEntity<?> registrarRepresentante(@RequestBody  NuevoCreditoDTO nuevoCreditoDTO) {
		System.out.println("idDto: " + nuevoCreditoDTO.getCodigo());
		
		NuevoCreditoDTO snuevoCreditoDTO = modelMapper.map(nuevoCreditoDTO, NuevoCreditoDTO.class);
		

		//HashMap<String, Object>res = creditoService.registrarRepresentante(representante,representanteDto.getPagina(),totalRegistroPorPagina);
		HashMap<String, Object>res =null;
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	@ResponseBody
	@GetMapping(value = "listar-creditos")
	public String listarCreditos() {	
		SecurityContextImpl sci = (SecurityContextImpl) (session().getAttribute("SPRING_SECURITY_CONTEXT"));
		Object us = (Object) sci.getAuthentication().getPrincipal();
		User c = ((UserDetailsImpl) us).getUser();
		
		dataTableObject.setData(creditoService.listarNuevosCreditos(c.getContribuyente().getId() ));
		 Gson gson = new GsonBuilder().setPrettyPrinting().create();
		 return gson.toJson(dataTableObject);
	}
	
	@ResponseBody
	@PostMapping(value = "eliminar-representante", consumes = "application/json",produces =  { "application/json" })
	public ResponseEntity<?> eliminarRepresentante(@RequestBody RepresentanteDto representanteDto) {
	
		HashMap<String, Object>res =null;
		/*HashMap<String, Object>res = 
				creditoService.eliminarRepresentantesByRucPaginado(
						representanteDto.getContribuyente().getSruc(),
						representanteDto.getPagina(),
				totalRegistroPorPagina, representanteDto.getId());*/
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
}
