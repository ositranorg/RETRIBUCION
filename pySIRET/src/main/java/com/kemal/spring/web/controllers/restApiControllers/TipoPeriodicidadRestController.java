package com.kemal.spring.web.controllers.restApiControllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.kemal.spring.domain.TipoPeriodicidad;
import com.kemal.spring.service.TipoPeriodicidadService;
import com.kemal.spring.web.controllers.restApiControllers.dto.AutoCompleteDto;
@RestController
@RequestMapping("api/tipoperiodicidad")
@Scope("session")
public class TipoPeriodicidadRestController {
	
	
	@Autowired
	TipoPeriodicidadService tipoPeriodicidadService;
	
	
	
	
	@RequestMapping(value = "/getTipoPeriodicidad", method = RequestMethod.GET)
	public ResponseEntity<AutoCompleteDto> getTags(@RequestParam String tagName) {
		AutoCompleteDto m=new AutoCompleteDto();
		m.setListaTPDto(simulateSearchResult(tagName));
		return new ResponseEntity<>(m, HttpStatus.OK);
	}

	private List<TipoPeriodicidad> simulateSearchResult(String tagName) {

		List<TipoPeriodicidad> result = new ArrayList<TipoPeriodicidad>();
		List<TipoPeriodicidad> data=tipoPeriodicidadService.findAll();
		// iterate a list and filter by tagName
		for (TipoPeriodicidad tag : data) {
			if (tag.getSDescripcion().toUpperCase().contains(tagName.toUpperCase())) {
				result.add(tag);
			}
		}

		return result;
	}

}
