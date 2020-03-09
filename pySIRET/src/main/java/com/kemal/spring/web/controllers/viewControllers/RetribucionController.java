/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kemal.spring.web.controllers.viewControllers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.w3c.dom.Document;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.kemal.spring.domain.AportePorcentaje;
import com.kemal.spring.domain.BaseCalculo;
import com.kemal.spring.domain.Concepto;
import com.kemal.spring.domain.Deduccion;
import com.kemal.spring.domain.Descuento;
import com.kemal.spring.domain.LiberacionPago;
import com.kemal.spring.domain.User;
import com.kemal.spring.service.AporteDeduccionService;
import com.kemal.spring.service.AporteDescuentoService;
import com.kemal.spring.service.AporteLiberacionService;
import com.kemal.spring.service.AportePorcentajeService;
import com.kemal.spring.service.AporteService;
import com.kemal.spring.service.BaseCalculoService;
import com.kemal.spring.service.ConceptoService;
import com.kemal.spring.service.DeduccionService;
import com.kemal.spring.service.LiberacionPagoService;
import com.kemal.spring.service.MonedaService;
import com.kemal.spring.service.OtroDescuentoService;
import com.kemal.spring.service.PagoService;
import com.kemal.spring.service.TipoPeriodicidadService;
import com.kemal.spring.service.TipoRetribucionService;
import com.kemal.spring.service.VencimientoService;
import com.kemal.spring.web.controllers.restApiControllers.dto.BuscarDJDto;
import com.kemal.spring.web.dto.AporteDeduccionModalDTO;
import com.kemal.spring.web.dto.AporteDescuentoModalDTO;
import com.kemal.spring.web.dto.AporteLiberacionModalDTO;
import com.kemal.spring.web.dto.BaseCalculoConceptodto;
import com.kemal.spring.web.dto.Util;
import com.kemal.spring.web.form.RetribucionForm;

/**
 *
 * @author consultor_jti07
 */
@Controller
@Scope("session")
public class RetribucionController {
	@Autowired
	VencimientoService calendarioDetService;
	

	@Autowired
	ConceptoService conceptoService;

	@Autowired
	BaseCalculoService baseCalculoService;
	@Autowired
	AporteService aporteService;

	@Autowired
	TipoPeriodicidadService calendarioService;
	@Autowired
	Util util;
	@Autowired
	TipoRetribucionService tipoRetribucionService;

	@Autowired
	LiberacionPagoService liberacionPagoService;

	@Autowired
	OtroDescuentoService otrosDescuentosService;

	@Autowired
	AportePorcentajeService aportePorcentajeService;

	@Autowired
	DeduccionService deduccionService;
	@Autowired
	MonedaService monedaService;

	@Autowired
	PagoService pagoService;
	
	@Autowired
	AporteLiberacionService aporteLiberacionService;
	@Autowired
	AporteDescuentoService aporteDescuentoService;	
	@Autowired
	AporteDeduccionService aporteDeduccionService;
	public static HttpSession session() {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		return attr.getRequest().getSession(); // true == allow create
	}
	 @RequestMapping(value = "/retribucion/registrar", method = RequestMethod.GET)
	    public String entrarDJ(Model model,HttpServletRequest request,HttpServletResponse response) {
			 BuscarDJDto c=new BuscarDJDto();
			 System.out.println("entrarDJ");
			 User u = (User)session().getAttribute("oUsuario");
			 System.out.println(request.getParameter(""));
			 /*model.addAttribute("ptipoperiodicidad",buscarDJDto.getTipoperiodicidad());
			 model.addAttribute("ptiporetribucion",buscarDJDto.getTiporetribucion());
			 model.addAttribute("panio",buscarDJDto.getSanioperiodo());
			 model.addAttribute("pmes",buscarDJDto.getSmesperiodo());
			 
			 model.addAttribute("pporcentaje",buscarDJDto.getPorcentaje());
			 model.addAttribute("pmoneda",buscarDJDto.getMoneda());
			 */
			 return "/user/retribucion";
	    }
	/*@RequestMapping(value = { "/retribucion/registrar" }, method = RequestMethod.GET)
	public ModelAndView pago(ModelMap model, @PathVariable String calendarioSel, @PathVariable String anioSel) {
		User u = (User)session().getAttribute("oUsuario");

		RetribucionForm form = new RetribucionForm();

		ModelAndView view = new ModelAndView("/user/retribucion", "command", form);

		return view;
	}*/

