package com.kemal.spring.web.controllers.viewControllers;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.kemal.spring.domain.Aporte;
import com.kemal.spring.domain.AporteDeduccion;
import com.kemal.spring.domain.AporteDescuento;
import com.kemal.spring.domain.AporteLiberacion;
import com.kemal.spring.domain.AportePorcentaje;
import com.kemal.spring.domain.BaseCalculo;
import com.kemal.spring.domain.CondicionBC;
import com.kemal.spring.domain.Moneda;
import com.kemal.spring.domain.PagoSinAporte;
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
import com.kemal.spring.web.dto.Util;
import com.kemal.spring.web.form.BuscarRetribucionForm;
import com.kemal.spring.web.form.RetribucionForm;

@Controller
@Scope("session")
public class MostrarRetribucionController {
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
	
	public static HttpSession session() {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		return attr.getRequest().getSession(); // true == allow create
	}

	@GetMapping(value = "/mostrarBuscar")
	public String mostrarBuscarDJ(@RequestParam(required = false, name = "tipoPeriodicidad") String tipoPeriodicidad,
			@RequestParam(required = false, name = "mes") String periodicidad,
			@RequestParam(required = false, name = "tipoRetribucion") String tipoRetribucion,
			@RequestParam(required = false, name = "anioRetribucion") String anioRetribucion,
			@RequestParam(required = false, name = "monedaRetribucion") String monedaRetribucion,
			@RequestParam(required = false, name = "buenContribuyente") String buenContribuyente,
			@RequestParam(required = false, name = "fdesde") String dfecReconocimientoDesde,
			@RequestParam(required = false, name = "fhasta") String dfecReconocimientoHasta,
			@RequestParam(required = false, name = "errorMessage") Integer errorMessage, Model model) {

		User u = (User)session().getAttribute("oUsuario");
		List<AportePorcentaje> lstAportePorcentaje = aportePorcentajeService.findAll(u.getConcesionario().getId());
		List<TipoPeriodicidad> lstCal = calendarioService.findAll();
		model.addAttribute("lsttipoPeriodicidad", lstCal);
		model.addAttribute("anios", util.getAnios());
		model.addAttribute("lsttipoRetribucion", tipoRetribucionService.findAll());
		model.addAttribute("lstMonedaRetribucion", monedaService.findAll());
		model.addAttribute("lstAportePorcentaje", lstAportePorcentaje);
		BuscarRetribucionForm x = new BuscarRetribucionForm();
		CondicionBC condicionBC = condicionBCservice.findByNCodigoCnsAndSEstado(u.getConcesionario().getId());
		x.setBuenContribuyente(condicionBC == null ? "" : condicionBC.getSBuenContribuyente());

		x.setMes(null == periodicidad || "".equals(periodicidad) ? "" : periodicidad);
		x.setAnioRetribucion(null == anioRetribucion || "".equals(periodicidad) ? "" : anioRetribucion);
		x.setTipoPeriodicidad(null == tipoPeriodicidad || "".equals(tipoPeriodicidad) ? "" : tipoPeriodicidad);
		x.setTipoRetribucion(null == tipoRetribucion || "".equals(tipoRetribucion) ? "" : tipoRetribucion);
		x.setMonedaRetribucion(null == monedaRetribucion||monedaRetribucion.length()==0? null : Integer.parseInt(monedaRetribucion));
		if (null != dfecReconocimientoDesde && !"".equals(dfecReconocimientoDesde))
			x.setFdesde(dfecReconocimientoDesde);
		if (null != dfecReconocimientoHasta && !"".equals(dfecReconocimientoHasta))
			x.setFhasta(dfecReconocimientoHasta);
		x.setLstAportePorcentajesize(lstAportePorcentaje.size());
		model.addAttribute("buscarRetribucionForm", x);

		model.addAttribute("buscarRetribucionForm.tipoPeriodicidad", tipoPeriodicidad);

		model.addAttribute("mes", periodicidad);
		model.addAttribute("buscarRetribucionForm.tipoRetribucion", tipoRetribucion);
		model.addAttribute("buscarRetribucionForm.anioRetribucion", anioRetribucion);
		model.addAttribute("buscarRetribucionForm.monedaRetribucion", monedaRetribucion);
		model.addAttribute("buscarRetribucionForm.buenContribuyente", x.getBuenContribuyente());
		model.addAttribute("buscarRetribucionForm.dfecReconocimientoDesde", dfecReconocimientoDesde);
		model.addAttribute("buscarRetribucionForm.dfecReconocimientoHasta", dfecReconocimientoHasta);
		model.addAttribute("csn", u.getConcesionario().getId());
		model.addAttribute("buscarRetribucionForm.errorMessage", errorMessage);
		model.addAttribute("lstAportePorcentajesize", lstAportePorcentaje.size());
		String msg = "";
		if (null != errorMessage) {
			if (errorMessage == 1) {
				msg = "El campo Tipo de Periodicidad es obligatorio.";

			} else

			if (errorMessage == 2) {
				msg = "El campo Tipo de Retribución es obligatorio.";

			} else

			if (errorMessage == 3) {
				msg = "El campo Periodicidad es obligatorio.";

			} else

			if (errorMessage == 4) {
				msg = "El campo Año es obligatorio.";

			} else

			if (errorMessage == 5) {
				msg = "Debe colocar la fecha en los campos Desde y Hasta.";

			} else

			if (errorMessage == 6) {
				msg = "El campo Moneda es obligatorio.";

			} else

			if (errorMessage == 7) {
				msg = "La fecha Hasta no puede ser menor que la fecha Desde.";

			}
		}

		model.addAttribute("errorMessage", null == msg ? "" : msg);
		return "/user/buscarDeclaracion";
	}

