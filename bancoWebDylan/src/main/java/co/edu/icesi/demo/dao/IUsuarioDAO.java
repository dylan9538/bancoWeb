package co.edu.icesi.demo.dao;

import java.util.List;

import co.edu.icesi.demo.modelo.Usuarios;

public interface IUsuarioDAO {

	public void crear(Usuarios cliente);

	public void modificar(Usuarios cliente);

	public void borrar(Usuarios cleinte);

	public Usuarios consultarPorID(long id);

	public List<Usuarios> consultarTodos();
	
	public Usuarios consultarUsuario(String usuLogin);
}
