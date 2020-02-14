package com.kemal.spring.web.controllers.restApiControllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.text.html.HTML.Tag;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.kemal.spring.domain.Concesionario;
import com.kemal.spring.service.ConcesionarioService;
import com.kemal.spring.web.controllers.restApiControllers.dto.ContribuyenteDto;
@RestController
@RequestMapping("api/contribuyente")
@Scope("session")
public class ContribuyenteRestController {
	
	private ModelMapper modelMapper = new ModelMapper();
	
	@Autowired
	ConcesionarioService contribuyenteService;
	
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
	
	@ResponseBody
	@PostMapping(value = "actualizar-contribuyente", consumes = "application/json",produces =  { "application/json" })
	public ResponseEntity<?> actualizarContribuyente(@RequestBody ContribuyenteDto contribuyenteDto) {
		System.out.println("correo: " + contribuyenteDto.getSCorreo());
		System.out.println("telefono: " + contribuyenteDto.getSTelefono());
		Concesionario contribuyente = modelMapper.map(contribuyenteDto, Concesionario.class);		
		return new ResponseEntity<>(contribuyenteService.actualizarContribuyenteByRuc(contribuyente), HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/getContribuyente", method = RequestMethod.GET)
	public @ResponseBody
	List<Concesionario> getTags(@RequestParam String tagName) {

		return simulateSearchResult(tagName);

	}

	private List<Concesionario> simulateSearchResult(String tagName) {

		List<Concesionario> result = new ArrayList<Concesionario>();
		List<Concesionario> data=contribuyenteService.listaContribuyente();
		// iterate a list and filter by tagName
		for (Concesionario tag : data) {
			if (tag.getSnombre().contains(tagName)) {
				result.add(tag);
			}
		}

		return result;
	}

}
