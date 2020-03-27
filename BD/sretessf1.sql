--------------------------------------------------------
--  File created - Friday-February-28-2020   
--------------------------------------------------------
DROP TABLE "SRETESSF1"."VW_CREDITOREGISTRADO" cascade constraints;
DROP TABLE "SRETESSF1"."VW_NUEVOCREDITO" cascade constraints;
DROP PACKAGE "SRETESSF1"."PK_SRET_APORTE";
DROP PACKAGE BODY "SRETESSF1"."PK_SRET_APORTE";
--------------------------------------------------------
--  DDL for Table VW_CREDITOREGISTRADO
--------------------------------------------------------

  CREATE TABLE "SRETESSF1"."VW_CREDITOREGISTRADO" 
   (	"NCODIGO" NUMBER(10,0), 
	"ESTADO" VARCHAR2(255 CHAR), 
	"NCODIGO_APORTE" NUMBER(10,0), 
	"NCODIGOCN" NUMBER(10,0), 
	"NCODIGO_ESTADO" NUMBER(10,0), 
	"NIMPORTE" NUMBER(19,2), 
	"SPERIODICIDAD_DESTINO" VARCHAR2(255 CHAR), 
	"SPERIODICIDAD_ORIGEN" VARCHAR2(255 CHAR), 
	"SRETRIBUCION_DESTINO" VARCHAR2(255 CHAR), 
	"SRETRIBUCION_ORIGEN" VARCHAR2(255 CHAR), 
	"SANIOPERIODO_DESTINO" VARCHAR2(255 CHAR), 
	"SANIOPERIODO_ORIGEN" VARCHAR2(255 CHAR), 
	"SMESPERIODO_DESTINO" VARCHAR2(255 CHAR), 
	"SMESPERIODO_ORIGEN" VARCHAR2(255 CHAR)
   ) PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Table VW_NUEVOCREDITO
--------------------------------------------------------

  CREATE TABLE "SRETESSF1"."VW_NUEVOCREDITO" 
   (	"NCODIGO" NUMBER(19,0), 
	"NCODIGOCN" NUMBER(10,0), 
	"NRET_RESULTANTE" NUMBER(19,2), 
	"S_ANIO_PERIODO" VARCHAR2(255 CHAR), 
	"S_MES_PERIODO" VARCHAR2(255 CHAR), 
	"S_TIPO_PERIODICIDAD" NUMBER(10,0), 
	"STIPO_PERIODO_NOM" VARCHAR2(255 CHAR), 
	"S_TIPO_RETRIBUCION" NUMBER(10,0), 
	"STIPO_RETRIBU_NOM" VARCHAR2(255 CHAR)
   ) PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT)
  TABLESPACE "USERS" ;
REM INSERTING into SRETESSF1.VW_CREDITOREGISTRADO
SET DEFINE OFF;
REM INSERTING into SRETESSF1.VW_NUEVOCREDITO
SET DEFINE OFF;
--------------------------------------------------------
--  DDL for Package PK_SRET_APORTE
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "SRETESSF1"."PK_SRET_APORTE" AS

PROCEDURE PRC_TIPODECLARACION (CODIGO_CONSECION       NUMBER,
                                  ANIO_PERIODO           VARCHAR2,
                                  MES_PERIODO            VARCHAR2,                               
                                  TIPODECLARACION    OUT NUMBER);
 FUNCTION FUN_GETFECHAVENCIMIENTOPRE(CODIGO_CONSECION NUMBER,ANIO_PERIODO VARCHAR2,MES_PERIODO VARCHAR2)RETURN DATE;
END PK_SRET_APORTE;

/
--------------------------------------------------------
--  DDL for Package Body PK_SRET_APORTE
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE BODY "SRETESSF1"."PK_SRET_APORTE" AS

