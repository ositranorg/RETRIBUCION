package com.kemal.spring.web.dto;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kemal.spring.domain.Archivo;
import com.kemal.spring.domain.TipoPeriodicidadDet;


@Component
public class Util {
	@Autowired
	Config config;
	
	public List<CalendarioDto> fromLstToCalendarioDto(List<?> lst) {
		List<CalendarioDto> salida = new ArrayList<CalendarioDto>();
		lst.stream().forEach(p -> {
			if (p instanceof TipoPeriodicidadDet) {
				TipoPeriodicidadDet f = (TipoPeriodicidadDet) p;
				CalendarioDto to = new CalendarioDto(f.getId(), f.getSMesPeriodo(), f.getDFechaVenc());
				salida.add(to);
			} 

		});

		return salida;
	}

	public int anioActual() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		return cal.get(Calendar.YEAR);
	}

	public List<String> getAnios() {
		int anio = anioActual();
		List<String> anios = new ArrayList<String>();
		for (int i = anio; 2000 < i; i--) {
			anios.add(String.valueOf(i));
		}
		return anios;
	}

	public Date strtoDate(String fecha) {
		if (fecha.length() > 0) {
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			try {
				Date dfecha = formatter.parse(fecha);
				return dfecha;
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		return null;
	}
	public String fomratDate(Date fecha) {
		if (fecha!=null) {
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			try {
				String dfecha = formatter.format(fecha);
				return dfecha;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
    }
	public String quitarComas(String monto) {
		String salida = monto.replaceAll(",", "");
		return salida;
	}

	public BigDecimal strToBigDecimal(String monto) {
		if (monto != null) {
			return new BigDecimal(quitarComas(monto));
		}
		return BigDecimal.ZERO;
	}

	public String getRandomLetra() {

		String[] letters = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r",
				"s", "t", "u", "w", "z", "y", "z" };
		String letra = letters[(int) Math.round(Math.random() * 15)];

		return letra.toUpperCase();
	}

	public String formatMiles(Object obj) {
		if (null != obj && obj.toString().length() > 0) {
			BigDecimal d = new BigDecimal(obj.toString());
			NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
			DecimalFormat df = (DecimalFormat) nf;
			df.applyPattern("###,###.###");
			return df.format(d);
		}
		return "";
	}

	public String getRuta(String modulo) {
		String ruta="";
		if (modulo.equals("fcondicionbc")) {
			ruta = config.getBuencontribuyente();
		} else if (modulo.equals("fliberacionpago")) {
			ruta = config.getLiberacion();
		} else if (modulo.equals("fdescuento")) {
			ruta = config.getOtrosDescuentos();
		} else if (modulo.equals("fdeduccion")) {
			ruta = config.getDeducciones();
		} else if (modulo.equals("fpago")) {
			ruta = config.getPago();
		}else if (modulo.equals("fdictamen")) {
			ruta = config.getPformatoLiquidacion();
		}
		
		return ruta;
	}
    public String quitarultimacomma(String cadena) {
        String salida = "";
        if (cadena != null && cadena.length() > 0) {
            salida = cadena.substring(0, cadena.length() - 1);
        }
        return salida;
    }
	public List<Archivo> lstArchivos(String modulo,String archivos) {
		List<Archivo> lst=new ArrayList<Archivo>();
		if(null!=archivos && archivos.length()>0) {
			String[] archivossc=quitarultimacomma(archivos).split(",");
			for (int i = 0; i < archivossc.length; i++) {
				Archivo archivo=new Archivo();
				archivo.setFileName(archivossc[i]);
				archivo.setModulo(modulo.toUpperCase());
				archivo.setTipoDocumento("1");
				lst.add(archivo);
			}
		}
		
		return lst;
	}
}
