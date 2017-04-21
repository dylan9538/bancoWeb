package co.edu.icesi.demo.vista;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import co.edu.icesi.demo.logica.IClienteLogica;
import co.edu.icesi.demo.logica.ICuentaLogica;
import co.edu.icesi.demo.logica.ITipoDocumentoLogica;
import co.edu.icesi.demo.logica.ITipoUsuarioLogica;
import co.edu.icesi.demo.logica.ITransaccionBancariaLogica;
import co.edu.icesi.demo.logica.IUsuarioLogica;
import co.edu.icesi.demo.modelo.Clientes;
import co.edu.icesi.demo.modelo.Consignaciones;
import co.edu.icesi.demo.modelo.Cuentas;
import co.edu.icesi.demo.modelo.CuentasRegistradas;
import co.edu.icesi.demo.modelo.Retiros;
import co.edu.icesi.demo.modelo.TiposDocumentos;
import co.edu.icesi.demo.modelo.TiposUsuarios;
import co.edu.icesi.demo.modelo.Transferencias;
import co.edu.icesi.demo.modelo.Usuarios;

@Scope("singleton")
@Component("delegadoDeNegocio")
public class DelegadoDeNegocio implements IDelegadoDeNegocio {

	@Autowired
	private IClienteLogica clienteLogica;

	@Autowired
	private ICuentaLogica cuentaLogica;

	@Autowired
	private ITipoDocumentoLogica tipoDocumentoLogica;

	@Autowired
	private ITipoUsuarioLogica tipoUsuarioLogica;

	@Autowired
	private IUsuarioLogica usuarioLogica;

	@Autowired
	private ITransaccionBancariaLogica transaccionBancariaLogica;

	@Override
	public List<Clientes> consultarTodosClientes() throws Exception {
		return clienteLogica.consultarTodos();
	}

	@Override
	public List<Cuentas> consultarTodasCuentas() throws Exception {
		return cuentaLogica.consultarTodos();
	}

	@Override
	public List<TiposDocumentos> consultarTodosTiposDocumentos() throws Exception {
		return tipoDocumentoLogica.consultarTodos();
	}

	@Override
	public List<TiposUsuarios> consultarTodosTiposUsuarios() throws Exception {
		return tipoUsuarioLogica.consultarTodos();
	}

	@Override
	public List<Usuarios> consultarTodosUsuarios() throws Exception {
		return usuarioLogica.consultarTodos();
	}

	@Override
	public void crearCliente(Clientes cliente) throws Exception {

		clienteLogica.crear(cliente);
	}

	@Override
	public void modificarCliente(Clientes cliente) throws Exception {

		clienteLogica.modificar(cliente);
	}

	@Override
	public void borrarCliente(Clientes cleinte) throws Exception {

		clienteLogica.borrar(cleinte);
	}

	@Override
	public Clientes consultarClientePorID(Long id) throws Exception {

		return clienteLogica.consultarPorID(id);
	}

	@Override
	public TiposDocumentos consultarTiposDocumentosPorID(Long id) throws Exception {

		return tipoDocumentoLogica.consultarPorID(id);
	}

	@Override
	public void crearUsuarios(Usuarios usuario) throws Exception {
		usuarioLogica.crear(usuario);

	}

	@Override
	public void modificarUsuarios(Usuarios usuario) throws Exception {
		usuarioLogica.modificar(usuario);

	}

	@Override
	public void borrarUsuarios(Usuarios usuario) throws Exception {
		usuarioLogica.borrar(usuario);

	}

	@Override
	public Usuarios consultarUsuariosPorID(long id) throws Exception {
		return usuarioLogica.consultarPorID(id);
	}

	@Override
	public void crearTiposUsuarios(TiposUsuarios tiposUsuarios) throws Exception {
		tipoUsuarioLogica.crear(tiposUsuarios);

	}

	@Override
	public void modificarTiposUsuarios(TiposUsuarios tiposUsuarios) throws Exception {
		tipoUsuarioLogica.modificar(tiposUsuarios);

	}

