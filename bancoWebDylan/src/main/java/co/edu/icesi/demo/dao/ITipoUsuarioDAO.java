package co.edu.icesi.demo.dao;

import java.util.List;

import co.edu.icesi.demo.modelo.TiposDocumentos;
import co.edu.icesi.demo.modelo.TiposUsuarios;

public interface ITipoUsuarioDAO {

	public void crear(TiposUsuarios tiposUsuarios);

	public void modificar(TiposUsuarios tiposUsuarios);

	public void borrar(TiposUsuarios tiposUsuarios);

	public TiposUsuarios consultarPorID(Long id);

	public List<TiposUsuarios> consultarTodos();
}
