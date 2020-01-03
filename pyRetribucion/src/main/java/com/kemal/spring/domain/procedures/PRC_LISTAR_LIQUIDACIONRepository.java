package com.kemal.spring.domain.procedures;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import org.springframework.stereotype.Repository;

@Repository
public class PRC_LISTAR_LIQUIDACIONRepository {
	@PersistenceContext
	EntityManager entityManager;

	public List<PRC_LISTAR_LIQUIDACION> listarLiquidacion() {
		StoredProcedureQuery procedureQuery = entityManager
				.createStoredProcedureQuery("PK_RET_LIQUIDACION.PRC_LISTAR_LIQUIDACION");

		procedureQuery.registerStoredProcedureParameter("P_CURSOR", void.class, ParameterMode.REF_CURSOR);
		procedureQuery.execute();
		List<Object[]> results = procedureQuery.getResultList();
		PRC_LISTAR_LIQUIDACION data = new PRC_LISTAR_LIQUIDACION();
		//List<PRC_LISTAR_LIQUIDACION> resultList = procedureQuery.getResultList();
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
			PRC_LISTAR_LIQUIDACION d = new PRC_LISTAR_LIQUIDACION();
		
			d.setnOrden(((BigDecimal) res[0]).intValue());
			d.setnCodigo(((BigDecimal) res[1]).intValue());
			d.setdFechaRegistro((String) res[2]);		
			d.setDFechaModifica((String) res[3]);	
			d.setnAnio(((BigDecimal) res[4]).intValue());
			d.setnIdTipoDocumento(((BigDecimal) res[5]).intValue());
			d.setnPeriodo(((BigDecimal) res[6]).intValue());
			d.setsDocumento((String) res[7]);
			d.setsEstado((String) res[8]);
			
			resultList.add(d);
		}
		return resultList;
	}
}