	@Override
	public void borrarTiposUsuarios(TiposUsuarios tiposUsuarios) throws Exception {
		tipoUsuarioLogica.borrar(tiposUsuarios);

	}

	@Override
	public TiposUsuarios consultarTiposUsuariosPorID(Long id) throws Exception {
		return tipoUsuarioLogica.consultarPorID(id);
	}

	@Override
	public void crearTiposDocumentos(TiposDocumentos tipoDocumento) throws Exception {
		tipoDocumentoLogica.crear(tipoDocumento);

	}

	@Override
	public void modificarTiposDocumentos(TiposDocumentos tipoDocumento) throws Exception {
		tipoDocumentoLogica.modificar(tipoDocumento);

	}

	@Override
	public void borrarTiposDocumentos(TiposDocumentos tipoDocumento) throws Exception {
		tipoDocumentoLogica.borrar(tipoDocumento);

	}

	@Override
	public void crearCuentas(Cuentas cuenta) throws Exception {
		
		cuentaLogica.crear(cuenta);

	}

	@Override
	public void modificarCuentas(Cuentas cuenta) throws Exception {
		cuentaLogica.modificar(cuenta);

	}

	@Override
	public void borrarCuentas(Cuentas cuenta) throws Exception {
		cuentaLogica.borrar(cuenta);

	}

	@Override
	public Cuentas consultarCuentasPorID(String cuenta) throws Exception {
		return cuentaLogica.consultarPorID(cuenta);
	}

	@Override
	public void retirar(String cueNumero, BigDecimal valor, long identificacionCliente, long usuCedula,
			String descripcion) throws Exception {
		transaccionBancariaLogica.retirar(cueNumero, valor, identificacionCliente, usuCedula, descripcion);

	}

	@Override
	public void consignar(String cueNumero, BigDecimal valor, long identificacionCliente, long usuCedula,
			String descripcion) throws Exception {
		transaccionBancariaLogica.consignar(cueNumero, valor, identificacionCliente, usuCedula, descripcion);

	}

	@Override
	public void transladoEntreCuentas(String cueNumeroOrigen, String cueNumeroDestino, BigDecimal valor,
			long identificacionClienteOrigen, long identificacionClienteDestino, long usuCedula, String descripcion)
			throws Exception {
		transaccionBancariaLogica.transladoEntreCuentas(cueNumeroOrigen, cueNumeroDestino, valor,
				identificacionClienteOrigen, identificacionClienteDestino, usuCedula, descripcion);

	}

	@Override
	public Usuarios validarUsuario(String usuLogin, String usuClave) throws Exception {
		
		return usuarioLogica.validarUsuario(usuLogin, usuClave);
	}

	@Override
	public List<Cuentas> consultarCuentasDeUnCliente(long idCliente) throws Exception {
		
		return cuentaLogica.consultarCuentasDeUnCliente(idCliente);
		
	}

	@Override
	public void asociarCuenta(CuentasRegistradas nCuentaReg) throws Exception {
		cuentaLogica.crearCuentaRegistrada(nCuentaReg);
		
	}

	@Override
	public void activarCuenta(Cuentas cuenta) throws Exception {
			cuentaLogica.activarCuenta(cuenta);
		
	}

	@Override
	public List<CuentasRegistradas> consultarCuentasRegistradasDeUnCliente(long cedulaCliente) {
		
		return cuentaLogica.consultarCuentasRegistradasDeUnCliente(cedulaCliente);

	}

	@Override
	public List<Consignaciones> consultarConsignaciones(String cueNumero) {
		return cuentaLogica.consultarConsignaciones(cueNumero);
	}

	@Override
	public List<Transferencias> consultarTraslados(String cueNumero) {
		return cuentaLogica.consultarTraslados(cueNumero);
	}

	@Override
	public List<Retiros> consultarRetiros(String cueNumero) {
		return cuentaLogica.consultarRetiros(cueNumero);
	}

}