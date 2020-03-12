package com.kemal.spring.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kemal.spring.domain.Aporte;
import com.kemal.spring.domain.AporteDeduccion;
import com.kemal.spring.domain.AporteDeduccionRepository;
import com.kemal.spring.domain.AporteDescuento;
import com.kemal.spring.domain.AporteDescuentoRepository;
import com.kemal.spring.domain.AporteLiberacion;
import com.kemal.spring.domain.AporteLiberacionRepository;
import com.kemal.spring.domain.AporteRepository;
import com.kemal.spring.domain.AporteEstadoDJ;
import com.kemal.spring.domain.Concesionario;
import com.kemal.spring.domain.Deduccion;
import com.kemal.spring.domain.DeduccionRepository;
import com.kemal.spring.domain.Descuento;
import com.kemal.spring.domain.DescuentoRepository;
import com.kemal.spring.domain.LiberacionPago;
import com.kemal.spring.domain.LiberacionPagoRepository;
import com.kemal.spring.domain.TipoPeriodicidad;
import com.kemal.spring.domain.TipoPeriodicidadRepository;
import com.kemal.spring.domain.TipoRetribucion;
import com.kemal.spring.domain.TipoRetribucionRepository;

@Service
public class AporteService {
	@Autowired
	AporteRepository dao;

	@Autowired
	AporteDeduccionRepository aporteDeduccionRepository;
	@Autowired
	AporteLiberacionRepository aporteLiberacionRepository;
	@Autowired
	AporteDescuentoRepository aporteDescuentoRepository;
	@Autowired
	LiberacionPagoRepository liberacionPagoRepository;
	@Autowired
	DeduccionRepository deduccionRepository;
	@Autowired
	DescuentoRepository descuentoRepository;
	@Autowired
	TipoPeriodicidadRepository tipoPeriodicidadRepository;
	@Autowired
	TipoRetribucionRepository tipoRetribucionRepository;

	public List<Aporte> findBysEstadoOrderById(String sEstado) {
		return dao.findBysEstadoOrderById("1");
	}

	@org.springframework.transaction.annotation.Transactional(readOnly = false)
	public void updateDeduccion(Integer idDeduccion, BigDecimal montoDeduccion, Integer codigoAporte, String usuario) {
		Date d = new Date();
		Deduccion deduccion = deduccionRepository.findById(idDeduccion).get();
		BigDecimal saldo = deduccion.getNSaldo().subtract(montoDeduccion);
		deduccionRepository.updateSaldoDeduccion(idDeduccion, saldo, usuario, d);
		AporteDeduccion l = new AporteDeduccion();
		l.setDeduccion(deduccion);
		l.setNImporteDed(montoDeduccion);
		l.setDfecRegistro(d);
		l.setSUsuRegistra(usuario);
		Aporte a = new Aporte();
		a.setId(codigoAporte);
		l.setAporte(a);
		l.setSEstado("1");
		aporteDeduccionRepository.save(l);
	}

	@org.springframework.transaction.annotation.Transactional(readOnly = false)
	public void updateLiberacion(Integer idliberacion, BigDecimal montoLiberacion, Integer codigoAporte,
			String usuario) {
		Date d = new Date();
		LiberacionPago liberacion = liberacionPagoRepository.findById(idliberacion).get();
		BigDecimal saldo = liberacion.getNSaldo().subtract(montoLiberacion);
		liberacionPagoRepository.updateSaldoLiberacion(idliberacion, saldo, usuario, d);
		AporteLiberacion l = new AporteLiberacion();
		l.setLiberacion(liberacion);
		l.setNImporteLib(montoLiberacion);
		l.setDfecRegistro(d);
		l.setSUsuRegistra(usuario);
		Aporte a = new Aporte();
		a.setId(codigoAporte);
		l.setAporte(a);
		l.setSEstado("1");
		aporteLiberacionRepository.save(l);
	}

	@org.springframework.transaction.annotation.Transactional(readOnly = false)
	public void updateDescuento(Integer iddescuento, BigDecimal montoDescuento, Integer codigoAporte, String usuario) {
		Date d = new Date();
		Descuento deduccion = descuentoRepository.findById(iddescuento).get();
		BigDecimal saldo = deduccion.getNSaldo().subtract(montoDescuento);
		descuentoRepository.updateSaldoDescuento(iddescuento, saldo, usuario, d);
		AporteDescuento l = new AporteDescuento();
		l.setDescuento(deduccion);
		l.setNImporteDes(montoDescuento);
		l.setDfecRegistro(d);
		l.setSUsuRegistra(usuario);
		Aporte a = new Aporte();
		a.setId(codigoAporte);
		l.setAporte(a);
		l.setSEstado("1");
		aporteDescuentoRepository.save(l);
	}

