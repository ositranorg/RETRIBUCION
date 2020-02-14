package com.kemal.spring.domain.procedures;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import org.hibernate.procedure.ProcedureOutputs;
import org.omg.CORBA.INTERNAL;
import org.springframework.stereotype.Repository;

@Repository
public class PRC_LISTAR_LIQUIDACIONRepository {
	@PersistenceContext
	EntityManager entityManager;

	@SuppressWarnings("unchecked")
	public Map<String,Object> listarLiquidacion1(int pagina,int totalRegistroPagina) {
		StoredProcedureQuery procedureQuery = entityManager
				.createStoredProcedureQuery("PK_RET_LIQUIDACION.PRC_LISTAR_LIQUIDACION_FINAL");

		procedureQuery.registerStoredProcedureParameter("P_PAGINA", int.class, ParameterMode.IN);
		procedureQuery.registerStoredProcedureParameter("P_TOTAL_REGISTROS_POR_PAGINA", int.class, ParameterMode.IN);
		procedureQuery.registerStoredProcedureParameter("P_TOTALREGISTROS", int.class, ParameterMode.OUT);
		procedureQuery.registerStoredProcedureParameter("P_CURSOR", void.class, ParameterMode.REF_CURSOR);
		
		// set input parameter
		procedureQuery.setParameter("P_PAGINA", pagina);
		procedureQuery.setParameter("P_TOTAL_REGISTROS_POR_PAGINA", totalRegistroPagina);
		
		procedureQuery.execute();
		
		Integer  totalRegistros = (Integer) procedureQuery.getOutputParameterValue("P_TOTALREGISTROS");
		//System.out.println("Calculation result: 1.23 + 4 = " + totalRegistros);
		
		@SuppressWarnings("unchecked")
		List<Object[]> results = procedureQuery.getResultList();
		//new PRC_LISTAR_LIQUIDACION();
		//List<PRC_LISTAR_LIQUIDACION> resultList = procedureQuery.getResultList();
		//List<PRC_LISTAR_LIQUIDACION> resultList = new ArrayList<>();
		List<PRC_LISTAR_LIQUIDACION> resultList = new ArrayList<>();
		/*
		for (int i = 0; i < results.size(); i++) {
			PRC_LISTAR_LIQUIDACION d = (PRC_LISTAR_LIQUIDACION) results.get(1)[i];
			resultList.add(d);
		}*/
		for (Object[] res : results) {
			/*
			PRC_LISTAR_LIQUIDACION d =  res;
			resultList.add(d);
			*/
			//System.out.println("INGRESE 1");
			PRC_LISTAR_LIQUIDACION d = new PRC_LISTAR_LIQUIDACION();
			
			d.setNOrden(((BigDecimal) res[0]).intValue());
			d.setNCodigo(((BigDecimal) res[1]).intValue());
			d.setDFechaRegistro((String) res[2]);		
			d.setDFechaModifica((String) res[3]);	
			d.setDHoraRegistro((String) res[4]);		
			d.setDHoraModifica((String) res[5]);	
			d.setNAnio(((BigDecimal) res[6]).intValue());
			d.setNIdTipoDocumento(((BigDecimal) res[7]).intValue());
			d.setNPeriodo(((BigDecimal) res[8]).intValue());
			d.setSDocumento((String) res[9]);
			d.setSEstado((String) res[10]);
			
			resultList.add(d);
		}
		HashMap<String, Object> result = new HashMap<String,Object>();
		result.put("lista", resultList);
		result.put("totalRegistros", totalRegistros);
		return result;
	}
	public List<PRC_LISTAR_LIQUIDACION> listarLiquidacion(){
		StoredProcedureQuery query = entityManager.createStoredProcedureQuery("PK_RET_LIQUIDACION.PRC_LISTAR_LIQUIDACION",
				"ListarLiquidacionResult");		
		query.registerStoredProcedureParameter("P_CURSOR", void.class, ParameterMode.REF_CURSOR);
		query.execute();
		@SuppressWarnings("unchecked")
		List<PRC_LISTAR_LIQUIDACION> result = query.getResultList();
		query.unwrap(ProcedureOutputs.class).release();
		return result;
	}
}
