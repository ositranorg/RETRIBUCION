package com.kemal.spring.web.controllers.restApiControllers;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

import org.modelmapper.ModelMapper;
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

import com.kemal.spring.domain.Representante;
import com.kemal.spring.domain.RepresentanteRepository;
import com.kemal.spring.service.RepresentanteService;
import com.kemal.spring.web.controllers.restApiControllers.dto.ContribuyenteDto;
import com.kemal.spring.web.controllers.restApiControllers.dto.RepresentanteDto;

@RestController
@RequestMapping("api/representante")
@Scope("session")
public class RepresentanteRestController {
	@Autowired
	RepresentanteService representanteService;
	
	@Value("${total-registro-por-pagina}")
	private int totalRegistroPorPagina;
	
	private ModelMapper modelMapper = new ModelMapper();
	
	@ResponseBody
	@PostMapping(value = "registrar-representante", consumes = "application/json",produces =  { "application/json" })
	public ResponseEntity<?> registrarRepresentante(@RequestBody RepresentanteDto representanteDto) {
		System.out.println("nombreDto: " + representanteDto.getSNombres());
		
		Representante representante = modelMapper.map(representanteDto, Representante.class);
		System.out.println("nombre: " + representante.getSNombres());
		

		HashMap<String, Object>res = representanteService.registrarRepresentante(representante,representanteDto.getPagina(),totalRegistroPorPagina);
			
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	@ResponseBody
	@PostMapping(value = "listar-representante", consumes = "application/json",produces =  { "application/json" })
	public ResponseEntity<?> listarRepresentante(@RequestBody ContribuyenteDto contribuyenteDto) {
	
		//List<Representante> lista = representanteService.listarRepresentantesByRuc(contribuyenteDto.getSruc());
		
		HashMap<String, Object>res = new HashMap<String, Object>();
		res.put("resultado", 1);
		res.put("lista", representanteService.listarRepresentantesByRucPaginado(contribuyenteDto.getSruc(), contribuyenteDto.getPagina(), totalRegistroPorPagina));
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
}
