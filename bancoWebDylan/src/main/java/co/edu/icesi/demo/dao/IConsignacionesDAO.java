package co.edu.icesi.demo.dao;

import java.util.List;

import co.edu.icesi.demo.modelo.Consignaciones;
import co.edu.icesi.demo.modelo.ConsignacionesId;

public interface IConsignacionesDAO {

	public void crear(Consignaciones consignacion);

	public void modificar(Consignaciones consignacion);

	public void borrar(Consignaciones consignacion);

	public Consignaciones consultarPorID(ConsignacionesId id);

	public List<Consignaciones> consultarTodos();
}
