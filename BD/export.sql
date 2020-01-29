create or replace PACKAGE           PK_RET_APORTE AS 

  /* TODO enter package declarations (types, exceptions, methods etc) here */ 
  TYPE C_APORTE IS REF CURSOR;
  FUNCTION FN_XTIPODECLARACION_MAN RETURN DATE;
   FUNCTION FUN_ULTDIGITO (CODCONSECION IN NUMBER) RETURN CHAR;

      
FUNCTION FUN_CALESPECIALD RETURN CHAR;
     FUNCTION FUN_GETFECHAVENCIMIENTOPAGO (ANIO           IN VARCHAR2,
                                         MES            IN VARCHAR2,                                         
                                        NCODIGOPERIODICIDAD IN NUMBER,
                                        NCODIGORETRIBUCION IN NUMBER,
                                         CODCONSECION   IN NUMBER)
      RETURN VARCHAR2;
FUNCTION FUN_GETFECHAVENCIMIENTOPRE (ANIO           IN VARCHAR2,
                                        MES            IN VARCHAR2,                                        
                                        NCODIGOPERIODICIDAD IN NUMBER,
                                        NCODIGORETRIBUCION IN NUMBER,
                                        CODCONSECION   IN NUMBER)
      RETURN VARCHAR2;
PROCEDURE PRC_TIPODECLARACION (CODIGO_CONSECION       NUMBER,
                                  ANIO_PERIODO           VARCHAR2,
                                  MES_PERIODO            VARCHAR2,
                                  TIPO_RETRIBUCION NUMBER,            
                                  TIPO_PERIODICIDAD NUMBER,
                                  TIPODECLARACION    OUT NUMBER);      
FUNCTION PRC_GETDJ (PMES CHAR,
                    PANIO CHAR,
                    TIPO_RETRIBUCION NUMBER,            
                    TIPO_PERIODICIDAD NUMBER,
                    PCONCESIONARIO NUMBER,
                    CODUSUARIO VARCHAR2)RETURN NUMBER;  
 FUNCTION GRABARDJBORRADOR (PCONCESION       NUMBER,
                              PMES             CHAR,
                              PANIO            CHAR,
                              TIPO_RETRIBUCION NUMBER,            
                              TIPO_PERIODICIDAD NUMBER,
                              CODIGOUSUARIO    VARCHAR2)RETURN NUMBER;
END PK_RET_APORTE;
create or replace PACKAGE BODY           PK_RET_APORTE 
AS
   FUNCTION FN_XTIPODECLARACION_MAN
      RETURN DATE
   AS
      PMES    CHAR (1);
      PANIO   NUMBER;
   BEGIN
      EXECUTE IMMEDIATE 'alter session set nls_date_format =''DD/MM/YYYY'' '; --aumentar esto

      SELECT S_VALOR,S_ANIO
        INTO PMES, PANIO
        FROM SRET_PARAMETRO
       WHERE NCODIGO = 3;

      RETURN TO_DATE (
                (   '01/'
                 || (CASE
                        WHEN TO_NUMBER (PMES) < 10 THEN ('0' || PMES)
                        ELSE PMES
                     END)
                 || '/'
                 || PANIO),
                'DD/MM/YYYY');
   END FN_XTIPODECLARACION_MAN;


   ----
   FUNCTION FUN_ULTDIGITO (CODCONSECION IN NUMBER)
      RETURN CHAR
   IS
      DIGITO           CHAR (1);
      BCONTRIBUYENTE   CHAR (1);
      NUMERORUC        VARCHAR2 (12);
   BEGIN
      SELECT DC.SBUENCONTRIBUYENTE, C.CNC_NRO_DOCUMENTO
        INTO BCONTRIBUYENTE, NUMERORUC
        FROM SRET_BUENCONTRIBUYENTE DC
             INNER JOIN SCINVGSF.T_CONCESIONARIO C
                ON DC.NCODIGOCNS = C.CNC_ID
       WHERE DC.NCODIGOCNS = CODCONSECION AND DC.SESTADO = 'A';

      IF (BCONTRIBUYENTE = 'N')
      THEN
         SELECT SUBSTR (NUMERORUC, 11) INTO DIGITO FROM DUAL;
      ELSE
         DIGITO := NULL;
      END IF;

      RETURN DIGITO;
   END FUN_ULTDIGITO;
   FUNCTION FUN_CALESPECIALD
      RETURN CHAR
   AS
      SSALIDA   CHAR (1) := '0';
   BEGIN
      
         SELECT S_VALOR
           INTO SSALIDA
           FROM SRET_PARAMETRO
          WHERE NCODIGO = 2;
     
      RETURN SSALIDA;
   EXCEPTION
      WHEN NO_DATA_FOUND
      THEN
         RETURN '0';
   END FUN_CALESPECIALD;
FUNCTION FUN_GETFECHAVENCIMIENTOPAGO (ANIO           IN VARCHAR2,
                                         MES            IN VARCHAR2,                                         
                                        NCODIGOPERIODICIDAD IN NUMBER,
                                        NCODIGORETRIBUCION IN NUMBER,
                                         CODCONSECION   IN NUMBER)
      RETURN VARCHAR2
   IS
      FEC_PAGO      VARCHAR2 (12);
      DIGITO        CHAR (1);
      CALESPECIAL   CHAR (1) := FUN_CALESPECIALD;
      FECHAMANUAL   DATE;
   BEGIN
      EXECUTE IMMEDIATE 'alter session set nls_date_format =''DD/MM/YYYY'' '; --aumentar esto

      SELECT FN_XTIPODECLARACION_MAN INTO FECHAMANUAL FROM DUAL;

      IF TO_DATE ('01/' || TO_CHAR(MES) || '/' || TO_CHAR(ANIO), 'DD/MM/YYYY') < FECHAMANUAL
      THEN
         SELECT SUBSTR (
                   (SELECT C.CNC_NRO_DOCUMENTO
                      FROM SRET_BUENCONTRIBUYENTE DC
                           INNER JOIN SCINVGSF.T_CONCESIONARIO C
                              ON DC.NCODIGOCNS = C.CNC_ID
                     WHERE     DC.NCODIGOCNS = CODCONSECION
                           AND DC.SESTADO = 'A'),
                   11)
           INTO DIGITO
           FROM DUAL;


         SELECT TO_CHAR (DFECHAVENC, 'DD/MM/YYYY')
           INTO FEC_PAGO
           FROM SRET_TIPOPERIODICIDADDET
          WHERE     SANIO_PERIODO = ANIO
                AND SMES_PERIODO = MES
                AND N_TIPO_PERIODICIDAD=NCODIGOPERIODICIDAD
                AND N_TIPO_RETRIBUCION=NCODIGORETRIBUCION
                AND SDIGITO_RUC = DIGITO
                AND SESPECIAL = '0';
      ELSE
         SELECT FUN_ULTDIGITO (CODCONSECION) INTO DIGITO FROM DUAL;

         IF DIGITO IS NULL
         THEN
            SELECT TO_CHAR (DFECHAVENC, 'DD/MM/YYYY')
              INTO FEC_PAGO
              FROM SRET_TIPOPERIODICIDADDET
             WHERE     SANIO_PERIODO = ANIO
                   AND SMES_PERIODO = MES
                   AND N_TIPO_PERIODICIDAD=NCODIGOPERIODICIDAD
                   AND N_TIPO_RETRIBUCION=NCODIGORETRIBUCION
                   AND SDIGITO_RUC IS NULL
                   AND SESPECIAL = CALESPECIAL;
         ELSE
            SELECT TO_CHAR (DFECHAVENC, 'DD/MM/YYYY')
              INTO FEC_PAGO
              FROM SRET_TIPOPERIODICIDADDET
             WHERE     SANIO_PERIODO = ANIO
                   AND SMES_PERIODO = MES
                   AND N_TIPO_PERIODICIDAD=NCODIGOPERIODICIDAD
                   AND N_TIPO_RETRIBUCION=NCODIGORETRIBUCION
                   
                   AND SDIGITO_RUC = DIGITO
                   AND SESPECIAL = CALESPECIAL;
         END IF;
      END IF;

      RETURN (FEC_PAGO);
   EXCEPTION
      WHEN NO_DATA_FOUND
      THEN
         RETURN NULL;
   END FUN_GETFECHAVENCIMIENTOPAGO;   
 FUNCTION FUN_GETFECHAVENCIMIENTOPRE (ANIO           IN VARCHAR2,
                                        MES            IN VARCHAR2,
                                        NCODIGOPERIODICIDAD IN NUMBER,
                                        NCODIGORETRIBUCION IN NUMBER,
                                        CODCONSECION   IN NUMBER)
      RETURN VARCHAR2
   IS
      FEC_PAGO      VARCHAR2 (12);
      DIGITO        CHAR (1);
      CALESPECIAL   CHAR (1) := FUN_CALESPECIALD;
      FECHAMANUAL   DATE;
   BEGIN
      SELECT FN_XTIPODECLARACION_MAN INTO FECHAMANUAL FROM DUAL;

      IF TO_DATE ('01/' || MES || '/' || ANIO, 'DD/MM/YYYY') < FECHAMANUAL
      THEN
         SELECT SUBSTR (
                   (SELECT C.CNC_NRO_DOCUMENTO
                      FROM SRET_BUENCONTRIBUYENTE DC
                           INNER JOIN SCINVGSF.T_CONCESIONARIO C
                              ON DC.NCODIGOCNS = C.CNC_ID
                     WHERE     DC.NCODIGOCNS = CODCONSECION
                           AND DC.SESTADO = 'A'),
                   11)
           INTO DIGITO
           FROM DUAL;


         SELECT TO_CHAR (DFECHAVENC, 'DD/MM/YYYY')
           INTO FEC_PAGO
           FROM  SRET_TIPOPERIODICIDADDET
          WHERE     SANIO_PERIODO = ANIO
                AND SMES_PERIODO = MES
                AND N_TIPO_PERIODICIDAD=NCODIGOPERIODICIDAD
                AND N_TIPO_RETRIBUCION=NCODIGORETRIBUCION
                
                
                AND SDIGITO_RUC = DIGITO
                AND SESPECIAL = '0';
      ELSE
         SELECT FUN_ULTDIGITO (CODCONSECION) INTO DIGITO FROM DUAL;

         IF DIGITO IS NULL
         THEN
            SELECT TO_CHAR (DFECHAVENC, 'DD/MM/YYYY')
              INTO FEC_PAGO
              FROM  SRET_TIPOPERIODICIDADDET
             WHERE     SANIO_PERIODO = ANIO
                   AND SMES_PERIODO = MES
                   AND N_TIPO_PERIODICIDAD=NCODIGOPERIODICIDAD
                   AND N_TIPO_RETRIBUCION=NCODIGORETRIBUCION
                   AND SDIGITO_RUC IS NULL
                   AND SESPECIAL = CALESPECIAL;
         ELSE
            SELECT TO_CHAR (DFECHAVENC, 'DD/MM/YYYY')
              INTO FEC_PAGO
              FROM  SRET_TIPOPERIODICIDADDET
             WHERE     SANIO_PERIODO = ANIO
                   AND SMES_PERIODO = MES
                   AND N_TIPO_PERIODICIDAD=NCODIGOPERIODICIDAD
                   AND N_TIPO_RETRIBUCION=NCODIGORETRIBUCION
                   AND SDIGITO_RUC = DIGITO
                   AND SESPECIAL = CALESPECIAL;
         END IF;
      END IF;

      RETURN (FEC_PAGO);
   EXCEPTION
      WHEN NO_DATA_FOUND
      THEN
         RETURN NULL;
   END FUN_GETFECHAVENCIMIENTOPRE;

