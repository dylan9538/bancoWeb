package co.edu.icesi.demo.logica;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.edu.icesi.demo.dao.IConsignacionesDAO;
import co.edu.icesi.demo.dao.IRetirosDAO;
import co.edu.icesi.demo.dao.ITransferenciaDAO;
import co.edu.icesi.demo.modelo.Clientes;
import co.edu.icesi.demo.modelo.Consignaciones;
import co.edu.icesi.demo.modelo.ConsignacionesId;
import co.edu.icesi.demo.modelo.Cuentas;
import co.edu.icesi.demo.modelo.Retiros;
import co.edu.icesi.demo.modelo.RetirosId;
import co.edu.icesi.demo.modelo.Transferencias;
import co.edu.icesi.demo.modelo.Usuarios;

@Scope("singleton")
@Service("transaccionBancariaLogica")
public class TransaccionBancariaLogica implements ITransaccionBancariaLogica {

	private final static BigDecimal VALOR_TRANSACCION = new BigDecimal(1000);
	private final static long ID_BANCO = 111222333L;
	private final static long TIPO_DOC_ID = 10L;

	@Autowired
	private IUsuarioLogica usuarioLogica;
	@Autowired
	private IConsignacionesDAO consignacionesDAO;
	@Autowired
	private IRetirosDAO retirosDAO;
	@Autowired
	private ICuentaLogica cuentaLogica;
	@Autowired
	private IClienteLogica clienteLogica;
	@Autowired
	private ITipoDocumentoLogica tipoDocLogica;
	@Autowired
	private ITransferenciaDAO transferenciaDAO;

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void retirar(String cueNumero, BigDecimal valor, long identificacionCliente, long usuCedula,
			String descripcion) throws Exception {

		if (cueNumero.trim().equals("") || cueNumero == null) {
			throw new Exception("La cuenta no puede ser nula.");
		}

		if (valor.compareTo(new BigDecimal(0)) == 0 || valor.compareTo(new BigDecimal(0)) == -1 || valor == null) {
			throw new Exception("El valor no puede ser menor o igual a cero o nulo.");
		}

		if (identificacionCliente == 0) {
			throw new Exception("La identificación del cliente no puede ser null.");
		}

		if (descripcion.trim().equals("") || descripcion == null) {
			throw new Exception("La descripcion no puede ser null.");
		}

		Usuarios entity = usuarioLogica.consultarPorID(usuCedula);
		if (entity == null) {
			throw new Exception("La Cedula con el numero " + usuCedula + " no existe.");
		}

		Cuentas cuentaRetiro = cuentaLogica.consultarPorID(cueNumero);
		if (cuentaRetiro == null) {
			throw new Exception("La cuenta con el numero" + cueNumero + " no existe.");
		}

		if (!cuentaRetiro.getCueActiva().trim().equals("S")) {
			throw new Exception("La cuenta no esta activa.");
		}

		if (cuentaRetiro.getClientes().getCliId() != identificacionCliente) {
			throw new Exception("El cliente no es dueño de esta cuenta.");
		}
		if (cuentaRetiro.getCueSaldo().equals(0)) {
			throw new Exception("No hay saldo en la cuenta.");
		}

		if (cuentaRetiro.getCueSaldo().compareTo(valor) < 0) {
			throw new Exception("El valor a retirar es mayor al saldo de la cuenta.");
		}

		Retiros retiro = new Retiros();
		RetirosId retiroId = new RetirosId();

		retiroId.setRetCodigo(generarCodigoRetiro());
		retiroId.setCueNumero(cueNumero);

		retiro.setId(retiroId);
		retiro.setRetValor(valor);
		retiro.setRetFecha(new Date());
		retiro.setRetDescripcion(descripcion);
		retiro.setUsuarios(entity);
		retiro.setCuentas(cuentaRetiro);
		retirosDAO.crear(retiro);

		cuentaRetiro.setCueSaldo(cuentaRetiro.getCueSaldo().subtract(valor));
		cuentaLogica.modificar(cuentaRetiro);

	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void consignar(String cueNumero, BigDecimal valor, long identificacionCliente, long usuCedula,
			String descripcion) throws Exception {

		Consignaciones consignaciones = new Consignaciones();

		if (cueNumero.trim().equals("") || cueNumero == null) {
			throw new Exception("La cuenta no puede ser nula.");
		}

		if (valor.compareTo(new BigDecimal(0)) == 0 || valor.compareTo(new BigDecimal(0)) == -1 || valor == null) {
			throw new Exception("El valor no puede ser menor o igual a cero o nulo.");
		}

		if (identificacionCliente == 0) {
			throw new Exception("La identificación del cliente no puede ser null.");
		}

		if (descripcion.trim().equals("") || descripcion == null) {
			throw new Exception("La descripcion no puede ser null.");
		}

		if (usuCedula != 0) {

			Usuarios entity = usuarioLogica.consultarPorID(usuCedula);
			if (entity == null) {
				throw new Exception("La cedula con el numero " + usuCedula + " no existe.");
			}

			consignaciones.setUsuarios(entity);

		} else {
			consignaciones.setUsuarios(new Usuarios());
		}

		Cuentas cuentaConsignacion = cuentaLogica.consultarPorID(cueNumero);
		if (cuentaConsignacion == null) {
			throw new Exception("La cuenta con el numero" + cueNumero + " no existe.");
		}

		if (cuentaConsignacion.getCueActiva().trim().equalsIgnoreCase("C")
				&& (valor.compareTo(new BigDecimal(99900)) > 0)) {
			cuentaLogica.activarCuenta(cuentaConsignacion);
		}
		
		if (cuentaConsignacion.getCueActiva().trim().equalsIgnoreCase("C")
				&& (valor.compareTo(new BigDecimal(100050)) < 0)) {
			throw new Exception("La cuenta no esta activa. Consigne un saldo mínimo de $100.000 para activarla");

		}

		if (cuentaConsignacion.getClientes().getCliId() != identificacionCliente) {
			throw new Exception("El cliente no es dueño de esta cuenta.");
		}

		ConsignacionesId consignacionesId = new ConsignacionesId();

		consignacionesId.setConCodigo(generarCodigoConsignacion());
		consignacionesId.setCueNumero(cueNumero);

		consignaciones.setId(consignacionesId);
		consignaciones.setConDescripcion(descripcion);
		consignaciones.setConFecha(new Date());
		consignaciones.setConValor(valor);
		consignaciones.setCuentas(cuentaConsignacion);

		consignacionesDAO.crear(consignaciones);

		cuentaConsignacion.setCueSaldo(cuentaConsignacion.getCueSaldo().add(valor));
		cuentaLogica.modificar(cuentaConsignacion);

	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void transladoEntreCuentas(String cueNumeroOrigen, String cueNumeroDestino, BigDecimal valor,
			long identificacionClienteOrigen, long identificacionClienteDestino, long usuCedula, String descripcion)
			throws Exception {

		Cuentas cuentaOrigen = cuentaLogica.consultarPorID(cueNumeroOrigen);

		BigDecimal total = valor.add(VALOR_TRANSACCION);

		System.out.println(total + " Vs. " + cuentaOrigen.getCueSaldo().intValue());

		int dif = total.subtract(cuentaOrigen.getCueSaldo()).intValue();

		if (dif > 0) {
			throw new Exception("No hay suficiente Saldo para hacer la transacción!");
		}

		retirar(cueNumeroOrigen, valor, identificacionClienteOrigen, usuCedula, descripcion);
		consignar(cueNumeroDestino, valor, identificacionClienteDestino, usuCedula, descripcion);

		Transferencias transferencia = new Transferencias();

		Clientes cliente = clienteLogica.consultarPorID(identificacionClienteOrigen);
		transferencia.setClientes(cliente);

		Cuentas cuentaDestino = cuentaLogica.consultarPorID(cueNumeroDestino);
		transferencia.setCuentasByCueNumeroDestino(cuentaDestino);

		transferencia.setCuentasByCueNumeroOrigen(cuentaOrigen);

		String codigo = (generarCodigoRetiro() + generarCodigoRetiro() + "");
		codigo = codigo.substring(0, (int) codigo.length() / 2);

		transferencia.setTrasCodigo(Long.parseLong(codigo));

		transferencia.setTrasDescripcion(descripcion);
		transferencia.setTrasFecha(new Date());

		transferencia.setTrasValor(valor);

		transferenciaDAO.crear(transferencia);

		Cuentas cuentaBanco = cuentaLogica.consultarPorID(ICuentaLogica.CUENTA_BANCO);

		if (cuentaBanco == null) {
			cuentaBanco = generarCuenta();
		}

		retirar(cueNumeroOrigen, VALOR_TRANSACCION, identificacionClienteOrigen, usuCedula, "Cobro Por Transacción!");
		consignar(ICuentaLogica.CUENTA_BANCO, VALOR_TRANSACCION, cuentaBanco.getClientes().getCliId(), usuCedula,
				"Cobro Por Transacción!");

	}

	private Cuentas generarCuenta() throws Exception {

		Cuentas nCuenta = new Cuentas();

		Clientes banco = clienteLogica.consultarPorID(ID_BANCO);

		if (banco == null) {
			banco = new Clientes();
			banco.setCliDireccion("Dirección Banco");
			banco.setCliId(ID_BANCO);
			banco.setCliMail("bancoBryan@hotmail.com");
			banco.setCliNombre("Banco Bryan");
			banco.setCliTelefono("318 258 8089");
			banco.setTiposDocumentos(tipoDocLogica.consultarPorID(TIPO_DOC_ID));

			clienteLogica.crear(banco);
			;
		}

		nCuenta.setClientes(banco);
		nCuenta.setCueNumero(ICuentaLogica.CUENTA_BANCO);

		cuentaLogica.crear(nCuenta);
		cuentaLogica.activarCuenta(nCuenta);

		return nCuenta;
	}

	private long generarCodigoRetiro() {
		String nano = "" + System.nanoTime();
		nano = nano.substring(nano.length() - 7, nano.length());

		return Long.parseLong(nano);
	}

	private long generarCodigoConsignacion() {
		String nano = "" + System.nanoTime();
		nano = nano.substring(nano.length() - 7, nano.length());

		return Long.parseLong(nano);
	}

}
