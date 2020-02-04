package com.kemal.spring.web.controllers.viewControllers;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.kemal.spring.domain.Banco;
import com.kemal.spring.domain.PagoSinAporte;
import com.kemal.spring.domain.TipoPeriodicidad;
import com.kemal.spring.domain.TipoRetribucion;
import com.kemal.spring.service.BancoService;
import com.kemal.spring.service.PagoService;
import com.kemal.spring.service.TipoPeriodicidadService;
import com.kemal.spring.service.TipoRetribucionService;
import com.kemal.spring.web.dto.Util;
import com.kemal.spring.web.form.PagoForm;

@Controller
@Scope("session")
public class PagoController {

	@Autowired
	PagoService service;
	@Autowired
	Util util;
	@Autowired
	TipoPeriodicidadService tipoPeriodicidadService;
	@Autowired
	TipoRetribucionService tipoRetribucionService;
	@Autowired
	BancoService bancoService;

	public static HttpSession session() {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		return attr.getRequest().getSession(); // true == allow create
	}

	@GetMapping(value = "/pagos")
	public String buscar(
			@RequestParam(required = false, name = "fbtipoPeriodicidad") Integer fbtipoPeriodicidad,
			@RequestParam(required = false, name = "fbtipoRetribucion") Integer fbtipoRetribucion,
			@RequestParam(required = false, name = "fbmesRetribucion") String fbmesRetribucion,
			@RequestParam(required = false, name = "fbanioRetribucion") String fbanioRetribucion,
			@RequestParam(required = false, name = "fbdfecpagovouDesde") String fdfecpagovouDesde,
			@RequestParam(required = false, name = "fbdfecpagovouHasta") String fdfecpagovouHasta,
			@RequestParam(required = false, name = "page") Integer page,Model model) {

		/*SecurityContextImpl sci = (SecurityContextImpl) (session().getAttribute("SPRING_SECURITY_CONTEXT"));
		Object us = (Object) sci.getAuthentication().getPrincipal();
		User c = ((_UserDetailsImpl) us).getUser();*/

		PageRequest pageable = PageRequest.of((null == page ? 1 : page.intValue()) - 1, 5);
		Page<PagoSinAporte> articlePage = 
				service.findByNCodigoCnsAndSEstado(
				fbtipoPeriodicidad, 
				fbtipoRetribucion,
				fbmesRetribucion, 
				fbanioRetribucion, 
				null==fdfecpagovouDesde?null:util.strtoDate(fdfecpagovouDesde),
				null==fdfecpagovouHasta?null:util.strtoDate(fdfecpagovouHasta),
				1/*c.getContribuyente().getId()*/, 
				1/*c.getPerfil().getId().intValue()*/==1?"0":"-1", 
				pageable);
		int totalPages = articlePage.getTotalPages();
		if (totalPages > 0) {
			List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
			model.addAttribute("pageNumbers", pageNumbers);
		}
		
		
		model.addAttribute("lstBancos",bancoService.findAll());		
		model.addAttribute("activeArticleList", true);
		model.addAttribute("basec", articlePage.getContent());
		List<TipoPeriodicidad> lstCal = tipoPeriodicidadService.findAll();
		model.addAttribute("lsttipoPeriodicidad", lstCal);
		model.addAttribute("anios", util.getAnios());
		model.addAttribute("lsttipoRetribucion", tipoRetribucionService.findAll());
		PagoForm x=new PagoForm();
		x.setFbtipoPeriodicidad(fbtipoPeriodicidad);
		x.setFbtipoRetribucion(fbtipoRetribucion);
		x.setFbmesRetribucion(fbmesRetribucion);
		x.setFbanioRetribucion(fbanioRetribucion);
		x.setFbdfecpagovouDesde(fdfecpagovouDesde);
		x.setFbdfecpagovouHasta(fdfecpagovouHasta);
		model.addAttribute("pagoForm",x);	
		model.addAttribute("pagoForm.fbtipoPeriodicidad", fbtipoPeriodicidad);
		model.addAttribute("pagoForm.fbmesRetribucion", fbmesRetribucion);
		model.addAttribute("pagoForm.fbtipoRetribucion", fbtipoRetribucion);
		model.addAttribute("pagoForm.fbanioRetribucion", fbanioRetribucion);
		model.addAttribute("pagoForm.fbdfecpagovouDesde", fdfecpagovouDesde);
		model.addAttribute("pagoForm.fbdfecpagovouHasta", fdfecpagovouHasta);
		
		return "/user/pago";

	}
	
