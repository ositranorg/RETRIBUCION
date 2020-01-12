package com.kemal.spring.web.dto;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.kemal.spring.domain.Archivo;
import com.kemal.spring.service.ArchivoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Controller
@Scope("session")
public class Uploads {

	@Autowired
	Util util;
	@Autowired
	ArchivoService archivoService;
	
	@PostMapping(value = "/Upload")
	@ResponseBody
	public ArchivoDTO upload(@RequestParam(required = false, name = "subir") String subir,
			@RequestParam(required = false, name = "tipoDocumento") String tipoDocumento,
			MultipartHttpServletRequest request, HttpServletResponse response) {
		String ruta = "";
		System.out.println("subir::" + subir);
		ruta = util.getRuta(subir);
		System.out.println("ruta::" + ruta);

		ArchivoDTO archivo = new ArchivoDTO();
		Iterator<String> itr = request.getFileNames();
		MultipartFile mpf = null;
		while (itr.hasNext()) {
			mpf = request.getFile(itr.next());

			String ext = mpf.getOriginalFilename().substring(mpf.getOriginalFilename().length() - 4,
					mpf.getOriginalFilename().length());
			String renombrar = util.getRandomLetra() + String.valueOf(System.currentTimeMillis())
					+ util.getRandomLetra() + ext;

			archivo.setFileName(renombrar);
			archivo.setFileNameOriginal(mpf.getOriginalFilename());
			//archivo.setFileName(ruta + renombrar);
			archivo.setFileSize(mpf.getSize() / 1024 + " Kb");
			archivo.setFileType(mpf.getContentType());
			archivo.setModulo(subir);
			archivo.setTipoDocumento(tipoDocumento);
			try {
				archivo.setBytes(mpf.getBytes());
				FileCopyUtils.copy(mpf.getBytes(),
						new FileOutputStream(
								(System.getProperty("os.name").toLowerCase().indexOf("win") >= 0 ? "c:" : "") + ruta
										+ renombrar));
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return archivo;
	}

	@GetMapping(value = "/get/{modulo}/{value}")
	@ResponseBody
	public void get(HttpServletResponse response, @PathVariable String modulo, @PathVariable String value) {

		try {
			if (value.indexOf(".pdf") != -1) {
				response.setContentType("application/pdf");
			} else if (value.indexOf(".doc") != -1) {
				response.setContentType("application/msword");
			} else if (value.indexOf(".xls") != -1) {
				response.setContentType("vnd.ms-excel");
			}
			response.setHeader("Expires", "0");
			response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
			response.setHeader("Content-disposition", "attachment; filename=\"" + value + "\"");
			response.setHeader("Accept-Ranges", "bytes");
			String ruta = "";
			System.out.println("modulo::" + modulo);
			ruta = util.getRuta(modulo);
			ruta = (System.getProperty("os.name").toLowerCase().indexOf("win") >= 0 ? "c:" : "") + ruta;

			File reportFile = new File(ruta + value);
			InputStream in = new FileInputStream(reportFile);
			ServletOutputStream out = response.getOutputStream();
			int i = 0;
			while ((i = in.read()) != -1) {
				out.write(i);
			}
			out.flush();
			out.close();
			in.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@PostMapping(value = "/getArchivos")
	@ResponseBody
	public List<Archivo> archivoCondicionbc(@RequestParam(required = false, name = "modulo") String modulo,
			@RequestParam(required = false, name = "tipo") String tipo,
			@RequestParam(required = false, name = "idPadre") Integer idPadre) {
		try {

			return archivoService.findBysEstadoOrderById(modulo.toUpperCase(), idPadre, tipo, "1");

		} catch (final Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@PostMapping(value = "/eliminarArchivo")
	@ResponseBody
	
	
	
	public String deshabilitar(@RequestParam(required = false, name = "idarchivoeliminar") Integer id) {
		String respuesta = "0";
		try {
			archivoService.deshabilitar(id);
			respuesta = "1";

		} catch (final Exception e) {
			e.printStackTrace();
			return respuesta;
		}
		return respuesta;
	}

}
