package co.edu.icesi.demo.logica;

import java.util.List;

import co.edu.icesi.demo.modelo.Usuarios;

public interface IUsuarioLogica {

	public void crear(Usuarios usuario) throws Exception;

	public void modificar(Usuarios usuario) throws Exception;

	public void borrar(Usuarios usuario) throws Exception;

	public Usuarios consultarPorID(Long cedula) throws Exception;

	public List<Usuarios> consultarTodos() throws Exception;
	
	public Usuarios validarUsuario(String usuLogin, String usuClave) throws Exception;
}
