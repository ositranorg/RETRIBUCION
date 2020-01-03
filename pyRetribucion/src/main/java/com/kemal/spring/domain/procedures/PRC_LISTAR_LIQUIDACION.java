package com.kemal.spring.domain.procedures;




import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PRC_LISTAR_LIQUIDACION {
	private int nOrden;
	private int nCodigo;
	private String dFechaRegistro;
	private String dFechaModifica;
	private int nAnio;
	private int nIdTipoDocumento;
	private int nPeriodo;
	private String sDocumento;
	private String sEstado;
	public int getnOrden() {
		return nOrden;
	}
	public void setnOrden(int nOrden) {
		this.nOrden = nOrden;
	}
	public int getnCodigo() {
		return nCodigo;
	}
	public void setnCodigo(int nCodigo) {
		this.nCodigo = nCodigo;
	}
	public String getdFechaRegistro() {
		return dFechaRegistro;
	}
	public void setdFechaRegistro(String dFechaRegistro) {
		this.dFechaRegistro = dFechaRegistro;
	}
	public int getnAnio() {
		return nAnio;
	}
	public void setnAnio(int nAnio) {
		this.nAnio = nAnio;
	}
	public int getnIdTipoDocumento() {
		return nIdTipoDocumento;
	}
	public void setnIdTipoDocumento(int nIdTipoDocumento) {
		this.nIdTipoDocumento = nIdTipoDocumento;
	}
	public int getnPeriodo() {
		return nPeriodo;
	}
	public void setnPeriodo(int nPeriodo) {
		this.nPeriodo = nPeriodo;
	}
	public String getsDocumento() {
		return sDocumento;
	}
	public void setsDocumento(String sDocumento) {
		this.sDocumento = sDocumento;
	}
	public String getsEstado() {
		return sEstado;
	}
	public void setsEstado(String sEstado) {
		this.sEstado = sEstado;
	}
	
}
