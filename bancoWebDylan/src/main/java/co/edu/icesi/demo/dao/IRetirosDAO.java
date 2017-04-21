package co.edu.icesi.demo.dao;

import java.util.List;

import co.edu.icesi.demo.modelo.Retiros;
import co.edu.icesi.demo.modelo.RetirosId;

public interface IRetirosDAO {

	public void crear(Retiros consignacion);

	public void modificar(Retiros consignacion);

	public void borrar(Retiros consignacion);

	public Retiros consultarPorID(RetirosId id);

	public List<Retiros> consultarTodos();
}
