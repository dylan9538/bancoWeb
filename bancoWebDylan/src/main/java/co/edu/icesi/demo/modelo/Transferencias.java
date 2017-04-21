package co.edu.icesi.demo.modelo;
// Generated 6/11/2016 08:36:05 PM by Hibernate Tools 5.1.0.Beta1

import java.math.BigDecimal;
import java.util.Date;

/**
 * Transferencias generated by hbm2java
 */
public class Transferencias implements java.io.Serializable {

	private long trasCodigo;
	private Clientes clientes;
	private Cuentas cuentasByCueNumeroDestino;
	private Cuentas cuentasByCueNumeroOrigen;
	private BigDecimal trasValor;
	private Date trasFecha;
	private String trasDescripcion;

	public Transferencias() {
	}

	public Transferencias(long trasCodigo, Clientes clientes, Cuentas cuentasByCueNumeroDestino,
			Cuentas cuentasByCueNumeroOrigen, BigDecimal trasValor, Date trasFecha, String trasDescripcion) {
		this.trasCodigo = trasCodigo;
		this.clientes = clientes;
		this.cuentasByCueNumeroDestino = cuentasByCueNumeroDestino;
		this.cuentasByCueNumeroOrigen = cuentasByCueNumeroOrigen;
		this.trasValor = trasValor;
		this.trasFecha = trasFecha;
		this.trasDescripcion = trasDescripcion;
	}

	public long getTrasCodigo() {
		return this.trasCodigo;
	}

	public void setTrasCodigo(long trasCodigo) {
		this.trasCodigo = trasCodigo;
	}

	public Clientes getClientes() {
		return this.clientes;
	}

	public void setClientes(Clientes clientes) {
		this.clientes = clientes;
	}

	public Cuentas getCuentasByCueNumeroDestino() {
		return this.cuentasByCueNumeroDestino;
	}

	public void setCuentasByCueNumeroDestino(Cuentas cuentasByCueNumeroDestino) {
		this.cuentasByCueNumeroDestino = cuentasByCueNumeroDestino;
	}

	public Cuentas getCuentasByCueNumeroOrigen() {
		return this.cuentasByCueNumeroOrigen;
	}

	public void setCuentasByCueNumeroOrigen(Cuentas cuentasByCueNumeroOrigen) {
		this.cuentasByCueNumeroOrigen = cuentasByCueNumeroOrigen;
	}

	public BigDecimal getTrasValor() {
		return this.trasValor;
	}

	public void setTrasValor(BigDecimal trasValor) {
		this.trasValor = trasValor;
	}

	public Date getTrasFecha() {
		return this.trasFecha;
	}

	public void setTrasFecha(Date trasFecha) {
		this.trasFecha = trasFecha;
	}

	public String getTrasDescripcion() {
		return this.trasDescripcion;
	}

	public void setTrasDescripcion(String trasDescripcion) {
		this.trasDescripcion = trasDescripcion;
	}

}