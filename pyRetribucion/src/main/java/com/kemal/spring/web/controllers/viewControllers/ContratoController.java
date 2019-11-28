/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kemal.spring.web.controllers.viewControllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.kemal.spring.service.TipoPeriodicidadService;
import com.kemal.spring.web.form.ContratoForm;

/**
 *
 * @author consultor_jti07
 */
@Controller
@Scope("session")
public class ContratoController {

    @Autowired
    TipoPeriodicidadService service;

    @RequestMapping(value = {"/contrato"}, method = RequestMethod.GET)
    public ModelAndView ositran(ModelMap model) {
        ContratoForm form = new ContratoForm();
        ModelAndView view = new ModelAndView("/pages/contrato", "command", form);
      
     
        return view;
    }
}
