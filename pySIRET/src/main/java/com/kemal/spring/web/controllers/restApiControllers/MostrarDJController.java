package com.kemal.spring.web.controllers.restApiControllers;


import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.kemal.spring.domain.AportePorcentaje;
import com.kemal.spring.domain.CondicionBC;
import com.kemal.spring.domain.TipoPeriodicidad;
import com.kemal.spring.domain.User;
import com.kemal.spring.service.AporteDeduccionService;
import com.kemal.spring.service.AporteDescuentoService;
import com.kemal.spring.service.AporteLiberacionService;
import com.kemal.spring.service.AportePorcentajeService;
import com.kemal.spring.service.AporteService;
import com.kemal.spring.service.BaseCalculoService;
import com.kemal.spring.service.CondicionBCService;
import com.kemal.spring.service.LiberacionPagoService;
import com.kemal.spring.service.MonedaService;
import com.kemal.spring.service.OtroDescuentoService;
import com.kemal.spring.service.PagoService;
import com.kemal.spring.service.TipoPeriodicidadService;
import com.kemal.spring.service.TipoRetribucionService;
import com.kemal.spring.web.controllers.restApiControllers.dto.RepresentanteDto;
import com.kemal.spring.web.dto.Util;

@RestController
@RequestMapping("api/mostrarDJ")
@Scope("session")
public class MostrarDJController {
	@Autowired
	TipoPeriodicidadService calendarioService;
	@Autowired
	Util util;
	@Autowired
	TipoRetribucionService tipoRetribucionService;

	@Autowired
	AporteService aporteService;
	@Autowired
	AportePorcentajeService aportePorcentajeService;
	@Autowired
	BaseCalculoService baseCalculoService;
	@Autowired
	AporteDeduccionService aporteDeduccionService;
	@Autowired
	AporteLiberacionService aporteLiberacionPagoService;
	@Autowired
	AporteDescuentoService aporteDescuentoService;	
	@Autowired
	LiberacionPagoService liberacionPagoService;
	@Autowired
	OtroDescuentoService otrosDescuentosService;
	@Autowired
	PagoService pagoService;
	@Autowired
	CondicionBCService condicionBCservice;
	@Autowired
	MonedaService monedaService;
	@Autowired
	public  HttpSession session() {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		return attr.getRequest().getSession(); // true == allow create
	}
	private ModelMapper modelMapper = new ModelMapper();
	
	@GetMapping(value = "/mostrarBuscar")
	public String mostrarBuscarDJ() {
		return "/user/buscarDeclaracion";
	}
	@ResponseBody
	@PostMapping(value = "/cargarDatos", consumes = "application/json",produces =  { "application/json" })
	public ResponseEntity<?> registrarRepresentante(@RequestBody RepresentanteDto representanteDto) {
		HashMap<String, Object> res =new HashMap<String, Object>();
		
		User u = (User)session().getAttribute("oUsuario");
		List<AportePorcentaje> lstAportePorcentaje = aportePorcentajeService.findAll(u.getConcesionario().getId());
		List<TipoPeriodicidad> lstCal = calendarioService.findAll();
		res.put("lsttipoPeriodicidad", lstCal);
		res.put("anios", util.getAnios());
		res.put("lsttipoRetribucion", tipoRetribucionService.findAll());
		res.put("lstMonedaRetribucion", monedaService.findAll());
		res.put("lstAportePorcentaje", lstAportePorcentaje);
		CondicionBC condicionBC = condicionBCservice.findByNCodigoCnsAndSEstado(u.getConcesionario().getId());
		res.put("condicionBC", condicionBC);
		res.put("csn", u.getConcesionario().getId());
		res.put("lstAportePorcentajesize", lstAportePorcentaje.size());
		

		return new ResponseEntity<>(null, HttpStatus.OK);
	}
	
}

