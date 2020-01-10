package com.kemal.spring.web.controllers.viewControllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Scope;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Controller
@Scope("session")
@RequestMapping("liquidacion")
public class FormatoLiquidacionController {
	@Value("${total-registro-por-pagina}")
	private int totalRegistroPorPagina;
	/*
	@Autowired
	private MessageSource mensajes;

	public String getSaludo() {
		return mensajes.getMessage("liquidacion.mensaje.adjunteArchivos", null, LocaleContextHolder.getLocale());
	}
	*/
	//@Value("${liquidacion.mensaje.adjunteArchivos}")
	//private String liquidacionMensajeAdjunteArchivos = 
	//		mensajes.getMessage("liquidacion.mensaje.adjunteArchivos",  null, LocaleContextHolder.getLocale());
	/*
	@Value("${liquidacion.mensaje.adjunteArchivoDictamen}")
	private String liquidacionMensajeAdjunteArchivoDictamen;
	
	@Value("${liquidacion.mensaje.adjunteArchivoFormato}")
	private String liquidacionMensajeAdjunteArchivoFormato;
	
	@Value("${liquidacion.mensaje.subieronCorrectamente}")
	private String liquidacionMensajeSubieronCorrectamente;
	
	@Value("${liquidacion.mensaje.limiteArchivo}")
	private String liquidacionMensajeLimiteArchivo;
	
	@Value("${liquidacion.mensaje.archivoPermitido}")
	private String liquidacionMensajeArchivoPermitido;
	
	@Value("${liquidacion.mensaje.agregarArchivosDictamen}")
	private String liquidacionMensajeAgregarArchivosDictamen;
	
	@Value("${liquidacion.mensaje.agregarArchivosFormato}")
	private String liquidacionMensajeAgregarArchivosFormato;
	*/
	public static HttpSession session() {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		return attr.getRequest().getSession(); // true == allow create
	}
	@GetMapping(value = "/formatoliquidacion")
	public String resetDescuento(Model model) {
		model.addAttribute("totalRegistroPorPagina", totalRegistroPorPagina);
		return "/user/liquidacion/formato-liquidacion";

	}

}