PROCEDURE PRC_TIPODECLARACION (CODIGO_CONSECION       NUMBER,
                                  ANIO_PERIODO           VARCHAR2,
                                  MES_PERIODO            VARCHAR2,
                                  TIPO_RETRIBUCION NUMBER,            
                                  TIPO_PERIODICIDAD NUMBER,
                                  TIPODECLARACION    OUT NUMBER)
   AS
      CODIGO_PERIODO   NUMBER := 0;
      CONTADOR         NUMBER := 0;
      FECHAVENC        DATE;
      DP               CHAR (1) := '';
   BEGIN
   /*  SELECT COUNT (*)
        INTO CONTADOR
        FROM SRET_APORTE S
       WHERE CONTRIBUYENTE_NCODIGO = CODIGO_CONSECION
             AND S_ANIO_PERIODO = ANIO_PERIODO
             AND S_MES_PERIODO = MES_PERIODO
             AND N_TIPO_RETRIBUCION = TIPO_RETRIBUCION
             AND N_TIPO_PERIODICIDAD = TIPO_PERIODICIDAD
             AND S.S_ESTADO <> 'E';
      
      FECHAVENC :=
         TO_DATE (
            FUN_GETFECHAVENCIMIENTOPRE (ANIO_PERIODO,
                                        MES_PERIODO,TIPO_RETRIBUCION,TIPO_PERIODICIDAD,
                                        CODIGO_CONSECION),
            'DD/MM/YYYY');
*/
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
  FUNCTION PRC_GETDJ (PMES              CHAR,
                       PANIO             CHAR,
                       TIPO_RETRIBUCION NUMBER,            
                        TIPO_PERIODICIDAD NUMBER,                    
                       PCONCESIONARIO    NUMBER,
                       CODUSUARIO        VARCHAR2)
      RETURN NUMBER
   AS
      CODIGO   NUMBER := 0;
   BEGIN
      SELECT MAX(NCODIGO)
        INTO CODIGO
        FROM SRET_APORTE A
      WHERE  a.NCODIGO =
                    (SELECT MAX (B.NCODIGO)
                       FROM SRET_APORTE B
                      WHERE    B.S_ESTADO = 'I')
             AND A.S_MES_PERIODO = PMES
             AND a.S_ANIO_PERIODO = PANIO
             AND a.S_ESTADO = 'I'
             AND a.CONTRIBUYENTE_NCODIGO = PCONCESIONARIO;

      IF CODIGO IS NULL THEN
         CODIGO :=GRABARDJBORRADOR(PCONCESIONARIO,PMES,PANIO,TIPO_RETRIBUCION,TIPO_PERIODICIDAD,CODUSUARIO);
      END IF;

      RETURN CODIGO;
   EXCEPTION
      WHEN NO_DATA_FOUND
      THEN
         RETURN GRABARDJBORRADOR (PCONCESIONARIO,
                                  PMES,
                                  PANIO,
                                  TIPO_RETRIBUCION ,            
                                  TIPO_PERIODICIDAD ,                    
                                  CODUSUARIO);
   END PRC_GETDJ;
   FUNCTION GRABARDJBORRADOR (PCONCESION       NUMBER,
                              PMES             CHAR,
                              PANIO            CHAR,
                              TIPO_RETRIBUCION NUMBER,            
                              TIPO_PERIODICIDAD NUMBER,                    
                              CODIGOUSUARIO    VARCHAR2)
      RETURN NUMBER
   IS
      codigo_periodo   NUMBER := 0;
      codigo           NUMBER := 0;
      FECHAVPAG DATE;
      FECHAVENC DATE;
      EXISTEPERIODO NUMBER:=0;
      TIPODECLARACION NUMBER;
   BEGIN
     BEGIN
       PRC_TIPODECLARACION (PCONCESION,
                                  PANIO,
                                  PMES,
                                  TIPO_RETRIBUCION,            
                                  TIPO_PERIODICIDAD,  
                                  TIPODECLARACION);
     
      
         FECHAVPAG :=TO_DATE (
                           FUN_GETFECHAVENCIMIENTOPAGO(PANIO,
                                                        PMES,
                                                        TIPO_RETRIBUCION,            
                                                        TIPO_PERIODICIDAD,  
                                                        PCONCESION),
                           'DD/MM/YYYY');
         FECHAVENC :=TO_DATE (
                           FUN_GETFECHAVENCIMIENTOPRE(PANIO,
                                                       PMES,
                                                       TIPO_RETRIBUCION,            
                                                       TIPO_PERIODICIDAD,  
                                                       PCONCESION),
                           'DD/MM/YYYY');
    
      --CODIGO:=SEQ_SRET_APORTE.NEXTVAL;
      INSERT INTO SRET_APORTE (NCODIGO,
                               S_ESTADO,
                               NCODIGOAT,
                               SUSUREGISTRA,
                               DFECHAREGISTRO,FECHA_VEN_PRES,FECHA_VEN_PAGO)
           VALUES (CODIGO,
                   'I',
                   TIPODECLARACION,
                   CODIGOUSUARIO,
                   SYSDATE,
                   FECHAVENC,
                   FECHAVPAG);

   

      RETURN CODIGO;
    EXCEPTION WHEN OTHERS THEN
    ROLLBACK;RETURN 0;
    END;
   END GRABARDJBORRADOR;
END PK_RET_APORTE;

create or replace PACKAGE PK_RET_LIQUIDACION AS 
  TYPE C_APORTE IS REF CURSOR;
  PROCEDURE PRC_LISTAR_LIQUIDACION (
    P_PAGINA IN NUMBER,
                                  P_TOTAL_REGISTROS_POR_PAGINA IN NUMBER,
  P_TOTALREGISTROS OUT NUMBER,
                                 P_CURSOR OUT C_APORTE);      
    PROCEDURE PRC_LISTAR_LIQUIDACION_FINAL (
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
        ORDER BY 2 DESC)T
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
        ORDER BY 2 DESC)M
   )a )
   where number_line between (P_PAGINA-1)*P_TOTAL_REGISTROS_POR_PAGINA+1 
   and (P_PAGINA*P_TOTAL_REGISTROS_POR_PAGINA);
 
 END PRC_LISTAR_LIQUIDACION;
 PROCEDURE PRC_LISTAR_LIQUIDACION_FINAL (
                                  P_PAGINA IN NUMBER,
                                  P_TOTAL_REGISTROS_POR_PAGINA IN NUMBER,
                                  P_TOTALREGISTROS OUT NUMBER,
                                  P_CURSOR OUT C_APORTE)
                                  AS
 BEGIN
 
 SELECT COUNT(*) INTO P_TOTALREGISTROS FROM sret_liquidacion WHERE sestado IN (1,2);
 
  OPEN P_CURSOR FOR
 SELECT * FROM(
     SELECT A.*, ROWNUM number_line FROM
     (
     SELECT
        norden norden,
        ncodigo ncodigo,
         TO_CHAR(dfecharegistro,'DD/MM/YYYY') dfechaRegistro,
        TO_CHAR(dfechamodifica,'DD/MM/YYYY') dfechaModifica,
         TO_CHAR(dfecharegistro,'hh:mm:ss') dhoraRegistro,
         TO_CHAR(dfechamodifica,'hh:mm:ss') dhoraModifica,
        nanio nanio,
        nidtipodocumento nidTipoDocumento,
        nperiodo nperiodo,
        sdocumento sdocumento,
        sestado sestado,
        susumodifica,
        susuregistra
    FROM
        sret_liquidacion
        ORDER BY 3,5 DESC
    )A
    )
  where number_line between (P_PAGINA-1)*P_TOTAL_REGISTROS_POR_PAGINA+1 
   and (P_PAGINA*P_TOTAL_REGISTROS_POR_PAGINA);
 
 END PRC_LISTAR_LIQUIDACION_FINAL;
END PK_RET_LIQUIDACION;

create or replace PACKAGE PK_RET_MENU AS 
  TYPE C_MENU IS REF CURSOR;  
  PROCEDURE GETMENU(CODPERFIL NUMBER,MENU OUT VARCHAR2);
END PK_RET_MENU;

create or replace PACKAGE BODY PK_RET_MENU AS     
  PROCEDURE GETMENU(CODPERFIL NUMBER,MENU OUT VARCHAR2) AS

  CURSOR B IS SELECT  NCODSUBMENU CSM, NOMMENU, URLMENU
             FROM  VW_RETMENU 
             WHERE NCODPERFIL=CODPERFIL;
  LIPADRE VARCHAR2(32767):='';
  ULHIJO VARCHAR2(32767):='';
  LIHIJO VARCHAR2(32767):='';
  BEGIN
  MENU:=' <ul class="nav nav-list" >
            <li >
                <a href="index.jsp?exp=Principal&opt=Inicio">
                    <i class="menu-icon fa fa-tachometer"></i>
                    <span class="menu-text"> Inicio </span>
                </a>
                <b class="arrow"></b>
            </li>';
            
  FOR PADRE IN B  LOOP
                   
                        LIPADRE:=LIPADRE||'<li style="padding-bottom: 25px;">';
                                                if PADRE.URLMENU is null then
                                                    LIPADRE:=LIPADRE||'<a href="javascript:void(0)"  class="dropdown-toggle" >';                        
                                                else
                                                   LIPADRE:=LIPADRE||'<a href="'||PADRE.URLMENU||'" >'; 
                                                end if;                      
                                                LIPADRE:=LIPADRE||'<i class="menu-icon fa fa-pencil-square-o"></i><span class="menu-text" style="font-size:10px;">'||PADRE.NOMMENU||'</span>';                      
                                                if PADRE.URLMENU is null then
                                                 LIPADRE:=LIPADRE||' <b class="arrow fa fa-angle-down"></b>';                     
                                                ELSE
                                                  LIPADRE:=LIPADRE||'';
                                                END IF;
                                                LIPADRE:=LIPADRE||'</a>';
                      
                                                ULHIJO:='';
                                                --TIENE HIJOS
                                                IF PADRE.URLMENU IS NULL THEN 
                                                       ULHIJO:='<b class="arrow"></b>
                                                                        <ul class="submenu" >';
                                                                             DECLARE
                                                                              CURSOR HIJO IS SELECT NOMSUBMENU,URLSUBMENU FROM VW_RETSUBMENU  WHERE NCODSUBMENU=PADRE.CSM AND NCODPERFIL=CODPERFIL;
                                                                             BEGIN        
                                                                             LIHIJO:='';
                                                                                   FOR H IN HIJO LOOP
                                                                                     LIHIJO:=LIHIJO||'<li >
                                                                                                                      <a href="'||H.URLSUBMENU||'">
                                                                                                                          <i class="icon-double-angle-right"></i>
                                                                                                                          '||H.NOMSUBMENU||'
                                                                                                                      </a>                              
                                                                                                                      <b class="arrow"></b>
                                                                                                       </li> ';
                                                                                    END LOOP;  
                                                                              ULHIJO:=ULHIJO||LIHIJO||
                                                                            '</ul>';                                              
                                                                            END;
                                                END IF;
                      LIPADRE:=LIPADRE||ULHIJO||'</li>';
  END LOOP;  
  MENU:=MENU||LIPADRE||' </ul>       
        <div class="sidebar-toggle sidebar-collapse" id="sidebar-collapse">
            <i class="ace-icon fa fa-angle-double-left" data-icon1="ace-icon fa fa-angle-double-left" data-icon2="ace-icon fa fa-angle-double-right"></i>
        </div>';
      
  END GETMENU;
