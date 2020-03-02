package com.kemal.spring.web.controllers.restApiControllers;


import java.util.List;

import javax.servlet.http.HttpSession;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

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
import com.kemal.spring.web.controllers.restApiControllers.dto.AnioDto;
import com.kemal.spring.web.controllers.restApiControllers.dto.AportePorcentajeDto;
import com.kemal.spring.web.controllers.restApiControllers.dto.MonedaDto;
import com.kemal.spring.web.controllers.restApiControllers.dto.MostrarDJDto;
import com.kemal.spring.web.controllers.restApiControllers.dto.ParseObjectUtil;
import com.kemal.spring.web.controllers.restApiControllers.dto.TipoPeriodicidadDto;
import com.kemal.spring.web.controllers.restApiControllers.dto.TipoRetribucionDto;
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
	ParseObjectUtil parseUtil;
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
	ParseObjectUtil parseObjectUtil;
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
	@SuppressWarnings("unchecked")
	@RequestMapping("/cargarDatos")
	public ResponseEntity<MostrarDJDto> cargarDatos() {
		MostrarDJDto m=new MostrarDJDto();
		User u = (User)session().getAttribute("oUsuario");
		m.setLstTipoPeriodicidad( (List<TipoPeriodicidadDto>)parseObjectUtil.parseList( calendarioService.findAll()));
		m.setLstAnios((List<AnioDto>)parseObjectUtil.parseList(util.getAnios()));
		m.setLstTipoRetribucion( (List<TipoRetribucionDto>)parseObjectUtil.parseList( tipoRetribucionService.findAll()));
		m.setLstMonedaRetribucion((List<MonedaDto>)parseObjectUtil.parseList( monedaService.findAll()));
		m.setLstAportePorcentaje((List<AportePorcentajeDto>)parseObjectUtil.parseList(aportePorcentajeService.findAll(u.getConcesionario().getId())));
		m.setCondicionBC( parseObjectUtil.parseObject(condicionBCservice.findByNCodigoCnsAndSEstado(u.getConcesionario().getId())));
		return new ResponseEntity<>(m, HttpStatus.OK);
	}
}

