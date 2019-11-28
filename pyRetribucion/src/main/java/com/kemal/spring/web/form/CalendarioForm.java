/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kemal.spring.web.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 *
 * @author consultor_jti07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CalendarioForm {
    private String tipoCalendario;
    private String anioSel;
}
