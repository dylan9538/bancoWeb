package co.edu.icesi.demo.vista;

import java.math.BigDecimal;
import java.util.List;

import co.edu.icesi.demo.modelo.Clientes;
import co.edu.icesi.demo.modelo.Consignaciones;
import co.edu.icesi.demo.modelo.Cuentas;
import co.edu.icesi.demo.modelo.CuentasRegistradas;
import co.edu.icesi.demo.modelo.Retiros;
import co.edu.icesi.demo.modelo.TiposDocumentos;
import co.edu.icesi.demo.modelo.TiposUsuarios;
import co.edu.icesi.demo.modelo.Transferencias;
import co.edu.icesi.demo.modelo.Usuarios;

public interface IDelegadoDeNegocio {

	public List<Clientes> consultarTodosClientes() throws Exception;

	public void crearCliente(Clientes cliente) throws Exception;

	public void modificarCliente(Clientes cliente) throws Exception;

	public void borrarCliente(Clientes cleinte) throws Exception;

	public Clientes consultarClientePorID(Long id) throws Exception;

	public void crearCuentas(Cuentas cuenta) throws Exception;

	public void modificarCuentas(Cuentas cuenta) throws Exception;

	public void borrarCuentas(Cuentas cuenta) throws Exception;

	public Cuentas consultarCuentasPorID(String cuenta) throws Exception;

	public List<Cuentas> consultarTodasCuentas() throws Exception;

	public List<TiposDocumentos> consultarTodosTiposDocumentos() throws Exception;

	public void crearTiposDocumentos(TiposDocumentos tipoDocumento) throws Exception;

	public void modificarTiposDocumentos(TiposDocumentos tipoDocumento) throws Exception;

	public void borrarTiposDocumentos(TiposDocumentos tipoDocumento) throws Exception;

	public TiposDocumentos consultarTiposDocumentosPorID(Long id) throws Exception;

	public List<TiposUsuarios> consultarTodosTiposUsuarios() throws Exception;

	public void crearTiposUsuarios(TiposUsuarios tiposUsuarios) throws Exception;

	public void modificarTiposUsuarios(TiposUsuarios tiposUsuarios) throws Exception;

	public void borrarTiposUsuarios(TiposUsuarios tiposUsuarios) throws Exception;

	public TiposUsuarios consultarTiposUsuariosPorID(Long id) throws Exception;

	public List<Usuarios> consultarTodosUsuarios() throws Exception;

	public void crearUsuarios(Usuarios usuario) throws Exception;

	public void modificarUsuarios(Usuarios usuario) throws Exception;

	public void borrarUsuarios(Usuarios usuario) throws Exception;

	public Usuarios consultarUsuariosPorID(long id) throws Exception;
	
	public void retirar(String cueNumero, BigDecimal valor, long identificacionCliente, long usuCedula,
			String descripcion) throws Exception;

	public void consignar(String cueNumero, BigDecimal valor, long identificacionCliente, long usuCedula,
			String descripcion) throws Exception;

	public void transladoEntreCuentas(String cueNumeroOrigen, String cueNumeroDestino, BigDecimal valor,
			long identificacionClienteOrigen, long identificacionClienteDestino, long usuCedula, String descripcion)
			throws Exception;
	
	public Usuarios validarUsuario(String usuLogin, String usuClave) throws Exception;
	
	public List<Cuentas> consultarCuentasDeUnCliente(long idCliente) throws Exception;

	public void asociarCuenta(CuentasRegistradas nCuentaReg) throws Exception;
	
	public void activarCuenta(Cuentas cuenta) throws Exception;

	public List<CuentasRegistradas> consultarCuentasRegistradasDeUnCliente(long cedulaCliente);

	public List<Consignaciones> consultarConsignaciones(String cueNumero);

	public List<Transferencias> consultarTraslados(String cueNumero);

	public List<Retiros> consultarRetiros(String cueNumero);
}
