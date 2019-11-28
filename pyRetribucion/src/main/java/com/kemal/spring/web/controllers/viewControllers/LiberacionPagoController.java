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

import com.kemal.spring.domain.LiberacionPago;
import com.kemal.spring.domain.Moneda;
import com.kemal.spring.domain.TipoPeriodicidad;
import com.kemal.spring.domain.User;
import com.kemal.spring.service.LiberacionPagoService;
import com.kemal.spring.service.MonedaService;
import com.kemal.spring.service.TipoPeriodicidadService;
import com.kemal.spring.service.TipoRetribucionService;
import com.kemal.spring.service.userDetails.UserDetailsImpl;
import com.kemal.spring.web.dto.Util;
import com.kemal.spring.web.form.LiberacionPagoForm;

@Controller
@Scope("session")
public class LiberacionPagoController {

	@Autowired
	LiberacionPagoService service;
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

	@GetMapping(value = "/liberacionpago")
	public String buscar(Model model,
			@RequestParam(required = false, name = "page") Integer page) {

		SecurityContextImpl sci = (SecurityContextImpl) (session().getAttribute("SPRING_SECURITY_CONTEXT"));
		Object us = (Object) sci.getAuthentication().getPrincipal();
		User c = ((UserDetailsImpl) us).getUser();

		PageRequest pageable = PageRequest.of((null == page ? 1 : page.intValue()) - 1, 5);
		Page<LiberacionPago> articlePage = service.findByNCodigoCnsAndSEstadox(
				c.getContribuyente().getId(),c.getPerfil().getId().intValue()==1? "0":"-1", pageable);
		int totalPages = articlePage.getTotalPages();
		if (totalPages > 0) {
			List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
			model.addAttribute("pageNumbers", pageNumbers);
		}

		LiberacionPagoForm x = new LiberacionPagoForm();
		model.addAttribute("liberacionPagoForm", x);

		model.addAttribute("activeArticleList", true);
		model.addAttribute("lstLiberacionPago", articlePage.getContent());
		List<TipoPeriodicidad> lstCal = calendarioService.findAll();
		model.addAttribute("lsttipoPeriodicidad", lstCal);
		model.addAttribute("anios", util.getAnios());
		model.addAttribute("lsttipoRetribucion", tipoRetribucionService.findAll());
		model.addAttribute("lstMonedaRetribucion", monedaService.findAll());
		return "/user/liberacionpago";

	}

	@PostMapping(value = "/eliminarLiberacion/{idEliminar}")
	public String eliminarCondicion(@PathVariable String idEliminar,
			@ModelAttribute("liberacionPagoForm") LiberacionPagoForm liberacionPagoForm) {

		SecurityContextImpl sci = (SecurityContextImpl) (session().getAttribute("SPRING_SECURITY_CONTEXT"));
		Object us = (Object) sci.getAuthentication().getPrincipal();
		User c = ((UserDetailsImpl) us).getUser();

		Optional<LiberacionPago> op = service.findById(Integer.parseInt(idEliminar));
		LiberacionPago condicion = op.get();
		condicion.setSEstado("0");

		service.save(condicion, null);
		return "/user/liberacionpago";

	}

	@PostMapping(value = "/resetLiberacion")
	public String resetLiberacion(Model model) {
		LiberacionPagoForm x = new LiberacionPagoForm();
		model.addAttribute("liberacionPagoForm", x);
		return "redirect:/liberacionpago";

	}

	@PostMapping(value = "/nuevaLiberacion")
	public String nuevaLiberacion(Model model,
			@ModelAttribute("liberacionPagoForm") LiberacionPagoForm liberacionPagoForm) {

		model.addAttribute("liberacionPagoForm", new LiberacionPagoForm());

		return "/user/liberacionpago";

	}

	@PostMapping(value = "/guardarLiberacion")
	public ModelAndView guardarLiberacion(@ModelAttribute("liberacionPagoForm") LiberacionPagoForm liberacionPagoForm) {

		SecurityContextImpl sci = (SecurityContextImpl) (session().getAttribute("SPRING_SECURITY_CONTEXT"));
		Object us = (Object) sci.getAuthentication().getPrincipal();
		User c = ((UserDetailsImpl) us).getUser();
		ModelAndView model = new ModelAndView();

		LiberacionPago liberacionPagox = new LiberacionPago();
		if (null != liberacionPagoForm.getFid() && liberacionPagoForm.getFid().trim().length() > 0)
			liberacionPagox.setId(Integer.parseInt(liberacionPagoForm.getFid()));
		liberacionPagox.setSDescripcion(liberacionPagoForm.getFsDescripcion());
		liberacionPagox.setDfecRegistro(new Date());
		liberacionPagox.setNImporte(util.strToBigDecimal(liberacionPagoForm.getFnImporte()));
		liberacionPagox.setNSaldo(util.strToBigDecimal(liberacionPagoForm.getFnImporte()));
		liberacionPagox
				.setDfecReconocimiento(util.strtoDate(liberacionPagoForm.getDfecReconocimiento().substring(0, 10)));
		liberacionPagox.setNCodigoCns(c.getContribuyente().getId());
		Moneda moneda=new Moneda();
		moneda.setId(liberacionPagoForm.getMonedaRetribucion());
		liberacionPagox.setMoneda(moneda);
		service.save(liberacionPagox, util.lstArchivos("FLIBERACIONPAGO", (liberacionPagoForm.getFsDocumento())));
		model.addObject("liberacionPagoForm", new LiberacionPagoForm());
		model.setViewName("redirect:/liberacionpago");
		return model;

	}

}
