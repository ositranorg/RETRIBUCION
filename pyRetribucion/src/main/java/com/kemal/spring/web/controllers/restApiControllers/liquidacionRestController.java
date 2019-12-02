package com.kemal.spring.web.controllers.restApiControllers;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
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

@RestController
@RequestMapping("api/liquidacion")
@Scope("session")
public class liquidacionRestController {
	@Autowired
	LiquidacionService liquidacionService;
	
	@PostMapping(value = "registrar-liquidacion", consumes = "application/json",produces =  { "application/json" })
	@ResponseBody
	public ResponseEntity<?> registrarLiquidacion(@RequestBody List<LiquidacionDto> liquidacionesDto) {
		
		Type listType = new TypeToken<List<Liquidacion>>() {}.getType();
		Type listTypeResult = new TypeToken<List<LiquidacionDto>>() {}.getType();
		List<Liquidacion> liquidaciones = new ModelMapper().map(liquidacionesDto, listType);
		liquidacionService.save(liquidaciones);
		List<Liquidacion> liquidacionesRest = liquidacionService.listarLiquidaciones();
		List<LiquidacionDto> liquidacionesDtoRest = new ModelMapper().map(liquidacionesRest, listTypeResult);
		
		HashMap<String, Object> resp = new HashMap<>();		
		resp.put("mensaje", "Se registró satisfactoriamente");
		resp.put("resultado", 1);
		resp.put("lista", liquidacionesDtoRest);
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}
}
