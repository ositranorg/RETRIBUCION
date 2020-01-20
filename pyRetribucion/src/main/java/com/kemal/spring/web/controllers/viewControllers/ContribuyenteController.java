package com.kemal.spring.web.controllers.viewControllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Controller
@RequestMapping("")
public class ContribuyenteController {
	@Value("${total-registro-por-pagina}")
	private int totalRegistroPorPagina;
	
	public static HttpSession session() {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		return attr.getRequest().getSession(); // true == allow create
	}
	@GetMapping(value = { "/mantenimiento/contribuyente" })
	public String index(ModelMap model) {		
		model.addAttribute("totalRegistroPorPagina", totalRegistroPorPagina);
		return "user/mantenimiento/contribuyente";
	}
}
