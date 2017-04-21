package co.edu.icesi.demo.logica;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.edu.icesi.demo.dao.ICuentasDAO;
import co.edu.icesi.demo.modelo.Clientes;
import co.edu.icesi.demo.modelo.Consignaciones;
import co.edu.icesi.demo.modelo.Cuentas;
import co.edu.icesi.demo.modelo.CuentasRegistradas;
import co.edu.icesi.demo.modelo.Retiros;
import co.edu.icesi.demo.modelo.Transferencias;

@Scope("singleton")
@Service("cuentaLogica")
public class CuentaLogica implements ICuentaLogica {

	private final static String CLAVE_INICIAL = "****";

	@Autowired
	private ICuentasDAO cuentaDAO;

	@Autowired
	private IClienteLogica clienteLogica;

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void crear(Cuentas cuenta) throws Exception {

		if (cuenta == null) {
			throw new Exception("La cuenta es nula.");
		}

		if (cuenta.getClientes() == null) {
			throw new Exception("El cliente no puede ser nulo.");
		}
		Clientes cliente = clienteLogica.consultarPorID(cuenta.getClientes().getCliId());
		if (cliente == null) {
			throw new Exception("El cliente con ID: " + cuenta.getClientes().getCliId() + " no existe.");
		}

		if (cuenta.getCueNumero() != null && cuenta.getCueNumero().trim().equals(ICuentaLogica.CUENTA_BANCO)) {
		} else {
			String numeroCuenta = generarNumeroCuenta();
			cuenta.setCueNumero(numeroCuenta);

		}

		cuenta.setCueSaldo(new BigDecimal("0"));
		cuenta.setCueActiva("C");
		cuenta.setCueClave(CLAVE_INICIAL);

		cuentaDAO.crear(cuenta);
	}

	public String generarNumeroCuenta() {

		Calendar calendario = Calendar.getInstance();

		String año = calendario.get(Calendar.YEAR) + "";
		año = año.substring(2, 4);

		String mes = (calendario.get(Calendar.MONTH) + 1) + "";
		String dia = calendario.get(Calendar.DATE) + "";

		String hora = calendario.get(Calendar.HOUR_OF_DAY) + "";
		String minuto = calendario.get(Calendar.MINUTE) + "";
		String segundos = calendario.get(Calendar.SECOND) + "";

		String tiempoEje = System.currentTimeMillis() + "";
		tiempoEje = tiempoEje.substring(tiempoEje.length() - 4, tiempoEje.length());

		String cueNumero = año + mes + "-" + dia + hora + "-" + minuto + segundos + "-" + tiempoEje;

		return cueNumero;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void modificar(Cuentas cuenta) throws Exception {
		if (cuenta == null) {
			throw new Exception("La cuenta es nula.");
		}
		if (cuenta.getCueNumero() == null || cuenta.getCueNumero().trim().equals("") == true) {
			throw new Exception("El Número de Cuenta es obligatorio.");
		}
		if (cuenta.getCueSaldo() == null || cuenta.getCueSaldo().equals(0) == true) {
			throw new Exception("El saldo es obligatorio.");
		}
		if (cuenta.getCueActiva() == null || cuenta.getCueActiva().trim().equals("") == true) {
			throw new Exception("La actividad es obligatoria.");
		}
		if (cuenta.getCueClave() == null || cuenta.getCueClave().trim().equals("") == true) {
			throw new Exception("La clave es obligatoria.");
		}
		if (cuenta.getClientes() == null) {
			throw new Exception("El cliente no puede ser nulo.");
		}
		Clientes cliente = clienteLogica.consultarPorID(cuenta.getClientes().getCliId());
		if (cliente == null) {
			throw new Exception("El cliente con ID: " + cuenta.getClientes().getCliId() + " no existe.");
		}
		cuentaDAO.modificar(cuenta);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void borrar(Cuentas cuenta) throws Exception {

		if (cuenta == null) {
			throw new Exception("La cuenta es nula!");
		}

		cuenta.setCueActiva("N");

		modificar(cuenta);
	}

	@Override
	@Transactional(readOnly = true)
	public Cuentas consultarPorID(String cuenta) throws Exception {
		if (cuenta == null) {
			throw new Exception("La cuenta es nula.");
		}
		return cuentaDAO.consultarPorID(cuenta);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Cuentas> consultarTodos() throws Exception {

		return cuentaDAO.consultarTodos();
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void activarCuenta(Cuentas cuenta) throws Exception {

		if (cuenta == null)
			throw new Exception("La Cuenta es Nula!");

		cuenta.setCueActiva("S");

		if (cuenta.getCueClave().equalsIgnoreCase(CLAVE_INICIAL)) {

			String nuevaClave = "";

			String cedulaCliente = cuenta.getClientes().getCliId() + "";
			cedulaCliente = cedulaCliente.substring(cedulaCliente.length() - 4, cedulaCliente.length());

			nuevaClave = cedulaCliente + cuenta.getCueNumero();

			cuenta.setCueClave(nuevaClave);
		}

		modificar(cuenta);

	}

	@Override
	@Transactional(readOnly = true)
	public List<Cuentas> consultarCuentasDeUnCliente(long idCliente) throws Exception {

		return cuentaDAO.consultarCuentasDeUnCliente(idCliente);

	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void crearCuentaRegistrada(CuentasRegistradas nCuentaReg) throws Exception {

		if (nCuentaReg == null) {
			throw new Exception("La entidad es nula!");
		}

		cuentaDAO.crearCuentaRegistrada(nCuentaReg);
	}

	@Override
	@Transactional(readOnly = true)
	public List<CuentasRegistradas> consultarCuentasRegistradasDeUnCliente(long cedulaCliente) {
		return cuentaDAO.consultarCuentasRegistradasDeUnCliente(cedulaCliente);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Consignaciones> consultarConsignaciones(String cueNumero) {
		return cuentaDAO.consultarConsignaciones(cueNumero);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Transferencias> consultarTraslados(String cueNumero) {
		return cuentaDAO.consultarTraslados(cueNumero);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Retiros> consultarRetiros(String cueNumero) {
		return cuentaDAO.consultarRetiros(cueNumero);
	}

}
