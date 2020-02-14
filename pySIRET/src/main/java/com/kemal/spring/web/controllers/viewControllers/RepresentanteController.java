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

import com.kemal.spring.domain.Concesionario;
import com.kemal.spring.domain.User;
import com.kemal.spring.service.ConcesionarioService;

@Controller
@Scope("session")
public class RepresentanteController {
	@Value("${total-registro-por-pagina}")
	private int totalRegistroPorPagina;
	
	@Autowired
	private ConcesionarioService contribuyenteService;
	
	public static HttpSession session() {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		return attr.getRequest().getSession(); // true == allow create
	}
	@GetMapping(value = "/mantenimiento/representante")
	public String registrar(Model model) {
		User u = (User)session().getAttribute("oUsuario");
	
		//System.out.println("id empresa: " + id);
		Concesionario contribuyente = contribuyenteService.obtenerContribuyente(u.getConcesionario().getId());
		
		model.addAttribute("idEmpresa",u.getConcesionario().getId());
		model.addAttribute("rucEmpresa",u.getConcesionario().getSruc());
		//System.out.println("nombre de la empresa: "+ c.getContribuyente().getSnombre());
		model.addAttribute("nombreEmpresa",u.getConcesionario().getSnombre());
		/*
		model.addAttribute("telefonoEmpresa",c.getContribuyente().getSTelefono());
		model.addAttribute("correoEmpresa",c.getContribuyente().getSCorreo());
		*/
		model.addAttribute("telefonoEmpresa",contribuyente.getSTelefono());
		model.addAttribute("correoEmpresa",contribuyente.getSCorreo());
		
		model.addAttribute("idUser", u.getId());
		model.addAttribute("totalRegistroPorPagina", totalRegistroPorPagina);
		//model.addAttribute("empresa", c.getContribuyente());
		return "/user/mantenimiento/representante/representante-registro";
	}
}
