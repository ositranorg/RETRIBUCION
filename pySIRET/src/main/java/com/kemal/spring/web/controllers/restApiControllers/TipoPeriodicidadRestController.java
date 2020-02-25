package com.kemal.spring.web.controllers.restApiControllers;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.kemal.spring.domain.Concesionario;
import com.kemal.spring.domain.TipoPeriodicidad;
import com.kemal.spring.service.TipoPeriodicidadService;
@RestController
@RequestMapping("api/contribuyente")
@Scope("session")
public class TipoPeriodicidadRestController {
	
	
	@Autowired
	TipoPeriodicidadService tipoPeriodicidadService;
	
	
	
	
	@RequestMapping(value = "/getTipoPeriodicidad", method = RequestMethod.GET)
	public @ResponseBody
	List<TipoPeriodicidad> getTags(@RequestParam String tagName) {

		return simulateSearchResult(tagName);

	}

	private List<TipoPeriodicidad> simulateSearchResult(String tagName) {

		List<TipoPeriodicidad> result = new ArrayList<TipoPeriodicidad>();
		List<TipoPeriodicidad> data=tipoPeriodicidadService.findAll();
		// iterate a list and filter by tagName
		for (TipoPeriodicidad tag : data) {
			if (tag.getSDescripcion().contains(tagName)) {
				result.add(tag);
			}
		}

		return result;
	}

}
