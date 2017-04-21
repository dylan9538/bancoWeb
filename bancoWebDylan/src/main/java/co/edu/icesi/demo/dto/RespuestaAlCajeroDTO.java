package co.edu.icesi.demo.dto;

import java.math.BigDecimal;

public class RespuestaAlCajeroDTO {
	
	private String mensajeError;
	private BigDecimal saldoNuevo;
	private boolean sePuedeRetirar;
	
	public RespuestaAlCajeroDTO(){
		
	}

	public String getMensajeError() {
		return mensajeError;
	}

	public void setMensajeError(String mensajeError) {
		this.mensajeError = mensajeError;
	}

	public BigDecimal getSaldoNuevo() {
		return saldoNuevo;
	}

	public void setSaldoNuevo(BigDecimal saldoNuevo) {
		this.saldoNuevo = saldoNuevo;
	}

	public boolean isSePuedeRetirar() {
		return sePuedeRetirar;
	}

	public void setSePuedeRetirar(boolean sePuedeRetirar) {
		this.sePuedeRetirar = sePuedeRetirar;
	}
	


}
