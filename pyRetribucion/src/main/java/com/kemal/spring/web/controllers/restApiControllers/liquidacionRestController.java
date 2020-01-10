package com.kemal.spring.web.controllers.restApiControllers;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
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

import com.kemal.spring.domain.Liquidacion;
import com.kemal.spring.service.LiquidacionService;
import com.kemal.spring.web.controllers.restApiControllers.dto.LiquidacionDto;
import com.kemal.spring.web.controllers.restApiControllers.dto.LiquidacionesDto;

@RestController
@RequestMapping("api/liquidacion")
@Scope("session")
public class liquidacionRestController {
	@Autowired
	LiquidacionService liquidacionService;
	
	@Value("${total-registro-por-pagina}")
	private int totalRegistroPorPagina;
	
	@PostMapping(value = "registrar-liquidacion", consumes = "application/json",produces =  { "application/json" })
	@ResponseBody
	public ResponseEntity<?> registrarLiquidacion(@RequestBody List<LiquidacionDto> liquidacionesDto) {
		
		Type listType = new TypeToken<List<Liquidacion>>() {}.getType();
		Type listTypeResult = new TypeToken<List<LiquidacionDto>>() {}.getType();
		List<Liquidacion> liquidaciones = new ModelMapper().map(liquidacionesDto, listType);
		liquidacionService.save(liquidaciones);
		List<Liquidacion> liquidacionesRest = liquidacionService.listarLiquidaciones();
		List<LiquidacionDto> liquidacionesDtoRest = new ModelMapper().map(liquidacionesRest, listTypeResult);
		System.out.println("TOTALES: " + totalRegistroPorPagina);
		Map<String,Object> listaLiquidacion= liquidacionService.listarLiquidacion(1,totalRegistroPorPagina);
		HashMap<String, Object> resp = new HashMap<>();		
		resp.put("mensaje", "Se registró satisfactoriamente");
		resp.put("resultado", 1);
		resp.put("lista", liquidacionesDtoRest);
		resp.put("listaLiquidacion", listaLiquidacion);
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}
	@PostMapping(value = "listar-liquidacion", consumes = "application/json",produces =  { "application/json" })
	@ResponseBody
	public ResponseEntity<?> listarLiquidacion(@RequestBody LiquidacionesDto liquidacionesDto)throws Exception{		
		System.out.println("TOTALES: " + totalRegistroPorPagina);
		System.out.println("PAGINA: " + liquidacionesDto.getPagina());
		Map<String, Object> listaLiquidacion = liquidacionService.listarLiquidacion(liquidacionesDto.getPagina(),totalRegistroPorPagina);
		//List<ListarLiquidacion> listaLiquidacion = liquidacionService.listarLiquidacion2();
		HashMap<String, Object> resp = new HashMap<>();		
		resp.put("mensaje", "Todo correcto.");
		resp.put("resultado", 1);
		resp.put("listaLiquidacion", listaLiquidacion);
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}
}
