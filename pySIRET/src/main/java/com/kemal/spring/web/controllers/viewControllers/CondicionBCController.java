package com.kemal.spring.web.controllers.viewControllers;

import java.util.Date;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.kemal.spring.domain.CondicionBC;
import com.kemal.spring.domain.TipoPeriodicidad;
import com.kemal.spring.domain.User;
import com.kemal.spring.service.CondicionBCService;
import com.kemal.spring.service.TipoPeriodicidadService;
import com.kemal.spring.service.TipoRetribucionService;
import com.kemal.spring.web.dto.Util;
import com.kemal.spring.web.form.CondicionBCForm;

@Controller
@Scope("session")
public class CondicionBCController {

	@Autowired
	CondicionBCService service;
	@Autowired
	Util util;
	@Autowired
	TipoPeriodicidadService calendarioService;
	@Autowired
	TipoRetribucionService tipoRetribucionService;
	

	public static HttpSession session() {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		return attr.getRequest().getSession(); // true == allow create
	}

	@GetMapping(value = "/condicionbc")
	public String mostrarCondicion(Model model, @RequestParam(required = false, name = "page") Integer page) {
		User u = (User)session().getAttribute("oUsuario");
		List<TipoPeriodicidad> lstCal = calendarioService.findAll();
		PageRequest pageable = PageRequest.of((null==page?1:page) - 1, 5);
		Page<CondicionBC> articlePage = service.findByNCodigoCnsAndSEstadoNotOrderBySEstadoAsc(u.getConcesionario().getId(), pageable);
		int totalPages = articlePage.getTotalPages();
		if (totalPages > 0) {
			List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
			model.addAttribute("pageNumbers", pageNumbers);
		}
		model.addAttribute("activeArticleList", true);
		model.addAttribute("lstCondicion", articlePage.getContent());
		model.addAttribute("condicionBCForm", new CondicionBCForm());		
		model.addAttribute("lsttipoPeriodicidad", lstCal);
		model.addAttribute("anios", util.getAnios());
		model.addAttribute("lsttipoRetribucion", tipoRetribucionService.findAll());
		
		return "/user/condicionbc";
	}

	@PostMapping(value = "/nuevaCondicion")
	public String nuevaLiberacion(Model model, @ModelAttribute("condicionBCForm") CondicionBCForm condicionBCForm) {

		model.addAttribute("fechaInicio", "");
		model.addAttribute("fechaExclusion", "");
		model.addAttribute("condicionBC", "");
		return "/user/condicionbc";

	}

	@PostMapping(value = "/guardarCondicion")
	public ModelAndView guardarLiberacion(@ModelAttribute("condicionBCForm") CondicionBCForm condicionBCForm) {

		User u = (User)session().getAttribute("oUsuario");
		ModelAndView model = new ModelAndView();

		CondicionBC condicion = new CondicionBC();
		condicion.setDfecRegistro(new Date());
		condicion.setDfecInicio(util.strtoDate(condicionBCForm.getDfecInicio().substring(0, 10)));
		if(null!=condicionBCForm.getDfecExclusion()&&!condicionBCForm.getDfecExclusion().equals(""))
			condicion.setDfecExclusion((util.strtoDate(condicionBCForm.getDfecExclusion().substring(0, 10))));
		condicion.setSBuenContribuyente(condicionBCForm.getSBuenContribuyente());
		condicion.setNCodigoCns(u.getConcesionario().getId());
		
		
		service.save(condicion,util.lstArchivos("FCONDICIONBC",condicionBCForm.getFsDocumento()));
		model.addObject("condicionBCForm", new CondicionBCForm());
		model.setViewName("redirect:/condicionbc");
		return model;

	}

	@PostMapping(value = "/eliminarCondicion/{idEliminar}")
	public String eliminarCondicion(@PathVariable String idEliminar,
			@ModelAttribute("condicionBCForm") CondicionBCForm condicionBCForm) {

		User u = (User)session().getAttribute("oUsuario");


		CondicionBC condicion =  service.findById(Integer.parseInt(idEliminar));
		condicion.setSEstado("0");
		condicion.setStatus("ELIMINADO");
		service.save(condicion,null);
		return "/user/condicionbc";

	}
	
}
