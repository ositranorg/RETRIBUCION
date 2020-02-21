package com.kemal.spring.web.controllers.viewControllers;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.kemal.spring.domain.Concesionario;
import com.kemal.spring.domain.User;
import com.kemal.spring.domain.Vencimiento;
import com.kemal.spring.service.VencimientoService;
import com.kemal.spring.web.controllers.restApiControllers.dto.VencimientoDTO;
import com.kemal.spring.web.dto.Util;

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
	@ResponseBody
	@PostMapping(value = "/calendario/pago/filtrar", consumes = "application/json",produces =  { "application/json" })
	public ResponseEntity<?> pagofilrar(@RequestBody  List<VencimientoDTO> vencimientodto) {
		HashMap<String, Object> a=getCalendarios(vencimientodto,4);
		return new ResponseEntity<>(a, HttpStatus.OK);
	}
	@RequestMapping(value = { "/calendario/pago" }, method = RequestMethod.GET)
	public String pago(Model model) {
		User u = (User)session().getAttribute("oUsuario");
		model.addAttribute("cn", u.getPerfil().getId()==2?u.getConcesionario().getId():-1);
		model.addAttribute("anioActual",util.anioActual());
		return "user/listaVencimientoPago";
	}
	@ResponseBody
	@PostMapping(value = "/calendario/presentacion/filtrar", consumes = "application/json",produces =  { "application/json" })
	public ResponseEntity<?> presentacionfilrar(@RequestBody  List<VencimientoDTO> vencimientodto) {
		HashMap<String, Object> a=getCalendarios(vencimientodto,5);
		return new ResponseEntity<>(a, HttpStatus.OK);
	}
	public 	HashMap<String, Object> getCalendarios(List<VencimientoDTO> v,Integer calendario) {
		User u = (User)session().getAttribute("oUsuario");
		HashMap<String, Object> a=new HashMap<String,Object>();
		List<Vencimiento> list=new ArrayList<Vencimiento>();
		List<VencimientoDTO> listdto=new ArrayList<VencimientoDTO>();
		Concesionario concesionario=util.getConcesionario(u,v.get(0).getConcesionario());
		try {
			list=vencimientoService.findByConcesionarioAndConceptoAndSAnioPeriodoAndSEstado(
					concesionario, 
					calendario,
					v.get(0).getSAnioPeriodo().equals("")?""+util.anioActual():v.get(0).getSAnioPeriodo());
			list.stream().map(z->(new VencimientoDTO(z.getSMesPeriodo(),z.getSAnioPeriodo(),util.fomratDate(z.getDFechaVenc())))).forEachOrdered(listdto::add);
			a.put("calendarioDet", listdto);
		} catch (Exception e) {
			a.put("calendarioDet", listdto);
			e.printStackTrace();
		}
		return a;
	}
	@RequestMapping(value = { "/calendario/presentacion" }, method = RequestMethod.GET)
	public String presentacion(Model model) {
		User u = (User)session().getAttribute("oUsuario");
		model.addAttribute("cn", u.getPerfil().getId()==2?u.getConcesionario().getId():-1);
		model.addAttribute("anioActual",util.anioActual());
		return "user/listaVencimientoPresentacion";
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
