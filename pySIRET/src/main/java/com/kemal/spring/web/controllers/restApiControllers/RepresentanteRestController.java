package com.kemal.spring.web.controllers.restApiControllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kemal.spring.domain.User;
import com.kemal.spring.service.RepresentanteService;
import com.kemal.spring.web.dto.DataTableVWRepresentante;

@RestController
@RequestMapping("api/representante")
@Scope("session")
public class RepresentanteRestController {
	@Autowired
	RepresentanteService representanteService;
	@Autowired
	DataTableVWRepresentante dataTableVWRepresentante;

	public static HttpSession session() {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		return attr.getRequest().getSession(); // true == allow create
	}

	@ResponseBody
	@GetMapping(value = "listar-representante")
	public String listarRepresentante() {
		User u = (User) session().getAttribute("oUsuario");
		dataTableVWRepresentante.setData(representanteService.listarRepresentantes(u.getConcesionario().getId()));
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(dataTableVWRepresentante);

	}
}