PROCEDURE PRC_TIPODECLARACION (CODIGO_CONSECION       NUMBER,
                                  ANIO_PERIODO           VARCHAR2,
                                  MES_PERIODO            VARCHAR2,                               
                                  TIPODECLARACION    OUT NUMBER)
   AS
      CODIGO_PERIODO   NUMBER := 0;
      CONTADOR         NUMBER := 0;
      FECHAVENC        DATE;
      DP               CHAR (1) := '';
      TIPOCALENDARIO NUMBER:=0;
   BEGIN
     

      SELECT COUNT (*) INTO CONTADOR
        FROM SRET_APORTE S
       WHERE  NCODIGOCN = CODIGO_CONSECION
             AND S_ANIO_PERIODO = ANIO_PERIODO
             AND S_MES_PERIODO = MES_PERIODO AND S.S_ESTADO <> 'E';
     BEGIN       
          SELECT ID_TIPO_VENCIMIENTO INTO TIPOCALENDARIO
          FROM SRET_CSNRIOTIPOVEN 
          WHERE ID_CONCESIONARIO=CODIGO_CONSECION
          AND S_ANIO_PERIODO=ANIO_PERIODO;
     EXCEPTION WHEN NO_DATA_FOUND THEN
        TIPOCALENDARIO:=0;
     END ;
     IF TIPOCALENDARIO!=7 THEN
         FECHAVENC :=
         TO_DATE (FUN_GETFECHAVENCIMIENTOPRE(ANIO_PERIODO,MES_PERIODO,CODIGO_CONSECION),'DD/MM/YYYY');        
     ELSE
        FECHAVENC :=
         TO_DATE (SAPREGSF.PK_SAPR_APORTE.FUN_GETFECHAVENCIMIENTOPRE 
                                        (   ANIO_PERIODO,
                                            MES_PERIODO,
                                            CODIGO_CONSECION),'DD/MM/YYYY');
     END IF;
    
     IF ( (FECHAVENC - TO_DATE (TO_CHAR (SYSDATE, 'DD/MM/YYYY'))) >= 0)
      THEN
         DP := '1';
      ELSE
         DP := '0';
      END IF;

      IF DP = '1' AND CONTADOR = 0
      THEN
         TIPODECLARACION := 1;
      ELSIF DP = '1' AND CONTADOR != 0
      THEN
         TIPODECLARACION := 2;
      ELSIF DP = '0' AND CONTADOR = 0
      THEN
         TIPODECLARACION := 1;
      ELSIF DP = '0' AND CONTADOR != 0
      THEN
         TIPODECLARACION := 3;
      END IF;
   EXCEPTION
      WHEN NO_DATA_FOUND
      THEN
         TIPODECLARACION := 1;
   END PRC_TIPODECLARACION;
 FUNCTION FUN_GETFECHAVENCIMIENTOPRE(CODIGO_CONSECION NUMBER,ANIO_PERIODO VARCHAR2,MES_PERIODO VARCHAR2)RETURN DATE
 AS
 SALIDA DATE;
 BEGIN  
    SELECT S.DFECHAVENCPRES
        INTO SALIDA
    FROM SRET_VENCIMIENTO S
    WHERE S.CONCESIONARIO_ID = CODIGO_CONSECION
             AND S.SANIO_PERIODO = ANIO_PERIODO
             AND S.SMES_PERIODO = MES_PERIODO AND S.SESTADO = '1';
    RETURN SALIDA;
    EXCEPTION WHEN NO_DATA_FOUND THEN
    RETURN NULL;
   
 END FUN_GETFECHAVENCIMIENTOPRE;
END PK_SRET_APORTE;

/
--------------------------------------------------------
--  Constraints for Table VW_CREDITOREGISTRADO
--------------------------------------------------------

  ALTER TABLE "SRETESSF1"."VW_CREDITOREGISTRADO" MODIFY ("NCODIGO" NOT NULL ENABLE);
 
  ALTER TABLE "SRETESSF1"."VW_CREDITOREGISTRADO" ADD PRIMARY KEY ("NCODIGO")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT)
  TABLESPACE "USERS"  ENABLE;
--------------------------------------------------------
--  Constraints for Table VW_NUEVOCREDITO
--------------------------------------------------------

  ALTER TABLE "SRETESSF1"."VW_NUEVOCREDITO" MODIFY ("NCODIGO" NOT NULL ENABLE);
 
  ALTER TABLE "SRETESSF1"."VW_NUEVOCREDITO" ADD PRIMARY KEY ("NCODIGO")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT)
  TABLESPACE "USERS"  ENABLE;