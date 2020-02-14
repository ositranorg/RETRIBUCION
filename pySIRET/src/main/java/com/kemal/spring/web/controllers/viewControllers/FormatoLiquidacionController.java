package com.kemal.spring.web.controllers.viewControllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Controller
@Scope("session")
@RequestMapping("liquidacion")
public class FormatoLiquidacionController {
	@Value("${total-registro-por-pagina}")
	private int totalRegistroPorPagina;
	
	public static HttpSession session() {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		return attr.getRequest().getSession(); // true == allow create
	}
	@GetMapping(value = "/formatoliquidacion")
	public String resetDescuento(Model model) {
		model.addAttribute("totalRegistroPorPagina", totalRegistroPorPagina);
		return "/user/liquidacion/formato-liquidacion";

	}

}
