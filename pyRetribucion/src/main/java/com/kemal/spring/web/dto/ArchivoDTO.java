package com.kemal.spring.web.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"bytes"})
public class ArchivoDTO {
	
	
    private String fileName;
    private String fileSize;
    private String fileType;
 
    private byte[] bytes;
    private String modulo;
    private Integer codigoPadre;
    private String tipoDocumento;
    
         //setters & getters
}
