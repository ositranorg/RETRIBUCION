create or replace PACKAGE PK_RET_LIQUIDACION AS 
  TYPE C_APORTE IS REF CURSOR;
  PROCEDURE PRC_LISTAR_LIQUIDACION (
    P_PAGINA IN NUMBER,
                                  P_TOTAL_REGISTROS_POR_PAGINA IN NUMBER,
  P_TOTALREGISTROS OUT NUMBER,
                                  P_CURSOR OUT C_APORTE);      
END PK_RET_LIQUIDACION;
create or replace PACKAGE BODY PK_RET_LIQUIDACION 
AS
PROCEDURE PRC_LISTAR_LIQUIDACION (
                                  P_PAGINA IN NUMBER,
                                  P_TOTAL_REGISTROS_POR_PAGINA IN NUMBER,
                                  P_TOTALREGISTROS OUT NUMBER,
                                  P_CURSOR OUT C_APORTE)
                                  AS
 BEGIN
 
 SELECT COUNT(*) INTO P_TOTALREGISTROS FROM sret_liquidacion WHERE sestado IN (1,2);
 OPEN P_CURSOR FOR
 select * from (
 SELECT a.*,ROWNUM number_line FROM (
     SELECT * FROM (SELECT    
        L2.NORDEN,
        ncodigo,
        TO_CHAR(dfecharegistro,'DD/MM/YYYY') dfecharegistro,
        TO_CHAR(dfechamodifica,'DD/MM/YYYY') dfechamodifica,
        TO_CHAR(dfecharegistro,'hh:mm:ss') dhoraregistro,
        TO_CHAR(dfechamodifica,'hh:mm:ss') dhoramodifica,
        nanio,
        nidtipodocumento,
        nperiodo,
        sdocumento,
        sestado,
        susumodifica,
        susuregistra
    FROM
        sret_liquidacion L1
        INNER JOIN
        (SELECT ROWNUM AS NORDEN, fecharegistro FROM (    
        SELECT TO_CHAR(dfecharegistro,'DD/MM/YYYY') AS fecharegistro FROM sret_liquidacion
        GROUP BY TO_CHAR(dfecharegistro,'DD/MM/YYYY')
        ORDER BY TO_DATE(TO_CHAR(dfecharegistro,'DD/MM/YYYY'),'DD/MM/YYYY'))) L2 
        ON TO_CHAR(L1.dfecharegistro,'DD/MM/YYYY')=L2.fecharegistro
        WHERE sestado=2
        ORDER BY 1)T
     UNION ALL   
     SELECT * FROM (
        SELECT  0 NORDEN,
        ncodigo,    
         TO_CHAR(dfecharegistro,'DD/MM/YYYY') dfecharegistro,
        TO_CHAR(dfechamodifica,'DD/MM/YYYY') dfechamodifica,
        TO_CHAR(dfecharegistro,'hh:mm:ss') dhoraregistro,
        TO_CHAR(dfechamodifica,'hh:mm:ss') dhoramodifica,
        nanio,
        nidtipodocumento,
        nperiodo,
        sdocumento,
        sestado,
        susumodifica,
        susuregistra
    FROM
        sret_liquidacion WHERE sestado=1
        ORDER BY 2)M
   )a )
   where number_line between (P_PAGINA-1)*P_TOTAL_REGISTROS_POR_PAGINA+1 
   and (P_PAGINA*P_TOTAL_REGISTROS_POR_PAGINA);
 
 END PRC_LISTAR_LIQUIDACION;
END PK_RET_LIQUIDACION;