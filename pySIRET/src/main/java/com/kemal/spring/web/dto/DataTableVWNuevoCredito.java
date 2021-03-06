package com.kemal.spring.web.dto;

import java.util.List;

import org.springframework.stereotype.Component;

import com.kemal.spring.bd.view.VWNuevoCredito;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
@EqualsAndHashCode(callSuper=false)
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Component
public class DataTableVWNuevoCredito extends DataTableObject{
	List<VWNuevoCredito> data;
}
