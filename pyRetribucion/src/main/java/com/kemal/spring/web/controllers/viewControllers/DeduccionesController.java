package com.kemal.spring.web.controllers.viewControllers;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.kemal.spring.domain.Deduccion;
import com.kemal.spring.domain.Moneda;
import com.kemal.spring.domain.TipoPeriodicidad;
import com.kemal.spring.domain.User;
import com.kemal.spring.service.TipoPeriodicidadService;
import com.kemal.spring.service.DeduccionService;
import com.kemal.spring.service.MonedaService;
import com.kemal.spring.service.TipoRetribucionService;
import com.kemal.spring.service.userDetails.UserDetailsImpl;
import com.kemal.spring.web.dto.Util;
import com.kemal.spring.web.form.DeduccionForm;

@Controller
@Scope("session")
public class DeduccionesController {

	@Autowired
	DeduccionService service;
	@Autowired
	Util util;
	@Autowired
	TipoPeriodicidadService calendarioService;
	@Autowired
	TipoRetribucionService tipoRetribucionService;
	@Autowired
	MonedaService monedaService;
	public static HttpSession session() {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		return attr.getRequest().getSession(); // true == allow create
	}

	@GetMapping(value = "/deduccion")
	public String buscar(Model model,

			@RequestParam(required = false, name = "page") Integer page) {

		SecurityContextImpl sci = (SecurityContextImpl) (session().getAttribute("SPRING_SECURITY_CONTEXT"));
		Object us = (Object) sci.getAuthentication().getPrincipal();
		User c = ((UserDetailsImpl) us).getUser();

		PageRequest pageable = PageRequest.of((null == page ? 1 : page.intValue()) - 1, 5);
		Page<Deduccion> articlePage = service.findByNCodigoCnsAndSEstado(
				c.getContribuyente().getId(), c.getPerfil().getId().intValue()==1? "0":"-1", pageable);
		int totalPages = articlePage.getTotalPages();
		if (totalPages > 0) {
			List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
			model.addAttribute("pageNumbers", pageNumbers);
		}
		DeduccionForm x = new DeduccionForm();
		model.addAttribute("deduccionForm", x);
		model.addAttribute("activeArticleList", true);
		model.addAttribute("lstdeduccion", articlePage.getContent());
		List<TipoPeriodicidad> lstCal = calendarioService.findAll();
		model.addAttribute("lsttipoPeriodicidad", lstCal);
		model.addAttribute("anios", util.getAnios());
		model.addAttribute("lsttipoRetribucion", tipoRetribucionService.findAll());
		model.addAttribute("lstMonedaRetribucion", monedaService.findAll());
		return "/user/deducciones";

	}

	@PostMapping(value = "/eliminardeduccion/{idEliminar}")
	public String eliminarCondicion(@PathVariable String idEliminar,
			@ModelAttribute("deduccionForm") DeduccionForm deduccionForm) {

		SecurityContextImpl sci = (SecurityContextImpl) (session().getAttribute("SPRING_SECURITY_CONTEXT"));
		Object us = (Object) sci.getAuthentication().getPrincipal();
		User c = ((UserDetailsImpl) us).getUser();

		Optional<Deduccion> op = service.findById(Integer.parseInt(idEliminar));
		Deduccion condicion = op.get();
		condicion.setSEstado("0");

		service.save(condicion, null);
		return "/user/deducciones";

	}


	@PostMapping(value = "/nuevodeduccion")
	public String nuevodeduccion(Model model, @ModelAttribute("deduccionForm") DeduccionForm deduccionForm) {

		model.addAttribute("deduccionForm", new DeduccionForm());

		return "/user/deducciones";

	}

	@PostMapping(value = "/guardardeduccion")
	public ModelAndView guardardeduccion(@ModelAttribute("deduccionForm") DeduccionForm deduccionForm) {
		SecurityContextImpl sci = (SecurityContextImpl) (session().getAttribute("SPRING_SECURITY_CONTEXT"));
		Object us = (Object) sci.getAuthentication().getPrincipal();
		User c = ((UserDetailsImpl) us).getUser();
		ModelAndView model = new ModelAndView();

		Deduccion deduccionx = new Deduccion();
		if (null != deduccionForm.getFid() && deduccionForm.getFid().trim().length() > 0)
			deduccionx.setId(Integer.parseInt(deduccionForm.getFid()));
		deduccionx.setSDescripcion(deduccionForm.getFsDescripcion());
		deduccionx.setDfecRegistro(new Date());
		deduccionx.setNImporte(util.strToBigDecimal(deduccionForm.getFnImporte()));
		deduccionx.setNSaldo(util.strToBigDecimal(deduccionForm.getFnImporte()));
		Moneda moneda=new Moneda();
		moneda.setId(deduccionForm.getMonedaRetribucion());
		
		deduccionx.setMoneda(moneda);
		deduccionx.setNCodigoCns(c.getContribuyente().getId());
		service.save(deduccionx, util.lstArchivos("FDEDUCCION", deduccionForm.getFsDocumento()));
		model.addObject("deduccionForm", new DeduccionForm());
		model.setViewName("redirect:/deduccion");
		return model;
	}
}
