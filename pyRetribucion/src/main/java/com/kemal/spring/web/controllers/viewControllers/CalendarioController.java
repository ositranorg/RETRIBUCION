package com.kemal.spring.web.controllers.viewControllers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.kemal.spring.domain.Concepto;
import com.kemal.spring.domain.Contrato;
import com.kemal.spring.domain.TipoPeriodicidad;
import com.kemal.spring.domain.User;
import com.kemal.spring.service.CalendarioDetService;
import com.kemal.spring.service.ContratoService;
import com.kemal.spring.service.userDetails.UserDetailsImpl;
import com.kemal.spring.web.dto.CalendarioDto;
import com.kemal.spring.web.dto.Util;
import com.kemal.spring.web.form.CalendarioForm;

@Controller(value = "calendario")
@Scope("session")
public class CalendarioController {

	private String saveDirectory = "T:/Upload/";

	@RequestMapping(value = { "/calendario/uploadFile" }, method = RequestMethod.POST)
	public String handleFileUpload(HttpServletRequest request, @RequestParam CommonsMultipartFile[] file)
			throws Exception {

		System.out.println("description: " + request.getParameter("description"));

		if (file != null && file.length > 0) {
			for (CommonsMultipartFile aFile : file) {

				System.out.println("Saving file: " + aFile.getOriginalFilename());

				if (!aFile.getOriginalFilename().equals("")) {
					aFile.transferTo(new File(saveDirectory + aFile.getOriginalFilename()));
				}
			}
		}

		// returns to the view "Result"
		return "/pages/contactus";
	}

	@Autowired
	CalendarioDetService calendarioDetService;
	@Autowired
	ContratoService contratoService;
	@Autowired
	Util util;

	public static HttpSession session() {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		return attr.getRequest().getSession(); // true == allow create
	}

	@RequestMapping(value = { "/calendario/pago/{calendarioSel}/{anioSel}" }, method = RequestMethod.GET)
	public ModelAndView pago(ModelMap model, @PathVariable String calendarioSel, @PathVariable String anioSel) {
		SecurityContextImpl sci = (SecurityContextImpl) (session().getAttribute("SPRING_SECURITY_CONTEXT"));
		Object us = (Object) sci.getAuthentication().getPrincipal();
		User c = ((UserDetailsImpl) us).getUser();

		int anio = util.anioActual();
		List<Contrato> contratos = contratoService.findByContribuyente(c.getContribuyente());

		List<CalendarioDto> calendarioDto = new ArrayList<CalendarioDto>();

		List<TipoPeriodicidad> listCalendario = new ArrayList<TipoPeriodicidad>();

		contratos.stream().forEach(p -> listCalendario.add(p.getCalendario()));
		Concepto concepto=new Concepto();
		concepto.setId(6);
		if (calendarioSel.equals("ver")) {// no escoge ningun calendario o es primera vez que pinta la pantalla

			int tipoVencimiento = contratos.get(0).getTipoVencimiento().getId();
			if (tipoVencimiento == 1) {// segun contrato				
				calendarioDto = calendarioDetService.getCalendariosxContratoAndConceptoOrderById(contratos.get(0),concepto);
			} else { // o segun sunat
			/*	calendarioDto = calendarioDetSunatService
						.getCalendarioVencimientoSunatOrderBy( null, anioSel,c.getContribuyente().getRuc().substring(10),concepto);*/
			}
		} else {// calendario=mensual , trimestral
			TipoPeriodicidad calend = new TipoPeriodicidad();
			calend.setId(Integer.parseInt(calendarioSel));
			Contrato contrato = contratoService.findByCalendarioAndContribuyente(calend, c.getContribuyente());
			if (contrato.getTipoVencimiento().getId().intValue() == 1) {// segun contrato
				calendarioDto = calendarioDetService.getCalendariosxContratoAndConceptoOrderById(contrato,concepto);
			} else { // o segun sunat
				/*calendarioDto = calendarioDetSunatService
						.getCalendarioVencimientoSunatOrderBy(null, anioSel,c.getContribuyente().getRuc().substring(10),concepto);*/
			}
		}
		List<String> anios = util.getAnios();
		CalendarioForm form = new CalendarioForm();
		ModelAndView view = new ModelAndView("/user/listaVencimientoPago", "command", form);
		view.addObject("lstCalendario", listCalendario);// mensual,trimestral...
		view.addObject("calendarioDet", calendarioDto);// fechasvenc
		view.addObject("anio", anio);
		view.addObject("anios", anios);
		return view;
	}

	@RequestMapping(value = { "/calendario/presentacion/{calendarioSel}/{anioSel}" }, method = RequestMethod.GET)
	public ModelAndView presentacion(ModelMap model, @PathVariable String calendarioSel, @PathVariable String anioSel) {
		SecurityContextImpl sci = (SecurityContextImpl) (session().getAttribute("SPRING_SECURITY_CONTEXT"));
		Object us = (Object) sci.getAuthentication().getPrincipal();
		User c = ((UserDetailsImpl) us).getUser();

		int anio = util.anioActual();
		List<Contrato> contratos = contratoService.findByContribuyente(c.getContribuyente());

		List<CalendarioDto> calendarioDto = new ArrayList<CalendarioDto>();

		List<TipoPeriodicidad> listCalendario = new ArrayList<TipoPeriodicidad>();

		contratos.stream().forEach(p -> listCalendario.add(p.getCalendario()));
		Concepto concepto=new Concepto();
		concepto.setId(7);
		if (calendarioSel.equals("ver")) {// no escoge ningun calendario o es primera vez que pinta la pantalla

			int tipoVencimiento = contratos.get(0).getTipoVencimiento().getId();
			// segun contrato				
		/*calendarioDto = tipoVencimiento == 1?
				calendarioDetService.getCalendariosxContratoAndConceptoOrderById(contratos.get(0),concepto)
				:calendarioDetSunatService.getCalendarioVencimientoSunatOrderBy(null, anioSel,c.getContribuyente().getRuc().substring(10),concepto);*/
			
		} else {// calendario=mensual , trimestral
			TipoPeriodicidad calend = new TipoPeriodicidad();
			calend.setId(Integer.parseInt(calendarioSel));
			Contrato contrato = contratoService.findByCalendarioAndContribuyente(calend, c.getContribuyente());
			if (contrato.getTipoVencimiento().getId().intValue() == 1) {// segun contrato
				calendarioDto = calendarioDetService.getCalendariosxContratoAndConceptoOrderById(contrato,concepto);
			} else { // o segun sunat
				/*calendarioDto = calendarioDetSunatService
						.getCalendarioVencimientoSunatOrderBy(null, anioSel,c.getContribuyente().getRuc().substring(10),concepto);*/
			}
		}
		List<String> anios =  util.getAnios();
		CalendarioForm form = new CalendarioForm();
		ModelAndView view = new ModelAndView("/user/listaVencimientoPresentacion", "command", form);
		view.addObject("lstCalendario", listCalendario);// mensual,trimestral...
		view.addObject("calendarioDet", calendarioDto);// fechasvenc
		view.addObject("anio", anio);
		view.addObject("anios", anios);
		return view;
	}

}
