package co.edu.icesi.demo.dao;

import java.util.List;

import co.edu.icesi.demo.modelo.Clientes;

public interface IClienteDAO {

	public void crear(Clientes cliente);

	public void modificar(Clientes cliente);

	public void borrar(Clientes cleinte);

	public Clientes consultarPorID(Long id);

	public List<Clientes> consultarTodos();
}
