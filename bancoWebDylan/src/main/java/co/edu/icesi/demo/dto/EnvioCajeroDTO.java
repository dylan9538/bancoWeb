package co.edu.icesi.demo.dto;

import java.math.BigDecimal;

public class EnvioCajeroDTO {

	private String noCuentaDigitado;
	private String claveDigitado;
	private BigDecimal valorDigitado;
	
	
	public String getNoCuentaDigitado() {
		return noCuentaDigitado;
	}
	public void setNoCuentaDigitado(String noCuentaDigitado) {
		this.noCuentaDigitado = noCuentaDigitado;
	}
	public String getClaveDigitado() {
		return claveDigitado;
	}
	public void setClaveDigitado(String claveDigitado) {
		this.claveDigitado = claveDigitado;
	}
	public BigDecimal getValorDigitado() {
		return valorDigitado;
	}
	public void setValorDigitado(BigDecimal valorDigitado) {
		this.valorDigitado = valorDigitado;
	}

	
	
}

