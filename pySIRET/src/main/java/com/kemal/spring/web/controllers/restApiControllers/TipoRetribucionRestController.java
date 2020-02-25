package com.kemal.spring.web.controllers.restApiControllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.kemal.spring.domain.TipoPeriodicidad;
import com.kemal.spring.domain.TipoRetribucion;
import com.kemal.spring.service.TipoRetribucionService;
@RestController
@RequestMapping("api/contribuyente")
@Scope("session")
public class TipoRetribucionRestController {
	
	@Autowired
	TipoRetribucionService tipoRetribucionService;
	
	
	
	
	@RequestMapping(value = "/getTipoRetribucion", method = RequestMethod.GET)
	public @ResponseBody
	List<TipoRetribucion> getTags(@RequestParam String tagName) {

		return simulateSearchResult(tagName);

	}

	private List<TipoRetribucion> simulateSearchResult(String tagName) {

		List<TipoRetribucion> result = new ArrayList<TipoRetribucion>();
		List<TipoRetribucion> data=tipoRetribucionService.findAll();
		// iterate a list and filter by tagName
		for (TipoRetribucion tag : data) {
			if (tag.getSDescripcion().contains(tagName)) {
				result.add(tag);
			}
		}

		return result;
	}

}
