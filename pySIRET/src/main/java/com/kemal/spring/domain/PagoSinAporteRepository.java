package com.kemal.spring.domain;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface PagoSinAporteRepository extends CrudRepository<PagoSinAporte, Integer> {

	public List<PagoSinAporte> findBySEstadoAndNCodigoCns(String sEstado,Integer nCodigoCns);
	
	
	
	
	
	@Query("SELECT "
			+ " u"
			+ " FROM PagoSinAporte u "
			+ " join u.tipoPeriodicidad t "
			+ " join u.tipoRetribucion r "
			+ " where "
			+ " ((:sTipoPeriodicidad is null) or t.id =:sTipoPeriodicidad)"
			+ " and ((:sTipoRetribucion is null) or r.id =:sTipoRetribucion)"
			+ " and (((:sMesRetribucion is null) or (:sMesRetribucion='')) or u.sMesRetribucion =:sMesRetribucion)"
			+ " and (((:sAnioRetribucion is null) or (:sAnioRetribucion='')) or u.sAnioRetribucion =:sAnioRetribucion)"			
			+ " and ((:dfecPagoVoucherDesde is null) or (u.dfecPagoVoucher>=:dfecPagoVoucherDesde))"
			+ " and ((:dfecPagoVoucherHasta is null) or (u.dfecPagoVoucher<=:dfecPagoVoucherHasta))"			
			+ " and u.nCodigoCns=:nCodigoCns"
			+ " and u.sEstado > :sEstado  order By u.id desc")
	public Page<PagoSinAporte> getListaPagos(
			
			@Param("sTipoPeriodicidad") Integer sTipoPeriodicidad,
			@Param("sTipoRetribucion") Integer sTipoRetribucion,
			@Param("sMesRetribucion") String sMesPeriodo,
			@Param("sAnioRetribucion") String sAnioPeriodo ,
			@Param("dfecPagoVoucherDesde") Date dfecPagoVoucherDesde,
			@Param("dfecPagoVoucherHasta") Date dfecPagoVoucherHasta,			
			@Param("nCodigoCns") Integer nCodigoCns,
			@Param("sEstado") String sEstado,
			Pageable pageable);	
	
	
	@Query("SELECT "
			+ " u"
			+ " FROM PagoSinAporte u  "
			+ " left join u.tipoPeriodicidad t"
			+ " left join u.tipoRetribucion r"
			+ " where "			
			+ " ((:sTipoPeriodicidad is null ) or t.id =:sTipoPeriodicidad)"
			+ " and ((:sTipoRetribucion is null ) or r.id =:sTipoRetribucion)"
			+ " and ((:sMesPeriodo is null or :sMesPeriodo='') or u.sMesRetribucion =:sMesPeriodo)"
			+ " and ((:sAnioPeriodo is null or :sAnioPeriodo='') or u.sAnioRetribucion =:sAnioPeriodo)"		
			
			+ " and u.nCodigoCns=:nCodigoCns"
			+ " and u.sEstado > :sEstado order By u.id desc")
	public List<PagoSinAporte> getListaPago(		
			@Param("sTipoPeriodicidad") Integer sTipoPeriodicidad,
			@Param("sTipoRetribucion") Integer sTipoRetribucion,
			@Param("sMesPeriodo") String sMesPeriodo,
			@Param("sAnioPeriodo") String sAnioPeriodo ,
			@Param("nCodigoCns") Integer nCodigoCns,
			@Param("sEstado") String sEstado);	
	
	
	
}
