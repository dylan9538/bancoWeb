package co.edu.icesi.demo.logica;

import java.util.List;

import co.edu.icesi.demo.modelo.TiposUsuarios;

public interface ITipoUsuarioLogica {
	public void crear(TiposUsuarios tiposUsuarios) throws Exception;

	public void modificar(TiposUsuarios tiposUsuarios) throws Exception;

	public void borrar(TiposUsuarios tiposUsuarios) throws Exception;

	public TiposUsuarios consultarPorID(Long id) throws Exception;

	public List<TiposUsuarios> consultarTodos() throws Exception;
}
