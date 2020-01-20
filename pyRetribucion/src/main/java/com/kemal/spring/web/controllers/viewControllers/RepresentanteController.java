package com.kemal.spring.web.controllers.viewControllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.kemal.spring.domain.User;
import com.kemal.spring.service.userDetails.UserDetailsImpl;

@Controller
@Scope("session")
public class RepresentanteController {
	@Value("${total-registro-por-pagina}")
	private int totalRegistroPorPagina;
	
	public static HttpSession session() {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		return attr.getRequest().getSession(); // true == allow create
	}
	@GetMapping(value = "/mantenimiento/representante")
	public String registrar(Model model) {
		SecurityContextImpl sci = (SecurityContextImpl) (session().getAttribute("SPRING_SECURITY_CONTEXT"));
		Object us = (Object) sci.getAuthentication().getPrincipal();
		User c = ((UserDetailsImpl) us).getUser();	
		model.addAttribute("idEmpresa",c.getContribuyente().getId());
		model.addAttribute("rucEmpresa",c.getContribuyente().getSruc());
		//System.out.println("nombre de la empresa: "+ c.getContribuyente().getSnombre());
		model.addAttribute("nombreEmpresa",c.getContribuyente().getSnombre());
		model.addAttribute("telefonoEmpresa",c.getContribuyente().getSTelefono());
		model.addAttribute("correoEmpresa",c.getContribuyente().getSCorreo());
		model.addAttribute("idUser", c.getId());
		model.addAttribute("totalRegistroPorPagina", totalRegistroPorPagina);
		//model.addAttribute("empresa", c.getContribuyente());
		return "/user/mantenimiento/representante/representante-registro";
	}
}
