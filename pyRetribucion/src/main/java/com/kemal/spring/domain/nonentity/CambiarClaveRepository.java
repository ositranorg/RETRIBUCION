package com.kemal.spring.domain.nonentity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import org.hibernate.procedure.ProcedureOutputs;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class CambiarClaveRepository {
	@PersistenceContext
	private EntityManager entityManager;

	public CambiarClave cambiarClave(String claveAnterior,String nuevaClave,String confirmarClave) {
		//List<CambiarClave> list = new ArrayList<>();
		CambiarClave result = null;
		StoredProcedureQuery query = entityManager.createStoredProcedureQuery("PK_USER.PRC_CAMBIARCLAVE",
				"CambiarClaveResult");
		query.registerStoredProcedureParameter("CLAVEANTERIOR", String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter("NUEVACLAVE", String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter("CONFIRMARCLAVE", String.class, ParameterMode.IN);		
		query.registerStoredProcedureParameter("cUsuario", void.class, ParameterMode.REF_CURSOR);
		
		// set input parameter
		query.setParameter("CLAVEANTERIOR", claveAnterior);
		query.setParameter("NUEVACLAVE", nuevaClave);
		query.setParameter("CONFIRMARCLAVE", confirmarClave);
		
		
		// Execute query
		query.execute();
		//list = query.getResultList();
		result = (CambiarClave) query.getSingleResult();
		query.unwrap(ProcedureOutputs.class).release();

		return result;
	}
}
