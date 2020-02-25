package com.kemal.spring.web.dto;

import java.math.BigDecimal;
import java.security.spec.AlgorithmParameterSpec;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kemal.spring.domain.Archivo;
import com.kemal.spring.domain.Concesionario;
import com.kemal.spring.domain.User;
import com.kemal.spring.domain.Vencimiento;


@Component
public class Util {
	@Autowired
	Config config;
	
	public List<CalendarioDto> fromLstToCalendarioDto(List<?> lst) {
		List<CalendarioDto> salida = new ArrayList<CalendarioDto>();
		lst.stream().forEach(p -> {
			if (p instanceof Vencimiento) {
				Vencimiento f = (Vencimiento) p;
				CalendarioDto to = new CalendarioDto(f.getId(), f.getSMesPeriodo(), f.getDFechaVenc());
				salida.add(to);
			} 

		});

		return salida;
	}
	public  Integer getConcesionario(User u,int vVar) {
		Concesionario concesionario=null;
		if(u.getPerfil().getId()!=2) {
			concesionario=new Concesionario(vVar);
		}else {
			concesionario=u.getConcesionario();
		}
		return u.getPerfil().getId()!=2?vVar:u.getConcesionario().getId();
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
		}else if (modulo.equals("fFormato")) {
			ruta = config.getPformatoLiquidacion();
		}else if (modulo.equals("pformatoLiquidacion")) {
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
	

	    public String ldapAutenticarExchange(String usuario, String clave) throws NamingException {
//	        System.out.println("SEGURIDAD ldapAutenticarExchange() - INICIO");
	        String LDAPUser = usuario;
	        String LDAPPwd = clave;
	        String sRetorno = "0";

	        Hashtable env = new Hashtable(5);
	        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory"); // Specify the initial context implementation to use.
	        env.put(Context.PROVIDER_URL, "ldap://100.134.1.223:389"); // HOST y PUERTO del EXCHANGE
	        env.put(Context.SECURITY_AUTHENTICATION, "simple"); // specify authentication information
	        env.put(Context.SECURITY_PRINCIPAL, usuario + "@ositranorg.gob.pe");
	        env.put(Context.SECURITY_CREDENTIALS, clave);

	        try {
	        	DirContext   ctx = new InitialDirContext(env); // get a handle to an Initial DirContext
	            sRetorno = "0";
	            System.out.println("CONEXIÓN EXITOSA LDAP-EXCHANGE, AUTENTICACIÓN EXITOSA.");
	        } catch (javax.naming.AuthenticationException e) {
	            sRetorno = mirrorLDAP(env);
	        } catch (NamingException e) {
	            sRetorno = mirrorLDAP(env);
	        }
//	        System.out.println("SEGURIDAD ldapAutenticarExchange() - FIN");
	        return sRetorno;
	    }

	    public String mirrorLDAP(Hashtable env) {
	        String sRetorno = "0";
	        try {
	            env.put(Context.PROVIDER_URL, "ldap://100.134.1.224:389"); // HOST y PUERTO del EXCHANGE           
	            DirContext   ctx = new InitialDirContext(env); // get a handle to an Initial DirContext
	            sRetorno = "0";
	        } catch (javax.naming.AuthenticationException e2) {
	            sRetorno = "1";
	            System.err.println("NO SE PUDO AUTENTICAR LDAP-EXCHANGE 223 Y 224");
	        } catch (NamingException e2) {
	            sRetorno = "2";
	            System.err.println("NO SE ENCONTRÓ EL SERVIDOR LDAP-EXCHANGE 223 Y 224");
	        }
	        return sRetorno;
	    }
	    
	    
		public String decrypt(final String encrypted) throws Exception {

			try {
				SecretKey key = new SecretKeySpec(Base64.decodeBase64("u/Gu5posvwDsXUnV5Zaq4g=="), "AES");
				AlgorithmParameterSpec iv = new IvParameterSpec(Base64.decodeBase64("5D9r9ZVzEYYgha93/aUK2w=="));
//		            SecretKey key = new SecretKeySpec(Base64.decode(sc), "AES");
//		            AlgorithmParameterSpec iv = new IvParameterSpec(Base64.decode(alg));
				byte[] decodeBase64 = Base64.decodeBase64(encrypted);
				Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
				cipher.init(Cipher.DECRYPT_MODE, key, iv);
				return new String(cipher.doFinal(decodeBase64), "UTF-8");
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException("This should not happen in production.", e);
			}
		}
		 public  String encrypt(String value) {
		        try {
		        	byte[] bytes = new byte[16];
		        	bytes=Base64.encodeBase64("u/Gu5posvwDsXUnV5Zaq4g==".getBytes());
		            IvParameterSpec iv = new IvParameterSpec(bytes);
		            SecretKeySpec skeySpec = new SecretKeySpec("u/Gu5posvwDsXUnV5Zaq4g==".getBytes("UTF-8"), "AES");

		            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
		            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
		            byte[] encrypted = cipher.doFinal(value.getBytes());
		            return Base64.encodeBase64String(encrypted);
		        } catch (Exception ex) {
		            ex.printStackTrace();
		        }

		        return null;
		    }
		public String parseDecrypt(final String pwd) {
			try {
				String afterDecrypt = decrypt(pwd);
				byte[] bytes = Hex.decodeHex(afterDecrypt.toCharArray());
				return new String(bytes, "UTF-8");
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException("This should not happen in production.", e);
			}
		}
}