END PK_RET_MENU;

create or replace PACKAGE PK_RET_PAGOSA 
AS
TYPE C_PAGOS IS REF  CURSOR;
CHECK_CONSTRAINT_VIOLATED EXCEPTION;
  PRAGMA EXCEPTION_INIT(CHECK_CONSTRAINT_VIOLATED, -2290);
PROCEDURE PRC_INSERTARPAGOSA(
    PNCODINST        NUMBER,
    banco            NUMBER,
    fechaBanco       DATE,
    noperancionBanco VARCHAR2,
    importe          NUMBER ,
    tipoTributo      VARCHAR2,
    nota             VARCHAR2,
    smes             CHAR,
    sanio            CHAR,
    ruta   VARCHAR2
    );      
  
   PROCEDURE PRC_LSTSA(  PNCODINST number,  anio CHAR, MESES CHAR, ESTADO CHAR,CPAGOS IN OUT C_PAGOS);                                    
   PROCEDURE cambiarEstado(PCODPAGOSADET_ID number,PESTADOREG varchar2);
   PROCEDURE GETPERIODOSANT(PNCODINST number,pmes CHAR,panio CHAR,SALIDA IN OUT NUMBER);
   PROCEDURE GETPAGOSPREVIOS(PNCODINST number,pmes CHAR,panio CHAR,SALIDA in out NUMBER);   
   PROCEDURE PRC_LSTSASUP(  PNCODINST number,  anio CHAR, MESES CHAR,CPAGOS IN OUT C_PAGOS);
   PROCEDURE GETESTFECPAGO(PNCODINST number,pmes CHAR,panio CHAR,tipopago varchar2,SALIDA in out VARCHAR2);
   PROCEDURE GETPAGOSINTERES(PNCODINST number,pmes CHAR,panio CHAR,SALIDA in out NUMBER);  
  
   PROCEDURE GETVERIFICAREGPAGO(PNCODINST number,pmes CHAR,panio CHAR,SALIDA in out VARCHAR2);    
   PROCEDURE PRC_GETCONCEPTOS(CPAGOS OUT C_PAGOS);
   /*PROCEDURE PRC_REPORTEPAGO(PNCODINST NUMBER,anio CHAR,MESES CHAR,MULTA CHAR,CPAGOS IN OUT C_PAGOS);*/
   PROCEDURE PRC_REPORTEPAGO(PNCODINST          NUMBER,
                           anio               CHAR,
                           MESES              CHAR,
                           MULTA              CHAR,
                           CODAPORTE          NUMBER,
                           CPAGOS      IN OUT C_PAGOS);
END PK_RET_PAGOSA;


create or replace PACKAGE BODY PK_RET_PAGOSA 
AS
PROCEDURE PRC_INSERTARPAGOSA(
    PNCODINST        NUMBER,
    banco            NUMBER,
    fechaBanco       DATE,
    noperancionBanco VARCHAR2,
    importe          NUMBER ,
    tipoTributo      VARCHAR2,
    nota             VARCHAR2,
    smes             CHAR,
    sanio            CHAR ,
    ruta VARCHAR2)
AS
BEGIN
  
  INSERT
  INTO SRET_PAGOSINAPORTE
    (
      NCODINST,
      NCODIGO_PSAD,
      NCODIGO_BANCO,
      DFECHAREG_PAGO,
      NIMPORTE,
      SNOPERACION_PAGO,
      NCODIGOCP,
      SESTADOSA,
      SNOTA,
      Dfechareg,
      MES,
      ANIO,
      SRUTA
    )
    VALUES
    (
      PNCODINST,
      (SELECT NVL(max(NCODIGO_PSAD),0)+1  FROM SRET_PAGOSINAPORTE),
      banco,
      fechaBanco ,
      importe,
      noperancionBanco ,
      tipoTributo ,
      '1',
      nota,
      SYSDATE,
      SMES,
      SANIO,
      ruta
    );
  COMMIT;
END PRC_INSERTARPAGOSA;
PROCEDURE PRC_LSTSA
  (
    PNCODINST NUMBER,
    anio      CHAR,
    MESES     CHAR,
    ESTADO CHAR,
    CPAGOS IN OUT C_PAGOS
  )
AS
  querystr VARCHAR2
  (
    32767
  )
  :='';
BEGIN
EXECUTE IMMEDIATE 'alter session set nls_date_format =''DD/MM/YYYY'' ';
  querystr     :='Select D.NCODIGO_PSAD,                           
