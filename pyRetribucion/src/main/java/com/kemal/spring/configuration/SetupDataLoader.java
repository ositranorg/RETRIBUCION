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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kemal.spring.domain.AportePorcentaje;
import com.kemal.spring.domain.AporteTipo;
import com.kemal.spring.domain.Banco;
import com.kemal.spring.domain.Concepto;
import com.kemal.spring.domain.Contrato;
import com.kemal.spring.domain.Contribuyente;
import com.kemal.spring.domain.Estado;
import com.kemal.spring.domain.Feriado;
import com.kemal.spring.domain.Modulo;
import com.kemal.spring.domain.Moneda;
import com.kemal.spring.domain.Perfil;
import com.kemal.spring.domain.Role;
import com.kemal.spring.domain.TipoPeriodicidad;
import com.kemal.spring.domain.TipoPeriodicidadDet;
import com.kemal.spring.domain.TipoRetribucion;
import com.kemal.spring.domain.TipoVencimiento;
import com.kemal.spring.domain.User;
import com.kemal.spring.service.AportePorcentajeService;
import com.kemal.spring.service.AporteTipoService;
import com.kemal.spring.service.BancoService;
import com.kemal.spring.service.CalendarioDetService;
import com.kemal.spring.service.ConceptoService;
import com.kemal.spring.service.ContratoService;
import com.kemal.spring.service.ContribuyenteService;
import com.kemal.spring.service.EstadoService;
import com.kemal.spring.service.FeriadoService;
import com.kemal.spring.service.ModuloService;
import com.kemal.spring.service.MonedaService;
import com.kemal.spring.service.PerfilService;
import com.kemal.spring.service.RoleService;
import com.kemal.spring.service.TipoPeriodicidadService;
import com.kemal.spring.service.TipoRetribucionService;
import com.kemal.spring.service.TipoVencimientoService;
import com.kemal.spring.service.UserService;


