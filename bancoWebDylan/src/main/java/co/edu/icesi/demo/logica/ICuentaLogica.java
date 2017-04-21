package co.edu.icesi.demo.logica;

import java.util.List;

import co.edu.icesi.demo.modelo.Consignaciones;
import co.edu.icesi.demo.modelo.Cuentas;
import co.edu.icesi.demo.modelo.CuentasRegistradas;
import co.edu.icesi.demo.modelo.Retiros;
import co.edu.icesi.demo.modelo.Transferencias;

public interface ICuentaLogica {
	public final static String CUENTA_BANCO = "9999-9999-0000";

	public void crear(Cuentas cuenta) throws Exception;

	public void modificar(Cuentas cuenta) throws Exception;

	public void borrar(Cuentas cuenta) throws Exception;

	public Cuentas consultarPorID(String cuenta) throws Exception;

	public List<Cuentas> consultarTodos() throws Exception;
	
	public void activarCuenta(Cuentas cuenta) throws Exception;
	
	public List<Cuentas> consultarCuentasDeUnCliente(long idCliente) throws Exception;

	public void crearCuentaRegistrada(CuentasRegistradas nCuentaReg)throws Exception;

	public List<CuentasRegistradas> consultarCuentasRegistradasDeUnCliente(long cedulaCliente);

	public List<Consignaciones> consultarConsignaciones(String cueNumero);

	public List<Transferencias> consultarTraslados(String cueNumero);

	public List<Retiros> consultarRetiros(String cueNumero);
}