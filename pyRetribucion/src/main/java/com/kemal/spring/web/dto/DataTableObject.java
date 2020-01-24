package com.kemal.spring.web.dto;

import java.util.List;

import org.springframework.stereotype.Component;

import com.kemal.spring.bd.view.VWNuevoCredito;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Component
public class DataTableObject {

	int recordsTotal=100;

	int recordsFiltered=100;

	int draw=1;
	List<VWNuevoCredito> data;
	
	
	
	
	

		   
		   

}
