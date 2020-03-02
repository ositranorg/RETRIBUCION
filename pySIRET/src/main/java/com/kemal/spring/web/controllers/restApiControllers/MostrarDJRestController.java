package com.kemal.spring.web.controllers.restApiControllers;


import javax.servlet.http.HttpSession;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
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
import com.kemal.spring.web.controllers.restApiControllers.dto.MostrarDJDto;
import com.kemal.spring.web.dto.Util;

@RestController
@RequestMapping("api/mostrarDJ")
@Scope("session")
public class MostrarDJRestController {
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
	@RequestMapping("/cargarDatos")
	public @ResponseBody ObjectMapper cargarDatos() {
		 ObjectMapper objectMapper = new ObjectMapper();
		MostrarDJDto m=new MostrarDJDto();
		User u = (User)session().getAttribute("oUsuario");
		m.setLstTipoPeriodicidad(calendarioService.findAll());
		m.setLstAnios(util.getAnios());
		m.setLstTipoRetribucion( tipoRetribucionService.findAll());
		m.setLstMonedaRetribucion(monedaService.findAll());
		m.setLstAportePorcentaje(aportePorcentajeService.findAll(u.getConcesionario().getId()));
		m.setCondicionBC( condicionBCservice.findByNCodigoCnsAndSEstado(u.getConcesionario().getId()));
		 objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);

		return objectMapper;
	}
}

