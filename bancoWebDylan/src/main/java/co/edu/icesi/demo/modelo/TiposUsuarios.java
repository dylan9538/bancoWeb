package co.edu.icesi.demo.modelo;
// Generated 16/08/2016 04:28:38 PM by Hibernate Tools 5.1.0.Beta1

import java.util.HashSet;
import java.util.Set;

/**
 * TiposUsuarios generated by hbm2java
 */
public class TiposUsuarios implements java.io.Serializable {

	private long tusuCodigo;
	private String tusuNombre;
	private Set<Usuarios> usuarioses = new HashSet<Usuarios>(0);

	public TiposUsuarios() {
	}

	public TiposUsuarios(long tusuCodigo, String tusuNombre) {
		this.tusuCodigo = tusuCodigo;
		this.tusuNombre = tusuNombre;
	}

	public TiposUsuarios(long tusuCodigo, String tusuNombre, Set<Usuarios> usuarioses) {
		this.tusuCodigo = tusuCodigo;
		this.tusuNombre = tusuNombre;
		this.usuarioses = usuarioses;
	}

	public long getTusuCodigo() {
		return this.tusuCodigo;
	}

	public void setTusuCodigo(long tusuCodigo) {
		this.tusuCodigo = tusuCodigo;
	}

	public String getTusuNombre() {
		return this.tusuNombre;
	}

	public void setTusuNombre(String tusuNombre) {
		this.tusuNombre = tusuNombre;
	}

	public Set<Usuarios> getUsuarioses() {
		return this.usuarioses;
	}

	public void setUsuarioses(Set<Usuarios> usuarioses) {
		this.usuarioses = usuarioses;
	}

}
