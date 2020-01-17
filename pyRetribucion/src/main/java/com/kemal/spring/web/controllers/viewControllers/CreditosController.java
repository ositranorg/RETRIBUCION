package com.kemal.spring.web.controllers.viewControllers;

import java.util.List;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.kemal.spring.bd.dto.VWNuevoCredito;
import com.kemal.spring.bd.dto.VWNuevoCreditoService;
import com.kemal.spring.domain.TipoPeriodicidad;
import com.kemal.spring.domain.User;
import com.kemal.spring.domain.dto.CreditoDeLaDJService;
import com.kemal.spring.service.CreditoService;
import com.kemal.spring.service.TipoPeriodicidadService;
import com.kemal.spring.service.TipoRetribucionService;
import com.kemal.spring.service.userDetails.UserDetailsImpl;
import com.kemal.spring.web.dto.Util;
import com.kemal.spring.web.form.CreditosForm;

@Controller
@Scope("session")
public class CreditosController {
	@Autowired
	Util util;
	@Autowired
	TipoPeriodicidadService calendarioService;
	@Autowired
	TipoRetribucionService tipoRetribucionService;
	
	@Autowired
	CreditoService service;
	@Autowired
	CreditoDeLaDJService creditoDeLaDJService;
	
	@Autowired
	VWNuevoCreditoService creditoService;
	
	
	public static HttpSession session() {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		return attr.getRequest().getSession(); // true == allow create
	}
	@GetMapping(value = "/creditos")
	public String buscar(Model model,
			@RequestParam(required = false, name = "page") Integer page) {
		SecurityContextImpl sci = (SecurityContextImpl) (session().getAttribute("SPRING_SECURITY_CONTEXT"));
		Object us = (Object) sci.getAuthentication().getPrincipal();
		User c = ((UserDetailsImpl) us).getUser();
		CreditosForm f=new CreditosForm();
		f.setFmesRetribucion("");
		f.setFmesRetribucionDestino("");
		f.setFanioRetribucion("");
		f.setFanioRetribucionDestino("");
		f.setFtipoRetribucion("");
		f.setFtipoRetribucionDestino("");
		f.setFtipoPeriodicidad("");
		f.setFtipoPeriodicidadDestino("");
		model.addAttribute("creditosForm", f);
		model.addAttribute("creditosForm.fmesRetribucion","");
		model.addAttribute("creditosForm.fmesRetribucionDestino","");
		model.addAttribute("creditosForm.fanioRetribucion","");
		model.addAttribute("creditosForm.fanioRetribucionDestino","");
		model.addAttribute("creditosForm.ftipoRetribucion","");
		model.addAttribute("creditosForm.ftipoRetribucionDestino","");
		model.addAttribute("creditosForm.ftipoPeriodicidad","");
		model.addAttribute("creditosForm.ftipoPeriodicidadDestino","");
		
		List<TipoPeriodicidad> lstCal = calendarioService.findAll();
		
		
		PageRequest pageable = PageRequest.of((null == page ? 1 : page.intValue()) - 1, 5);
		Page<VWNuevoCredito> articlePage = creditoService.findByNCodigocn(c.getContribuyente().getId(), pageable);				
		int totalPages = articlePage.getTotalPages();
		if (totalPages > 0) {
			List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
			model.addAttribute("pageNumbers", pageNumbers);
		}
		
	
		model.addAttribute("lstCreditos",articlePage.getContent());
		
		model.addAttribute("lsttipoPeriodicidad", lstCal);
		model.addAttribute("anios", util.getAnios());
		model.addAttribute("lsttipoRetribucion", tipoRetribucionService.findAll());
		
	
		return "/user/creditos";
	}
	
}