	@GetMapping(value = "/retribucion/openDeduccion")
	@ResponseBody
	public List<AporteDeduccionModalDTO> deduccion(@RequestParam(required = false, name = "moneda") Integer moneda) {
		try {
			User u = (User)session().getAttribute("oUsuario");
			List<Deduccion> lfindByNCodigoCnsAndSEstado = deduccionService.getListaSaldo(u.getConcesionario().getId(),
					moneda, "1");
			List<AporteDeduccionModalDTO> lst = new ArrayList<AporteDeduccionModalDTO>();
			lfindByNCodigoCnsAndSEstado.stream().forEach((a) -> {
				Calendar cal = Calendar.getInstance();
				cal.setTime(a.getDfecRegistro());
				AporteDeduccionModalDTO d = new AporteDeduccionModalDTO();
				d.setId(a.getId());
				d.setMoneda(a.getMoneda().getSDescripcion());
				d.setNImporte(a.getNImporte());
				d.setNSaldo(null == a.getNSaldo() ? BigDecimal.ZERO : a.getNSaldo());
				d.setSDescripcion(null == a.getSDescripcion() ? "" : a.getSDescripcion());
				d.setDfecRegistro(
						cal.get(Calendar.DATE) + "/" + cal.get(Calendar.MONTH) + "/" + cal.get(Calendar.YEAR));
				lst.add(d);
			});
			return lst;

		} catch (final Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@GetMapping(value = "/retribucion/openLiberacion")
	@ResponseBody
	public List<AporteLiberacionModalDTO> liberacion(@RequestParam(required = false, name = "moneda") Integer moneda) {
		try {
			User u = (User)session().getAttribute("oUsuario");
			List<LiberacionPago> lfindByNCodigoCnsAndSEstado = liberacionPagoService
					.getListaSaldo(u.getConcesionario().getId(), moneda, "1");
			List<AporteLiberacionModalDTO> lst = new ArrayList<AporteLiberacionModalDTO>();
			lfindByNCodigoCnsAndSEstado.stream().forEach((a) -> {
				Calendar cal = Calendar.getInstance();
				cal.setTime(a.getDfecReconocimiento());
				AporteLiberacionModalDTO d = new AporteLiberacionModalDTO();
				d.setId(a.getId());
				d.setMoneda(a.getMoneda().getSDescripcion());
				d.setNImporte(a.getNImporte());
				d.setNSaldo(null == a.getNSaldo() ? BigDecimal.ZERO : a.getNSaldo());
				d.setSDescripcion(null == a.getSDescripcion() ? "" : a.getSDescripcion());
				d.setDfecReconocimiento(
						cal.get(Calendar.DATE) + "/" + cal.get(Calendar.MONTH) + "/" + cal.get(Calendar.YEAR));
				lst.add(d);
			});
			return lst;

		} catch (final Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@GetMapping(value = "/retribucion/openDescuento")
	@ResponseBody
	public List<AporteDescuentoModalDTO> otrosDescuentos(
			@RequestParam(required = false, name = "moneda") Integer moneda) {
		try {
			User u = (User)session().getAttribute("oUsuario");
			List<Descuento> lotrosDescuentos = otrosDescuentosService.getListaSaldo(u.getConcesionario().getId(),
					moneda, "1");
			List<AporteDescuentoModalDTO> lst = new ArrayList<AporteDescuentoModalDTO>();
			lotrosDescuentos.stream().forEach((a) -> {
				Calendar cal = Calendar.getInstance();
				cal.setTime(a.getDfecRegistro());
				AporteDescuentoModalDTO d = new AporteDescuentoModalDTO();
				d.setId(a.getId());
				d.setMoneda(a.getMoneda().getSDescripcion());
				d.setNImporte(a.getNImporte());
				d.setNSaldo(null == a.getNSaldo() ? BigDecimal.ZERO : a.getNSaldo());
				d.setSDescripcion(null == a.getSDescripcion() ? "" : a.getSDescripcion());
				d.setDfecRegistro(
						cal.get(Calendar.DATE) + "/" + cal.get(Calendar.MONTH) + "/" + cal.get(Calendar.YEAR));
				lst.add(d);
			});
			return lst;
		} catch (final Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@GetMapping(value = "/retribucion/getPorcentaje")
	@ResponseBody
	public String getPorcentaje(@RequestParam(required = false, name = "tipoRetribucion") int tipoRetribucion) {
		AportePorcentaje c = null;
		try {
			User u = (User)session().getAttribute("oUsuario");
			c = aportePorcentajeService.findByContribuyenteAndTipoRetribucionAndSEstado(u.getConcesionario().getId(),
					tipoRetribucion);
			if (null != c)
				return c.getPorcentaje().toString();
		} catch (final Exception e) {
			e.printStackTrace();
			return "";
		}
		return "";

	}

	@PostMapping(value = "/retribucion/baseCalculo")
	@ResponseBody
	public List<BaseCalculoConceptodto> action() {
		try {
			User u = (User)session().getAttribute("oUsuario");
			final int codigo = u.getConcesionario().getId().intValue();

			List<BaseCalculoConceptodto> s = new ArrayList<BaseCalculoConceptodto>();
			List<Concepto> ls = conceptoService.findConceptosBaseImponible();
			ls.stream().forEach((f) -> {

				BaseCalculoConceptodto a = new BaseCalculoConceptodto();
				a.setId(f.getId());
				a.setSDescripcion(f.getSDescripcion());
				System.out.println(f.getSDescripcion());
				if (codigo == 2) {
					s.add(a);
				} else if (u.getConcesionario().getId().intValue() != 2 && (f.getId() < 7)) {
					s.add(a);
				}

			});
			return s;
		} catch (final Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@PostMapping(value = "/guardarbi")
	public String guardarbi(RedirectAttributes a, @ModelAttribute("command") RetribucionForm retribucion,
			ModelMap model) {
		try {

			if (!(retribucion.getCodigobihdd().intValue() == 0 && retribucion.getAccion().equals("E"))) {
				BaseCalculo calculo = new BaseCalculo();
				calculo.setId(retribucion.getCodigobihdd().intValue() == 0 ? null : retribucion.getCodigobihdd());
				calculo.setSDescripcion(retribucion.getBiconcepto());

				calculo.setNimporte(retribucion.getBiimporte());
				Concepto concepto = new Concepto();
				concepto.setId(Integer.parseInt(retribucion.getCboDetalleBC()));
				calculo.setConcepto(concepto);

				calculo.setNcodigoAp(retribucion.getCodaportebihdd());
				calculo.setSEstado(retribucion.getAccion().equals("E") ? "0" : "1");
				baseCalculoService.save(calculo);
			} else {
				baseCalculoService.updateEliminarContenedorBi(retribucion.getCodaportebihdd());
			}

		} catch (final Exception e) {
			e.printStackTrace();

		}
		model.addAttribute("retribucionForm", retribucion);

		return "redirect:/retribucion?" + "tipoPeriodicidad=" + retribucion.getTipoPeriodicidad() + "&tipoRetribucion="
				+ retribucion.getTipoRetribucion() + "&mes=" + retribucion.getMesRetribucion() + "&anioRetribucion="
				+ retribucion.getAnioRetribucion() + "&porcentajehdd=" + retribucion.getPorcentajehdd()
				+ "&monedaRetribucion=" + retribucion.getMonedaRetribucion() + "&buenContribuyente="
				+ retribucion.getBuenContribuyente() + "&fdesde=" + retribucion.getFdesde() + "&fhasta="
				+ retribucion.getFhasta();

	}

	@PostMapping(value = "/aporteDeduccion")
	public String guardarDeduccion(RedirectAttributes a, @ModelAttribute("command") RetribucionForm retribucion,
			ModelMap model) {
		try {
			User u = (User)session().getAttribute("oUsuario");
			aporteService.updateDeduccion(retribucion.getIddeduccionhdd(), retribucion.getDeduccion(),
					retribucion.getCodaportehdd(), u.getUsername());

		} catch (final Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("retribucionForm", retribucion);

		return "redirect:/retribucion?tipoPeriodicidad=" + retribucion.getTipoPeriodicidad() + "&tipoRetribucion="
				+ retribucion.getTipoRetribucion() + "&mes=" + retribucion.getMesRetribucion() + "&anioRetribucion="
				+ retribucion.getAnioRetribucion() + "&monedaRetribucion=" + retribucion.getMonedaRetribucion()
				+ "&buenContribuyente=" + retribucion.getBuenContribuyente() + "&porcentajehdd="
				+ retribucion.getPorcentajehdd() + "&fdesde=" + retribucion.getFdesde() + "&fhasta="
				+ retribucion.getFhasta();
	}

	@PostMapping(value = "/aporteLiberacion")
	public String guardarliberacion(RedirectAttributes a, @ModelAttribute("command") RetribucionForm retribucion,
			ModelMap model) {
		try {
			User u = (User)session().getAttribute("oUsuario");
			aporteService.updateLiberacion(retribucion.getIdliberacionhdd(), retribucion.getLiberacionpago(),
					retribucion.getCodaportehdd(), u.getUsername());

		} catch (final Exception e) {
			e.printStackTrace();

		}
		model.addAttribute("retribucionForm", retribucion);

		return "redirect:/retribucion?tipoPeriodicidad=" + retribucion.getTipoPeriodicidad() + "&tipoRetribucion="
				+ retribucion.getTipoRetribucion() + "&mes=" + retribucion.getMesRetribucion() + "&anioRetribucion="
				+ retribucion.getAnioRetribucion() + "&monedaRetribucion=" + retribucion.getMonedaRetribucion()
				+ "&buenContribuyente=" + retribucion.getBuenContribuyente() + "&porcentajehdd="
				+ retribucion.getPorcentajehdd() + "&fdesde=" + retribucion.getFdesde() + "&fhasta="
				+ retribucion.getFhasta();

	}

	@PostMapping(value = "/aporteOtros")
	public String guardarOtroDescuento(RedirectAttributes a, @ModelAttribute("command") RetribucionForm retribucion,
			ModelMap model) {
		try {
			User u = (User)session().getAttribute("oUsuario");

			aporteService.updateDescuento(retribucion.getIddescuentohdd(), retribucion.getOtrosDescuentos(),
					retribucion.getCodaportehdd(), u.getUsername());

		} catch (final Exception e) {
			e.printStackTrace();

		}

		model.addAttribute("retribucionForm", retribucion);

		return "redirect:/retribucion?tipoPeriodicidad=" + retribucion.getTipoPeriodicidad() + "&mes="
				+ retribucion.getMesRetribucion() + "&tipoRetribucion=" + retribucion.getTipoRetribucion()
				+ "&anioRetribucion=" + retribucion.getAnioRetribucion() + "&monedaRetribucion="
				+ retribucion.getMonedaRetribucion() + "&buenContribuyente=" + retribucion.getBuenContribuyente()
				+ "&porcentajehdd=" + retribucion.getPorcentajehdd() + "&fdesde=" + retribucion.getFdesde() + "&fhasta="
				+ retribucion.getFhasta();

	}

	@PostMapping(value = "/eliminarLiberacion")
	public String eliminarLiberacion(RedirectAttributes a, @ModelAttribute("command") RetribucionForm retribucion,
			ModelMap model) {
		try {
			User u = (User)session().getAttribute("oUsuario");

			aporteService.eliminarLiberacion(retribucion.getCodaportehdd(), u.getUsername());

		} catch (final Exception e) {
			e.printStackTrace();

		}
		model.addAttribute("retribucionForm", retribucion);

		return "redirect:/retribucion?tipoPeriodicidad=" + retribucion.getTipoPeriodicidad() + "&tipoRetribucion="
				+ retribucion.getTipoRetribucion() + "&mes=" + retribucion.getMesRetribucion() + "&anioRetribucion="
				+ retribucion.getAnioRetribucion() + "&monedaRetribucion=" + retribucion.getMonedaRetribucion()
				+ "&porcentajehdd=" + retribucion.getPorcentajehdd() + "&buenContribuyente="
				+ retribucion.getBuenContribuyente() + "&fdesde=" + retribucion.getFdesde() + "&fhasta="
				+ retribucion.getFhasta();

	}

	@PostMapping(value = "/eliminarOtro")
	public String eliminarOtro(RedirectAttributes a, @ModelAttribute("command") RetribucionForm retribucion,
			ModelMap model) {
		try {
			User u = (User)session().getAttribute("oUsuario");

			aporteService.eliminarDescuento(retribucion.getCodaportehdd(), u.getUsername());

		} catch (final Exception e) {
			e.printStackTrace();

		}
		model.addAttribute("retribucionForm", retribucion);

		return "redirect:/retribucion?tipoPeriodicidad=" + retribucion.getTipoPeriodicidad() + "&tipoRetribucion="
				+ retribucion.getTipoRetribucion() + "&mes=" + retribucion.getMesRetribucion() + "&anioRetribucion="
				+ retribucion.getAnioRetribucion() + "&monedaRetribucion=" + retribucion.getMonedaRetribucion()
				+ "&porcentajehdd=" + retribucion.getPorcentajehdd() + "&buenContribuyente="
				+ retribucion.getBuenContribuyente() + "&fdesde=" + retribucion.getFdesde() + "&fhasta="
				+ retribucion.getFhasta();

	}

	@PostMapping(value = "/eliminarDeduccion")
	public String eliminarDeduccion(RedirectAttributes a, @ModelAttribute("command") RetribucionForm retribucion,
			ModelMap model) {
		try {
			User u = (User)session().getAttribute("oUsuario");
			aporteService.eliminarDeduccion(retribucion.getCodaportehdd(), u.getUsername());

		} catch (final Exception e) {
			e.printStackTrace();

		}
		model.addAttribute("retribucionForm", retribucion);

		return "redirect:/retribucion?tipoPeriodicidad=" + retribucion.getTipoPeriodicidad() + "&tipoRetribucion="
				+ retribucion.getTipoRetribucion() + "&mes=" + retribucion.getMesRetribucion() + "&anioRetribucion="
				+ retribucion.getAnioRetribucion() + "&monedaRetribucion=" + retribucion.getMonedaRetribucion()
				+ "&porcentajehdd=" + retribucion.getPorcentajehdd() + "&buenContribuyente="
				+ retribucion.getBuenContribuyente() + "&fdesde=" + retribucion.getFdesde() + "&fhasta="
				+ retribucion.getFhasta();

	}
	
	

	@PostMapping(value = "/grabarInteres")
	public String grabarInteres(RedirectAttributes a, @ModelAttribute("command") RetribucionForm retribucion,
			ModelMap model) {
		try {
			User u = (User)session().getAttribute("oUsuario");

			aporteService.grabarInteresResultante(
					retribucion.getCodaportehdd(),
					retribucion.getIntereses(), 
					retribucion.getRetribucionresultante(),
					u.getUsername());

		} catch (final Exception e) {
			e.printStackTrace();

		}
		model.addAttribute("retribucionForm", retribucion);

		return "redirect:/retribucion?tipoPeriodicidad=" + retribucion.getTipoPeriodicidad() + "&tipoRetribucion="
				+ retribucion.getTipoRetribucion() + "&mes=" + retribucion.getMesRetribucion() + "&anioRetribucion="
				+ retribucion.getAnioRetribucion() + "&monedaRetribucion=" + retribucion.getMonedaRetribucion()
				+ "&porcentajehdd=" + retribucion.getPorcentajehdd() + "&buenContribuyente="
				+ retribucion.getBuenContribuyente() + "&fdesde=" + retribucion.getFdesde() + "&fhasta="
				+ retribucion.getFhasta();

	}
	
	@PostMapping(value = "/grabarRetResultante")
	public String grabarRetResultante(RedirectAttributes a, @ModelAttribute("command") RetribucionForm retribucion,
			ModelMap model) {
		try {
			User u = (User)session().getAttribute("oUsuario");

			aporteService.grabarInteresResultante(
					retribucion.getCodaportehdd(),
					null, 
					retribucion.getRetribucionresultante(),
					u.getUsername());

		} catch (final Exception e) {
			e.printStackTrace();

		}
		model.addAttribute("retribucionForm", retribucion);

		return "redirect:/retribucion?tipoPeriodicidad=" + retribucion.getTipoPeriodicidad() + "&tipoRetribucion="
				+ retribucion.getTipoRetribucion() + "&mes=" + retribucion.getMesRetribucion() + "&anioRetribucion="
				+ retribucion.getAnioRetribucion() + "&monedaRetribucion=" + retribucion.getMonedaRetribucion()
				+ "&porcentajehdd=" + retribucion.getPorcentajehdd() + "&buenContribuyente="
				+ retribucion.getBuenContribuyente() + "&fdesde=" + retribucion.getFdesde() + "&fhasta="
				+ retribucion.getFhasta();

	}
	
	
	
	@PostMapping(value = "/graboexitoso")
	public void xx(RedirectAttributes a, @ModelAttribute("command") RetribucionForm retribucion, ModelMap model,
			HttpServletRequest request, HttpServletResponse response) {

		User u = (User)session().getAttribute("oUsuario");

		response.setContentType("text/html;charset=UTF-8");
		response.setContentType("application/pdf");
		OutputStream out = null;
		try {
			
			aporteService.presentar(retribucion.getCodaportehdd(), u.getUsername());
			
			
			
			out = response.getOutputStream();

			StringBuffer buf = new StringBuffer();

			buf.append("<html><head>"

					+ "<style>" + "@page {     " + "   size: 210mm 291mm;  " + "   margin-top: 1.0cm;  "
					+ "   margin-bottom:1.4cm; " + "   margin-left: 1cm;   " + "   margin-right: 1cm;  "
					+ "   padding: 1em;   " + "@top-right {    " + "   content: element(header);    " + "}"
					+ "@bottom-center {    " + "   content: element(footer);    " + "}" + "}"
					+ " #pagenumber:before {   content: counter(page); } "
					+ " #pagecount:before {   content: counter(pages);    }" + " .pie { font-size : 11px;}"
					+ "@page :first {      " + "@bottom-right {" + "content: element(firstPageFooter);" + "}" + "}"
					+ "body {" + "    margin-left:0px;" + "    margin-right:0px;" + "    margin-top:0px;"
					+ "    margin-bottom:10px;" + "    padding:0px;" + "    font-family:Arial, Helvetica, sans-serif ;"
					+ "    font-size: 10px;" + "    }  " + "#parent{ " + "    overflow:hidden; "
					+ "    position:relative; " + "} " + ".left{ " + "    padding-right:50px; " + "} " + ".right{ "
					+ "    width:200px; " + "    position:absolute; " + "    height:100%; " + "    top:0; "
					+ "    right:0; " + "}" + "h4 { font-size: 12px; font-weight: bold;}"
					+ "table { border-collapse: collapse; }"
					+ "th { font-weight: bold; text-align: left; text-align: center;}"
					+ ".negrita { font-weight: bold; }" + ".importe { text-align: right; }"
					+ ".centro { text-align: center; }" + "</style> ");

			buf.append("</head><body>");
			// CABECERA

			buf.append("<div id=\"parent\">\n"
					+ "  <div class=\"left\"> <img src=\"https://www.ositran.gob.pe/images/logo.png\" height=\"100\" width=\"200\"/></div>"
					+ "  <div class=\"right\" width=\"500px\" >"
					+ "       <table border=\"1\" style=\"text-align : center;margin-right:100px;\" > "
					+ "           <tr> <td colspan=\"2\"> PERIODO TRIBUTARIO </td> </tr>"
					+ "           <tr> <td> TIPO DE PERIODICIDAD </td> <td> TIPO DE RETRIBUCIÓN </td> </tr> "
					+ "           <tr> <td> "
					+ calendarioService.findById(Integer.parseInt(retribucion.getTipoPeriodicidad())).getSDescripcion()
					+ "<br/> </td> " + "		   <td> "
					+ tipoRetribucionService.findById(Integer.parseInt(retribucion.getTipoPeriodicidad())).getSDescripcion()
					+ " </td> </tr> " + "           <tr> <td> PERIODO </td> <td> AÑO </td> </tr> "
					+ "           <tr> <td> " + retribucion.getMesRetribucion() + " </td> <td> "
					+ retribucion.getAnioRetribucion() + " </td> </tr> " + "       "
					+ "           <tr> <td> MONEDA </td> <td> BUENCONTRIBUYENTE</td> </tr> " + "           <tr> <td> "
					+ (monedaService.findById(Integer.parseInt(retribucion.getMonedaRetribucion()))).getSDescripcion()
					+ " </td> <td> " + retribucion.getBuenContribuyente() + " </td> </tr> " + "       " + "</table>"
					+ "   </div><br/>\n" + "</div>");

			// TITULO
			buf.append(
					"<center><h2> DECLARACIÓN JURADA DE LA DETERMINACIÓN Y PAGO DE LA RETRIBUCIÓN AL ESTADO </h2></center><br/>");

			// TIPO DECLARACION
			buf.append("<table border=\"1|0\" width=\"100%\"><tr> "
					+ "<td width=\"25%\"> Declaración Original: </td> <td width=\"5%\">"
					+ (retribucion.getCbocodTipoDeclaracion().equals("1") ? "X" : "") + " </td> "
					+ "<td width=\"25%\">Declaración Sustitutoria:</td> <td width=\"5%\"> "
					+ (retribucion.getCbocodTipoDeclaracion().equals("2") ? "X" : "") + " </td> "
					+ "<td width=\"25%\">Declaración Rectificatoria:</td> <td width=\"5%\">"
					+ (retribucion.getCbocodTipoDeclaracion().equals("3") ? "X" : "") + "  </td> </tr></table><br/>");

			buf.append("<h4>Identificación de la Entidad Prestadora</h4>");
			buf.append("<table border=\"1\" width=\"100%\">\n");
			buf.append("    <tr> \n");
			buf.append("         <th width=\"20%\"> No. de RUC </th> \n");
			buf.append("         <th width=\"70%\"> Razón Social </th> \n");
			buf.append("    </tr>\n");
			buf.append("    <tr> \n");
			buf.append("        <td> " + u.getConcesionario().getSruc() + " </td> \n");
			buf.append("       <td>" + u.getConcesionario().getSnombre() + "</td> \n");
			buf.append("    </tr>\n");
			buf.append("</table>");
			// Resumen

			buf.append("<h4>DETERMINACIÓN Y LIQUIDACIÓN DE LA RETRIBUCIÓN AL ESTADO</h4>");
			buf.append("<table border=\"1\" width=\"100%\">\n");
			buf.append("            <tr> \n");
			buf.append("                <th width=\"60%\"> CONCEPTOS </th> \n");
			buf.append("                <th width=\"15%\" > CASILLAS </th> \n");
			buf.append("                <th width=\"25%\"> IMPORTE S/ </th> \n");
			buf.append("            </tr>\n");
			buf.append("            <tr> \n");
			buf.append("                <td>Base Cálculo</td> \n");
			buf.append("                <td class=\"centro\" > 1 </td> \n");
			buf.append("                <td class=\"importe\"> "
					+ util.formatMiles(retribucion.getBaseCalculo().intValue()) + " </td> \n");
			buf.append("            </tr>\n");
			buf.append("            <tr> \n");
			buf.append("                <td>Porcentaje a Aplicar(%)</td> \n");
			buf.append("                <td class=\"centro\" > 2 </td> \n");
			buf.append("                <td class=\"importe\"> " + retribucion.getPorcentaje() + " </td> \n");
			buf.append("            </tr>\n");
			buf.append("            <tr> \n");
			buf.append("                <td>Deducciones</td> \n");
			buf.append("                <td class=\"centro\" >  </td> \n");
			buf.append("                <td class=\"importe\"> " + util.formatMiles(retribucion.getDeduccion())
					+ " </td> \n");
			buf.append("            </tr>\n");

			buf.append("            <tr> \n");
			buf.append(
					"                <td class=\"negrita\">Retribución al Estado Pendiente a Pagar  : (1)*(2)</td> \n");
			buf.append("                <td class=\"centro\" > 3</td> \n");
			buf.append("                <td class=\"importe\"> "
					+ util.formatMiles(retribucion.getRetribucionpendienteapagar()) + " </td> \n");
			buf.append("            </tr>\n");
			buf.append("            <tr> \n");
			buf.append("                <td class=\"negrita\">( -) Menos:</td> \n");
			buf.append("                <td></td> \n");
			buf.append("                <td class=\"importe\"></td> \n");
			buf.append("            </tr>\n");

			buf.append("            <tr> \n");
			buf.append("                <td>Pagos Previos </td> \n");
			buf.append("                <td class=\"centro\" > 4 </td> \n");
			buf.append("                <td class=\"importe\"> " + util.formatMiles(retribucion.getPagosprevios())
					+ " </td> \n");
			buf.append("            </tr>\n");
			buf.append("            <tr> \n");
			buf.append("                <td>Créditos</td> \n");
			buf.append("                <td class=\"centro\" > 5 </td> \n");
			buf.append("                <td class=\"importe\"> " + util.formatMiles(retribucion.getCreditos())
					+ " </td> \n");
			buf.append("            </tr>\n");
			buf.append("            <tr> \n");

			buf.append("                <td>Liberación de Pago</td> \n");
			buf.append("                <td class=\"centro\" > 6 </td> \n");
			buf.append("                <td class=\"importe\">  " + util.formatMiles(retribucion.getLiberacionpago())
					+ "</td> \n");
			buf.append("            </tr>\n");

			buf.append("            <tr> \n");
			buf.append("                <td>Otros Descuentos</td> \n");
			buf.append("                <td class=\"centro\" > 7 </td> \n");
			buf.append("                <td class=\"importe\">" + util.formatMiles(retribucion.getOtrosDescuentos())
					+ "  </td> \n");
			buf.append("            </tr>\n");

			buf.append("            <tr> \n");
			buf.append(
					"                <td class=\"negrita\">Retribución al Estado a pagar (3)-(4)-(5)-(6)-(7) </td> \n");
			buf.append("                <td></td> \n");
			buf.append("                <td class=\"importe\"> " + util.formatMiles(retribucion.getRetribucionapagar())
					+ "</td> \n");
			buf.append("            </tr>\n");
			buf.append("            <tr> \n");
			buf.append("                <td class=\"negrita\">( +) Más: </td> \n");
			buf.append("                <td></td> \n");
			buf.append("                <td class=\"importe\"></td> \n");
			buf.append("            </tr>\n");
			buf.append("            <tr> \n");
			buf.append("                <td>Interés </td> \n");
			buf.append("                <td class=\"centro\"> 8 </td> \n");
			buf.append("                <td class=\"importe\">" + util.formatMiles(retribucion.getIntereses())
					+ " </td> \n");
			buf.append("            </tr>\n");

			buf.append("            <tr> \n");
			buf.append("                <td class=\"negrita\">Retribución al Estado Resultante (8)+(9)</td> \n");
			buf.append("                <td class=\"centro\"> 9 </td> \n");
			buf.append("                <td class=\"importe\">"
					+ util.formatMiles(retribucion.getRetribucionresultante()) + "</td> \n");
			buf.append("            </tr>\n");
			buf.append("        </table>");
			// Sunat

			// Pagos

			buf.append("<h4>Información relacionada al pago y otros</h4>");
			buf.append("<table border=\"1\" width=\"100%\">\n");
			buf.append("    <tr> \n");
			buf.append("        <th width=\"25%\"> ENTIDAD BANCARIA </th> \n");
			buf.append("        <th width=\"10%\"> FECHA </th>\n");
			buf.append("        <th width=\"40%\"> Nº DE OPERACIÓN / Nº DE AGENCIA </th> \n");
			buf.append("        <th width=\"15%\"> IMPORTE </th> \n");
			buf.append("    </tr>\n");

			pagoService
					.getListaPago(
							Integer.parseInt(retribucion.getTipoPeriodicidad()),
							Integer.parseInt(retribucion.getTipoRetribucion()), 
							retribucion.getMesRetribucion(),
							retribucion.getAnioRetribucion(), 
							u.getConcesionario().getId(), 
							"0")
					.stream().forEach((f) -> {			
						buf.append("    <tr> \n");
						buf.append("        <td class=\"dato\"> "+f.getBanco().getSDescripcion()+" </td> \n");
						buf.append("        <td class=\"dato\"> "+ util.fomratDate(f.getDfecPagoVoucher())+" </td>\n");
						buf.append("        <td class=\"dato\"> "+f.getNroOperacion()+"</td>\n");
						buf.append("        <td class=\"importe\"> "+util.formatMiles(f.getNImporte())+" </td>\n");
						buf.append("    </tr>\n");	
					});

			buf.append("    <tr>\n");
			buf.append("        <th colspan=\"3\"> TOTAL S/ </th>\n");
			buf.append("        <td class=\"importe negrita\">  </td>\n");
			buf.append("    </tr>\n");
			buf.append("</table>");

			buf.append("<h6 style=\"page-break-before: always\"> </h6>");
			buf.append("<center><h2> DETALLE DE LA BASE DE CÁLCULO - CASILLA 1 </h2></center><br/>");

			buf.append(" <table border=\"1\" width=\"100%\">\n");
			buf.append("    <tr> \n");
			buf.append("        <th width=\"80%\"> DETALLE DEL TOTAL DE LOS CONCEPTOS</th> \n");
			buf.append("        <th width=\"20%\"> TIPO </th>\n");
			
			buf.append("        <th width=\"20%\"  > IMPORTE </th>\n");
			buf.append("    </tr> \n");
			
			baseCalculoService.findByNcodigoAp(retribucion.getCodaportehdd())
			.stream().forEach((f) -> {			
				buf.append("    <tr> \n");
				buf.append("        <td width=\"80%\"> "+f.getSDescripcion()+"</td> \n");
				buf.append("        <td width=\"20%\"> "+f.getConcepto().getSDescripcion()+" </td>\n");
				
				buf.append("        <td width=\"20%\" style=\"text-align:right !important\"> "+f.getNimporte()+" </td>\n");
				buf.append("    </tr> \n");
			});
			
			
			
		
			buf.append("    <tr> \n");
			buf.append("        <td width=\"80%\"> TOTAL</td> \n");
			buf.append("        <td width=\"20%\">  </td>\n");
			
			buf.append("        <td width=\"20%\">  </td>\n");
			buf.append("    </tr> \n");
			buf.append(" </table><br/>");

			buf.append("<center><h2> DETALLE DE LIBERACIÓN DE PAGO-CASILLA 6</h2></center><br/>");
			buf.append(" <table border=\"1\" width=\"100%\">\n");
			buf.append("    <tr> \n");
			buf.append("        <th width=\"80%\"> DESCRIPCIÓN</th> \n");
			
			buf.append("        <th width=\"20%\" > IMPORTE </th>\n");
			buf.append("    </tr> \n");
			
			aporteLiberacionService.findByNcodigoAp(retribucion.getCodaportehdd())
			.stream().forEach((f) -> {			
				buf.append("    <tr> \n");
				buf.append("        <td width=\"80%\"> "+f.getLiberacion().getSDescripcion()+"</td> \n");		
				
				buf.append("        <td width=\"20%\"  style=\"text-align:right !important\"> "+f.getNImporteLib()+" </td>\n");
				buf.append("    </tr> \n");
			});
						
			
			
			buf.append("    <tr> \n");
			buf.append("        <td width=\"80%\"> TOTAL</td> \n");

			
			buf.append("        <td width=\"20%\">  </td>\n");
			buf.append("    </tr> \n");

			buf.append(" </table><br/>");
			buf.append("<center><h2> DETALLE DE OTROS DESCUENTOS-CASILLA 7</h2></center><br/>");
			buf.append(" <table border=\"1\" width=\"100%\">\n");
			buf.append("    <tr> \n");
			buf.append("        <th width=\"80%\"> DESCRIPCIÓN</th> \n");
			
			buf.append("        <th width=\"20%\" > IMPORTE </th>\n");
			buf.append("    </tr> \n");
			
			aporteDescuentoService.findByNcodigoAp(retribucion.getCodaportehdd())
			.stream().forEach((f) -> {			
				buf.append("    <tr> \n");
				buf.append("        <td width=\"80%\"> "+f.getDescuento().getSDescripcion()+"</td> \n");		
				
				buf.append("        <td width=\"20%\" style=\"text-align:right !important\"> "+f.getNImporteDes()+" </td>\n");
				buf.append("    </tr> \n");
			});
			buf.append("    <tr> \n");
			buf.append("        <td width=\"80%\"> TOTAL</td> \n");

			
			buf.append("        <td width=\"20%\">  </td>\n");
			buf.append("    </tr> \n");

			buf.append(" </table><br/>");
			buf.append("<center><h2> DETALLE DE DEDUCCIONES</h2></center><br/>");
			buf.append(" <table border=\"1\" width=\"100%\">\n");
			buf.append("    <tr> \n");
			buf.append("        <th width=\"80%\"> DESCRIPCIÓN</th> \n");
		
			buf.append("        <th width=\"20%\"> IMPORTE </th>\n");
			buf.append("    </tr> \n");
			aporteDeduccionService.findByNcodigoAp(retribucion.getCodaportehdd())
			.stream().forEach((f) -> {			
				buf.append("    <tr> \n");
				buf.append("        <td width=\"80%\"> "+f.getDeduccion().getSDescripcion()+"</td> \n");		
		
				buf.append("        <td width=\"20%\"  style=\"text-align:right !important\"> "+f.getNImporteDed()+" </td>\n");
				buf.append("    </tr> \n");
			});
			buf.append("    <tr> \n");
			buf.append("        <td width=\"80%\"> TOTAL</td> \n");

			buf.append("        <td width=\"20%\">  </td>\n");
		
			buf.append("    </tr> \n");
			buf.append(" </table><br/>");
			buf.append("</body></html>");

			ITextRenderer renderer = new ITextRenderer();
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = builder.parse(new ByteArrayInputStream(buf.toString().getBytes("UTF-8")));
			renderer.setDocument(doc, null);
			renderer.layout();
			renderer.createPDF(out);
			out.close();
			out.flush();
		} catch (Exception e) {
			System.out.print(e.getMessage());
			e.printStackTrace(); /* Throw exceptions to log files */

		} finally {
			try {
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	
	}

}
