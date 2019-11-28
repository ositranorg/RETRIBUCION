package com.kemal.spring.bd.store.procedures;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kemal.spring.bd.dto.CreditoDTO;

@Repository
public class SP_Credito {
	private final EntityManager entityManager;

	@Autowired
	public SP_Credito(final EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public List<CreditoDTO> getCreditosLibres() {
		/*
		Session session = entityManager.unwrap(Session.class);
		ProcedureCall call = session
		    .createStoredProcedureCall("PK_RET_SALDOS.PRC_DETSALDOACT");
		     
		call.registerParameter(1,Integer.class,ParameterMode.IN);
		     
		call.registerParameter(2,Class.class,ParameterMode.REF_CURSOR);
		 
		Output output = call.getOutputs().getCurrent();
		List<Object[]> postComments = null;
		if (output.isResultSet()) {
		     postComments = ((ResultSetOutput) output).getResultList();
		}
		*/
		
		StoredProcedureQuery storedProcedureQuery = entityManager
				.createStoredProcedureQuery("PK_RET_SALDOS.PRC_DETSALDOACT");
		storedProcedureQuery.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN);
		storedProcedureQuery.registerStoredProcedureParameter(2, Class.class, ParameterMode.REF_CURSOR);
		
		storedProcedureQuery.setParameter(1, 1);
		  
		storedProcedureQuery.execute();		
		// Obtenemos el resultado del cursos en una lista
		//List<CreditoDTO> results = ;
		List<Object[]> objArr =storedProcedureQuery.getResultList(); 
		// Recorremos la lista con map y devolvemos un List<BusinessObject>
		
		
		 for (int i = 0; i < objArr.size(); i++) {
			 
				System.out.println("x::"+objArr.get(i)[0]);
				CreditoDTO credito =new CreditoDTO();
				credito.setCodigo(String.valueOf(objArr.get(i)[0]));
				
		 }	 
			
		
		 
		 return null;

	}
}
