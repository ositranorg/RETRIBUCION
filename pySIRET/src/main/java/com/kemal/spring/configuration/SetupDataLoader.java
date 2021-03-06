package com.kemal.spring.configuration;

import java.math.BigDecimal;
import java.net.SocketTimeoutException;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kemal.spring.domain.AportePorcentaje;
import com.kemal.spring.domain.AporteEstadoDJ;
import com.kemal.spring.domain.Banco;
import com.kemal.spring.domain.Concepto;
import com.kemal.spring.domain.Concesionario;
import com.kemal.spring.domain.ConcesionarioTipoVencimiento;
import com.kemal.spring.domain.Estado;
import com.kemal.spring.domain.Feriado;
import com.kemal.spring.domain.Modulo;
import com.kemal.spring.domain.Moneda;
import com.kemal.spring.domain.Perfil;
import com.kemal.spring.domain.Role;
import com.kemal.spring.domain.TipoPeriodicidad;
import com.kemal.spring.domain.TipoRetribucion;
import com.kemal.spring.domain.TipoVencimiento;
import com.kemal.spring.domain.User;
import com.kemal.spring.domain.Vencimiento;
import com.kemal.spring.service.AportePorcentajeService;
import com.kemal.spring.service.AporteTipoService;
import com.kemal.spring.service.BancoService;
import com.kemal.spring.service.ConceptoService;
import com.kemal.spring.service.ConcesionarioService;
import com.kemal.spring.service.ConcesionarioTipoVencimientoService;
import com.kemal.spring.service.EstadoService;
import com.kemal.spring.service.FeriadoService;
import com.kemal.spring.service.ModuloService;
import com.kemal.spring.service.MonedaService;
import com.kemal.spring.service.PerfilService;
import com.kemal.spring.service.RoleService;
import com.kemal.spring.service.TipoPeriodicidadService;
import com.kemal.spring.service.TipoRetribucionService;
import com.kemal.spring.service.TipoVencimientoService;
import com.kemal.spring.service.UsuarioService;
import com.kemal.spring.service.VencimientoService;


