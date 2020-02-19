package com.kemal.spring.web.controllers.viewControllers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.kemal.spring.domain.Concesionario;
import com.kemal.spring.domain.TipoPeriodicidad;
import com.kemal.spring.domain.User;
import com.kemal.spring.domain.Vencimiento;
import com.kemal.spring.service.VencimientoService;
import com.kemal.spring.web.dto.CalendarioDto;
import com.kemal.spring.web.dto.Util;
import com.kemal.spring.web.form.CalendarioForm;

@Controller(value = "calendario")
@Scope("session")
public class CalendarioController {

	private String saveDirectory = "T:/Upload/";

	@RequestMapping(value = { "/calendario/uploadFile" }, method = RequestMethod.POST)
	public String handleFileUpload(HttpServletRequest request, @RequestParam CommonsMultipartFile[] file)
			throws Exception {

		System.out.println("description: " + request.getParameter("description"));

		if (file != null && file.length > 0) {
			for (CommonsMultipartFile aFile : file) {

				System.out.println("Saving file: " + aFile.getOriginalFilename());

				if (!aFile.getOriginalFilename().equals("")) {
					aFile.transferTo(new File(saveDirectory + aFile.getOriginalFilename()));
				}
			}
		}

		// returns to the view "Result"
		return "/pages/contactus";
	}

	@Autowired
	Util util;

	public static HttpSession session() {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		return attr.getRequest().getSession(); // true == allow create
	}
	@Autowired
	VencimientoService vencimientoService;
	@RequestMapping(value = { "/calendario/pago" }, method = RequestMethod.GET)
	public String pago(Model model,		
			@RequestParam(value = "anioSel", required = false) String anioSel) {
		User u = (User)session().getAttribute("oUsuario");
		List<Vencimiento> list=vencimientoService.findByConcesionarioAndSEstado(u.getConcesionario(), 
				anioSel==null||anioSel.equals("")?""+util.anioActual():anioSel);
		int anio = util.anioActual();
		List<String> anios = util.getAnios();
		model.addAttribute("calendarioDet", list);// fechasvenc
		model.addAttribute("anio", anio);
		model.addAttribute("anios", anios);
		return "user/listaVencimientoPago";
	}

	@RequestMapping(value = { "/calendario/presentacion/" }, method = RequestMethod.GET)
	public ModelAndView presentacion(ModelMap model, @PathVariable String calendarioSel, @PathVariable String anioSel) {
		User u = (User)session().getAttribute("oUsuario");

	int anio = util.anioActual();

    List<CalendarioDto> calendarioDto = new ArrayList<CalendarioDto>();

    List<TipoPeriodicidad> listCalendario = new ArrayList<TipoPeriodicidad>();


    	List<String> anios =  util.getAnios();
//		CalendarioForm form = new CalendarioForm();
//		ModelAndView view = new ModelAndView("/user/listaVencimientoPresentacion", "command", form);
//		view.addObject("lstCalendario", listCalendario);// mensual,trimestral...
//		view.addObject("calendarioDet", calendarioDto);// fechasvenc
//		view.addObject("anio", anio);
//		view.addObject("anios", anios);
//		return view;
		return null;
	}
	
	@RequestMapping(value = "/getAnios", method = RequestMethod.GET)
	public @ResponseBody
	List<String> getTags(@RequestParam String tagName) {
		List<String> anios = util.getAnios();
		return simulateSearchResult(tagName,anios);

	}
	private List<String> simulateSearchResult(String tagName,List<String> data) {

		List<String> result = new ArrayList<String>();
		// iterate a list and filter by tagName
		for (String tag : data) {
			if (tag.contains(tagName)) {
				result.add(tag);
			}
		}

		return result;
	}
}