	@GetMapping(value = "/retribucion")
	public String retribucion(@RequestParam(required = false, name = "tipoPeriodicidad") String tipoPeriodicidad,
			@RequestParam(required = false, name = "mes") String periodicidad,
			@RequestParam(required = false, name = "tipoRetribucion") String tipoRetribucion,
			@RequestParam(required = false, name = "anioRetribucion") String anioRetribucion,
			@RequestParam(required = false, name = "monedaRetribucion") String monedaRetribucion,
			@RequestParam(required = false, name = "buenContribuyente") String buenContribuyente,
			@RequestParam(required = false, name = "fdesde") String dfecReconocimientoDesde,
			@RequestParam(required = false, name = "fhasta") String dfecReconocimientoHasta,
			@RequestParam(required = false, name = "porcentajehdd") String porcentajehdd,
			@RequestParam(required = false, name = "page") Integer page, Model model) {
		User u = (User)session().getAttribute("oUsuario");
		String errorMessage = "";
		BigDecimal porcentaje = new BigDecimal(
				"".equals(porcentajehdd) || "null".equals(porcentajehdd) ? "0" : porcentajehdd);
		if ("".equals(tipoPeriodicidad)) {
			errorMessage = "1";

		} else

		if ("".equals(tipoRetribucion)) {
			errorMessage = "2";

		} else

		if ("".equals(periodicidad)) {
			errorMessage = "3";

		} else

		if ("".equals(anioRetribucion)) {
			errorMessage = "4";

		} else

		if (null==monedaRetribucion||monedaRetribucion.length()==0) {
			errorMessage = "6";

		} else

		if ((!"".equals(dfecReconocimientoDesde) && "".equals(dfecReconocimientoHasta))
				|| ("".equals(dfecReconocimientoDesde) && !"".equals(dfecReconocimientoHasta))) {
			errorMessage = "5";

		} else

		if ((!"".equals(dfecReconocimientoDesde) && !"".equals(dfecReconocimientoHasta))
				&& util.strtoDate(dfecReconocimientoHasta).before(util.strtoDate(dfecReconocimientoDesde))) {
			errorMessage = "7";

		} else {

			Aporte aporte = aporteService.getAporte(tipoPeriodicidad, tipoRetribucion, periodicidad, anioRetribucion,
					u.getConcesionario().getId());

			RetribucionForm r = new RetribucionForm();
			r.setCodaportehdd(aporte.getId());
			r.setMesRetribucion(periodicidad);
			r.setAnioRetribucion(anioRetribucion);
			r.setTipoPeriodicidad(tipoPeriodicidad);
			r.setTipoRetribucion(tipoRetribucion);
			r.setTipoPeriodicidadDes(calendarioService.findById(Integer.parseInt(tipoPeriodicidad)).getSDescripcion());
			r.setTipoRetribucionDes(
					tipoRetribucionService.findById(Integer.parseInt(tipoPeriodicidad)).getSDescripcion());
			r.setPorcentaje(porcentaje);
			r.setMonedaRetribucion(monedaRetribucion);
			Moneda moneda = monedaService.findById((Integer.parseInt(monedaRetribucion)));
			r.setMonedaRetribucionDes(moneda.getSDescripcion());
			r.setMonedaSimbolo(moneda.getSSimbolo());
			r.setBuenContribuyente(buenContribuyente);

			if (null != dfecReconocimientoDesde && !"".equals(dfecReconocimientoDesde))
				r.setFdesde(dfecReconocimientoDesde);
			if (null != dfecReconocimientoHasta && !"".equals(dfecReconocimientoHasta))
				r.setFhasta(dfecReconocimientoHasta);

			r.setCodaportehdd(aporte.getId());


			PageRequest pageable = PageRequest.of((null == page ? 1 : page) - 1, 15);
			Page<BaseCalculo> articlePage = baseCalculoService.findByNcodigoApAndSEstado(aporte.getId(), pageable);
			int totalPages = articlePage.getTotalPages();
			if (totalPages > 0) {
				List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
				model.addAttribute("pageNumbers", pageNumbers);
			}
			BigDecimal retPendientePagar = BigDecimal.ZERO;
			model.addAttribute("activeArticleList", true);
			model.addAttribute("basec", articlePage.getContent());
			List<BaseCalculo> lstBaseCalculo = articlePage.getContent();
			BigDecimal totalBaseCalculo = lstBaseCalculo.stream().map(BaseCalculo::getNimporte).reduce(BigDecimal.ZERO,
					BigDecimal::add);
			if (null != porcentaje && (porcentaje.compareTo(BigDecimal.ZERO) > 0))
				retPendientePagar = (porcentaje.multiply(totalBaseCalculo));
			r.setBaseCalculo(totalBaseCalculo);
			r.setPagosprevios((pagoService
					.getListaPago(
							Integer.parseInt(tipoPeriodicidad), 
							Integer.parseInt(tipoRetribucion),
							periodicidad, anioRetribucion,
							u.getConcesionario().getId(),
							"0")
					.stream().map(PagoSinAporte::getNImporte).reduce(BigDecimal.ZERO, (a, t) -> {
						a = a.add(t);
						return a;
					})));
			r.setDeduccion(aporteDeduccionService
					.getListaSaldo(
							aporte.getId()
							)
					.stream().map(AporteDeduccion::getNImporteDed).reduce(BigDecimal.ZERO, (a, t) -> {
						a = a.add(t);
						return a;
					}));
			
			r.setLiberacionpago((aporteLiberacionPagoService
					.getListaSaldo(
							aporte.getId()
							)
					.stream().map(AporteLiberacion::getNImporteLib).reduce(BigDecimal.ZERO, (a, t) -> {
						a = a.add(t);
						return a;
					})));
			r.setOtrosDescuentos((aporteDescuentoService
					.getListaSaldo(
							aporte.getId()
							)
					.stream().map(AporteDescuento::getNImporteDes).reduce(BigDecimal.ZERO, (a, t) -> {
						a = a.add(t);
						return a;
					})));
			
		
			
			r.setRetribucionpendienteapagar( retPendientePagar);
			r.setRetribucionapagar(retPendientePagar);
			r.setRetribucionresultante(retPendientePagar);
			r.setCodaportehdd(aporte.getId());
			model.addAttribute("retribucionForm", r);
			return "/user/retribucion";

		}

		List<TipoPeriodicidad> lstCal = calendarioService.findAll();
		model.addAttribute("lsttipoPeriodicidad", lstCal);
		model.addAttribute("anios", util.getAnios());
		model.addAttribute("lsttipoRetribucion", tipoRetribucionService.findAll());
		model.addAttribute("lstMonedaRetribucion", monedaService.findAll());

		return "redirect:/mostrarBuscar?tipoPeriodicidad=" + tipoPeriodicidad + "&mes=" + periodicidad
				+ "&tipoRetribucion=" + tipoRetribucion + "&anioRetribucion=" + anioRetribucion + "&monedaRetribucion="
				+ monedaRetribucion + "&porcentajehdd=" + porcentajehdd + "&fdesde=" + dfecReconocimientoDesde
				+ "&fhasta=" + dfecReconocimientoHasta + "&errorMessage=" + errorMessage;

	}

}
