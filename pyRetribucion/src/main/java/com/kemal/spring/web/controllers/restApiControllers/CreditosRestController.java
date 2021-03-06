package com.kemal.spring.web.controllers.restApiControllers;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kemal.spring.domain.Credito;
import com.kemal.spring.domain.dto.CreditoDeLaDJService;
import com.kemal.spring.service.CreditoService;
import com.kemal.spring.service.TipoPeriodicidadService;
import com.kemal.spring.service.TipoRetribucionService;
import com.kemal.spring.web.controllers.restApiControllers.dto.CreditoDTO;
import com.kemal.spring.web.dto.DataTableVWCreditoRegistrado;
import com.kemal.spring.web.dto.DataTableVWNuevoCredito;
import com.kemal.spring.web.dto.Util;

@RestController
@RequestMapping("api/creditos")
@Scope("session")
public class CreditosRestController {
	@Autowired
	CreditoService creditoService;

	@Autowired
	DataTableVWNuevoCredito dataTableVWNuevoCredito;
	@Autowired
	DataTableVWCreditoRegistrado dataTableVWCreditoRegistrado;
	
	private ModelMapper modelMapper = new ModelMapper();
	
	
	@Autowired
	Util util;
	@Autowired
	TipoPeriodicidadService calendarioService;
	@Autowired
	TipoRetribucionService tipoRetribucionService;
	
	@Autowired
	CreditoService service;
	@Autowired
	CreditoDeLaDJService creditoDeLaDJService;
	
	
	public static HttpSession session() {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		return attr.getRequest().getSession(); // true == allow create
	}
	@ResponseBody
	@PostMapping(value = "registrarConsumo", consumes = "application/json",produces =  { "application/json" })
	public ResponseEntity<?> registrarRepresentante(@RequestBody  List<CreditoDTO> creditoDTO) {
		/*SecurityContextImpl sci = (SecurityContextImpl) (session().getAttribute("SPRING_SECURITY_CONTEXT"));
		Object us = (Object) sci.getAuthentication().getPrincipal();
		User c = ((_UserDetailsImpl) us).getUser();*/
		
		Type listType = new TypeToken<List<Credito>>() {}.getType();
		List<Credito> creditos = new ModelMapper().map(creditoDTO, listType);
		HashMap<String, Object>res =creditoService.save(creditos,null/*c.getUsername()*/);
		return new ResponseEntity<>(res, HttpStatus.OK); 
	}
	@ResponseBody
	@GetMapping(value = "listar-creditos")
	public String listarCreditos() {	
		/*SecurityContextImpl sci = (SecurityContextImpl) (session().getAttribute("SPRING_SECURITY_CONTEXT"));
		Object us = (Object) sci.getAuthentication().getPrincipal();
		User c = ((_UserDetailsImpl) us).getUser();*/
		
		dataTableVWNuevoCredito.setData(creditoService.listarNuevosCreditos(null/*c.getContribuyente().getId()*/ ));
		 Gson gson = new GsonBuilder().setPrettyPrinting().create();
		 return gson.toJson(dataTableVWNuevoCredito);
	}
	
	@GetMapping(value = "/abrirRegistrarCreditos")
	public String buscar() {
		
		return "/user/creditos";
	}
	
	@ResponseBody
	@PostMapping(value = "eliminarCredito", consumes = "application/json",produces =  { "application/json" })
	public ResponseEntity<?> eliminarRepresentante(@RequestBody  CreditoDTO credito) {
		/*SecurityContextImpl sci = (SecurityContextImpl) (session().getAttribute("SPRING_SECURITY_CONTEXT"));
		Object us = (Object) sci.getAuthentication().getPrincipal();
		User c = ((_UserDetailsImpl) us).getUser();*/
		Integer a=0;
		try {
			a=creditoService.delete(credito.getIdCredito(), null/*c.getUsername()*/);
		} catch (Exception e) {
			a=0;
			e.printStackTrace();
		}
		return new ResponseEntity<>(a, HttpStatus.OK);
	}
	
	@ResponseBody
	@GetMapping(value = "listar-creditosregistrados")
	public String listarCreditosRegistrados(@RequestParam(required = false, name = "codigoCN") Integer codigoCN) {	
		/*SecurityContextImpl sci = (SecurityContextImpl) (session().getAttribute("SPRING_SECURITY_CONTEXT"));
		Object us = (Object) sci.getAuthentication().getPrincipal();
		User c = ((_UserDetailsImpl) us).getUser();*/
		/*if(c.getPerfil().getId()==2)
			codigoCN=c.getContribuyente().getId();*/
		dataTableVWCreditoRegistrado.setData(creditoService.findCreditosRegistrados(codigoCN));
		 Gson gson = new GsonBuilder().setPrettyPrinting().create();
		 return gson.toJson(dataTableVWCreditoRegistrado);
	}
	
}
