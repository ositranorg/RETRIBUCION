package com.kemal.spring.web.controllers.viewControllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

import com.kemal.spring.domain.User;
import com.kemal.spring.domain.Vencimiento;
import com.kemal.spring.service.VencimientoService;
import com.kemal.spring.web.controllers.restApiControllers.dto.VencimientoDto;
import com.kemal.spring.web.dto.Util;

@Controller(value = "calendario")
@Scope("session")
public class CalendarioRestController {

	@Autowired
	Util util;

	public static HttpSession session() {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		return attr.getRequest().getSession(); // true == allow create
	}
	@Autowired
	VencimientoService vencimientoService;
	
	
	@RequestMapping(value = { "/calendario/pago" }, method = RequestMethod.GET)
	public String pago(Model model) {
		User u = (User)session().getAttribute("oUsuario");
		model.addAttribute("cn", u.getPerfil().getId()==2?u.getConcesionario().getId():-1);
		model.addAttribute("anioActual",util.anioActual());
		return "user/listaVencimientoPago";
	}
	@RequestMapping(value = { "/calendario/presentacion" }, method = RequestMethod.GET)
	public String presentacion(Model model) {
		User u = (User)session().getAttribute("oUsuario");
		model.addAttribute("cn", u.getPerfil().getId()==2?u.getConcesionario().getId():-1);
		model.addAttribute("anioActual",util.anioActual());
		return "user/listaVencimientoPresentacion";
	}
	
	
	@ResponseBody
	@PostMapping(value = "/calendario/pago/filtrar", consumes = "application/json",produces =  { "application/json" })
	public ResponseEntity<?> pagofilrar(@RequestBody  List<VencimientoDto> vencimientodto) {
		HashMap<String, Object> a=getCalendarios(vencimientodto);
		return new ResponseEntity<>(a, HttpStatus.OK);
	}
	@ResponseBody
	@PostMapping(value = "/calendario/presentacion/filtrar", consumes = "application/json",produces =  { "application/json" })
	public ResponseEntity<?> presentacionfilrar(@RequestBody  List<VencimientoDto> vencimientodto) {
		HashMap<String, Object> a=getCalendarios(vencimientodto);
		return new ResponseEntity<>(a, HttpStatus.OK);
	}
	
	public 	HashMap<String, Object> getCalendarios(List<VencimientoDto> v) {
		User u = (User)session().getAttribute("oUsuario");
		HashMap<String, Object> a=new HashMap<String,Object>();
		List<Vencimiento> list=new ArrayList<Vencimiento>();
		List<VencimientoDto> listdto=new ArrayList<VencimientoDto>();
		Integer idconcesionario=util.getConcesionario(u,v.get(0).getConcesionario());
		try {
			list=vencimientoService.getVencimiento(
					idconcesionario, 
					v.get(0).getItipoPeriodicidaddto(),
					v.get(0).getItipoRetribuciondto(),
					v.get(0).getConcepto(),
					v.get(0).getSAnioPeriodo().equals("")?""+util.anioActual():v.get(0).getSAnioPeriodo());
			list.stream().map(z->(new VencimientoDto(
					z.getTipoPeriodicidad().getOrden() ,
					z.getTipoPeriodicidad().getSDescripcion(),
					z.getTipoRetribucion().getSDescripcion(),
					z.getSMesPeriodo(),z.getSAnioPeriodo(),
					util.fomratDate(z.getDFechaVenc()
					))))
			.forEachOrdered(listdto::add);
			a.put("calendarioDet", listdto);
		} catch (Exception e) {
			a.put("calendarioDet", listdto);
			e.printStackTrace();
		}
		return a;
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
