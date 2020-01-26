package com.kemal.spring.service;

import java.math.BigDecimal;
import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.kemal.spring.domain.Liquidacion;
import com.kemal.spring.domain.LiquidacionRepository;
import com.kemal.spring.domain.nonentity.ListarLiquidacion;
import com.kemal.spring.domain.procedures.PkgListarLiquidacionMapper;
import com.kemal.spring.domain.procedures.PkgListarLiquidacionMapperFilter;



@Service
public class LiquidacionService {
	@Resource
	LiquidacionRepository liquidacionRepository;
	
	@Autowired
	private	PkgListarLiquidacionMapper pkgListarLiquidacionRepository;
	
	@Resource
	com.kemal.spring.domain.procedures.PRC_LISTAR_LIQUIDACIONRepository PRC_LISTAR_LIQUIDACIONRepository;
	volatile Integer ordenFinal;
	
	@Transactional(readOnly = false)
	public void save(Integer estado, List<Liquidacion> liquidaciones) {
		BigDecimal nOrden = liquidacionRepository.maxOrdenByEstadoDos();
		//System.out.println("ORDEN: " + nOrden);
		ordenFinal = nOrden == null ? 1 :nOrden.intValue() + 1;
		//long esAccionEnviado = liquidaciones.stream().filter(x->x.getSEstado().equals("2")).count();
		if(estado==2) {
			SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
			Date currentDate = new Date(System.currentTimeMillis());
			liquidacionRepository.actualizarOrden(ordenFinal,currentDate);
			//ordenFinal=+1;
		}
		liquidaciones.forEach((liquidacion)->{			
			liquidacion.setId(liquidacionRepository.getNextSeriesId());
			if(estado==1) {
				ordenFinal=0;
			}
			liquidacion.setNOrden(ordenFinal);
			liquidacionRepository.save(liquidacion);	
		});
				
	}
	public List<Liquidacion> listarLiquidaciones(){
		return liquidacionRepository.findAll();
	}
	public Map<String,Object> listarLiquidacion(int pagina,int totalRegistroPagina){
		return PRC_LISTAR_LIQUIDACIONRepository.listarLiquidacion1(pagina,totalRegistroPagina);
		//PRC_LISTAR_LIQUIDACIONFilter filtro = new PRC_LISTAR_LIQUIDACIONFilter();
		//PRC_LISTAR_LIQUIDACION filtro = new PRC_LISTAR_LIQUIDACION();
		
		//return pkgListarLiquidacionRepository.listarLiquidacion2();
	}
	public  List<ListarLiquidacion> listarLiquidacion2() throws Exception{
		//return PRC_LISTAR_LIQUIDACIONRepository.listarLiquidacion();
		//PRC_LISTAR_LIQUIDACIONFilter filtro = new PRC_LISTAR_LIQUIDACIONFilter();
		//PRC_LISTAR_LIQUIDACION filtro = new PRC_LISTAR_LIQUIDACION();
		@SuppressWarnings("unused")
		List<ListarLiquidacion> lista=new ArrayList<ListarLiquidacion>();
		//HashMap<String , Object> lista=null;
		PkgListarLiquidacionMapperFilter filtro = new PkgListarLiquidacionMapperFilter();
		filtro.setPagina(1);
		filtro.setTotalRegistroPagina(5);
		pkgListarLiquidacionRepository.listarLiquidacion(filtro);
		Gson gson = new Gson();
		System.out.println("DAT:" + gson.toJson(filtro.getLista()));
		return filtro.getLista();
	}
}