	@org.springframework.transaction.annotation.Transactional(readOnly = false)
	public void eliminarDeduccion(Integer codigoAporte, String usuario) {
		Aporte a = new Aporte();
		a.setId(codigoAporte);
		Date x = new Date();
		List<AporteDeduccion> s = aporteDeduccionRepository.findByAporteAndSEstado(a, "1");

		s.stream().forEach((f) -> {
			f.setSEstado("0");
			f.setSUsuModifica(usuario);
			f.setDfecModifica(x);
			BigDecimal saldo = f.getDeduccion().getNSaldo();
			saldo = saldo.add(f.getNImporteDed());
			f.getDeduccion().setNSaldo(saldo);
		});
		aporteDeduccionRepository.saveAll(s);

	}

	@org.springframework.transaction.annotation.Transactional(readOnly = false)
	public void eliminarDescuento(Integer codigoAporte, String usuario) {
		Aporte a = new Aporte();
		a.setId(codigoAporte);
		Date x = new Date();
		List<AporteDescuento> s = aporteDescuentoRepository.findByAporteAndSEstado(a, "1");
		s.stream().forEach((f) -> {
			f.setSEstado("0");
			f.setSUsuModifica(usuario);
			f.setDfecModifica(x);
			BigDecimal saldo = f.getDescuento().getNSaldo();
			saldo = saldo.add(f.getNImporteDes());
			f.getDescuento().setNSaldo(saldo);
		});
		aporteDescuentoRepository.saveAll(s);
	}

	@org.springframework.transaction.annotation.Transactional(readOnly = false)
	public void eliminarLiberacion(Integer codigoAporte, String usuario) {
		Aporte a = new Aporte();
		a.setId(codigoAporte);
		Date x = new Date();
		List<AporteLiberacion> s = aporteLiberacionRepository.findByAporteAndSEstado(a, "1");
		s.stream().forEach((f) -> {
			f.setSEstado("0");
			f.setSUsuModifica(usuario);
			f.setDfecModifica(x);
			BigDecimal saldo = f.getLiberacion().getNSaldo();
			saldo = saldo.add(f.getNImporteLib());
			f.getLiberacion().setNSaldo(saldo);
		});
		aporteLiberacionRepository.saveAll(s);
	}

	public void save(Aporte aporte) {
		dao.save(aporte);
	}

	public Aporte getAporte(String tipoPeriodicidad, String tipoRetribucion, String SMesPeriodo, String SanioPeriodo,
			Integer csiId) {
		AporteEstadoDJ at = new AporteEstadoDJ();
		at.setId("I");
		at.setSDescripcion("INGRESADO");
		at.setSEstado("1");
		String s = SanioPeriodo.replaceAll(",", "");
		Concesionario c = new Concesionario();
		c.setId(csiId);
		TipoPeriodicidad p = tipoPeriodicidadRepository.findById(new Integer(tipoPeriodicidad)).get();
		TipoRetribucion r = tipoRetribucionRepository.findById(new Integer(tipoRetribucion)).get();
		r.setId(Integer.parseInt(tipoRetribucion));
		Aporte aporte = dao
				.findByTipoPeriodicidadAndTipoRetribucionAndSMesPeriodoAndSAnioPeriodoAndAporteEstadoDJAndContribuyente(p,
						r, SMesPeriodo.replaceAll(",", ""), s, at, c);
		if (null == aporte) {
			Aporte n = new Aporte();
			n.setAporteTipoEstadoDJ(at);
			n.setSMesPeriodo(SMesPeriodo);
			n.setSAnioPeriodo((SanioPeriodo));
			n.setConcesionario(c);
			n.setTipoPeriodicidad(p);
			n.setTipoRetribucion(r);
			dao.save(n);
			return n;
		}
		return aporte;
	}
	
	
	@org.springframework.transaction.annotation.Transactional(readOnly = false)
	public void grabarInteresResultante(Integer codigoAporte,BigDecimal nIntereses,BigDecimal nResultante, String usuario) {
		Aporte a = new Aporte();
		a.setId(codigoAporte);
		Date x = new Date();
		
		Aporte aporte = dao
				.findById(codigoAporte).get();
		if(null!=nIntereses)
		aporte.setNInteres(nIntereses);
		if(null!=nResultante)
		aporte.setNRetribucionResultante(nResultante);
		aporte.setSUsuModifica(usuario);
		aporte.setDfecModifica(x);
		dao.save(aporte);
	}
	@org.springframework.transaction.annotation.Transactional(readOnly = false)
	public void presentar(Integer codigoAporte,String usuario) {
		Aporte a = new Aporte();
		a.setId(codigoAporte);
		Date x = new Date();
		
		Aporte aporte = dao
				.findById(codigoAporte).get();
		AporteEstadoDJ apTipo=new AporteEstadoDJ();
		apTipo.setId("P");
		aporte.setAporteTipoEstadoDJ(apTipo);
		aporte.setSEstado("1");
		
		aporte.setSUsuModifica(usuario);
		aporte.setDfecModifica(x);
		dao.save(aporte);
	}

}
