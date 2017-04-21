package co.edu.icesi.demo.dao;

import java.util.List;

import co.edu.icesi.demo.modelo.Consignaciones;
import co.edu.icesi.demo.modelo.Cuentas;
import co.edu.icesi.demo.modelo.CuentasRegistradas;
import co.edu.icesi.demo.modelo.Retiros;
import co.edu.icesi.demo.modelo.Transferencias;

public interface ICuentasDAO {

	public void crear(Cuentas cuentas);

	public void modificar(Cuentas cuentas);

	public void borrar(Cuentas cuentas);

	public Cuentas consultarPorID(String id);

	public List<Cuentas> consultarTodos();
	
	public List<Cuentas> consultarCuentasDeUnCliente(long idCliente) throws Exception;

	public void crearCuentaRegistrada(CuentasRegistradas nCuentaReg);

	public List<CuentasRegistradas> consultarCuentasRegistradasDeUnCliente(long cedulaCliente);

	public List<Transferencias> consultarTraslados(String cueNumero);

	public List<Consignaciones> consultarConsignaciones(String cueNumero);

	public List<Retiros> consultarRetiros(String cueNumero);	
}
