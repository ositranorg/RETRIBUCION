package com.kemal.spring.service;

import java.util.ArrayList;
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
	
	@Transactional(readOnly = false)
	public void save(List<Liquidacion> liquidaciones) {
			
		liquidaciones.forEach((liquidacion)->{
			liquidacion.setId(liquidacionRepository.getNextSeriesId());
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
