package com.kemal.spring.web.controllers.viewControllers;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

import com.kemal.spring.domain.ConcesionarioTipoVencimiento;
import com.kemal.spring.domain.User;
import com.kemal.spring.service.ConcesionarioTipoVencimientoService;
import com.kemal.spring.service.UsuarioService;
import com.kemal.spring.web.dto.UserDto;
import com.kemal.spring.web.dto.Util;
/**
 * Created by Keno&Kemo on 30.09.2017..
 */

@Controller
@RequestMapping("")
public class IndexController {
	@Autowired
	UsuarioService usuarioService;
	@Autowired
	ConcesionarioTipoVencimientoService concesionarioTipoVencimientoService;
	@Autowired
	Util util;
	@GetMapping(value = { "/", "/index" })
	public String index( HttpServletRequest request,ModelMap model) {
		User us=null;
        try {
        	  us=(User)request.getSession(false).getAttribute("oUsuario");
        }catch (Exception e) {
			//e.printStackTrace();
		}
		if(null==us) {
			return "website/login";
		}

		return "user/home";

	}
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String loginPage(HttpServletRequest request,
			@RequestParam(value = "username", required = false) String username,
			@RequestParam(value = "password", required = false) String password, Model model) {
		String usdesc=util.parseDecrypt(username);
				String pwdesc=util.parseDecrypt(password);
				System.out.println(password);
		User oUsuario = usuarioService.findByUsername(usdesc);
		boolean respuesta = true;
		if (oUsuario == null) {
			respuesta = false;
		}else {
			if (oUsuario.getSTipoUsuario().equals("E")) {
				oUsuario=null;
				oUsuario = usuarioService.findByUsernameAndPassword(usdesc, pwdesc);
				if (oUsuario == null) {
					respuesta = false;
				} else {
					respuesta = true;
				}
			} else {
				String ldap="";
				try {
					ldap = util.ldapAutenticarExchange(usdesc.toUpperCase(),pwdesc);
				} catch (NamingException e) {
					e.printStackTrace();
				}
				if (ldap.equals("0")) {
					respuesta = true;
				} else {
					respuesta = false;
				}
			}
		}
		
		if (respuesta) {
			 HttpSession sesion = request.getSession(true);
             sesion.setAttribute("oUsuario", oUsuario);
             ConcesionarioTipoVencimiento concesionarioTipoVencimiento =concesionarioTipoVencimientoService.findById(oUsuario.getConcesionario().getId(), 7);
             if(null!=concesionarioTipoVencimiento) {
            	 sesion.setAttribute("puedeVerBC", "1");
             }else {
            	 sesion.setAttribute("puedeVerBC", "0");
             }
			return "redirect:/home";
		} else {
			request.getSession().setAttribute("oUsuario", null);
			model.addAttribute("errorMessge", "Datos incorrectos.");
		}

		return "redirect:/";
	}
	@GetMapping(value = "/home" )
	public String home( HttpServletRequest request,ModelMap model) {
		User us=null;
        try {
        	  us=(User)request.getSession(false).getAttribute("oUsuario");
        }catch (Exception e) {
			//e.printStackTrace();
		}
		if(null==us) {
			return "redirect:/logout";
		}

		return "user/home";

	}
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
		/*
		 * Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		 * if (auth != null){ new SecurityContextLogoutHandler().logout(request,
		 * response, auth); }
		 */
		request.getSession().setAttribute("oUsuario", null);
		request.getSession().invalidate();
		return "redirect:/";
	}

	@GetMapping(value = "/register")
	public String showRegistrationForm(WebRequest request, Model model) {
		UserDto userDto = new UserDto();
		model.addAttribute("userDto", userDto);
		return "website/register";
	}

	@GetMapping(value = "/abrir-recuperar-clave")
	public String recuperarClave() {
		return "website/recuperar-clave";
	}

	@PostMapping(value = "/cambiar-clave")
	public String cambiarClave(Model model) {
		/*
		 * SecurityContextImpl sci = (SecurityContextImpl)
		 * (session().getAttribute("SPRING_SECURITY_CONTEXT")); Object us = (Object)
		 * sci.getAuthentication().getPrincipal();
		 */
		User u = new User();
		model.addAttribute("idUser", u.getId());
		return "website/cambiar-clave";
	}



}
