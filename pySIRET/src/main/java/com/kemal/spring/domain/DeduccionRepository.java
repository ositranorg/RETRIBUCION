package com.kemal.spring.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface DeduccionRepository extends CrudRepository<Deduccion, Integer> {

public List<Deduccion> findBySEstado(String sEstado);
	
@Query("SELECT "
		+ " u"
		+ " FROM Deduccion u "
		+ " where u.nCodigoCns=:nCodigoCns"
		+ " and u.sEstado = :sEstado order By u.id desc")			
public List<Deduccion> findBySEstadoAndNCodigoCnsx(
		@Param("sEstado")String sEstado,
		@Param("nCodigoCns")Integer nCodigoCns);


public  List<Deduccion> findByNCodigoCns(Integer nCodigoCns);


	@Query("SELECT "
			+ " u"
			+ " FROM Deduccion u  "
			+ " where "
			+ " u.nCodigoCns=:nCodigoCns"
			+ " and u.sEstado>:sEstado  order By u.id desc")
	public Page<Deduccion> getListaDeducciones(			
			@Param("nCodigoCns") Integer nCodigoCns,
			@Param("sEstado") String sEstado,
			Pageable pageable);	
	
	public List<Deduccion> findByNCodigoCnsAndSEstado( Integer nCodigoCns,String sEstado);
	
	
	@Query("SELECT "
			+ " u"
			+ " FROM Deduccion u  "
			+ " inner join u.moneda m"
			+ " where "		
			+ " m.id=:idMoneda"
			+ " and u.nCodigoCns=:nCodigoCns"
			+ " and u.sEstado = :sEstado "
			+ " and u.nSaldo>0 order By u.id desc")
	public List<Deduccion> getListaSaldo(		
			@Param("nCodigoCns") Integer nCodigoCns,
			@Param("idMoneda") Integer idMoneda,
			@Param("sEstado") String sEstado);		

	
	
	@Transactional
	@Modifying
	@Query("update Deduccion a "
			+ "set a.nSaldo=:montoDeduccion,"
			+ "a.sUsuModifica=:psUsuModifica,"
			+ "a.dfecModifica=:pdfecModifica "
			+ "where a.id=:codigoDeduccion")
	public void updateSaldoDeduccion(
			 						@Param("codigoDeduccion") Integer codigoDeduccion,
									@Param("montoDeduccion") BigDecimal montoDeduccion,
									@Param("psUsuModifica") String psUsuModifica,
									@Param("pdfecModifica") Date pdfecModifica
								 	);
	
	
	
	
	
	
	
}