@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

	private boolean alreadySetup = false;

	private UserService userService;

	private RoleService roleService;

	private BCryptPasswordEncoder bCryptPasswordEncoder;

	private ConceptoService conceptoService;

	private ModuloService moduloService;

	private CalendarioDetService calendarioDetService;
	
	private TipoPeriodicidadService calendarioService;
	private FeriadoService feriadoService;
	private ContribuyenteService contribuyenteService;

	private ContratoService contratoService;
	private TipoRetribucionService tipoRetribucionService;

	
	private TipoVencimientoService tipoVencimientoService;
	private AporteTipoService aporteTipoService;
	private MonedaService monedaService;
	
	private AportePorcentajeService aportePorcentajeService;
	private BancoService bancoService;
	private PerfilService perfilService;
	private EstadoService estadoService;
	public SetupDataLoader(UserService userService, RoleService roleService,
			BCryptPasswordEncoder bCryptPasswordEncoder, ConceptoService conceptoService, ModuloService moduloService,
			TipoPeriodicidadService calendarioService, CalendarioDetService calendarioDetService,
			FeriadoService feriadoService, ContribuyenteService contribuyenteService, ContratoService contratoService,
			
			TipoVencimientoService tipoVencimientoService,
			
			TipoRetribucionService tipoRetribucionService,		
			AporteTipoService aporteTipoService,
			MonedaService monedaService,
			AportePorcentajeService aportePorcentajeService,
			BancoService bancoService,
			PerfilService perfilService,
			EstadoService estadoService) {
		this.userService = userService;
		this.roleService = roleService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.conceptoService = conceptoService;
		this.moduloService = moduloService;
		this.calendarioDetService = calendarioDetService;
		this.calendarioService = calendarioService;
		this.feriadoService = feriadoService;
		this.contribuyenteService = contribuyenteService;
		this.contratoService = contratoService;
		
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
		
			
		createCalendario(1,"MENSUAL", 1);		
		createCalendario(2,"TRIMESTRAL", 2);
		createCalendario(3,"SEMESTRAL", 3);
		createCalendario(4,"ANUAL", 4);
		
		createTiporRetribucion(1,"RETRIBUCION");		
		createTiporRetribucion(2,"PRINCIPAL");	
		createTiporRetribucion(3,"ESPECIAL");	
		createTiporRetribucion(4,"FONCEPRI");	
		
		createContribuyente("LIMA AIRPORT PARTNERS S.R.L.", "20501577252");
		createContribuyente("AEROPUERTOS DEL PERÚ S.A.", "20514513172");
		createContribuyente("FERROCARRILES TRANSANDINO S.A", "20432747833");
		createContribuyente("FERROVIAS CENTRAL ANDINA S.A.", "20432347142");
		createContribuyente("CONCESIONARIA VIAL DEL PERU S.A. ", "20511465061");
		createContribuyente("NORVIAL S.A.", "20505377142");
		createContribuyente("TERMINAL INTERNACIONAL DEL SUR S.A.", "20428500475");
		createContribuyente("APM TERMINALS CALLAO S.A.", "20543083888");
		createContribuyente("DP WORLD CALLAO S.R.L.", "20513462388");
		createContribuyente("TERMINALES PORTUARIOS EUROANDINOS PAITA S.A. - TPE PAITA S.A.", "20522473571");
		createContribuyente("TERMINAL PORTUARIO PARACAS S.A.", "20562916360");
		createContribuyente("TRANSPORTADORA CALLAO S.A.", "20537577232");
		createContribuyente("SALAVERRY TERMINAL INTERNACIONAL S.A.", "20603487321");
		

		
		createTipoVencimiento("SEGÚN CONTRATO");
		createTipoVencimiento("SEGÚN SUNAT");
		
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
		
		createPerfil("CONCESIONARIO");
		createPerfil("SUPERVISOR");
		createPerfil("TESORERIA");
		createPerfil("INVITADO");
		
		createUserIfNotFound("admin@gmail.com", "Admin", "Admin", "limaai", "admin", adminRoles, 1,2);

		createUserIfNotFound("adp"  + "@gmail.com", "User" , "User", "adp" , "ayp", userRoles,2,2);
				

		createUserIfNotFound("tisur"  + "@gmail.com", "apmter" , "tisur", "tisur" , "tisur", userRoles,7,2);
	
		*/
		
//		
//		createAportePorcentaje(1,1, new BigDecimal("0.46511"));//lap
//		createAportePorcentaje(2,1, new BigDecimal("0.15"));//adp
//		createAportePorcentaje(3,1, new BigDecimal("0.3725"));//ferrocarriles trasandino
//		createAportePorcentaje(4,3, new BigDecimal("0.5"));//ferrocarriles trasandino
//		createAportePorcentaje(5,1, new BigDecimal("0.2475"));//ferrovias central andina
//		createAportePorcentaje(6,3, new BigDecimal("0.5"));//ferrovias central andina
//		createAportePorcentaje(7,1, new BigDecimal("0.1861"));//CONCESIONARIA VIAL DEL PERU S.A.
//		createAportePorcentaje(8,1, new BigDecimal("0.055"));//norvial
//		
//		createAportePorcentaje(9,1,new BigDecimal("0.5"));//tisur-retribucion(canon)
//		createAportePorcentaje(10,3, new BigDecimal("0.985"));//tisur-especial
//		
//		createAportePorcentaje(11,1, new BigDecimal("0.03"));//apm terminals
//		createAportePorcentaje(12,1, new BigDecimal("0.03"));//dpworl
//		createAportePorcentaje(13,1, new BigDecimal("0.02"));//euroandinos
//		createAportePorcentaje(14,1, new BigDecimal("0.03"));//paracas
//		createAportePorcentaje(15,1, new BigDecimal("0.02"));//transportadora
//		
//		createAportePorcentaje(16,1, new BigDecimal("0.03"));//sALAVERRY
//		createAportePorcentaje(17,4, new BigDecimal("0.0006"));//sALAVERRY
		
		
			/*
		
		createContrato(1,1,"último dia útil del mes inmediato siguiente",1);
		createContrato(2,3,"Trimestralmente, de acuerdo a lo establecido en el Anexo 17 del Contrato de Concesión.",1);
		createContrato(3,1,"Dentro de los 10 primeros días calendarios del mes siguiente",1);
		createContrato(4,1,"Dentro de los 10 primeros días calendarios del mes siguiente",1);
		createContrato(5,1,"Primeros 7 días hábiles del mes siguiente ",1);
		createContrato(6,1,"Primeros 7 días hábiles del mes siguiente ",1);
		createContrato(7,1,"Quinto día útil del mes siguiente",1);
		createContrato(8,1,"De acuerdo al cronograma establecido por la SUNAT para el cumplimiento de sus obligaciones tributarias",2);
		createContrato(9,1,"De acuerdo al cronograma establecido por la SUNAT para el cumplimiento de sus obligaciones tributarias. ",2);
		createContrato(10,1,"De acuerdo al cronograma establecido por la SUNAT para el cumplimiento de sus obligaciones tributarias. ",2);
		createContrato(11,1,"De acuerdo al cronograma establecido por la SUNAT para el cumplimiento de sus obligaciones tributarias. ",2);
		createContrato(12,1,"De acuerdo al cronograma establecido por la SUNAT para el cumplimiento de sus obligaciones tributarias. ",2);
		createContrato(13,1,"De acuerdo al cronograma establecido por la SUNAT para el cumplimiento de sus obligaciones tributarias. ",2);
		// region Creating users
		
		
		// endregion

	
		
	
	
		createCalendarioDet(1,"01", "2019",
				obtenerUltimoDiaHabilMesSiguiente(2019, Calendar.JANUARY).getTime(), 1, 1, 6);
		createCalendarioDet(1,"02", "2019",
				obtenerUltimoDiaHabilMesSiguiente(2019, Calendar.FEBRUARY).getTime(), 1, 1, 6);
		createCalendarioDet(1,"03", "2019",
				obtenerUltimoDiaHabilMesSiguiente(2019, Calendar.MARCH).getTime(), 1, 1, 6);
		createCalendarioDet(1,"04", "2019",
				obtenerUltimoDiaHabilMesSiguiente(2019, Calendar.APRIL).getTime(), 1, 1, 6);
		createCalendarioDet(1,"05", "2019",
				obtenerUltimoDiaHabilMesSiguiente(2019, Calendar.MAY).getTime(), 1, 1, 6);
		createCalendarioDet(1,  "06", "2019",
				obtenerUltimoDiaHabilMesSiguiente(2019, Calendar.JUNE).getTime(), 1, 1, 6);
		createCalendarioDet(1,"07", "2019",
				obtenerUltimoDiaHabilMesSiguiente(2019, Calendar.JULY).getTime(), 1, 1, 6);
		createCalendarioDet(1,"08", "2019",
				obtenerUltimoDiaHabilMesSiguiente(2019, Calendar.AUGUST).getTime(), 1, 1, 6);
		createCalendarioDet(1,  "09", "2019",
				obtenerUltimoDiaHabilMesSiguiente(2019, Calendar.SEPTEMBER).getTime(), 1, 1, 6);
		createCalendarioDet(1, "10", "2019",
				obtenerUltimoDiaHabilMesSiguiente(2019, Calendar.OCTOBER).getTime(), 1, 1, 6);
		createCalendarioDet(1, "11", "2019",
				obtenerUltimoDiaHabilMesSiguiente(2019, Calendar.NOVEMBER).getTime(), 1, 1, 6);
		createCalendarioDet(1,"12", "2019",
				obtenerUltimoDiaHabilMesSiguiente(2019, Calendar.DECEMBER).getTime(), 1, 1, 6);

		createCalendarioDet(2, 
				"01", "2019", obtenerUltimoDiaHabilMesSiguiente(2019, Calendar.MARCH).getTime(), 1, 3, 6);
		createCalendarioDet(2,
				"02", "2019", obtenerUltimoDiaHabilMesSiguiente(2019, Calendar.JUNE).getTime(), 1, 3, 6);
		createCalendarioDet(2,
				"03", "2019", obtenerUltimoDiaHabilMesSiguiente(2019, Calendar.SEPTEMBER).getTime(), 1, 3, 6);
		createCalendarioDet(2, 
				"04", "2019", obtenerUltimoDiaHabilMesSiguiente(2019, Calendar.DECEMBER).getTime(), 1, 3, 6);

		createCalendarioDet(3,  "01", "2019",
				obtenerDiezCalendarioMesSiguiente(2019, Calendar.JANUARY).getTime(), 1, 1, 6);
		createCalendarioDet(3,  "02", "2019",
				obtenerDiezCalendarioMesSiguiente(2019, Calendar.FEBRUARY).getTime(), 1, 1, 6);
		createCalendarioDet(3,  "03", "2019",
				obtenerDiezCalendarioMesSiguiente(2019, Calendar.MARCH).getTime(), 1, 1, 6);
		createCalendarioDet(3, "04", "2019",
				obtenerDiezCalendarioMesSiguiente(2019, Calendar.APRIL).getTime(), 1, 1, 6);
		createCalendarioDet(3,  "05", "2019",
				obtenerDiezCalendarioMesSiguiente(2019, Calendar.MAY).getTime(), 1, 1, 6);
		createCalendarioDet(3,  "06", "2019",
				obtenerDiezCalendarioMesSiguiente(2019, Calendar.JUNE).getTime(), 1, 1, 6);
		createCalendarioDet(3,  "07", "2019",
				obtenerDiezCalendarioMesSiguiente(2019, Calendar.JULY).getTime(), 1, 1, 6);
		createCalendarioDet(3,  "08", "2019",
				obtenerDiezCalendarioMesSiguiente(2019, Calendar.AUGUST).getTime(), 1, 1, 6);
		createCalendarioDet(3,  "09", "2019",
				obtenerDiezCalendarioMesSiguiente(2019, Calendar.SEPTEMBER).getTime(), 1, 1, 6);
		createCalendarioDet(3,  "10", "2019",
				obtenerDiezCalendarioMesSiguiente(2019, Calendar.OCTOBER).getTime(), 1, 1, 6);
		createCalendarioDet(3, "11", "2019",
				obtenerDiezCalendarioMesSiguiente(2019, Calendar.NOVEMBER).getTime(), 1, 1, 6);
		createCalendarioDet(3,  "12", "2019",
				obtenerDiezCalendarioMesSiguiente(2019, Calendar.DECEMBER).getTime(), 1, 1, 6);

		createCalendarioDet(4,  "01", "2019",
				obtenerDiezCalendarioMesSiguiente(2019, Calendar.JANUARY).getTime(), 1, 1, 6);
		createCalendarioDet(4,  "02", "2019",
				obtenerDiezCalendarioMesSiguiente(2019, Calendar.FEBRUARY).getTime(), 1, 1, 6);
		createCalendarioDet(4, "03", "2019",
				obtenerDiezCalendarioMesSiguiente(2019, Calendar.MARCH).getTime(), 1, 1, 6);
		createCalendarioDet(4,  "04", "2019",
				obtenerDiezCalendarioMesSiguiente(2019, Calendar.APRIL).getTime(), 1, 1, 6);
		createCalendarioDet(4,  "05", "2019",
				obtenerDiezCalendarioMesSiguiente(2019, Calendar.MAY).getTime(), 1, 1, 6);
		createCalendarioDet(4,  "06", "2019",
				obtenerDiezCalendarioMesSiguiente(2019, Calendar.JUNE).getTime(), 1, 1, 6);
		createCalendarioDet(4,  "07", "2019",
				obtenerDiezCalendarioMesSiguiente(2019, Calendar.JULY).getTime(), 1, 1, 6);
		createCalendarioDet(4,  "08", "2019",
				obtenerDiezCalendarioMesSiguiente(2019, Calendar.AUGUST).getTime(), 1, 1, 6);
		createCalendarioDet(4,  "09", "2019",
				obtenerDiezCalendarioMesSiguiente(2019, Calendar.SEPTEMBER).getTime(), 1, 1, 6);
		createCalendarioDet(4,  "10", "2019",
				obtenerDiezCalendarioMesSiguiente(2019, Calendar.OCTOBER).getTime(), 1, 1, 6);
		createCalendarioDet(4,  "11", "2019",
				obtenerDiezCalendarioMesSiguiente(2019, Calendar.NOVEMBER).getTime(), 1, 1, 6);
		createCalendarioDet(4,  "12", "2019",
				obtenerDiezCalendarioMesSiguiente(2019, Calendar.DECEMBER).getTime(), 1, 1, 6);

		createCalendarioDet(5,  "01", "2019",
				obtenerDiaHabilMesSiguiente(2019, Calendar.JANUARY, 7).getTime(), 1, 1, 6);
		createCalendarioDet(5,  "02", "2019",
				obtenerDiaHabilMesSiguiente(2019, Calendar.FEBRUARY, 7).getTime(), 1, 1, 6);
		createCalendarioDet(5,  "03", "2019",
				obtenerDiaHabilMesSiguiente(2019, Calendar.MARCH, 7).getTime(), 1, 1, 6);
		createCalendarioDet(5,  "04", "2019",
				obtenerDiaHabilMesSiguiente(2019, Calendar.APRIL, 7).getTime(), 1, 1, 6);
		createCalendarioDet(5,  "05", "2019",
				obtenerDiaHabilMesSiguiente(2019, Calendar.MAY, 7).getTime(), 1, 1, 6);
		createCalendarioDet(5,  "06", "2019",
				obtenerDiaHabilMesSiguiente(2019, Calendar.JUNE, 7).getTime(), 1, 1, 6);
		createCalendarioDet(5,  "07", "2019",
				obtenerDiaHabilMesSiguiente(2019, Calendar.JULY, 7).getTime(), 1, 1, 6);
		createCalendarioDet(5,  "08", "2019",
				obtenerDiaHabilMesSiguiente(2019, Calendar.AUGUST, 7).getTime(), 1, 1, 6);
		createCalendarioDet(5,  "09", "2019",
				obtenerDiaHabilMesSiguiente(2019, Calendar.SEPTEMBER, 7).getTime(), 1, 1, 6);
		createCalendarioDet(5,  "10", "2019",
				obtenerDiaHabilMesSiguiente(2019, Calendar.OCTOBER, 7).getTime(), 1, 1, 6);
		createCalendarioDet(5, "11", "2019",
				obtenerDiaHabilMesSiguiente(2019, Calendar.NOVEMBER, 7).getTime(), 1, 1, 6);
		createCalendarioDet(5,  "12", "2019",
				obtenerDiaHabilMesSiguiente(2019, Calendar.DECEMBER, 7).getTime(), 1, 1, 6);

		createCalendarioDet(6,  "01", "2019",
				obtenerDiaHabilMesSiguiente(2019, Calendar.JANUARY, 7).getTime(), 1, 1, 6);
		createCalendarioDet(6, "02", "2019",
				obtenerDiaHabilMesSiguiente(2019, Calendar.FEBRUARY, 7).getTime(), 1, 1, 6);
		createCalendarioDet(6,  "03", "2019",
				obtenerDiaHabilMesSiguiente(2019, Calendar.MARCH, 7).getTime(), 1, 1, 6);
		createCalendarioDet(6,  "04", "2019",
				obtenerDiaHabilMesSiguiente(2019, Calendar.APRIL, 7).getTime(), 1, 1, 6);
		createCalendarioDet(6,  "05", "2019",
				obtenerDiaHabilMesSiguiente(2019, Calendar.MAY, 7).getTime(), 1, 1, 6);
		createCalendarioDet(6, "06", "2019",
				obtenerDiaHabilMesSiguiente(2019, Calendar.JUNE, 7).getTime(), 1, 1, 6);
		createCalendarioDet(6, "07", "2019",
				obtenerDiaHabilMesSiguiente(2019, Calendar.JULY, 7).getTime(), 1, 1, 6);
		createCalendarioDet(6,  "08", "2019",
				obtenerDiaHabilMesSiguiente(2019, Calendar.AUGUST, 7).getTime(), 1, 1, 6);
		createCalendarioDet(6,  "09", "2019",
				obtenerDiaHabilMesSiguiente(2019, Calendar.SEPTEMBER, 7).getTime(), 1, 1, 6);
		createCalendarioDet(6,  "10", "2019",
				obtenerDiaHabilMesSiguiente(2019, Calendar.OCTOBER, 7).getTime(), 1, 1, 6);
		createCalendarioDet(6,  "11", "2019",
				obtenerDiaHabilMesSiguiente(2019, Calendar.NOVEMBER, 7).getTime(), 1, 1, 6);
		createCalendarioDet(6,  "12", "2019",
				obtenerDiaHabilMesSiguiente(2019, Calendar.DECEMBER, 7).getTime(), 1, 1, 6);

		createCalendarioDet(7,  "01", "2019",
				obtenerDiaHabilMesSiguiente(2019, Calendar.JANUARY, 5).getTime(), 1, 5, 6);
		createCalendarioDet(7,  "02", "2019",
				obtenerDiaHabilMesSiguiente(2019, Calendar.FEBRUARY, 5).getTime(), 1, 5, 6);
		createCalendarioDet(7, "03", "2019",
				obtenerDiaHabilMesSiguiente(2019, Calendar.MARCH, 5).getTime(), 1, 5, 6);
		createCalendarioDet(7,  "04", "2019",
				obtenerDiaHabilMesSiguiente(2019, Calendar.APRIL, 5).getTime(), 1, 5, 6);
		createCalendarioDet(7, "05", "2019",
				obtenerDiaHabilMesSiguiente(2019, Calendar.MAY, 5).getTime(), 1, 5, 6);
		createCalendarioDet(7, "06", "2019",
				obtenerDiaHabilMesSiguiente(2019, Calendar.JUNE, 5).getTime(), 1, 5, 6);
		createCalendarioDet(7, "07", "2019",
				obtenerDiaHabilMesSiguiente(2019, Calendar.JULY, 5).getTime(), 1, 5, 6);
		createCalendarioDet(7,  "08", "2019",
				obtenerDiaHabilMesSiguiente(2019, Calendar.AUGUST, 5).getTime(), 1, 5, 6);
		createCalendarioDet(7,  "09", "2019",
				obtenerDiaHabilMesSiguiente(2019, Calendar.SEPTEMBER, 5).getTime(), 1, 5, 6);
		createCalendarioDet(7,  "10", "2019",
				obtenerDiaHabilMesSiguiente(2019, Calendar.OCTOBER, 5).getTime(), 1, 5, 6);
		createCalendarioDet(7, "11", "2019",
				obtenerDiaHabilMesSiguiente(2019, Calendar.NOVEMBER, 5).getTime(), 1, 5, 6);
		createCalendarioDet(7,  "12", "2019",
				obtenerDiaHabilMesSiguiente(2019, Calendar.DECEMBER, 5).getTime(), 1, 5, 6);
		
		createCalendarioDet("2019", 2, 6, 1);*/
		// createCalendarioDet("2018", 2, 6, 1);
		// createCalendarioDet("2017", 2, 6, 1);
		// createCalendarioDet("2016", 2, 6, 1);
		// calendario presentacion

	/*	List<CalendarioDet> det = calendarioDetService.findAll();
		List<CalendarioDetSunat> detSunat = calendarioDetSunatService.findAll();
		det.stream().forEach((f) -> {
			CalendarioDet d = new CalendarioDet();
			d.setSMesPeriodo(f.getSMesPeriodo());
			d.setSAnioPeriodo(f.getSAnioPeriodo());				
			d.setTipoVencimiento(f.getTipoVencimiento());
			Concepto c = new Concepto();
			c.setId(7);//cronograma de presentacion
			d.setConcepto(c);			
			Contrato con=new Contrato();
			con.setId(f.getContrato().getId());
			d.setContrato(con);
			Calendar cal = Calendar.getInstance();
			cal.setTime(f.getDFechaVenc());
			fechaPresentacion(cal, 1, 3,0);
			d.setDFechaVenc(cal.getTime());
			calendarioDetService.save(d);
		});
		detSunat.stream().forEach((f) -> {
			CalendarioDetSunat d = new CalendarioDetSunat();
			d.setSMesPeriodo(f.getSMesPeriodo());
			d.setSAnioPeriodo(f.getSAnioPeriodo());		
			d.setSDigitoRUC(f.getSDigitoRUC());	
			Concepto c = new Concepto();
			c.setId(7);//cronograma de presentacion
			d.setConcepto(c);
			Calendar cal = Calendar.getInstance();
			cal.setTime(f.getDFechaVenc());
			fechaPresentacion(cal, 1, 3,1);
			d.setDFechaVenc(cal.getTime());
			calendarioDetSunatService.save(d);
		});*/
		alreadySetup = true;
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
		AporteTipo aporteTipo = new AporteTipo();
		aporteTipo.setId(id);
		aporteTipo.setSDescripcion(name);
		
		// contribuyente.
		aporteTipoService.save(aporteTipo);

	}
	
	@Transactional
	void createContribuyente(final String name, final String ruc) {
		Contribuyente contribuyente = new Contribuyente();
		contribuyente.setSnombre(name);
		contribuyente.setSruc(ruc);
		// contribuyente.
		contribuyenteService.save(contribuyente);

	}
	@Transactional
	void createTipoVencimiento(String descripcion) {
		TipoVencimiento tipoVencimiento=new TipoVencimiento();
		tipoVencimiento.setSDescripcion(descripcion);
		tipoVencimientoService.save(tipoVencimiento);
	}
	
	@Transactional
	void createContrato(
			Integer codigoContribuyente,
			Integer idCalendario,
			String descripcion,			
			Integer tipoVencimiento) {
		Contrato contrato = new Contrato();
		contrato.setSDescripcion(descripcion);
		
		
		
		Contribuyente cn = new Contribuyente();
		cn.setId(codigoContribuyente);
		contrato.setContribuyente(cn);
		
		TipoPeriodicidad cal=new TipoPeriodicidad();
		cal.setId(idCalendario);
		contrato.setCalendario(cal);
		TipoVencimiento t=new TipoVencimiento();
		t.setId(tipoVencimiento);
		contrato.setTipoVencimiento(t);
		contratoService.save(contrato);
	}

	public Calendar obtenerDiaHabilMesSiguiente(int anio, int mes, int numeroDiasHab) {
		int mesSiguiente = mes + 1;
		Calendar cal = Calendar.getInstance();
		cal.set(anio, mesSiguiente, 1);		
		fechaPresentacion(cal, 1, numeroDiasHab,0);
		
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
		cal.set(anio, mesSiguiente, ultimo);	
		
		return cal;
	}

	public Calendar obtenerUltimoDiaHabilMesSiguiente(int anio, int mes) {
		Calendar cal = obtenerUltimoDiaMesSiguiente(anio, mes);
		fechaPresentacion(cal, -1,-1,0);
		return cal;
	}

	@Transactional
	void createCalendarioDet(int contrato,String mes, String anio, Date fecha,
			int tipoVencimiento, int tipoCalendario, int concepto) {

		TipoPeriodicidadDet calendarioDet = new TipoPeriodicidadDet();
		calendarioDet.setSMesPeriodo(mes);
		calendarioDet.setSAnioPeriodo(anio);
		calendarioDet.setDFechaVenc(fecha);
		TipoVencimiento tipoVencimientox=new TipoVencimiento();
		tipoVencimientox.setId(tipoVencimiento);
		calendarioDet.setTipoVencimiento(tipoVencimientox);
		
		Concepto c = new Concepto();
		c.setId(concepto);
		calendarioDet.setConcepto(c);	
		
		Contrato contra=new Contrato();
		contra.setId(contrato);
		calendarioDet.setContrato(contra);
		
		calendarioDetService.save(calendarioDet);

		
	}

	@Transactional
	Role createRoleIfNotFound(final String name) {
		Role role = roleService.findByName(name);
		if (role == null) {
			role = new Role(name);
			roleService.save(role);
		}
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
			List<Role> userRoles, Integer idContribuyente,int idPerfil) {
		User user  = new User();
			user.setName(name);
			user.setSurname(surname);
			user.setUsername(username);
			user.setPassword(bCryptPasswordEncoder.encode(password));
			user.setEmail(email);
			user.setRoles(userRoles);
			user.setEnabled(true);
			Contribuyente contribuyente = new Contribuyente();
			contribuyente.setId(idContribuyente);
			user.setContribuyente(contribuyente);
			Perfil perfil=new Perfil();
			perfil.setId(idPerfil);
			user.setPerfil(perfil);
			userService.save(user);
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
		TipoRetribucion t=new TipoRetribucion();
		t.setId(idtiporetribucion);
		n.setTipoRetribucion(t);
		
		Contribuyente c=new Contribuyente();
		c.setId(concesionario);
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
	void createCalendario(Integer id,final String name, final int orden) {
		TipoPeriodicidad n = new TipoPeriodicidad();
		n.setId(id);
		n.setSDescripcion(name);
		n.setOrden(orden);
		calendarioService.save(n);

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

	public void fechaPresentacion(Calendar dt, int sumaoresta, int tope,int sunat) {
		int cont = 0;
		int fin = 2 + tope;
		while (cont < fin) {		
			
			int a = dt.get(Calendar.YEAR);
			int m = dt.get(Calendar.MONTH);
			int d = dt.get(Calendar.DATE);
			int ndia = dt.get(Calendar.DAY_OF_WEEK);
			if (!(ndia == Calendar.SUNDAY && ndia == Calendar.SATURDAY)) {
				Feriado f = feriadoService.findByFerAnyoAndFerMesAndFerDia(a, m+1, d);
				if (null == f){cont++;} 
			}
			dt.setTime(variarFecha(dt, Calendar.DATE, sumaoresta).getTime());
			System.out.println(dt.getTime());
		}
	
	}

	public Calendar getFechaVencimiento(int dia, int mes, int anio, int p, int codigoConcepto) {

		Calendar calendario = Calendar.getInstance();
		calendario.set(anio, mes, dia);
		if (codigoConcepto == Constantes.CALENDARIO_PRESENTACION) {
			fechaPresentacion(calendario, p, 3,1);
		}

		return calendario;
	}

	public Calendar variarFecha(Calendar fecha, int campo, int sumaoresta) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha.getTime());
		calendar.add(campo, sumaoresta);
		return calendar;
	}


}