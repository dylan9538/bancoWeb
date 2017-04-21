package co.edu.icesi.demo.logica;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.edu.icesi.demo.dao.IClienteDAO;
import co.edu.icesi.demo.modelo.Clientes;
import co.edu.icesi.demo.modelo.Cuentas;
import co.edu.icesi.demo.modelo.TiposDocumentos;

@Scope("singleton")
@Service("clienteLogica")
public class ClienteLogica implements IClienteLogica {

	private static final int LONGITUD_MINIMA_CEDULA = 8;

	@Autowired
	private ITipoDocumentoLogica tipoDocumentoLogica;

	@Autowired
	private ICuentaLogica cuentaLogica;
	
	@Autowired
	private IClienteDAO clienteDAO;

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void crear(Clientes cliente) throws Exception {

		if (cliente == null) {
			throw new Exception("El cliente es nulo.");
		}
		if (cliente.getCliMail() == null || cliente.getCliMail().trim().equals("") == true) {
			throw new Exception("El correo electrónico es obligatorio.");
		}
		if (cliente.getCliDireccion() == null || cliente.getCliDireccion().trim().equals("") == true) {
			throw new Exception("La dirección es obligatoria.");
		}
		if (cliente.getCliId() == 0) {
			throw new Exception("El ID es obligatorio.");
		}
		
		if (String.valueOf(cliente.getCliId()).length() < LONGITUD_MINIMA_CEDULA) {
			throw new Exception("La longitud de la cedula debe ser mínimo de 8 dígitos.");
		}
		
		if (cliente.getCliNombre() == null || cliente.getCliNombre().trim().equals("") == true) {
			throw new Exception("El nombre es obligatorio.");
		}
		if (cliente.getCliTelefono() == null || cliente.getCliTelefono().trim().equals("") == true) {
			throw new Exception("El teléfono es obligatorio.");
		}
		if (cliente.getTiposDocumentos() == null) {
			throw new Exception("El tipo de documento no puede ser nulo.");
		}
		TiposDocumentos tiposDocumentos = tipoDocumentoLogica
				.consultarPorID(cliente.getTiposDocumentos().getTdocCodigo());
		if (tiposDocumentos == null) {
			throw new Exception(
					"El tipo de documento con ID: " + cliente.getTiposDocumentos().getTdocCodigo() + " no existe.");
		}
		
		clienteDAO.crear(cliente);
		
		Cuentas cuenta = new Cuentas();
		cuenta.setClientes(cliente);
		
		cuentaLogica.crear(cuenta);
		
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void modificar(Clientes cliente) throws Exception {
		if (cliente == null) {
			throw new Exception("El cliente es nulo.");
		}
		if (cliente.getCliMail() == null || cliente.getCliMail().trim().equals("") == true) {
			throw new Exception("El correo electrónico es obligatorio.");
		}
		if (cliente.getCliDireccion() == null || cliente.getCliDireccion().trim().equals("") == true) {
			throw new Exception("La dirección es obligatoria.");
		}
		if (cliente.getCliId() == 0) {
			throw new Exception("El ID es obligatorio.");
		}
		if (cliente.getCliNombre() == null || cliente.getCliNombre().trim().equals("") == true) {
			throw new Exception("El nombre es obligatorio.");
		}
		if (cliente.getCliTelefono() == null || cliente.getCliTelefono().trim().equals("") == true) {
			throw new Exception("El teléfono es obligatorio.");
		}
		if (cliente.getTiposDocumentos() == null) {
			throw new Exception("El tipo de documento no puede ser nulo.");
		}
		TiposDocumentos tiposDocumentos = tipoDocumentoLogica
				.consultarPorID(cliente.getTiposDocumentos().getTdocCodigo());
		if (tiposDocumentos == null) {
			throw new Exception(
					"El tipo de documento con ID: " + cliente.getTiposDocumentos().getTdocCodigo() + " no existe.");
		}
		clienteDAO.modificar(cliente);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void borrar(Clientes cliente) throws Exception {
		if (cliente == null) {
			throw new Exception("El cliente es nulo.");
		}
		clienteDAO.borrar(cliente);

	}

	@Override
	@Transactional(readOnly = true)
	public Clientes consultarPorID(Long id) throws Exception {
		if (id == null) {
			throw new Exception("El ID es nulo.");
		}
		return clienteDAO.consultarPorID(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Clientes> consultarTodos() throws Exception {

		return clienteDAO.consultarTodos();
	}

}
