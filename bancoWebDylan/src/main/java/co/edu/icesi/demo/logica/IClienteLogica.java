package co.edu.icesi.demo.logica;

import java.util.List;

import co.edu.icesi.demo.modelo.Clientes;

public interface IClienteLogica {

	public void crear(Clientes cliente) throws Exception;

	public void modificar(Clientes cliente) throws Exception;

	public void borrar(Clientes cleinte) throws Exception;

	public Clientes consultarPorID(Long id) throws Exception;

	public List<Clientes> consultarTodos() throws Exception;
}