D.Mes,D.Anio,                          
(Select SNOMBRE_BANCO From SRET_Banco where NCODIGO_BANCO= D.Ncodigo_Banco),                           
to_char(D.Dfechareg_Pago,''dd/MM/yyyy''),                           
D.Snoperacion_Pago,                           
D.Nimporte,                                                     
(SELECT C.SDESCRIPCION FROM SRET_CONCEPTOPAGO C WHERE C.NCODIGOCP=D.NCODIGOCP),                                                 
D.Sestadosa,                           
D.Snota,
D.SRUTA,
to_char(D.Dfechareg,''dd/MM/yyyy''),
PK_UTIL_DATE.FN_DIFERECIA(Dfechareg,D.Dfechareg_Pago,''dd/MM/yyyy''),
CASE WHEN D.Dfechareg-D.Dfechareg_Pago>=0 AND D.Dfechareg-D.Dfechareg_Pago<=3 THEN ''DENTRO DEL PLAZO'' ELSE ''FUERA DEL PLAZO'' END,
(SELECT CNC_NOMBRE FROM SCINVGSF.T_CONCESIONARIO WHERE CNC_ID=D.NCODINST)
From  SRET_PAGOSINAPORTE D                     
WHERE 1=1 ';
 IF ESTADO  !='-1' THEN
    querystr:=querystr||' and D.SESTADOSA in (''1'',''2'')';
  END IF;
  IF PNCODINST !=-1 THEN
    querystr   :=querystr||' and D.NCODINST='||PNCODINST||'';
  END IF;
  IF anio   !='-1' THEN
    querystr:=querystr||' and D.ANIO='''||anio||'''';
  END IF;
  IF MESES  !='-1' THEN
    querystr:=querystr||' and D.Mes='''||MESES||'''';
  END IF;
  querystr:=querystr||' order by D.Mes,D.Anio';
  OPEN CPAGOS FOR querystr;
 
END PRC_LSTSA;

---------------------------------------
PROCEDURE cambiarEstado
  (
    PCODPAGOSADET_ID NUMBER,
    PESTADOREG       VARCHAR2
  )
AS
BEGIN
  UPDATE SRET_PAGOSINAPORTE
  SET SESTADOSA      =PESTADOREG
  WHERE NCODIGO_PSAD =PCODPAGOSADET_ID;
END cambiarEstado;
PROCEDURE GETPERIODOSANT(
    PNCODINST NUMBER,
    pmes      CHAR,
    panio     CHAR,
    SALIDA IN OUT NUMBER)
AS
BEGIN
  SELECT SUM(NVL(NCONSUMO*-1,0))
  INTO SALIDA
  FROM SRET_CONSUMOSALDO
  WHERE NCODIGO_CONCESIONARIO=PNCODINST
  AND MES                    =pmes
  AND ANIO                   =panio
  AND SESTADO               IN ('1','2');
END GETPERIODOSANT;
PROCEDURE GETPAGOSPREVIOS(
    PNCODINST NUMBER,
    pmes      CHAR,
    panio     CHAR,
    salida IN OUT NUMBER)
AS
fecVenPago DATE;
BEGIN
  SELECT TO_DATE(Pk_RET_Aporte.Fun_Getfechavencimientopago(panio,pmes,PNCODINST),'dd/MM/yyyy')
  INTO fecVenPago
  FROM dual;
  SELECT NVL(SUM(
                CASE
                  WHEN NVL((D.DFECHAREG_PAGO - fecVenPago),0)<=0
                  THEN D.Nimporte
                  ELSE 0
                END),0)
  INTO salida
  FROM SRET_PAGOSINAPORTE D
  WHERE D.NCODINST =PNCODINST
  AND D.MES        =pmes
  AND D.anio       =panio
  AND D.Sestadosa IN ('1','2')
  --AND D.NCODIGOCP = 1
  ;
END GETPAGOSPREVIOS;

PROCEDURE PRC_LSTSASUP(
    PNCODINST NUMBER,
    anio      CHAR,
    MESES     CHAR,
    CPAGOS IN OUT C_PAGOS)
AS
  querystr VARCHAR2(32767):='';
BEGIN
  querystr     :='Select D.NCODIGO_PSAD,                           
D.Mes,D.Anio,                          
(Select SNOMBRE_BANCO From SRET_Banco where NCODIGO_BANCO= D.Ncodigo_Banco),                           
D.Dfechareg_Pago,                           
D.Snoperacion_Pago,                           
D.Nimporte,                           
D.Sruta,                           
D.Stipotributo,                           
                          
D.Sestadosa,                           
D.Snota                                             
From  SRET_PAGOSINAPORTE D                     
WHERE D.Sestadosa IN (''1'',''2'')';
  IF PNCODINST !=-1 THEN
    querystr   :=querystr||' and D.NCODINST='||PNCODINST||''; 
  END IF;
  IF anio   !='-1' THEN
    querystr:=querystr||' and D.ANIO='''||anio||'''';
  END IF;
  IF MESES  !='-1' THEN
    querystr:=querystr||' and D.Mes='''||MESES||'''';
  END IF;
  querystr:=querystr||' order by D.DFECHAREG_PAGO DESC,D.Dfechareg desc';
  OPEN CPAGOS FOR querystr;
END PRC_LSTSASUP;
PROCEDURE GETESTFECPAGO(
    PNCODINST NUMBER,
    pmes      CHAR,
    panio     CHAR,
    tipopago  VARCHAR2,
    SALIDA IN OUT VARCHAR2)
AS
BEGIN
  NULL;
END GETESTFECPAGO;
PROCEDURE GETPAGOSINTERES(PNCODINST number,pmes CHAR,panio CHAR,SALIDA in out NUMBER)AS
BEGIN
  SELECT NVL(SUM(D.Nimporte),0)
  INTO salida
  FROM SRET_PAGOSINAPORTE D
  WHERE D.NCODINST =PNCODINST
  AND D.MES        =pmes
  AND D.anio       =panio
  AND D.Sestadosa IN ('1','2')
  AND D.NCODIGOCP = 2;
  END;
  PROCEDURE GETVERIFICAREGPAGO(PNCODINST number,pmes CHAR,panio CHAR,SALIDA in out VARCHAR2)as
  CONTADOR NUMBER;
  BEGIN
      SELECT COUNT(D.Nimporte)
      INTO CONTADOR
      FROM SRET_PAGOSINAPORTE D
      WHERE D.NCODINST =PNCODINST
      AND D.MES        =pmes
      AND D.anio       =panio
      AND D.Sestadosa IN ('1','2')
      AND D.NCODIGOCP IN (1,2);
      IF CONTADOR=0 THEN
        SALIDA:='-1';
      ELSE
        SALIDA:='1';
      END IF;
      EXCEPTION WHEN NO_DATA_FOUND THEN
        SALIDA:='-1';
  END;
  PROCEDURE PRC_GETCONCEPTOS(CPAGOS OUT C_PAGOS) AS  
  BEGIN      
  OPEN CPAGOS FOR SELECT  NCODIGOCP,
                      SDESCRIPCION,
                      SESTADO FROM VW_RETCONCEPTOPAGO;
  END PRC_GETCONCEPTOS;
  
  PROCEDURE PRC_REPORTEPAGO (PNCODINST          NUMBER,
                           anio               CHAR,
                           MESES              CHAR,
                           MULTA              CHAR,
                           CODAPORTE          NUMBER,
                           CPAGOS      IN OUT C_PAGOS)
AS
   querystr   VARCHAR2 (32767) := '';
BEGIN
   EXECUTE IMMEDIATE 'alter session set nls_date_format =''DD/MM/YYYY'' ';

   IF MULTA != '-1'
   THEN
      querystr :=
         'SELECT 
                      M.M1,
                      M.M2,
                      M.M3,
                      M.M4,                           
                      M.M5,                           
                      M.M6,                           
                      M.M7,                                                     
                      M.M8,                                                 
                      M.M9,                           
                      M.M10,
                      M.M11,
                      M.M12,
                      '''' ,
                      '''' ,
                      M.M13,
                      M.M14,
                      '''',
                      '''',
                      M.M15,
                      M.M16,
                      M.M17,
                      M.M18,
                      M.M19,
                      M.M20
                      FROM VW_RETREPORTEMULTA M
                      WHERE M.M9 is not null AND M.M20 IN (3,4,5,6) ';

      IF PNCODINST != -1
      THEN
         querystr := querystr || ' and M.M21=' || PNCODINST || '';
      END IF;

      IF anio != '-1'
      THEN
         querystr := querystr || ' and M.M2=''' || ANIO || '''';
      END IF;

      IF MESES != '-1'
      THEN
         querystr := querystr || ' and M.M3=''' || MESES || '''';
      END IF;
   ELSE
      querystr := 'SELECT 
                       X1,
                       X2,
                       X3,
                       X4,
                       X5,
                       X6,
                       X7,
                       X8,
                       X9,
                       X10,
                       X11,
                       X12,
                       X13,
                       X14,
                       X15,
                       X16,
                       X17,
                       X18,
                       X19,
                       X20,
                       X21,
                       X22,
                       X23
                      FROM VW_RETREPORTEPAGO A 
            WHERE A.X9<>''A'' AND A.X27<>-1 ';

      IF PNCODINST != -1
      THEN
         querystr := querystr || ' and A.X26=' || PNCODINST || '';
      END IF;

      IF MESES != '-1'
      THEN
         querystr := querystr || ' and A.X2=''' || MESES || '''';
      END IF;

      IF anio != '-1'
      THEN
         querystr := querystr || ' and A.X3=''' || ANIO || '''';
      END IF;

      IF CODAPORTE != '-1'
      THEN
         querystr := querystr || ' and A.X28<=''' || CODAPORTE || '''';
      END IF;

      querystr := querystr || ' and A.X25 IN(1,2) ';

      querystr := querystr || ' order by A.X5,A.X17,A.X18';
   END IF;

   OPEN CPAGOS FOR querystr;
END PRC_REPORTEPAGO;
END PK_RET_PAGOSA;

create or replace PACKAGE           PK_RET_SALDOS AS
 TYPE C_PAGOS IS REF CURSOR;

PROCEDURE PRC_DETSALDOACT(PCONCESIONARIO IN NUMBER,PPAGE NUMBER,CPAGOS IN OUT C_PAGOS);

END PK_RET_SALDOS;

create or replace PACKAGE BODY           PK_RET_SALDOS 
AS

PROCEDURE PRC_DETSALDOACT(PCONCESIONARIO IN NUMBER,PPAGE NUMBER,CPAGOS IN OUT C_PAGOS)
AS
INI NUMBER:=0;
FIN NUMBER:=0;
PTOTAL NUMBER:=0;
NUMEROPAGINA NUMBER:=0;
PAGINADOR NUMBER:=5;
TOTALPAGESW NUMBER:=0;
PMOD NUMBER:=0;
RES NUMBER:=0;
BEGIN
SELECT COUNT(*) INTO PTOTAL
FROM VW_DECLARACION A 
LEFT JOIN SRET_CREDITO S 
ON S.NCODIGO_APORTE=A.NCODIGO AND S.SESTADO IN ('2') 
WHERE A.NCODIGOCN=PCONCESIONARIO 
and a.NRET_RESULTANTE<0;

IF PPAGE IS NULL THEN
    INI:=0;
    NUMEROPAGINA:=0;
ELSE
    NUMEROPAGINA:=PPAGE;
    INI:=(NUMEROPAGINA*PAGINADOR)-(PAGINADOR-1);
END  IF;

IF (NUMEROPAGINA*PAGINADOR)=0 THEN
  FIN:=PAGINADOR;
ELSE
  FIN:=NUMEROPAGINA*PAGINADOR;
END IF;
IF PTOTAL>1	THEN
      SELECT MOD(PTOTAL,PAGINADOR) INTO PMOD FROM DUAL;
      IF PMOD!=0 THEN
        RES:=1;
      ELSE
        RES:=0;
      END IF ;
  TOTALPAGESW:=(PTOTAL/PAGINADOR)+RES;
ELSE 
  TOTALPAGESW:=1;
END IF;
IF PPAGE IS NOT NULL AND PPAGE=TOTALPAGESW THEN
  FIN:=PTOTAL;
END IF;
--SALIDA
--PTOTALPAGESW:=TOTALPAGESW;
OPEN CPAGOS FOR 
  SELECT 
  X.S_TIPO_PERIODICIDAD PERIODO,
  X.S_TIPO_RETRIBUCION TIPORETRIBUCION,
  X.S_ANIO_PERIODO ANIO,
  X.S_MES_PERIODO MES,
  X.NRET_RESULTANTE  NMONTO ,
 X.RN      
  FROM (
  
  SELECT 
  A.S_TIPO_PERIODICIDAD ,
  A.S_TIPO_RETRIBUCION ,
  A.S_ANIO_PERIODO ,
  A.S_MES_PERIODO ,
  a.NRET_RESULTANTE  ,
  ROW_NUMBER () OVER (ORDER BY 4,3 DESC) RN      
  FROM VW_DECLARACION A 
  LEFT JOIN SRET_CREDITO S 
  ON S.NCODIGO_APORTE=A.NCODIGO AND S.SESTADO IN ('2') 
  WHERE A.NCODIGOCN=PCONCESIONARIO 
  and a.NRET_RESULTANTE<0
  order by A.S_ANIO_PERIODO DESC,
  A.S_MES_PERIODO DESC)X
  WHERE X.rn BETWEEN INI AND FIN;
  
  
  
 
  
END PRC_DETSALDOACT;

END PK_RET_SALDOS;

create or replace PACKAGE PK_USER AS 

  /* TODO enter package declarations (types, exceptions, methods etc) here */ 
  TYPE C_USUARIO IS REF CURSOR;
  PROCEDURE PRC_CAMBIARCLAVE (IDUSUARIO IN NUMBER, CLAVEANTERIOR IN VARCHAR2, 
  NUEVACLAVE IN VARCHAR2, CONFIRMARCLAVE IN VARCHAR2, cUsuario OUT C_USUARIO);
  PROCEDURE PRC_ENVIAR_CLAVE(IDENTIDAD_PRESTADORA IN NUMBER, CORREO IN VARCHAR2,CLAVE_DECODE IN VARCHAR2,
        CLAVE_ENCODE IN VARCHAR2, cUsuario OUT C_USUARIO);
END PK_USER;

create or replace PACKAGE BODY PK_USER AS
  PROCEDURE PRC_CAMBIARCLAVE ( 
        IDUSUARIO IN NUMBER,
        CLAVEANTERIOR IN VARCHAR2,
        NUEVACLAVE IN VARCHAR2, CONFIRMARCLAVE IN VARCHAR2, cUsuario OUT C_USUARIO)
AS
 v_esCorrectoClaveAnterior number;

BEGIN
--SELECT COUNT(*) FROM user_app WHERE ID=2 AND PASSWORD='$1a$10$0NmJY/DokERlDqE4nWDhVuf0Y.B8BnoDW2gRrzFdzvc1dqNVEek7C';
IF NUEVACLAVE<>CONFIRMARCLAVE THEN
    OPEN cUsuario FOR
    SELECT 0 RESULTADO,'LA NUEVA CLAVE NO COINCIDE CON CONFIRMAR CLAVE.' MENSAJE FROM DUAL;    
ELSE

    select count(*)into v_esCorrectoClaveAnterior from USER_APP 
    where id=IDUSUARIO and PASSWORD = CLAVEANTERIOR; 
    if v_esCorrectoClaveAnterior = 0 then
        OPEN cUsuario FOR
        SELECT 0 RESULTADO,'La clave actual no es correcta.' MENSAJE FROM DUAL;
        RETURN;
    end if;
    
    update USER_APP set
    PASSWORD=CONFIRMARCLAVE
    where ID=IDUSUARIO;
    commit;
    
    OPEN cUsuario FOR
    SELECT 1 RESULTADO,'TODO CORRECTO.' MENSAJE FROM DUAL;
END IF;

END PRC_CAMBIARCLAVE;
  PROCEDURE PRC_ENVIAR_CLAVE(IDENTIDAD_PRESTADORA IN NUMBER, CORREO IN VARCHAR2,CLAVE_DECODE IN VARCHAR2,
        CLAVE_ENCODE IN VARCHAR2, cUsuario OUT C_USUARIO)
AS
    v_existeUsuario number;
    v_idUsuario number;
    contribuyente varchar2(200);
BEGIN
    select count(*)into v_existeUsuario from USER_APP WHERE EMAIL = CORREO and NCODIGOCNT=IDENTIDAD_PRESTADORA;
    if v_existeUsuario=0 then
        OPEN cUsuario FOR
        SELECT 0 RESULTADO,'El correo ingresado no está registrado.' MENSAJE FROM DUAL;
        return;
    else
        select id into v_idUsuario from USER_APP WHERE EMAIL = CORREO and NCODIGOCNT=IDENTIDAD_PRESTADORA;
        update USER_APP set PASSWORD=CLAVE_ENCODE,
        estado_correo=0
        where id = v_idUsuario
        ;
        commit;
        SELECT SNOMBRE into contribuyente FROM SRET_CONTRIBUYENTE where NCODIGO=IDENTIDAD_PRESTADORA and SESTADO='1';
       alertas.PK_ALERTAS.SIRET_CAMBIO_CLAVE (contribuyente,CLAVE_DECODE,CORREO);
       
   
        OPEN cUsuario FOR
        SELECT 1 RESULTADO,'Se ha enviado la nueva clave a su correo.' MENSAJE FROM DUAL;
       -- return;
    end if;
    

END PRC_ENVIAR_CLAVE;
END pk_user;

create or replace PACKAGE PK_USUARIO AS 

  /* TODO enter package declarations (types, exceptions, methods etc) here */ 
  TYPE C_USUARIO IS REF CURSOR;
  
  PROCEDURE PRC_INSERTAR(EMPRESA_USUARIO IN VARCHAR2,
                           LOGIN IN VARCHAR2,  
                           CLAVE IN VARCHAR2,
                           CODPERFIL IN NUMBER,
                           INSTITUCION IN NUMBER,
                           SRUA IN VARCHAR2,
                           SCODIGOUDUARIO OUT NUMBER) ; 
  
  PROCEDURE PRC_GETUSUARIOBYLOGIN(LOGIN VARCHAR2,PWDENC VARCHAR2,cUsuario IN OUT C_USUARIO);
  
  PROCEDURE PRC_VALIDAUSUARIO ( CODUSUARIO VARCHAR2, CANTIDAD IN OUT NUMBER );
  
 
  
  PROCEDURE PRC_LOGINCONSECION ( LOGIN VARCHAR2, CLAVE VARCHAR2, cUsuario IN OUT C_USUARIO);
  PROCEDURE PRC_GET0CONSECION ( CODCONCESION NUMBER, cUsuario IN OUT C_USUARIO);
 
  PROCEDURE PRC_CAMBIARCLAVE ( codusuario NUMBER,clave VARCHAR2);
  PROCEDURE PRC_GETUSUARIOBYNCODIGOUS ( CODIGOUSUARIO NUMBER, cUsuario IN OUT C_USUARIO);
  PROCEDURE PRC_GETUSUARIOBYRUC ( RUC VARCHAR2, cUsuario IN OUT C_USUARIO);
  PROCEDURE PRC_GETREPRESENTANTE ( CODSOLICITUD NUMBER, cUsuario IN OUT C_USUARIO);
  PROCEDURE PRC_GETREPXCONCESION ( CONCESION NUMBER, cUsuario IN OUT C_USUARIO);
  PROCEDURE PRC_GRABARUSUARIO ( PCODSOUSUARIO NUMBER,PEMPRESA VARCHAR2,PLOGIN VARCHAR2,PCLAVE VARCHAR2,PCODCONCESION VARCHAR2,PRUC VARCHAR2,PCORREO VARCHAR2,PTELEFONO VARCHAR2,PCREARUSUARIO VARCHAR2,PUSUREGMOD VARCHAR2, MSG OUT NUMBER);
  FUNCTION PRC_UPDANTREP(CODIGO NUMBER)RETURN VARCHAR2;
  PROCEDURE PRC_INSERTARREP(
      REP_TIPODOC    IN VARCHAR2,
      REP_NUMDOC     IN VARCHAR2,      
      REP_NOMBRE     IN VARCHAR2,
      REP_APEPAT     IN VARCHAR2,
      REP_APEMAT     IN VARCHAR2,
      REP_CARGO     IN VARCHAR2,
      REP_FECPODER  IN DATE,      
      ARCHIVOS VARCHAR2,
      CODSOLICITUD IN NUMBER,
      PUSUARIOREG IN VARCHAR2);
  PROCEDURE PRC_SEPARARARCHIVO(PTIPO CHAR,PPADRE VARCHAR2,CODIGO NUMBER,PCADENA VARCHAR2);
  PROCEDURE PRC_INSERTAARCHIVO(PTIPO CHAR,PPADRE VARCHAR2,CODIGO NUMBER,PARCHIVO VARCHAR2);
  PROCEDURE PRC_ENCADENARCHIVO(PTIPO CHAR,PPADRE VARCHAR2,CODIGO NUMBER,PARCHIVO VARCHAR2,EXISTE OUT CHAR);
  PROCEDURE PRC_GRABARARCHIVO(PPADRE VARCHAR2,PTIPO CHAR,CODIGO NUMBER,PARCHIVO VARCHAR2);
  PROCEDURE PRC_GETSOLARCHIVO(CODIGO NUMBER,PPADRE VARCHAR2, cUsuario IN OUT C_USUARIO, cUsuario2 IN OUT C_USUARIO, cUsuario3 IN OUT C_USUARIO);
  PROCEDURE PRC_GRABARARCHSO ( NCODIGO NUMBER, NOMARCH VARCHAR2)  ;
  PROCEDURE PRC_GETARCHIVOS(PPADRE VARCHAR2,CODIGOREP NUMBER,PTIPOARCHIVO CHAR,cUsuario IN OUT C_USUARIO);
  PROCEDURE PRC_BUSCARXCORREO(CORREO VARCHAR2,NOMCONTRIBUYENTE OUT VARCHAR2,IDCONTRI OUT NUMBER);
  PROCEDURE PRC_ACTCLAVE(CLAVE VARCHAR2,PCOD_INSTITUCION number);
  PROCEDURE PRC_INSERTARDETALLE(LOGIN IN VARCHAR2,INSTITUCION IN NUMBER,SRUA IN VARCHAR2);
  PROCEDURE PRC_BUSCARXIDCONCESION(PCOD_INSTITUCION NUMBER,CORREO VARCHAR2,NOMCONTRIBUYENTE OUT VARCHAR2);
  PROCEDURE PRC_UPDDOMICILIOFISCAL(PCOD_INSTITUCION NUMBER,DOMICILIO VARCHAR2,LOGUSUARIO VARCHAR2);
  PROCEDURE PRC_UPDATE_ELIMINARARCH (CODIGOAPORTE    NUMBER,
                                   NOMBREARCH      VARCHAR2,
                                   NOMBREPADRE     VARCHAR2);
PROCEDURE PRC_INSERTARPAGINALOG(PNCONCESION NUMBER,PSLOGIN VARCHAR2,PSPAGINA VARCHAR2);  
 PROCEDURE PRC_CAMBIARCLAVE ( CLAVEANTERIOR VARCHAR2, NUEVACLAVE VARCHAR2, CONFIRMARCLAVE VARCHAR2, cUsuario IN OUT C_USUARIO);
END PK_USUARIO;

create or replace PACKAGE BODY PK_USUARIO AS
  
  
-----------------------------------------
PROCEDURE PRC_INSERTAR(EMPRESA_USUARIO IN VARCHAR2,
                           LOGIN IN VARCHAR2,  
                           CLAVE IN VARCHAR2,
                           CODPERFIL IN NUMBER,
                           INSTITUCION IN NUMBER,
                           SRUA IN VARCHAR2,
                           SCODIGOUDUARIO OUT NUMBER) AS         
  IDCODIGO NUMBER:=0;
  BEGIN
    UPDATE SRET_USUARIO SET SESTADO='0' WHERE  STIPO_USUARIO='E' AND COD_INSTITUCION=INSTITUCION;
    COMMIT;
    BEGIN
      SELECT NVL(MAX(NCODIGO_USUARIO),0)+1 INTO IDCODIGO FROM SRET_USUARIO;
      EXCEPTION WHEN NO_DATA_FOUND THEN
      IDCODIGO:=1;      
    END;
    INSERT INTO SRET_USUARIO(NCODIGO_USUARIO,SEMPRESA_USUARIO,SNOMBRE_USUARIO,SLOGIN,SCLAVE,NCODPERFIL,COD_INSTITUCION,SRUA,STIPO_USUARIO)
    VALUES(IDCODIGO,EMPRESA_USUARIO,EMPRESA_USUARIO,LOGIN,CLAVE,CODPERFIL,INSTITUCION,SRUA ,'E');    
    SCODIGOUDUARIO:=IDCODIGO;
    COMMIT;
    BEGIN
        SELECT count(ncodigo) INTO IDCODIGO FROM SRET_DETCONSECION WHERE NCODIGO_CONSECION=INSTITUCION AND SESTADO='A';
       IF IDCODIGO=0 THEN
             PRC_INSERTARDETALLE(LOGIN,INSTITUCION,SRUA);
        END IF;
        EXCEPTION WHEN NO_DATA_FOUND THEN
            PRC_INSERTARDETALLE(LOGIN,INSTITUCION,SRUA);
    END;
  END PRC_INSERTAR;
  --------------------------   
  PROCEDURE PRC_GETUSUARIOBYLOGIN(LOGIN VARCHAR2,PWDENC VARCHAR2,cUsuario IN OUT C_USUARIO) AS
  
        ESINTERNO NUMBER:=0;
  BEGIN
        BEGIN
          
            SELECT COUNT(*) INTO ESINTERNO FROM SRET_USUARIO WHERE SLOGIN=LOGIN AND STIPO_USUARIO='I' AND SESTADO='1';
            IF ESINTERNO > 0 THEN --interno
                   OPEN cUsuario FOR  
                                    SELECT    NCODIGO_USUARIO,  
                                              SNOMBRE_USUARIO,
                                              SCORREOEMP,
                                              SLOGIN, 
                                              NCODPERFIL, 
                                              COD_INSTITUCION, 
                                              STIPO_USUARIO,
                                              SEMPRESA_USUARIO,                                             
                                              STELFEMP,
                                              SRUA,SPASSWD_STATUS
                                    FROM SRET_USUARIO WHERE SLOGIN=LOGIN  and SESTADO=1; 
            ELSE --externo
                   OPEN cUsuario FOR  
                                    SELECT    NCODIGO_USUARIO,  
                                              SNOMBRE_USUARIO,
                                              SCORREOEMP,
                                              SLOGIN, 
                                              NCODPERFIL, 
                                              COD_INSTITUCION, 
                                              STIPO_USUARIO,
                                              SEMPRESA_USUARIO,                                             
                                              STELFEMP,
                                              SRUA,SPASSWD_STATUS 
                                    FROM SRET_USUARIO WHERE SLOGIN=LOGIN AND SCLAVE=PWDENC AND STIPO_USUARIO='E' and SESTADO='1'; 
                     
            END IF;
                    BEGIN --LOGUEO ERRADO or  OK
                        INSERT INTO 
                                SRET_USUARIO_LOG(ACCION_LOG,
                                                  FECHA_LOG,
                                                  NCODIGO_USUARIO,
                                                  SNOMBRE_USUARIO,
                                                  SEMPRESA_USUARIO,
                                                  SLOGIN,
                                                  SCLAVE,
                                                  NCODPERFIL,
                                                  COD_INSTITUCION,
                                                  SRUA,
                                                  STIPO_USUARIO,
                                                  SPASSWD_STATUS,
                                                  SESTADO,
                                                  SCORREOEMP,
                                                  STELFEMP,
                                                  DFECHAREG,
                                                  SUSUREG,
                                                  SUSUMOD,
                                                  DFECHAMOD) 
                                           SELECT 'L',
                                                  SYSDATE,
                                                  NCODIGO_USUARIO,
                                                  SNOMBRE_USUARIO,
                                                  SEMPRESA_USUARIO,
                                                  SLOGIN,
                                                  SCLAVE,
                                                  NCODPERFIL,
                                                  COD_INSTITUCION,
                                                  SRUA,
                                                  STIPO_USUARIO,
                                                  SPASSWD_STATUS,
                                                  SESTADO,
                                                  SCORREOEMP,
                                                  STELFEMP,
                                                  DFECHAREG,
                                                  SUSUREG,
                                                  SUSUMOD,
                                                  DFECHAMOD
                                          FROM    SRET_USUARIO
                                          WHERE SLOGIN=LOGIN  and SESTADO='1';  
                                          
                                           INSERT INTO  SRET_USUARIO_LOG(ACCION_LOG,
                                                  FECHA_LOG,
                                                  NCODIGO_USUARIO,
                                                  SNOMBRE_USUARIO,
                                                  SEMPRESA_USUARIO,
                                                  SLOGIN,
                                                  SCLAVE,
                                                  NCODPERFIL,
                                                  COD_INSTITUCION,
                                                  SRUA,
                                                  STIPO_USUARIO,
                                                  SPASSWD_STATUS,
                                                  SESTADO,
                                                  SCORREOEMP,
                                                  STELFEMP,
                                                  DFECHAREG,
                                                  SUSUREG,
                                                  SUSUMOD,
                                                  DFECHAMOD) 
                                           SELECT 'P',
                                                  SYSDATE,
                                                  NCODIGO_USUARIO,
                                                  SNOMBRE_USUARIO,
                                                  SEMPRESA_USUARIO,
                                                  LOGIN,
                                                  PWDENC,
                                                  NCODPERFIL,
                                                  COD_INSTITUCION,
                                                  SRUA,
                                                  STIPO_USUARIO,
                                                  SPASSWD_STATUS,
                                                  SESTADO,
                                                  SCORREOEMP,
                                                  STELFEMP,
                                                  DFECHAREG,
                                                  SUSUREG,
                                                  SUSUMOD,
                                                  DFECHAMOD
                                          FROM    SRET_USUARIO
                                          WHERE SLOGIN=LOGIN  and SESTADO='1'; 
                                          
                        EXCEPTION 
                            WHEN NO_DATA_FOUND THEN 
                          Raise_Application_Error (-20342, 'ERROR AL INSERTAR EL LOG DE INGRESO');
                     END;
        END; 
           
    EXCEPTION WHEN NO_DATA_FOUND THEN
      Raise_Application_Error (-20343, 'NO SE ENCONTRO AL USUARIO');
             WHEN TOO_MANY_ROWS THEN
      Raise_Application_Error (-20344, 'DEVUELVE MAS DE UNA FILA');              
            
  END PRC_GETUSUARIOBYLOGIN;
  
  PROCEDURE PRC_VALIDAUSUARIO ( CODUSUARIO VARCHAR2, CANTIDAD IN OUT NUMBER ) AS
  
  BEGIN 
    SELECT COUNT ( NCODIGO_USUARIO )  INTO CANTIDAD
    FROM SRET_USUARIO 
    WHERE UPPER(TRIM(SLOGIN)) = UPPER (TRIM(CODUSUARIO));
    
  END PRC_VALIDAUSUARIO; 
  
  
  
  PROCEDURE PRC_LOGINCONSECION ( LOGIN VARCHAR2, CLAVE VARCHAR2, cUsuario IN OUT C_USUARIO) AS
  BEGIN
    OPEN cUsuario FOR
    
    SELECT NCODIGO_USUARIO,  SNOMBRE_USUARIO,  SEMPRESA_USUARIO,  SLOGIN, NCODPERFIL, COD_INSTITUCION,STIPO_USUARIO
      FROM SRET_USUARIO WHERE UPPER ( SLOGIN ) = UPPER ( LOGIN ) AND SCLAVE=CLAVE and Sestado='1';
    
  END PRC_LOGINCONSECION;
  
  PROCEDURE PRC_GET0CONSECION ( CODCONCESION NUMBER, cUsuario IN OUT C_USUARIO) AS
  BEGIN
    OPEN cUsuario FOR 
     SELECT C.CNC_ID, C.CNC_NOMBRE, C.CNC_REPRESENTANTE_LEGAL, '', CNC_NRO_DOCUMENTO
       FROM  SCINVGSF.T_CONCESIONARIO C INNER JOIN SRET_USUARIO U ON C.CNC_ID=U.COD_INSTITUCION
      WHERE  C.CNC_ID=CODCONCESION; 
  END PRC_GET0CONSECION;
  

  
  PROCEDURE PRC_CAMBIARCLAVE ( codusuario NUMBER,clave VARCHAR2)
  AS BEGIN
    UPDATE SRET_USUARIO SET SCLAVE=clave,SPASSWD_STATUS=1 WHERE NCODIGO_USUARIO=codusuario;
  END;
  PROCEDURE PRC_GETUSUARIOBYNCODIGOUS ( CODIGOUSUARIO NUMBER, cUsuario IN OUT C_USUARIO) AS
BEGIN 
    OPEN cUsuario FOR 
      
        SELECT    NCODIGO_USUARIO,  
                                              SNOMBRE_USUARIO,
                                              SCORREOEMP,
                                              SLOGIN, 
                                              NCODPERFIL, 
                                              COD_INSTITUCION, 
                                              STIPO_USUARIO,
                                              SEMPRESA_USUARIO,                                             
                                              STELFEMP,
                                              SRUA,SPASSWD_STATUS
      FROM SRET_USUARIO WHERE NCODIGO_USUARIO=CODIGOUSUARIO;   
  END PRC_GETUSUARIOBYNCODIGOUS;
   PROCEDURE PRC_GETUSUARIOBYRUC ( RUC VARCHAR2, cUsuario IN OUT C_USUARIO) AS
  BEGIN 
    OPEN cUsuario FOR 
      
      SELECT NCODIGO_USUARIO,  SNOMBRE_USUARIO,  SEMPRESA_USUARIO,  SLOGIN, NCODPERFIL, COD_INSTITUCION, STIPO_USUARIO,SPASSWD_STATUS,SCLAVE
      FROM SRET_USUARIO WHERE SRUA=RUC AND STIPO_USUARIO='E';      
    
  END PRC_GETUSUARIOBYRUC;    

  PROCEDURE PRC_GETREPRESENTANTE ( CODSOLICITUD NUMBER, cUsuario IN OUT C_USUARIO)as
  BEGIN
      EXECUTE IMMEDIATE 'alter session set nls_date_format =''DD/MM/YYYY'' ';
      OPEN cUsuario FOR SELECT NCODIGO,
      NCODIGO_USUARIO,
      NVL(SNOMBRE,'-'),
      NVL(SAPEPAT,'-'),
      NVL(SAPEMAT,'-'),
      NVL(STIPODOC,'-'),--aumentar esto igual en el paquete PRC_GETREPXCONCESION
      SNUMDOC,
      SCARGO,  
      TO_CHAR(DFECINIPODER,'dd/MM/yyyy'),
      SESTADO,
      TO_CHAR(DFECHAREGISTRO,'dd/MM/yyyy'),
      TO_CHAR(DFECHAMODIFICA,'dd/MM/yyyy')      
      FROM SRET_REPRESENTANTE
      WHERE NCODIGO_USUARIO=CODSOLICITUD
      AND SESTADO='1'
      ORDER BY SESTADO DESC,DFECHAMODIFICA ASC;
  END PRC_GETREPRESENTANTE;
  PROCEDURE PRC_GETREPXCONCESION ( CONCESION NUMBER, cUsuario IN OUT C_USUARIO)as
  CODUS NUMBER:=0;
  BEGIN
      EXECUTE IMMEDIATE 'alter session set nls_date_format =''DD/MM/YYYY'' ';
      BEGIN
      SELECT NCODIGO_USUARIO INTO CODUS FROM SRET_USUARIO WHERE COD_INSTITUCION=CONCESION and SESTADO='1';
      EXCEPTION WHEN NO_DATA_FOUND THEN
      CODUS:=0;
      END;
      OPEN cUsuario FOR SELECT NCODIGO,
      NCODIGO_USUARIO, 
      NVL(SNOMBRE,'-') SNOMBRE ,
      NVL(SAPEPAT,'-') SAPEPAT,
      NVL(SAPEMAT,'-') SAPEMAT,
      NVL(STIPODOC,'-') STIPODOC , 
      SNUMDOC,
      SCARGO,  
      TO_CHAR(DFECINIPODER,'dd/MM/yyyy'),
      SESTADO,
      TO_CHAR(DFECHAREGISTRO,'dd/MM/yyyy'),
      TO_CHAR(DFECHAMODIFICA,'dd/MM/yyyy')
      FROM SRET_REPRESENTANTE
      WHERE NCODIGO_USUARIO=CODUS
      AND SESTADO='1'
      ORDER BY SESTADO DESC,DFECHAMODIFICA ASC;
  END  PRC_GETREPXCONCESION;     
      
 PROCEDURE PRC_GRABARUSUARIO ( PCODSOUSUARIO NUMBER,PEMPRESA VARCHAR2,PLOGIN VARCHAR2,PCLAVE VARCHAR2,PCODCONCESION VARCHAR2,PRUC VARCHAR2,PCORREO VARCHAR2,PTELEFONO VARCHAR2,PCREARUSUARIO VARCHAR2,PUSUREGMOD VARCHAR2, MSG OUT NUMBER)AS
  IDCODIGO NUMBER:=0;
  IDUSUARIODET NUMBER:=0;
  BEGIN 
  IF PCREARUSUARIO='1' THEN
    UPDATE SRET_USUARIO SET SESTADO='0' 
    WHERE  STIPO_USUARIO='E' AND COD_INSTITUCION=PCODCONCESION;
    COMMIT;
    BEGIN
      SELECT NVL(MAX(NCODIGO_USUARIO),0)+1 INTO IDCODIGO FROM SRET_USUARIO;
      EXCEPTION WHEN NO_DATA_FOUND THEN
      IDCODIGO:=1;      
    END;
    BEGIN
            INSERT INTO SRET_USUARIO
            (NCODIGO_USUARIO,
             SEMPRESA_USUARIO,
             SNOMBRE_USUARIO,
             SLOGIN,
             SCLAVE,
             NCODPERFIL,
             COD_INSTITUCION,
             SRUA,
             STIPO_USUARIO,
             DFECHAREG,
             SUSUREG,
             SESTADO,
             SCORREOEMP,
             STELFEMP)
            VALUES
            (IDCODIGO,
            PEMPRESA,
            PEMPRESA,
            PLOGIN,
            PCLAVE,
            '2',
            PCODCONCESION,
            PRUC,
            'E',
            SYSDATE,
            PUSUREGMOD,
            '1',
            PCORREO,
            PTELEFONO);    
            COMMIT;            
            MSG:=IDCODIGO;
            BEGIN
               SELECT count(ncodigo) INTO IDUSUARIODET FROM SRET_DETCONSECION WHERE NCODIGO_CONSECION=PCODCONCESION AND SESTADO='A';
               IF IDUSUARIODET=0 THEN
                     PRC_INSERTARDETALLE(PLOGIN,PCODCONCESION,PRUC);
               END IF;
               EXCEPTION WHEN NO_DATA_FOUND THEN
                    PRC_INSERTARDETALLE(PLOGIN,PCODCONCESION,PRUC);
            END;
           

    END ;
    ELSE 
       BEGIN
         UPDATE SRET_USUARIO 
         SET  SEMPRESA_USUARIO=PEMPRESA,
              SNOMBRE_USUARIO=PEMPRESA,
              NCODPERFIL='2',
              COD_INSTITUCION=PCODCONCESION,
              SRUA=PRUC,
              STIPO_USUARIO='E',
              SCORREOEMP=PCORREO,
              STELFEMP=PTELEFONO,
              DFECHAMOD=SYSDATE,
              SUSUMOD=PUSUREGMOD
         WHERE NCODIGO_USUARIO=PCODSOUSUARIO; 
         MSG:=PCODSOUSUARIO;
         IF SQL%NOTFOUND THEN
              MSG:=-1;
         END IF;
  
       END;
    END IF;
    
   
  END PRC_GRABARUSUARIO ;
    FUNCTION PRC_UPDANTREP(CODIGO NUMBER)RETURN VARCHAR2 AS
  CANTIDADREP NUMBER:=0;
  BEGIN
      BEGIN
      SELECT NVL(COUNT(NCODIGO),0) INTO CANTIDADREP FROM SRET_REPRESENTANTE WHERE NCODIGO_USUARIO=CODIGO;
      EXCEPTION WHEN NO_DATA_FOUND THEN
      CANTIDADREP:=0;
    END;
    IF(CANTIDADREP>0) THEN
      UPDATE SRET_REPRESENTANTE SET SESTADO='0' WHERE NCODIGO_USUARIO=CODIGO;
      UPDATE SRET_ARCHIVO SET SESTADO='0' 
      WHERE PADRE='REPRESENTANTE' 
      AND TIPOARCHIVO=1 
      AND NCODIGO_PADRE IN (SELECT NCODIGO FROM SRET_REPRESENTANTE WHERE NCODIGO_USUARIO=CODIGO);
    END IF;
    RETURN '1';
    EXCEPTION WHEN OTHERS THEN
    RETURN '-1';    
  END PRC_UPDANTREP;
  PROCEDURE PRC_INSERTARREP(
      REP_TIPODOC    IN VARCHAR2,
      REP_NUMDOC     IN VARCHAR2,      
      REP_NOMBRE     IN VARCHAR2,
      REP_APEPAT     IN VARCHAR2,
      REP_APEMAT     IN VARCHAR2,
      REP_CARGO     IN VARCHAR2,
      REP_FECPODER  IN DATE,      
      ARCHIVOS VARCHAR2,
      CODSOLICITUD IN NUMBER,
      PUSUARIOREG IN VARCHAR2)
  AS
  CODIGO NUMBER :=0;
  CANTIDADREP NUMBER:=0;
  BEGIN        
        SELECT NVL(MAX(NCODIGO),0)+1 INTO CODIGO FROM SRET_REPRESENTANTE;
        INSERT
        INTO SRET_REPRESENTANTE
          (
            NCODIGO, 
            STIPODOC,
            SNUMDOC,
            SNOMBRE,
            SAPEPAT,
            SAPEMAT,
            SCARGO,
            DFECINIPODER,
            SESTADO,        
            NCODIGO_USUARIO,
            SCODUSUREGISTRA,
            DFECHAREGISTRO       
          )
          VALUES
          (
            CODIGO,   
            REP_TIPODOC,
            REP_NUMDOC,
            REP_NOMBRE,
            REP_APEPAT,
            REP_APEMAT,
            REP_CARGO,
            REP_FECPODER,        
            '1',     
            CODSOLICITUD,
            PUSUARIOREG,
            SYSDATE
          );    
    IF ARCHIVOS IS NOT NULL THEN
      PRC_GRABARARCHIVO('REPRESENTANTE','1',CODIGO,ARCHIVOS);       
    END IF;
    COMMIT;
  END PRC_INSERTARREP;
  PROCEDURE PRC_SEPARARARCHIVO(
      PTIPO CHAR,
      PPADRE     VARCHAR2,
      CODIGO NUMBER,
      PCADENA    VARCHAR2)
  AS
    l_tablen BINARY_INTEGER;
    l_tab DBMS_UTILITY.uncl_array;
    CURSOR cur IS SELECT PCADENA val FROM dual;
    rec cur%rowtype;
  BEGIN
    OPEN cur;
    LOOP FETCH cur INTO rec;
         EXIT WHEN cur%notfound;
            DBMS_UTILITY.comma_to_table (
            list => rec.val, tablen => l_tablen, tab => l_tab);
            FOR i IN 1 .. l_tablen LOOP
             PRC_INSERTAARCHIVO(PTIPO,PPADRE,CODIGO,l_tab(i));
            END LOOP;            
    END LOOP;
    CLOSE cur;
      EXCEPTION WHEN OTHERS THEN
     raise_application_error(-20001,SQLERRM);
  END;
  PROCEDURE PRC_INSERTAARCHIVO(
      PTIPO CHAR,
      PPADRE   VARCHAR2,
      CODIGO NUMBER,
      PARCHIVO VARCHAR2)
  AS

  BEGIN    
    INSERT INTO SRET_ARCHIVO (
        NCODIGO_ARCHIVO,
        PADRE,
        NCODIGO_PADRE,
        TIPOARCHIVO,
        NOMBRE,
        SESTADO)
       VALUES(
        (SELECT NVL(MAX(NCODIGO_ARCHIVO),0)+1 FROM SRET_ARCHIVO),
        PPADRE,
        CODIGO,
        PTIPO,
        PARCHIVO,       
        '1');
    EXCEPTION WHEN OTHERS THEN
     raise_application_error(-20001,'An error was encountered - '||SQLCODE||' -ERROR- '||SQLERRM);
         
  END PRC_INSERTAARCHIVO;
  PROCEDURE PRC_ENCADENARCHIVO(PTIPO CHAR,PPADRE VARCHAR2,CODIGO NUMBER,PARCHIVO VARCHAR2,EXISTE OUT CHAR)AS 
  CADENA VARCHAR2(4000);
  BEGIN
    FOR I IN (SELECT NOMBRE FROM SRET_ARCHIVO 
              WHERE TIPOARCHIVO=PTIPO AND PADRE=PPADRE          
                AND NCODIGO_PADRE=CODIGO AND SESTADO='1')
      LOOP
          CADENA:=CADENA||I.NOMBRE||',';
      END LOOP;      
      IF CADENA=PARCHIVO||',' THEN
        EXISTE:='1';
      ELSE
        EXISTE:='0';
      END IF;
  END PRC_ENCADENARCHIVO;
  PROCEDURE PRC_GRABARARCHIVO(PPADRE VARCHAR2,PTIPO CHAR,CODIGO NUMBER,PARCHIVO VARCHAR2)AS
  EXISTE CHAR(1);
  BEGIN 
   PRC_ENCADENARCHIVO(PTIPO,PPADRE,CODIGO,PARCHIVO,EXISTE);
       
        IF EXISTE='0' THEN
            IF PTIPO='1' OR PTIPO='2' THEN
              UPDATE SRET_ARCHIVO SET SESTADO='0'
              WHERE TIPOARCHIVO IN ('1','2') AND PADRE=PPADRE 
              AND NCODIGO_PADRE=CODIGO;               
            ELSE
              UPDATE SRET_ARCHIVO SET SESTADO='0'
              WHERE TIPOARCHIVO='3' AND PADRE=PPADRE 
              AND NCODIGO_PADRE=CODIGO;                   
            END IF;
              PRC_SEPARARARCHIVO(PTIPO,PPADRE,CODIGO,PARCHIVO);
        END IF;
  END PRC_GRABARARCHIVO;
 PROCEDURE PRC_GETSOLARCHIVO(CODIGO NUMBER,PPADRE VARCHAR2,cUsuario IN OUT C_USUARIO, cUsuario2 IN OUT C_USUARIO,cUsuario3 IN OUT C_USUARIO)AS
  BEGIN
      BEGIN
      OPEN cUsuario for SELECT NOMBRE FROM SRET_ARCHIVO WHERE NCODIGO_PADRE=CODIGO AND  PADRE=PPADRE AND TIPOARCHIVO='1' AND SESTADO='1';
      EXCEPTION WHEN NO_DATA_FOUND THEN
      RAISE_APPLICATION_ERROR(-20001, 'NO EXISTE ARCHIVO DE PODER PARA EL CODIGO '||CODIGO||' ');
      END;
      BEGIN
      OPEN cUsuario2 for SELECT NOMBRE FROM SRET_ARCHIVO WHERE  NCODIGO_PADRE=CODIGO AND PADRE=PPADRE AND TIPOARCHIVO='2' AND SESTADO='1';
      EXCEPTION WHEN NO_DATA_FOUND THEN
      RAISE_APPLICATION_ERROR(-20002, 'NO EXISTE DOCUMENTO DE ASIGNACIÓN DE PODER PARA EL CODIGO '||CODIGO||' ');
      END;
      BEGIN
      OPEN cUsuario3 for SELECT NOMBRE FROM SRET_ARCHIVO WHERE  NCODIGO_PADRE=CODIGO AND PADRE=PPADRE AND TIPOARCHIVO='3' AND SESTADO='1';
      EXCEPTION WHEN NO_DATA_FOUND THEN
      RAISE_APPLICATION_ERROR(-20003, 'NO EXISTE DOCUMENTOS EN SOLICITUD FIRMADA PARA EL CODIGO '||CODIGO||' ');
      END;
  END PRC_GETSOLARCHIVO;
    PROCEDURE PRC_GRABARARCHSO(
      NCODIGO NUMBER,
      NOMARCH VARCHAR2 )
  AS
  BEGIN
    PRC_GRABARARCHIVO('SOLICITUDFIRMADA','2',NCODIGO,NOMARCH);    
    
   
  END PRC_GRABARARCHSO;
   PROCEDURE PRC_GETARCHIVOS(PPADRE VARCHAR2,CODIGOREP NUMBER,PTIPOARCHIVO CHAR,cUsuario IN OUT C_USUARIO)AS
  BEGIN
    OPEN cUsuario FOR 
    SELECT       
        NOMBRE    
    FROM SRET_ARCHIVO 
    WHERE PADRE=PPADRE
    AND NCODIGO_PADRE=CODIGOREP 
    AND TIPOARCHIVO=PTIPOARCHIVO
    AND SESTADO='1';
  END PRC_GETARCHIVOS;
    PROCEDURE PRC_BUSCARXCORREO(CORREO VARCHAR2,NOMCONTRIBUYENTE OUT VARCHAR2,IDCONTRI OUT NUMBER)AS
  BEGIN
    SELECT SEMPRESA_USUARIO,COD_INSTITUCION INTO NOMCONTRIBUYENTE,IDCONTRI FROM SRET_USUARIO WHERE SESTADO='1' AND UPPER(SCORREOEMP)=UPPER(CORREO) AND STIPO_USUARIO='E';
    EXCEPTION WHEN NO_DATA_FOUND THEN
      NOMCONTRIBUYENTE:='';
  END PRC_BUSCARXCORREO;
  
  PROCEDURE PRC_ACTCLAVE(CLAVE VARCHAR2,PCOD_INSTITUCION NUMBER)AS
 
    BEGIN
  UPDATE SRET_USUARIO SET SPASSWD_STATUS=0 , SCLAVE=CLAVE WHERE SESTADO='1' AND STIPO_USUARIO='E' AND COD_INSTITUCION=PCOD_INSTITUCION;
  commit;
  END PRC_ACTCLAVE;
  -------------
  PROCEDURE PRC_INSERTARDETALLE(LOGIN IN VARCHAR2,INSTITUCION IN NUMBER,SRUA IN VARCHAR2)AS
   IDCODIGO NUMBER:=0;
  BEGIN   
          UPDATE SRET_DETCONSECION 
          SET SESTADO='I'
          WHERE SRUC_CONSECION=SRUA AND SESTADO='A';          
          BEGIN
            SELECT NVL(MAX(NCODIGO),0)+1 INTO IDCODIGO FROM SRET_DETCONSECION;
            EXCEPTION WHEN NO_DATA_FOUND THEN
            IDCODIGO:=1;      
          END;    
          Insert into SRET_DETCONSECION(NCODIGO,NCODIGO_CONSECION,SRUC_CONSECION,SBCONTRIBUYENTE,DFECHAMODIFICA,SCODUMODIFICA,SESTADO,DFECHAREGISTRO,SCODUREGISTRA) 
          values (IDCODIGO,INSTITUCION,SRUA,null,null,null,'A',SYSDATE,LOGIN);
          COMMIT;   
  END PRC_INSERTARDETALLE;
  ---
   PROCEDURE PRC_BUSCARXIDCONCESION(PCOD_INSTITUCION NUMBER,CORREO VARCHAR2,NOMCONTRIBUYENTE OUT VARCHAR2)as
  BEGIN 
     SELECT SEMPRESA_USUARIO INTO NOMCONTRIBUYENTE 
     FROM SRET_USUARIO 
     WHERE SESTADO='1'  AND STIPO_USUARIO='E' 
     AND COD_INSTITUCION=PCOD_INSTITUCION AND UPPER(SCORREOEMP)=UPPER(CORREO);
    EXCEPTION WHEN NO_DATA_FOUND THEN
      NOMCONTRIBUYENTE:='-1';
 END PRC_BUSCARXIDCONCESION;
 PROCEDURE PRC_UPDDOMICILIOFISCAL(PCOD_INSTITUCION NUMBER,DOMICILIO VARCHAR2,LOGUSUARIO VARCHAR2)AS
COD NUMBER;
BEGIN
SELECT NVL(MAX(NCODIGO_LOG),0)+1 INTO COD FROM SCINVGSF.T_CONCESIONARIO_LOG;

INSERT INTO SCINVGSF.T_CONCESIONARIO_LOG 
    (
    NCODIGO_LOG,
    CNC_ID,
    CNC_NOMBRE,
    CNC_DESCRIPCION,
    CNC_SIGLAS,
    CNC_ESTADO,
    CNC_TELEFONO,
    CNC_TERMINAL,
    CNC_DIRECCION,
    CNC_CORREO,
    CNC_REPRESENTANTE_LEGAL,
    CNC_FECHA_ALTA,
    CNC_USUARIO_ALTA,
    CNC_FECHA_CAMBIO,
    CNC_USUARIO_CAMBIO,
    CNC_FECHA_BAJA,
    CNC_USUARIO_BAJA,
    CNC_NRO_DOCUMENTO,
    TDO_ID,
    LOG_FECHAREG,
    ACCION,
    LOG_USUARIO
    )
SELECT 
COD,
CNC_ID,
CNC_NOMBRE,
CNC_DESCRIPCION,
CNC_SIGLAS,
CNC_ESTADO,
CNC_TELEFONO,
CNC_TERMINAL,
CNC_DIRECCION,
CNC_CORREO,
CNC_REPRESENTANTE_LEGAL,
CNC_FECHA_ALTA,
CNC_USUARIO_ALTA,
CNC_FECHA_CAMBIO,
CNC_USUARIO_CAMBIO,
CNC_FECHA_BAJA,
CNC_USUARIO_BAJA,
CNC_NRO_DOCUMENTO,
TDO_ID,
SYSDATE,
'UO',
LOGUSUARIO
FROM SCINVGSF.T_CONCESIONARIO C WHERE C.CNC_ID=PCOD_INSTITUCION AND C.CNC_ESTADO='1';
UPDATE SCINVGSF.T_CONCESIONARIO  C SET C.CNC_DIRECCION=DOMICILIO WHERE C.CNC_ID=PCOD_INSTITUCION AND C.CNC_ESTADO='1';
END PRC_UPDDOMICILIOFISCAL;
PROCEDURE PRC_UPDATE_ELIMINARARCH (CODIGOAPORTE    NUMBER,
                                   NOMBREARCH      VARCHAR2,
                                   NOMBREPADRE     VARCHAR2)
AS
BEGIN
   UPDATE SRET_ARCHIVO
      SET SESTADO = '0'
    WHERE     NOMBRE = NOMBREARCH
          AND NCODIGO_PADRE = CODIGOAPORTE
          AND PADRE = NOMBREPADRE;

   COMMIT;
END PRC_UPDATE_ELIMINARARCH;
PROCEDURE PRC_INSERTARPAGINALOG(PNCONCESION NUMBER,PSLOGIN VARCHAR2,PSPAGINA VARCHAR2)AS
BEGIN
INSERT INTO SRET_PAGINALOG(
NCODIGO,
NCONCESION,
SLOGIN,
SPAGINA,
DFECREGISTRO
)
VALUES(
SQ_SRET_PAGINALOG.NEXTVAL,
PNCONCESION,
PSLOGIN,
PSPAGINA,
SYSDATE
);
END PRC_INSERTARPAGINALOG;
PROCEDURE PRC_CAMBIARCLAVE ( CLAVEANTERIOR VARCHAR2, NUEVACLAVE VARCHAR2, CONFIRMARCLAVE VARCHAR2, cUsuario IN OUT C_USUARIO)
AS
BEGIN
--SELECT COUNT(*) FROM user_app WHERE ID=2 AND PASSWORD='$1a$10$0NmJY/DokERlDqE4nWDhVuf0Y.B8BnoDW2gRrzFdzvc1dqNVEek7C';
IF NUEVACLAVE=CONFIRMARCLAVE THEN
    OPEN cUsuario FOR
    SELECT 0 RESULTADO,'LA NUEVA CLAVE NO COINCIDE CON CONFIRMAR CLAVE.' FROM DUAL;
END IF;
END PRC_CAMBIARCLAVE;
END PK_USUARIO;

create or replace PACKAGE PK_UTIL_DATE AS 

  FUNCTION FN_DIFERECIA(FECHA1 DATE,FECHA2 DATE,FORMATO CHAR)RETURN VARCHAR2;
  FUNCTION FN_FORMATEAR(FECHA DATE,FORMATO CHAR)RETURN DATE;
  FUNCTION FN_DIASHAB_2FECHAS(fecha_inicio IN DATE, fecha_fin IN DATE)RETURN NUMBER;
  FUNCTION FN_SUMARDIAS_HAB(fecha_inicio IN DATE,PDIAS NUMBER)RETURN DATE; 
    FUNCTION FN_RESTARDIAS_HAB(FECHA_INICIO IN DATE,PDIAS NUMBER)RETURN DATE;
END PK_UTIL_DATE;

create or replace PACKAGE BODY PK_UTIL_DATE
AS
    FUNCTION FN_DIFERECIA(FECHA1 DATE,FECHA2 DATE,FORMATO CHAR)RETURN VARCHAR2
  AS 
  BEGIN
      RETURN FN_FORMATEAR(FECHA1,FORMATO)-FN_FORMATEAR(FECHA2,FORMATO); 
  END FN_DIFERECIA;  
  FUNCTION FN_FORMATEAR(FECHA DATE,FORMATO CHAR)RETURN DATE AS
  BEGIN
      RETURN TO_DATE(TO_CHAR(FECHA,FORMATO),FORMATO);
  END FN_FORMATEAR;
  FUNCTION FN_DIASHAB_2FECHAS(fecha_inicio IN DATE, fecha_fin IN DATE)RETURN NUMBER IS
      numero_dias NUMBER := 0;
      fecha_actual DATE;
      FERIADOHABIL NUMBER:=0;
  BEGIN       
    EXECUTE IMMEDIATE 'alter session set nls_date_format =''DD/MM/YYYY'' ';
   fecha_actual := fecha_inicio;
           WHILE fecha_actual <= fecha_fin LOOP             
               IF TO_CHAR(fecha_actual,'DY') NOT IN ('SAT','SUN','SÁB','DOM') AND fecha_actual <> fecha_inicio THEN 
                          BEGIN
                              SELECT COUNT (*) INTO FERIADOHABIL
                              FROM SCINVGSF.T_FERIADO
                              WHERE FER_DIA=TO_NUMBER(TO_CHAR(fecha_actual,'DD'))
                                AND FER_MES=TO_NUMBER(TO_CHAR(fecha_actual,'MM'))
                                AND FER_ANYO=TO_NUMBER(TO_CHAR(fecha_actual,'YYYY'));
                          EXCEPTION WHEN NO_DATA_FOUND THEN
                              FERIADOHABIL:=0;
                          END;
                          IF FERIADOHABIL=0 THEN                         
                            numero_dias := numero_dias + 1;
                          END IF;             
               END IF;
             fecha_actual := fecha_actual + 1;
            END LOOP;      
          RETURN numero_dias;
    
  END FN_DIASHAB_2FECHAS;
  FUNCTION FN_SUMARDIAS_HAB(fecha_inicio IN DATE,PDIAS NUMBER)RETURN DATE IS
      numero_dias NUMBER := 0;
      fecha_actual DATE;
      FERIADOHABIL NUMBER;
  BEGIN
    EXECUTE IMMEDIATE 'alter session set nls_date_format =''DD/MM/YYYY'' ';
  numero_dias:=PDIAS;
  fecha_actual:=fecha_inicio;  
        WHILE numero_dias>0 LOOP
                     fecha_actual:= fecha_actual+1;
                     IF TO_CHAR(fecha_actual,'DY') NOT IN ('SAT','SUN','SÁB','DOM')  THEN  
                          SELECT COUNT (*) INTO FERIADOHABIL
                          FROM SCINVGSF.T_FERIADO
                          WHERE FER_DIA=TO_NUMBER(TO_CHAR(fecha_actual,'DD'))
                            AND FER_MES=TO_NUMBER(TO_CHAR(fecha_actual,'MM'))
                            AND FER_ANYO=TO_NUMBER(TO_CHAR(fecha_actual,'YYYY'));
                          IF FERIADOHABIL=0 THEN                         
                            numero_dias:=numero_dias-1;
                          END IF;
                     END IF;                         
        END LOOP;
      RETURN fecha_actual;
  END FN_SUMARDIAS_HAB;
  ----------------------------------------
  FUNCTION FN_RESTARDIAS_HAB(FECHA_INICIO IN DATE,PDIAS NUMBER)RETURN DATE IS
      NUMERO_DIAS NUMBER := 0;
      FECHA_ACTUAL DATE;
      FERIADOHABIL NUMBER;
  BEGIN
  EXECUTE IMMEDIATE 'alter session set nls_date_format =''DD/MM/YYYY'' ';
  NUMERO_DIAS:=PDIAS;
  FECHA_ACTUAL:=FECHA_INICIO;  
        WHILE NUMERO_DIAS>0 LOOP
                     FECHA_ACTUAL:= FECHA_ACTUAL-1;
                     IF TO_CHAR(FECHA_ACTUAL,'DY') NOT IN ('SÁB','DOM','SAT','SUN') THEN  
                          SELECT COUNT (*) INTO FERIADOHABIL
                          FROM SCINVGSF.T_FERIADO
                          WHERE FER_DIA=TO_NUMBER(TO_CHAR(FECHA_ACTUAL,'DD'))
                            AND FER_MES=TO_NUMBER(TO_CHAR(FECHA_ACTUAL,'MM'))
                            AND FER_ANYO=TO_NUMBER(TO_CHAR(FECHA_ACTUAL,'YYYY'));
                          IF FERIADOHABIL=0 THEN                         
                            NUMERO_DIAS:=NUMERO_DIAS-1;
                          END IF;
                     END IF;                         
        END LOOP;
      RETURN FECHA_ACTUAL;
END FN_RESTARDIAS_HAB;
END PK_UTIL_DATE;