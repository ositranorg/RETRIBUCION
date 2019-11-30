package com.kemal.spring.web.controllers.viewControllers;

import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.kemal.spring.web.form.DescuentoForm;

@Controller
@Scope("session")
@RequestMapping("liquidacion")
public class FormatoLiquidacionController {
	public static HttpSession session() {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		return attr.getRequest().getSession(); // true == allow create
	}
	@GetMapping(value = "/formatoliquidacion")
	public String resetDescuento(Model model) {
		return "/liquidacion/formato-liquidacion";

	}

}