	@PostMapping(value = "/eliminarPago/{idEliminar}")
	public String eliminarCondicion(
			@PathVariable String idEliminar,@ModelAttribute("pagoForm") PagoForm PagoForm) {

	/*	SecurityContextImpl sci = (SecurityContextImpl) (session().getAttribute("SPRING_SECURITY_CONTEXT"));
		Object us = (Object) sci.getAuthentication().getPrincipal();
		((_UserDetailsImpl) us).getUser();*/
		
		Optional<PagoSinAporte> op =service.findById(Integer.parseInt(idEliminar));
		PagoSinAporte condicion=op.get();
		condicion.setSEstado("0");
		
		service.save(condicion);
		return "/user/pago";
		

	}	
	@PostMapping(value = "/resetPago")
	public String resetPago(Model model) {
		PagoForm x=new PagoForm();
		model.addAttribute("pagoForm", x);
		return "redirect:/pago";

	}
	
	@PostMapping(value = "/nuevaPago")
	public String nuevaPago(Model model,
			@ModelAttribute("pagoForm") PagoForm PagoForm) {
		
		model.addAttribute("pagoForm", new PagoForm());

		return "/user/pago";

	}
	
	@PostMapping(value = "/guardarPago")
	public String  guardarPago(
			@ModelAttribute("pagoForm") PagoForm pagoForm,Model model) {

		/*SecurityContextImpl sci = (SecurityContextImpl) (session().getAttribute("SPRING_SECURITY_CONTEXT"));
		Object us = (Object) sci.getAuthentication().getPrincipal();
		User c = ((_UserDetailsImpl) us).getUser();*/
		
		PagoSinAporte pagox = new PagoSinAporte();
		TipoPeriodicidad p=new TipoPeriodicidad();
		p.setId(new Integer(pagoForm.getFtipoPeriodicidad()));
		pagox.setTipoPeriodicidad(p);
		TipoRetribucion r=new TipoRetribucion();
		r.setId(new Integer(pagoForm.getFtipoRetribucion()));
		pagox.setTipoRetribucion(r);
		pagox.setSMesRetribucion(pagoForm.getFmesRetribucion());
		pagox.setSAnioRetribucion(pagoForm.getFanioRetribucion());
		Banco s=new Banco();
		s.setId(Integer.parseInt(pagoForm.getFbanco()));
		pagox.setBanco(s);
		
		if(null!=pagoForm.getFid()&&pagoForm.getFid().trim().length()>0)
			pagox.setId(Integer.parseInt(pagoForm.getFid()));
		
		pagox.setDfecRegistro(new Date());
		pagox.setSDocumento(util.quitarultimacomma(pagoForm.getFsDocumento()));
		pagox.setNroOperacion(pagoForm.getFnroOperacion());
		pagox.setNImporte(util.strToBigDecimal(pagoForm.getFnImporte()));
		pagox.setDfecPagoVoucher(util.strtoDate(pagoForm.getFdfecpagovou().substring(0, 10)));
		pagox.setNCodigoCns(1/*c.getContribuyente().getId()*/);
		pagox.setObservacion(pagoForm.getFobservacion());
		service.save(pagox);
		
		PagoForm x=new PagoForm();
		x.setFbtipoPeriodicidad(null);
		x.setFbtipoRetribucion(null);
		x.setFbmesRetribucion(null);
		x.setFbanioRetribucion(null);
		x.setFbdfecpagovouDesde(null);
		x.setFbdfecpagovouHasta(null);
		model.addAttribute("pagoForm",x);	
		
		
		model.addAttribute("pagoForm.fbtipoPeriodicidad", "");
		model.addAttribute("pagoForm.fbmesRetribucion", "");
		model.addAttribute("pagoForm.fbtipoRetribucion", "");
		model.addAttribute("pagoForm.fbanioRetribucion", "");
		model.addAttribute("pagoForm.fbdfecpagovouDesde", "");
		model.addAttribute("pagoForm.fbdfecpagovouHasta", "");
		return "redirect:/pagos";

	}

}
