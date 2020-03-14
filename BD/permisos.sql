--con el usuario scinvgsf
grant select on SCINVGSF.t_concesionario to sretessf1;
grant select on SCINVGSF.t_concesionario to sretessf1  with grant option;
grant references on SCINVGSF.t_concesionario to sretessf1 ;
--con el usuario SAPREGSF
grant select on SAPREGSF.SAPR_TIPODECLARACION to sretessf1;
grant references on SAPREGSF.SAPR_TIPODECLARACION to sretessf1;
--con el usuario sys
grant select on SAPREGSF.VW_REPRESENTANTE to sretessf1 ;


--CREAR VIEW EN SAPREGSF
SELECT R.NCODIGO, R.SNOMBRE, R.SAPEPAT, R.SAPEMAT, R.STIPODOC, R.SNUMDOC, R.SCARGO, R.DFECINIPODER,T.CNC_ID
FROM SAPR_REPRESENTANTE R
INNER JOIN SAPR_USUARIO U
ON U.NCODIGO_USUARIO=R.NCODIGO_USUARIO
INNER JOIN SCINVGSF.T_CONCESIONARIO T
ON T.CNC_ID=U.COD_INSTITUCION
WHERE R.SESTADO='1'
AND U.SESTADO='1'
AND T.CNC_ESTADO='1';

