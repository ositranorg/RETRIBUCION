package com.kemal.spring.web.controllers.viewControllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.kemal.spring.service.ContribuyenteService;

@Controller
@Scope("session")
public class RepresentanteController {
	@Value("${total-registro-por-pagina}")
	private int totalRegistroPorPagina;
	
	@Autowired
	private ContribuyenteService contribuyenteService;
	
	public static HttpSession session() {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		return attr.getRequest().getSession(); // true == allow create
	}
	@GetMapping(value = "/mantenimiento/representante")
	public String registrar(Model model) {
		/*SecurityContextImpl sci = (SecurityContextImpl) (session().getAttribute("SPRING_SECURITY_CONTEXT"));
		Object us = (Object) sci.getAuthentication().getPrincipal();
		User c = ((_UserDetailsImpl) us).getUser();	
	
		Contribuyente contribuyente = contribuyenteService.obtenerContribuyente(c.getContribuyente().getId());
		
		model.addAttribute("idEmpresa",c.getContribuyente().getId());
		model.addAttribute("rucEmpresa",c.getContribuyente().getSruc());
		model.addAttribute("nombreEmpresa",c.getContribuyente().getSnombre());
		model.addAttribute("telefonoEmpresa",contribuyente.getSTelefono());
		model.addAttribute("correoEmpresa",contribuyente.getSCorreo());
		
		model.addAttribute("idUser", c.getId());
		model.addAttribute("totalRegistroPorPagina", totalRegistroPorPagina);
		return "/user/mantenimiento/representante/representante-registro";*/
		return "";
	}
}
