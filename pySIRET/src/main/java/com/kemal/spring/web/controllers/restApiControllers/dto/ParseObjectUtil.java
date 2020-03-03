package com.kemal.spring.web.controllers.restApiControllers.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.kemal.spring.domain.AportePorcentaje;
import com.kemal.spring.domain.CondicionBC;
import com.kemal.spring.domain.Moneda;
import com.kemal.spring.domain.TipoPeriodicidad;
import com.kemal.spring.domain.TipoRetribucion;

@Component
public class ParseObjectUtil {
	
	public List<?> parseList(List<?> obj) {
		if(null!=obj&&obj.size()>0) {
			if(obj.get(0) instanceof TipoPeriodicidad ) {
				List<TipoPeriodicidadDto> x=new ArrayList<TipoPeriodicidadDto>();
				for (Object item : obj) {
					TipoPeriodicidad  t=(TipoPeriodicidad)item;
					TipoPeriodicidadDto d=new TipoPeriodicidadDto(t.getId(),t.getSDescripcion());
					x.add(d);
				}
				return x;
			}
			if(obj.get(0) instanceof TipoRetribucion ) {
				List<TipoRetribucionDto> x=new ArrayList<TipoRetribucionDto>();
				for (Object item : obj) {
					TipoRetribucion  t=(TipoRetribucion)item;
					TipoRetribucionDto d=new TipoRetribucionDto(t.getId(),t.getSDescripcion());
					x.add(d);
				}
				return x;
			}
			if(obj.get(0) instanceof Moneda ) {
				List<MonedaDto> x=new ArrayList<MonedaDto>();
				for (Object item : obj) {
					Moneda  t=(Moneda)item;
					MonedaDto d=new MonedaDto(t.getId(),t.getSDescripcion());
					x.add(d);
				}
				return x;
			}
			if(obj.get(0) instanceof AportePorcentaje ) {
				List<AportePorcentajeDto> x=new ArrayList<AportePorcentajeDto>();
				for (Object item : obj) {
					AportePorcentaje  t=(AportePorcentaje)item;
					AportePorcentajeDto d=new AportePorcentajeDto(t.getId(),t.getPorcentaje());
					x.add(d);
				}
				return x;
			}
			if(obj.get(0) instanceof String ) {
				List<AnioDto> x=new ArrayList<AnioDto>();
				for (Object item : obj) {
					String  t=(String)item;
					AnioDto d=new AnioDto(t,t);
					x.add(d);
				}
				return x;
			}
			
		}
		return null;
	}
	public CondicionBCDTO parseObject(CondicionBC x) {
			if(null!=x) {
				CondicionBCDTO d=new CondicionBCDTO(x.getId(),x.getSBuenContribuyente());
				return d;	
			}else {
				return new CondicionBCDTO(-1,"");
			}
			
	}
}
