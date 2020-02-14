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

public interface DescuentoRepository extends CrudRepository<Descuento, Integer> {

	@Query("SELECT "
			+ " u"
			+ " FROM Descuento u  "
			+ " where "
			+ " u.nCodigoCns=:nCodigoCns"
			+ " and u.sEstado = :sEstado order By u.id desc")			
	public List<Descuento> findBySEstadoAndNCodigoCnsx(
			@Param("sEstado")String sEstado,
			@Param("nCodigoCns")Integer nCodigoCns);
	
	
	public  List<Descuento> findByNCodigoCns(Integer nCodigoCns);
	
	
	
	
	@Query("SELECT "
			+ " u"
			+ " FROM Descuento u "
			+ " where "
			+ " u.nCodigoCns=:nCodigoCns "
			+ " and u.sEstado > :sEstado order By u.id desc"
			)
	public Page<Descuento> getListaDescuentos(			
			@Param("nCodigoCns") Integer nCodigoCns,
			@Param("sEstado") String sEstado,
			Pageable pageable);	
	

	@Query("SELECT "
			+ " u"
			+ " FROM Descuento u  "
			+ " inner join u.moneda m"
			+ " where "		
			+ " m.id=:idMoneda"
			+ " and u.nCodigoCns=:nCodigoCns"
			+ " and u.sEstado = :sEstado "
			+ " and u.nSaldo>0 order By u.id desc")
	public List<Descuento> getListaSaldo(		
			@Param("nCodigoCns") Integer nCodigoCns,
			@Param("idMoneda") Integer idMoneda,
			@Param("sEstado") String sEstado);	
	
	@Transactional
	@Modifying
	@Query("update Descuento a "
			+ "set a.nSaldo=:montoDescuento,"
			+ "a.sUsuModifica=:psUsuModifica,"
			+ "a.dfecModifica=:pdfecModifica "
			+ "where a.id=:codigoDescuento")
	public void updateSaldoDescuento(
			 						@Param("codigoDescuento") Integer codigoDescuento,
									@Param("montoDescuento") BigDecimal montoDescuento,
									@Param("psUsuModifica") String psUsuModifica,
									@Param("pdfecModifica") Date pdfecModifica
								 	);
	
								 	
	
}