@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

	private boolean alreadySetup = false;

	

	private ConceptoService conceptoService;

	private ModuloService moduloService;

	private VencimientoService vencimientoService;

	private TipoPeriodicidadService tipoPeriodicidadService;
	private FeriadoService feriadoService;
	private ConcesionarioService concesionarioService;
	private  ConcesionarioTipoVencimientoService concesionarioTipoVencimientoService;
	private TipoRetribucionService tipoRetribucionService;

	
	private TipoVencimientoService tipoVencimientoService;
	private AporteTipoService aporteTipoService;
	private MonedaService monedaService;
	
	private AportePorcentajeService aportePorcentajeService;
	private BancoService bancoService;
	private PerfilService perfilService;
	private EstadoService estadoService;
	private UsuarioService usuarioService;
	private RoleService roleService;
	public SetupDataLoader( UsuarioService usuarioService,
			RoleService roleService,
			ConceptoService conceptoService, 
			ModuloService moduloService,
			TipoPeriodicidadService tipoPeriodicidadService, 
			VencimientoService vencimientoService,
			FeriadoService feriadoService, 
			ConcesionarioService concesionarioService, 
			ConcesionarioTipoVencimientoService concesionarioTipoVencimientoService,
			TipoVencimientoService tipoVencimientoService,
			
			TipoRetribucionService tipoRetribucionService,		
			AporteTipoService aporteTipoService,
			MonedaService monedaService,
			AportePorcentajeService aportePorcentajeService,
			BancoService bancoService,
			PerfilService perfilService,
			EstadoService estadoService) {
		this.usuarioService = usuarioService;
		this.roleService=roleService;
		this.conceptoService = conceptoService;
		this.moduloService = moduloService;
		this.vencimientoService = vencimientoService;
		this.tipoPeriodicidadService = tipoPeriodicidadService;
		this.feriadoService = feriadoService;
		this.concesionarioService = concesionarioService;
		this.concesionarioTipoVencimientoService=concesionarioTipoVencimientoService;
		this.tipoVencimientoService=tipoVencimientoService;
	
		this.tipoRetribucionService=tipoRetribucionService;
		
		this.aporteTipoService=aporteTipoService;
		this.monedaService=monedaService;
		this.aportePorcentajeService=aportePorcentajeService;
		this.bancoService=bancoService;
		this.perfilService=perfilService;
		this.estadoService=estadoService;
	
	}

	// API

	@Override
	@Transactional
	public void onApplicationEvent(final ContextRefreshedEvent event) {
		if (alreadySetup) {
			return;
		}
		
/*
		createEstado(0,"ELIMINADO");
		createEstado(1,"REGISTRADO");
		createEstado(2,"APLICADO");
		createEstado(3,"ANULADO");
		
		 
		createEstado(0,"ELIMINADO");
		createEstado(1,"REGISTRADO");
		createEstado(2,"APLICADO");
		createEstado(3,"ANULADO");
		
		
		createBanco("BANCO DE COMERCIO");
		createBanco("BANCO INTERAMERICANO DE FINANZAS (BANBIF)");
		createBanco("BANCO PICHINCHA");
		createBanco("BBVA");
		createBanco("CITIBANK PERÚ");
		createBanco("INTERBANK");
		createBanco("MIBANCO");
		createBanco("BANCO GNB PERÚ");
		createBanco("BANCO FALABELLA");
		createBanco("BANCO RIPLEY");
		createBanco("BANCO SANTANDER PERÚ");
		createBanco("BANCO AZTECA");
		createBanco("CRAC CAT PERÚ");
		createBanco("ICBC PERU BANK");
		
		
		createBanco("AGROBANCO");
		createBanco("BANCO DE LA NACIÓN");
		createBanco("COFIDE");
		createBanco("FONDO MIVIVIENDA");
		
		createBanco("BANCO DE CRÉDITO DEL PERÚ");
		createBanco("SCOTIABANK");
		
		createMoneda(1, "SOL","S/");
		createMoneda(2, "DÓLARES AMERICANOS","$");
		
		// region Creating roles
		Role roleAdmin = createRoleIfNotFound("ROLE_ADMIN");
		Role roleUser = createRoleIfNotFound("ROLE_USER");
		
		
		List<Role> adminRoles = Collections.singletonList(roleAdmin);
		List<Role> userRoles = Collections.singletonList(roleUser);
		
		// endregion
	
		createAporteTipo("I","INGRESADO");
		createAporteTipo("P","PRESENTADO");
		createAporteTipo("R","RECHAZADADO");
		createAporteTipo("E","ELIMINADO");
		
			
		createTipoPeriodicidad(1,"MENSUAL", 1);		
		createTipoPeriodicidad(2,"TRIMESTRAL", 2);
		createTipoPeriodicidad(3,"SEMESTRAL", 3);
		createTipoPeriodicidad(4,"ANUAL", 4);
		
		createTiporRetribucion(1,"RETRIBUCION");		
		createTiporRetribucion(2,"PRINCIPAL");	
		createTiporRetribucion(3,"ESPECIAL");	
		createTiporRetribucion(4,"FONCEPRI");	
		
		createTipoVencimiento("último dia útil del mes inmediato siguiente.".toUpperCase());
		createTipoVencimiento("Trimestralmente, el último día útil del mes inmediato siguiente.".toUpperCase()); 
		createTipoVencimiento("Trimestralmente, de acuerdo a lo establecido en el Anexo 17 del Contrato de Concesión.".toUpperCase());
		createTipoVencimiento("Dentro de los 10 primeros días calendarios del mes siguiente.".toUpperCase());
		createTipoVencimiento("Primeros 7 días hábiles del mes siguiente.".toUpperCase());
		createTipoVencimiento("Quinto día útil del mes siguiente.".toUpperCase());
		createTipoVencimiento("De acuerdo al cronograma establecido por la SUNAT para el cumplimiento de sus obligaciones tributarias.".toUpperCase());
		
	
		
		
		
		int aniocalendario=2020;
		createConcesionarioTipoVencimiento(1, 2, ""+aniocalendario);
		createConcesionarioTipoVencimiento(2, 3, ""+aniocalendario);
		createConcesionarioTipoVencimiento(22, 4, ""+aniocalendario);
		createConcesionarioTipoVencimiento(21, 4, ""+aniocalendario);
		createConcesionarioTipoVencimiento(14, 5, ""+aniocalendario);
		createConcesionarioTipoVencimiento(5, 5, ""+aniocalendario);
		createConcesionarioTipoVencimiento(25, 6, ""+aniocalendario);
		createConcesionarioTipoVencimiento(29, 7, ""+aniocalendario);
		createConcesionarioTipoVencimiento(26, 7, ""+aniocalendario);
		createConcesionarioTipoVencimiento(27, 7, ""+aniocalendario);
		createConcesionarioTipoVencimiento(31, 7, ""+aniocalendario);
		createConcesionarioTipoVencimiento(28, 7, ""+aniocalendario);
		createConcesionarioTipoVencimiento(35, 7, ""+aniocalendario);
		
		
		createModulo("PAGO");
		createModulo("APORTE TIPO DJ");
		createModulo("APORTE BASEIMPONIBLE");
		createModulo("CONTRATO");
		createModulo("CRONOGRAMA");
		createModulo("APORTE OTROS CONCEPTOS");
		
		
		createConcepto("ORIGINAL", 2);
		createConcepto("SUSTITUTORIA", 2);
		createConcepto("RECTIFICATORIA", 2);
		createConcepto("CRONOGRAMA DE PAGO", 5);
		createConcepto("CRONOGRAMA DE PRESENTACIÓN", 5);		
		createConcepto("INGRESO",3);		
		createConcepto("INGRESOS NO REGULADOS",3);
		createConcepto("INGRESOS NO REGULADOS BASE",3);
		createConcepto("DEDUCCIONES",6);
		createConcepto("PAGOS PREVIOS",6);
		createConcepto("CREDITOS",6);
		createConcepto("LIBERACION DE PAGO",6);
		createConcepto("OTROS DESCUENTOS",6);
		
		createPerfil("SUPERVISOR");
		createPerfil("CONCESIONARIO");		
		createPerfil("TESORERIA");
		createPerfil("INVITADO");
		
		createUserIfNotFound("admin@gmail.com", "Admin", "Admin", "limaai", "admin", adminRoles, 1,2);

		createUserIfNotFound("adp"  + "@gmail.com", "User" , "User", "adp" , "admin", userRoles,2,2);
				
		createUserIfNotFound("tisur"  + "@gmail.com", "apmter" , "tisur", "tisur" , "admin", userRoles,25,2);
		createUserIfNotFound("cferro"  + "@gmail.com", "apmter" , "cferro", "cferro" , "admin", userRoles,22,2);
		createUserIfNotFound("trasan"  + "@gmail.com", "trasan" , "trasan", "trasan" , "admin", userRoles,21,2);
	

		
		
		createAportePorcentaje(1,1, new BigDecimal("0.46511"));//lap
		createAportePorcentaje(2,1, new BigDecimal("0.15"));//adp
		createAportePorcentaje(22,1, new BigDecimal("0.3725"));//ferrocarriles trasandino
		createAportePorcentaje(22,3, new BigDecimal("0.5"));//ferrocarriles trasandino
		createAportePorcentaje(21,1, new BigDecimal("0.2475"));//ferrovias central andina
		createAportePorcentaje(21,3, new BigDecimal("0.5"));//ferrovias central andina
		createAportePorcentaje(14,1, new BigDecimal("0.1861"));//CONCESIONARIA VIAL DEL PERU S.A.
		createAportePorcentaje(5,1, new BigDecimal("0.055"));//norvial
		
		createAportePorcentaje(25,1,new BigDecimal("0.5"));//tisur-retribucion(canon)
		createAportePorcentaje(25,3, new BigDecimal("0.985"));//tisur-especial
		
		createAportePorcentaje(29,1, new BigDecimal("0.03"));//apm terminals
		createAportePorcentaje(26,1, new BigDecimal("0.03"));//dpworl
		createAportePorcentaje(27,1, new BigDecimal("0.02"));//euroandinos
		createAportePorcentaje(31,1, new BigDecimal("0.03"));//paracas
		createAportePorcentaje(28,1, new BigDecimal("0.02"));//transportadora
		
		createAportePorcentaje(35,1, new BigDecimal("0.03"));//sALAVERRY
		createAportePorcentaje(35,4, new BigDecimal("0.0006"));//sALAVERRY
		
		
		
		
		delAllVencimiento();
		aniocalendario=2019;
		
		createVencimiento("01", ""+aniocalendario,
				obtenerUltimoDiaHabilMesSiguiente(aniocalendario, Calendar.JANUARY).getTime(), 1,1,4,1,1);
		createVencimiento("02",  ""+aniocalendario,
				obtenerUltimoDiaHabilMesSiguiente(aniocalendario, Calendar.FEBRUARY).getTime(), 1,1,4,1,1);
		createVencimiento("03",  ""+aniocalendario,
				obtenerUltimoDiaHabilMesSiguiente(aniocalendario, Calendar.MARCH).getTime(), 1,1,4,1,1);
		createVencimiento("04",  ""+aniocalendario,
				obtenerUltimoDiaHabilMesSiguiente(aniocalendario, Calendar.APRIL).getTime(), 1,1,4,1,1);
		createVencimiento("05",  ""+aniocalendario,
				obtenerUltimoDiaHabilMesSiguiente(aniocalendario, Calendar.MAY).getTime(), 1,1,4,1,1);
		createVencimiento(  "06",  ""+aniocalendario,
				obtenerUltimoDiaHabilMesSiguiente(aniocalendario, Calendar.JUNE).getTime(), 1,1,4,1,1);
		createVencimiento("07",  ""+aniocalendario,
				obtenerUltimoDiaHabilMesSiguiente(aniocalendario, Calendar.JULY).getTime(), 1,1,4,1,1);
		createVencimiento("08",  ""+aniocalendario,
				obtenerUltimoDiaHabilMesSiguiente(aniocalendario, Calendar.AUGUST).getTime(), 1,1,4,1,1);
		createVencimiento(  "09",  ""+aniocalendario,
				obtenerUltimoDiaHabilMesSiguiente(aniocalendario, Calendar.SEPTEMBER).getTime(), 1,1,4,1,1);
		createVencimiento( "10",  ""+aniocalendario,
				obtenerUltimoDiaHabilMesSiguiente(aniocalendario, Calendar.OCTOBER).getTime(), 1,1,4,1,1);
		createVencimiento( "11",  ""+aniocalendario,
				obtenerUltimoDiaHabilMesSiguiente(aniocalendario, Calendar.NOVEMBER).getTime(), 1,1,4,1,1);
		createVencimiento("12",  ""+aniocalendario,
				obtenerUltimoDiaHabilMesSiguiente(aniocalendario, Calendar.DECEMBER).getTime(), 1,1,4,1,1);
		
		
		aniocalendario=2020;
		createVencimiento("01",  ""+aniocalendario, obtenerUltimoDiaHabilMesSiguiente(aniocalendario, Calendar.MARCH).getTime(), 2,1,4,1,1);
		createVencimiento("02",  ""+aniocalendario, obtenerUltimoDiaHabilMesSiguiente(aniocalendario, Calendar.JUNE).getTime(), 2,1,4,1,1);
		createVencimiento("03",  ""+aniocalendario, obtenerUltimoDiaHabilMesSiguiente(aniocalendario, Calendar.SEPTEMBER).getTime(), 2,1,4,1,1);
		createVencimiento("04",  ""+aniocalendario, obtenerUltimoDiaHabilMesSiguiente(aniocalendario, Calendar.DECEMBER).getTime(), 2,1,4,1,1);
		
		
		createVencimiento("01",  ""+aniocalendario, obtenerUltimoDiaHabilMesSiguiente(aniocalendario, Calendar.MARCH).getTime(), 2,1,4,2,1);
		createVencimiento("02",  ""+aniocalendario, obtenerUltimoDiaHabilMesSiguiente(aniocalendario, Calendar.JUNE).getTime(), 2,1,4,2,1);
		createVencimiento("03",  ""+aniocalendario, obtenerUltimoDiaHabilMesSiguiente(aniocalendario, Calendar.SEPTEMBER).getTime(), 2,1,4,2,1);
		createVencimiento("04",  ""+aniocalendario, obtenerUltimoDiaHabilMesSiguiente(aniocalendario, Calendar.DECEMBER).getTime(), 2,1,4,2,1);
		
		

		createVencimiento(  "01",  ""+aniocalendario,
				obtenerDiezCalendarioMesSiguiente(aniocalendario, Calendar.JUNE).getTime(), 3,3,4,22,1);
		createVencimiento(  "02",  ""+(aniocalendario),
				obtenerDiezCalendarioMesSiguiente(aniocalendario, Calendar.DECEMBER).getTime(), 3,3,4,22,1);
		
		createVencimiento(  "01",  ""+aniocalendario,
				obtenerDiezCalendarioMesSiguiente(aniocalendario, Calendar.JUNE).getTime(), 3,3,4,21,1);
		createVencimiento(  "02",  ""+(aniocalendario),
				obtenerDiezCalendarioMesSiguiente(aniocalendario, Calendar.DECEMBER).getTime(), 3,3,4,21,1);

		createVencimiento(  "01",  ""+aniocalendario,
				obtenerCalendarioPrincipal(aniocalendario, Calendar.DECEMBER, 20).getTime(), 4,2,4,22,1);				
		createVencimiento(  "01",  ""+aniocalendario,
				obtenerCalendarioPrincipal(aniocalendario, Calendar.DECEMBER, 20).getTime(), 4,2,4,21,1);				
		
		createVencimiento(  "01",  ""+aniocalendario,
				obtenerDiaHabilMesSiguiente(aniocalendario, Calendar.JANUARY, 7).getTime(), 1,1,4,14,1);
		createVencimiento(  "02",  ""+aniocalendario,
				obtenerDiaHabilMesSiguiente(aniocalendario, Calendar.FEBRUARY, 7).getTime(), 1,1,4,14,1);
		createVencimiento(  "03",  ""+aniocalendario,
				obtenerDiaHabilMesSiguiente(aniocalendario, Calendar.MARCH, 7).getTime(), 1,1,4,14,1);
		createVencimiento(  "04",  ""+aniocalendario,
				obtenerDiaHabilMesSiguiente(aniocalendario, Calendar.APRIL, 7).getTime(), 1,1,4,14,1);
		createVencimiento(  "05",  ""+aniocalendario,
				obtenerDiaHabilMesSiguiente(aniocalendario, Calendar.MAY, 7).getTime(), 1,1,4,14,1);
		createVencimiento(  "06",  ""+aniocalendario,
				obtenerDiaHabilMesSiguiente(aniocalendario, Calendar.JUNE, 7).getTime(), 1,1,4,14,1);
		createVencimiento(  "07",  ""+aniocalendario,
				obtenerDiaHabilMesSiguiente(aniocalendario, Calendar.JULY, 7).getTime(), 1,1,4,14,1);
		createVencimiento(  "08",  ""+aniocalendario,
				obtenerDiaHabilMesSiguiente(aniocalendario, Calendar.AUGUST, 7).getTime(), 1,1,4,14,1);
		createVencimiento(  "09",  ""+aniocalendario,
				obtenerDiaHabilMesSiguiente(aniocalendario, Calendar.SEPTEMBER, 7).getTime(), 1,1,4,14,1);
		createVencimiento(  "10",  ""+aniocalendario,
				obtenerDiaHabilMesSiguiente(aniocalendario, Calendar.OCTOBER, 7).getTime(), 1,1,4,14,1);
		createVencimiento( "11",  ""+aniocalendario,
				obtenerDiaHabilMesSiguiente(aniocalendario, Calendar.NOVEMBER, 7).getTime(), 1,1,4,14,1);
		createVencimiento(  "12",  ""+aniocalendario,
				obtenerDiaHabilMesSiguiente(aniocalendario, Calendar.DECEMBER, 7).getTime(), 1,1,4,14,1);

		
		createVencimiento(  "01",  ""+aniocalendario,
				obtenerDiaHabilMesSiguiente(aniocalendario, Calendar.JANUARY, 7).getTime(), 1,1,4,5,1);
		createVencimiento(  "02",  ""+aniocalendario,
				obtenerDiaHabilMesSiguiente(aniocalendario, Calendar.FEBRUARY, 7).getTime(), 1,1,4,5,1);
		createVencimiento(  "03",  ""+aniocalendario,
				obtenerDiaHabilMesSiguiente(aniocalendario, Calendar.MARCH, 7).getTime(), 1,1,4,5,1);
		createVencimiento(  "04",  ""+aniocalendario,
				obtenerDiaHabilMesSiguiente(aniocalendario, Calendar.APRIL, 7).getTime(), 1,1,4,5,1);
		createVencimiento(  "05",  ""+aniocalendario,
				obtenerDiaHabilMesSiguiente(aniocalendario, Calendar.MAY, 7).getTime(), 1,1,4,5,1);
		createVencimiento(  "06",  ""+aniocalendario,
				obtenerDiaHabilMesSiguiente(aniocalendario, Calendar.JUNE, 7).getTime(), 1,1,4,5,1);
		createVencimiento(  "07",  ""+aniocalendario,
				obtenerDiaHabilMesSiguiente(aniocalendario, Calendar.JULY, 7).getTime(), 1,1,4,5,1);
		createVencimiento(  "08",  ""+aniocalendario,
				obtenerDiaHabilMesSiguiente(aniocalendario, Calendar.AUGUST, 7).getTime(), 1,1,4,5,1);
		createVencimiento(  "09",  ""+aniocalendario,
				obtenerDiaHabilMesSiguiente(aniocalendario, Calendar.SEPTEMBER, 7).getTime(), 1,1,4,5,1);
		createVencimiento(  "10",  ""+aniocalendario,
				obtenerDiaHabilMesSiguiente(aniocalendario, Calendar.OCTOBER, 7).getTime(), 1,1,4,5,1);
		createVencimiento( "11",  ""+aniocalendario,
				obtenerDiaHabilMesSiguiente(aniocalendario, Calendar.NOVEMBER, 7).getTime(), 1,1,4,5,1);
		createVencimiento(  "12",  ""+aniocalendario,
				obtenerDiaHabilMesSiguiente(aniocalendario, Calendar.DECEMBER, 7).getTime(), 1,1,4,5,1);
		
		createVencimiento(  "01",  ""+aniocalendario,
				obtenerDiaHabilMesSiguiente(aniocalendario, Calendar.JANUARY, 5).getTime(), 1,1,4,25,1);
		createVencimiento(  "02",  ""+aniocalendario,
				obtenerDiaHabilMesSiguiente(aniocalendario, Calendar.FEBRUARY, 5).getTime(), 1,1,4,25,1);
		createVencimiento( "03",  ""+aniocalendario,
				obtenerDiaHabilMesSiguiente(aniocalendario, Calendar.MARCH, 5).getTime(), 1,1,4,25,1);
		createVencimiento(  "04",  ""+aniocalendario,
				obtenerDiaHabilMesSiguiente(aniocalendario, Calendar.APRIL, 5).getTime(), 1,1,4,25,1);
		createVencimiento( "05",  ""+aniocalendario,
				obtenerDiaHabilMesSiguiente(aniocalendario, Calendar.MAY, 5).getTime(), 1,1,4,25,1);
		createVencimiento( "06",  ""+aniocalendario,
				obtenerDiaHabilMesSiguiente(aniocalendario, Calendar.JUNE, 5).getTime(), 1,1,4,25,1);
		createVencimiento( "07",  ""+aniocalendario,
				obtenerDiaHabilMesSiguiente(aniocalendario, Calendar.JULY, 5).getTime(), 1,1,4,25,1);
		createVencimiento(  "08",  ""+aniocalendario,
				obtenerDiaHabilMesSiguiente(aniocalendario, Calendar.AUGUST, 5).getTime(), 1,1,4,25,1);
		createVencimiento(  "09",  ""+aniocalendario,
				obtenerDiaHabilMesSiguiente(aniocalendario, Calendar.SEPTEMBER, 5).getTime(), 1,1,4,25,1);
		createVencimiento(  "10",  ""+aniocalendario,
				obtenerDiaHabilMesSiguiente(aniocalendario, Calendar.OCTOBER, 5).getTime(), 1,1,4,25,1);
		createVencimiento( "11",  ""+aniocalendario,
				obtenerDiaHabilMesSiguiente(aniocalendario, Calendar.NOVEMBER, 5).getTime(), 1,1,4,25,1);
		createVencimiento(  "12",  ""+aniocalendario,
				obtenerDiaHabilMesSiguiente(aniocalendario, Calendar.DECEMBER, 5).getTime(), 1,1,4,25,1);
		
		*/
//	    createVencimiento(  "01",  ""+aniocalendario,
//				obtenerDiezCalendarioMesSiguiente(aniocalendario, Calendar.JANUARY).getTime(), 1,1,4,3,3,0);
//		createVencimiento(  "02",  ""+aniocalendario,
//				obtenerDiezCalendarioMesSiguiente(aniocalendario, Calendar.FEBRUARY).getTime(), 1,1,4,3,3,0);
//		createVencimiento(  "03",  ""+aniocalendario,
//				obtenerDiezCalendarioMesSiguiente(aniocalendario, Calendar.MARCH).getTime(), 1,1,4,3,3,0);
//		createVencimiento( "04",  ""+aniocalendario,
//				obtenerDiezCalendarioMesSiguiente(aniocalendario, Calendar.APRIL).getTime(), 1,1,4,3,3,0);
//		createVencimiento(  "05",  ""+aniocalendario,
//				obtenerDiezCalendarioMesSiguiente(aniocalendario, Calendar.MAY).getTime(), 1,1,4,3,3,0);
//		createVencimiento(  "06",  ""+aniocalendario,
//				obtenerDiezCalendarioMesSiguiente(aniocalendario, Calendar.JUNE).getTime(), 1,1,4,3,3,0);
//		createVencimiento(  "07",  ""+aniocalendario,
//				obtenerDiezCalendarioMesSiguiente(aniocalendario, Calendar.JULY).getTime(), 1,1,4,3,3,0);
//		createVencimiento(  "08",  ""+aniocalendario,
//				obtenerDiezCalendarioMesSiguiente(aniocalendario, Calendar.AUGUST).getTime(), 1,1,4,3,3,0);
//		createVencimiento(  "09",  ""+aniocalendario,
//				obtenerDiezCalendarioMesSiguiente(aniocalendario, Calendar.SEPTEMBER).getTime(), 1,1,4,3,3,0);
//		createVencimiento(  "10",  ""+aniocalendario,
//				obtenerDiezCalendarioMesSiguiente(aniocalendario, Calendar.OCTOBER).getTime(), 1,1,4,3,3,0);
//		createVencimiento( "11",  ""+aniocalendario,
//				obtenerDiezCalendarioMesSiguiente(aniocalendario, Calendar.NOVEMBER).getTime(), 1,1,4,3,3,0);
//		createVencimiento(  "12",  ""+aniocalendario,
//				obtenerDiezCalendarioMesSiguiente(aniocalendario, Calendar.DECEMBER).getTime(), 1,1,4,3,3,0);
//
//		createVencimiento(  "01",  ""+aniocalendario,
//				obtenerDiezCalendarioMesSiguiente(aniocalendario, Calendar.JANUARY).getTime(), 1,1,4,3,4,0);
//		createVencimiento(  "02",  ""+aniocalendario,
//				obtenerDiezCalendarioMesSiguiente(aniocalendario, Calendar.FEBRUARY).getTime(), 1,1,4,3,4,0);
//		createVencimiento(  "03",  ""+aniocalendario,
//				obtenerDiezCalendarioMesSiguiente(aniocalendario, Calendar.MARCH).getTime(), 1,1,4,3,4,0);
//		createVencimiento( "04",  ""+aniocalendario,
//				obtenerDiezCalendarioMesSiguiente(aniocalendario, Calendar.APRIL).getTime(), 1,1,4,3,4,0);
//		createVencimiento(  "05",  ""+aniocalendario,
//				obtenerDiezCalendarioMesSiguiente(aniocalendario, Calendar.MAY).getTime(), 1,1,4,3,4,0);
//		createVencimiento(  "06",  ""+aniocalendario,
//				obtenerDiezCalendarioMesSiguiente(aniocalendario, Calendar.JUNE).getTime(), 1,1,4,3,4,0);
//		createVencimiento(  "07",  ""+aniocalendario,
//				obtenerDiezCalendarioMesSiguiente(aniocalendario, Calendar.JULY).getTime(), 1,1,4,3,4,0);
//		createVencimiento(  "08",  ""+aniocalendario,
//				obtenerDiezCalendarioMesSiguiente(aniocalendario, Calendar.AUGUST).getTime(), 1,1,4,3,4,0);
//		createVencimiento(  "09",  ""+aniocalendario,
//				obtenerDiezCalendarioMesSiguiente(aniocalendario, Calendar.SEPTEMBER).getTime(), 1,1,4,3,4,0);
//		createVencimiento(  "10",  ""+aniocalendario,
//				obtenerDiezCalendarioMesSiguiente(aniocalendario, Calendar.OCTOBER).getTime(), 1,1,4,3,4,0);
//		createVencimiento( "11",  ""+aniocalendario,
//				obtenerDiezCalendarioMesSiguiente(aniocalendario, Calendar.NOVEMBER).getTime(), 1,1,4,3,4,0);
//		createVencimiento(  "12",  ""+aniocalendario,
//				obtenerDiezCalendarioMesSiguiente(aniocalendario, Calendar.DECEMBER).getTime(), 1,1,4,3,4,0);
//				

		alreadySetup = true;
	}
	
	@Transactional
	Role createRoleIfNotFound(final String name) {
		Role role = roleService.findByName(name);
//		if (role == null) {
//			role = new Role(name);
//			roleService.save(role);
//		}
		return role;
	}
	@Transactional
	void createPerfil(final String name) {
		Perfil perfil = new Perfil();
		perfil.setSDescripcion(name);
		perfilService.save(perfil);
	}
	@Transactional
	void createUserIfNotFound(final String email, String name, String surname, String username, String password,
			List<Role> userRoles, Integer idConcesionario,int idPerfil) {
		User user  = new User();
			user.setName(name);
			user.setSurname(surname);
			user.setUsername(username);
			user.setPassword(password);
			user.setEmail(email);
			//user.setRoles(userRoles);
			user.setEnabled(true);
			Concesionario concesionario = new Concesionario();
			concesionario.setId(idConcesionario);
			user.setConcesionario(concesionario);
			Perfil perfil=new Perfil();
			perfil.setId(idPerfil);
			user.setPerfil(perfil);
			usuarioService.save(user);
	}
	@Transactional
	void createEstado(Integer id, String sestado) {
		Estado estado = new Estado();
		estado.setId(id);
		estado.setSDescripcion(sestado);
		estadoService.save(estado);
	}
	
	
	@Transactional
	void createBanco(final String name) {
		Banco banco = new Banco();
		banco.setSDescripcion(name);
		bancoService.save(banco);

	}
	
	@Transactional
	void createMoneda(final Integer id,final String name,final String sSymbolo) {
		Moneda moneda = new Moneda();
		moneda.setId(id);
		moneda.setSDescripcion(name);
		moneda.setSSimbolo(sSymbolo);
		
		monedaService.save(moneda);

	}
	@Transactional
	void createAporteTipo(final String id,final String name) {
		AporteEstadoDJ aporteTipo = new AporteEstadoDJ();
		aporteTipo.setId(id);
		aporteTipo.setSDescripcion(name);
		
		// contribuyente.
		aporteTipoService.save(aporteTipo);

	}
	@Transactional
	void deleteConcesionario() {
		concesionarioService.deleteAll();

	}
	@Transactional
	void createConcesionarioTipoVencimiento(final Integer idConcesionario,final Integer idTipoVencimiento,final String sAnioPeriodo) {
		ConcesionarioTipoVencimiento concesionarioTipoVencimiento = new ConcesionarioTipoVencimiento(idConcesionario, idTipoVencimiento, sAnioPeriodo);                            
		concesionarioTipoVencimientoService.save(concesionarioTipoVencimiento);
	}
	
	
	@Transactional
	void createConcesionario(final Integer id,final String name, final String ruc) {
		Concesionario concesionario = new Concesionario();
		concesionario.setSnombre(name);
		concesionario.setSruc(ruc);
		concesionario.setId(id);
		concesionarioService.save(concesionario);

	}
	@Transactional
	void createTipoVencimiento(String descripcion) {
		TipoVencimiento tipoVencimiento=new TipoVencimiento();
		tipoVencimiento.setSDescripcion(descripcion);
		tipoVencimientoService.save(tipoVencimiento);
	}
	@Transactional
	void delAllVencimiento() {
		vencimientoService.deleteall();
	}
	@Transactional
	void createVencimiento(String mes, String anio, Date fecha,
			int tipoPeriodicidad, int tipoRetribucion, int concepto,int concesionario,int diahabil) {
		Vencimiento vencimiento = new Vencimiento();
		vencimiento.setSMesPeriodo(mes);
		vencimiento.setSAnioPeriodo(anio);
		vencimiento.setDFechaVencPago(fecha);	
		Calendar dt=Calendar.getInstance();
		dt.setTime(fecha);
		if(diahabil==1) {	
			dt.add(Calendar.DATE, 1);
			addfechaHabil(dt, 1, 3);
		}else {
			dt.setTime(moverFecha(dt, Calendar.DATE, 3).getTime());
		}
		vencimiento.setDFechaVencPres(dt.getTime()); 
		
		vencimiento.setConcesionario(concesionarioService.findById(concesionario));
		vencimiento.setTipoPeriodicidad(tipoPeriodicidadService.findById((tipoPeriodicidad)));
		vencimiento.setTipoRetribucion(tipoRetribucionService.findById(tipoRetribucion));
		vencimiento.setSDiaHabil(diahabil);
		vencimientoService.save(vencimiento);
	}

	final static String URL_CRON = "http://www.sunat.gob.pe/cl-ti-itcronobligme/fvS01Alias?accion=rptGral&periodo=";

	@Transactional
	void createCalendarioDet(final String periodo, final int tipovencimiento, final Integer concepto,final Integer tipoCalendario) {
		StringBuilder URL = new StringBuilder(URL_CRON).append(periodo);

		try {
			Document doc = null;

			try {
				Connection connection = Jsoup.connect(URL.toString()).followRedirects(true).referrer(URL.toString())
						.ignoreContentType(true).ignoreHttpErrors(true);
				connection.userAgent(
						"Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103 Safari/537.36");
				connection.timeout(10 * 1000);
				doc = connection.get();
			} catch (SocketTimeoutException e) {
				e.printStackTrace();

			}

			doc.select("table tr td:has(table)");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional
	void createAportePorcentaje(int concesionario,int idtiporetribucion,BigDecimal porcentaje) {
		AportePorcentaje n = new AportePorcentaje();
		TipoRetribucion tipoRetribucion=tipoRetribucionService.findById(idtiporetribucion);
		n.setTipoRetribucion(tipoRetribucion);
		
		Concesionario c=concesionarioService.findById(concesionario);
		n.setPorcentaje(porcentaje);
		n.setContribuyente(c);
	
		aportePorcentajeService.save(n);

	}
	@Transactional
	void createTiporRetribucion(Integer id,final String name) {
		TipoRetribucion n = new TipoRetribucion();
		n.setId(id);
		n.setSDescripcion(name);
	
		tipoRetribucionService.save(n);

	}
	@Transactional
	void createTipoPeriodicidad(Integer id,final String name, final int orden) {
		TipoPeriodicidad n = new TipoPeriodicidad();
		n.setId(id);
		n.setSDescripcion(name);
		n.setOrden(orden);
		tipoPeriodicidadService.save(n);

	}

	@Transactional
	void createModulo(final String name) {
		Modulo n = new Modulo();
		n.setSDescripcion(name);
		moduloService.save(n);

	}

	@Transactional
	void createConcepto(final String name, int codigoModulo) {
		Concepto n = new Concepto();
		n.setSDescripcion(name);
		Modulo m = new Modulo();
		m.setNcodigo(codigoModulo);
		n.setModulo(m);
		conceptoService.save(n);

	}

	public Calendar obtenerCalendarioPrincipal(int anio, int mes,int dia) {
		/*ESTE CALENDARIO ES ANUAL 20 enero*/
		int mesSiguiente = mes + 1;
		Calendar cal = Calendar.getInstance();
		cal.set(mes==12?anio+1:anio, mesSiguiente, dia);
		addfechaHabil(cal,1,1);
		return cal;
	}
	
	public Calendar obtenerDiezCalendarioMesSiguiente(int anio, int mes) {
		int mesSiguiente = mes + 1;
		Calendar cal = Calendar.getInstance();
		cal.set(anio, mesSiguiente, 10);
		return cal;
	}

	public Calendar obtenerUltimoDiaMesSiguiente(int anio, int mes) {
		
		int mesSiguiente = mes + 1;
		Calendar cal = Calendar.getInstance();
		cal.set(anio, mesSiguiente, 1);
		int ultimo = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		cal.set((anio), mesSiguiente, ultimo);	
		
		return cal;
	}
	public Calendar obtenerDiaHabilMesSiguiente(int anio, int mes, int numeroDiasHab) {
		int mesSiguiente = mes + 1;
		Calendar cal = Calendar.getInstance();
		cal.set(anio, mesSiguiente, 1);		
		addfechaHabil(cal, 1, numeroDiasHab);
		
		return cal;
	}
	public Calendar obtenerUltimoDiaHabilMesSiguiente(int anio, int mes) {
		Calendar cal = obtenerUltimoDiaMesSiguiente(anio, mes);
		retrocederFecha(cal, -1);
		return cal;
	}
	public void addfechaHabil(Calendar dt, int sumaoresta, int tope) {
		int cont = 0;
		while (cont < tope) {		
			int a = dt.get(Calendar.YEAR);
			int m = dt.get(Calendar.MONTH);
			int d = dt.get(Calendar.DATE);
			int ndia = dt.get(Calendar.DAY_OF_WEEK);
			Feriado f = null;
			if (!(ndia == Calendar.SUNDAY || ndia == Calendar.SATURDAY)) {
				f = feriadoService.findByFerAnyoAndFerMesAndFerDia(a, m+1, d);
				if (null == f)cont++;
			}
			if(cont!=tope)dt.setTime(moverFecha(dt, Calendar.DATE, sumaoresta).getTime());
		}
	}
	public void retrocederFecha(Calendar dt,  int tope) {
		int cont = 0;
		int fin = 1 + tope;
		while (cont <= fin) {		
			int a = dt.get(Calendar.YEAR);
			int m = dt.get(Calendar.MONTH);
			int d = dt.get(Calendar.DATE);
			int ndia = dt.get(Calendar.DAY_OF_WEEK);
			Feriado f = null;
			if (!(ndia == Calendar.SUNDAY && ndia == Calendar.SATURDAY)) {
				f = feriadoService.findByFerAnyoAndFerMesAndFerDia(a, m+1, d);
				if (null == f)cont++;
			}
			if(cont<fin)
				dt.setTime(moverFecha(dt, Calendar.DATE, -1).getTime());
		}
	}

	public Calendar moverFecha(Calendar fecha, int campo, int sumaoresta) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha.getTime());
		calendar.add(campo, sumaoresta);
		return calendar;
	}

	public int getNumeroMes(String mes) {
		int n = 0;
		switch (mes) {
		case "Ene": {
			n = 0;
			break;
		}
		case "Feb": {
			n = 1;
			break;
		}
		case "Mar": {
			n = 2;
			break;
		}
		case "Abr": {
			n = 3;
			break;
		}
		case "May": {
			n = 4;
			break;
		}
		case "Jun": {
			n = 5;
			break;
		}
		case "Jul": {
			n = 6;
			break;
		}
		case "Ago": {
			n = 7;
			break;
		}
		case "Set": {
			n = 8;
			break;
		}
		case "Oct": {
			n = 9;
			break;
		}
		case "Nov": {
			n = 10;
			break;
		}
		case "Dic": {
			n = 11;
			break;
		}
		}
		return n;
	}

	public String getSNumeroMes(String mes) {
		String n = "";
		switch (mes) {
		case "Ene": {
			n = "01";
			break;
		}
		case "Feb": {
			n = "02";
			break;
		}
		case "Mar": {
			n = "03";
			break;
		}
		case "Abr": {
			n = "04";
			break;
		}
		case "May": {
			n = "05";
			break;
		}
		case "Jun": {
			n = "06";
			break;
		}
		case "Jul": {
			n = "07";
			break;
		}
		case "Ago": {
			n = "08";
			break;
		}
		case "Set": {
			n = "09";
			break;
		}
		case "Oct": {
			n = "10";
			break;
		}
		case "Nov": {
			n = "11";
			break;
		}
		case "Dic": {
			n = "12";
			break;
		}
		}
		return n;
	}

	


}