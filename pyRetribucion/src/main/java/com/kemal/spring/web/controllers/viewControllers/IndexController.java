package com.kemal.spring.web.controllers.viewControllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.WebRequest;

import com.kemal.spring.web.dto.UserDto;

/**
 * Created by Keno&Kemo on 30.09.2017..
 */

@Controller
@RequestMapping("")
public class IndexController {
	
	
	
	public static HttpSession session() {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		return attr.getRequest().getSession(); // true == allow create
	}

	@GetMapping(value = { "/", "/index" })
	public String index(ModelMap model) {			
		return "user/home";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "logout", required = false) String logout,
                            Model model) {
        String errorMessge = null;
        if(error != null) {
            errorMessge = "Usuario o clave incorrecto!!";
        }
        if(logout != null) {
            errorMessge = "Saliste del sistema!!";
        }
        model.addAttribute("errorMessge", errorMessge);
        return "website/login";
    }
  
    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){   
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "website/login?logout=true";
    }

	@GetMapping(value = "/register")
	public String showRegistrationForm(WebRequest request, Model model) {
		UserDto userDto = new UserDto();
		model.addAttribute("userDto", userDto);
		return "website/register";
	}
	@GetMapping(value="/recuperar-clave")
	public String recuperarClave() {
		return "website/recuperar-clave";
	}
	@GetMapping(value="/cambiar-clave")
	public String cambiarClave() {
		return "website/cambiar-clave";
	}
}
