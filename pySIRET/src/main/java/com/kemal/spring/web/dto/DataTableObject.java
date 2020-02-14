package com.kemal.spring.web.dto;

import org.springframework.stereotype.Component;

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
	
	
	
	
	
	

		   
		   

}
