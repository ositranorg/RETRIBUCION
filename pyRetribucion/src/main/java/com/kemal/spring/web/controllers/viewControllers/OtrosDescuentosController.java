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

import com.kemal.spring.domain.Moneda;
import com.kemal.spring.domain.Descuento;
import com.kemal.spring.domain.TipoPeriodicidad;
import com.kemal.spring.domain.User;
import com.kemal.spring.service.TipoPeriodicidadService;
import com.kemal.spring.service.MonedaService;
import com.kemal.spring.service.OtroDescuentoService;
import com.kemal.spring.service.TipoRetribucionService;
import com.kemal.spring.service.userDetails.UserDetailsImpl;
import com.kemal.spring.web.dto.Util;
import com.kemal.spring.web.form.DescuentoForm;

@Controller
@Scope("session")
public class OtrosDescuentosController {

	@Autowired
	OtroDescuentoService service;
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

	@GetMapping(value = "/descuento")
	public String buscar(Model model,

			@RequestParam(required = false, name = "page") Integer page) {

		SecurityContextImpl sci = (SecurityContextImpl) (session().getAttribute("SPRING_SECURITY_CONTEXT"));
		Object us = (Object) sci.getAuthentication().getPrincipal();
		User c = ((UserDetailsImpl) us).getUser();

		PageRequest pageable = PageRequest.of((null == page ? 1 : page.intValue()) - 1, 5);
		Page<Descuento> articlePage = service.findByNCodigoCnsAndSEstadox(
				c.getContribuyente().getId(),c.getPerfil().getId().intValue()==1? "0":"-1", pageable);
		int totalPages = articlePage.getTotalPages();
		if (totalPages > 0) {
			List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
			model.addAttribute("pageNumbers", pageNumbers);
		}

		DescuentoForm x = new DescuentoForm();


		model.addAttribute("descuentoForm", x);
		
		model.addAttribute("activeArticleList", true);
		model.addAttribute("lstDescuento", articlePage.getContent());
		List<TipoPeriodicidad> lstCal = calendarioService.findAll();
		model.addAttribute("lsttipoPeriodicidad", lstCal);
		model.addAttribute("anios", util.getAnios());
		model.addAttribute("lsttipoRetribucion", tipoRetribucionService.findAll());
		model.addAttribute("lstMonedaRetribucion", monedaService.findAll());
		return "/user/otrosdescuentos";

	}

	@PostMapping(value = "/eliminarDescuento/{idEliminar}")
	public String eliminarCondicion(@PathVariable String idEliminar,
			@ModelAttribute("descuentoForm") DescuentoForm descuentoForm) {

		SecurityContextImpl sci = (SecurityContextImpl) (session().getAttribute("SPRING_SECURITY_CONTEXT"));
		Object us = (Object) sci.getAuthentication().getPrincipal();
		User c = ((UserDetailsImpl) us).getUser();

		Optional<Descuento> op = service.findById(Integer.parseInt(idEliminar));
		Descuento condicion = op.get();
		condicion.setSEstado("0");

		service.save(condicion, null);
		return "/user/otrosdescuentos";

	}

	@PostMapping(value = "/resetDescuento")
	public String resetDescuento(Model model) {
		DescuentoForm x = new DescuentoForm();
		model.addAttribute("descuentoForm", x);
		return "redirect:/descuento";

	}

	@PostMapping(value = "/nuevoDescuento")
	public String nuevoDescuento(Model model, @ModelAttribute("descuentoForm") DescuentoForm descuentoForm) {

		model.addAttribute("descuentoForm", new DescuentoForm());

		return "/user/otrosdescuentos";

	}

	@PostMapping(value = "/guardarDescuento")
	public ModelAndView guardarDescuento(@ModelAttribute("descuentoForm") DescuentoForm descuentoForm) {
		SecurityContextImpl sci = (SecurityContextImpl) (session().getAttribute("SPRING_SECURITY_CONTEXT"));
		Object us = (Object) sci.getAuthentication().getPrincipal();
		User c = ((UserDetailsImpl) us).getUser();
		ModelAndView model = new ModelAndView();

		Descuento descuentox = new Descuento();
		if (null != descuentoForm.getFid() && descuentoForm.getFid().trim().length() > 0)
			descuentox.setId(Integer.parseInt(descuentoForm.getFid()));
		descuentox.setSDescripcion(descuentoForm.getFsDescripcion());
		descuentox.setDfecRegistro(new Date());
		descuentox.setNImporte(util.strToBigDecimal(descuentoForm.getFnImporte()));
		descuentox.setNSaldo(util.strToBigDecimal(descuentoForm.getFnImporte()));
		Moneda moneda=new Moneda();
		moneda.setId(descuentoForm.getMonedaRetribucion());
		descuentox.setMoneda(moneda);
		descuentox.setNCodigoCns(c.getContribuyente().getId());

		service.save(descuentox, util.lstArchivos("FDESCUENTO", descuentoForm.getFsDocumento()));
		model.addObject("descuentoForm", new DescuentoForm());
		model.setViewName("redirect:/descuento");
		return model;
	}
}
