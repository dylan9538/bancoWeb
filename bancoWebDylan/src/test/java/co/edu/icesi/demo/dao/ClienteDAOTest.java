package co.edu.icesi.demo.dao;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.edu.icesi.demo.modelo.Clientes;
import co.edu.icesi.demo.modelo.TiposDocumentos;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ClienteDAOTest {

	private final static Logger log = LoggerFactory.getLogger(ClienteDAOTest.class);

	@Autowired
	private ITipoDocumentoDAO tipoDocumentoDAO;

	@Autowired
	private IClienteDAO clienteDAO;

	long cliId = 100L;

	@Test
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Rollback(false)
	public void aTestCrear() {
		assertNotNull(tipoDocumentoDAO);
		assertNotNull(clienteDAO);

		Clientes clientes = new Clientes();

		clientes.setCliDireccion("Calle 100 # 20-40");
		clientes.setCliId(cliId);
		clientes.setCliMail("dgomez@gmail.com");
		clientes.setCliNombre("Diego Gomez");
		clientes.setCliTelefono("452-89-63");

		TiposDocumentos tiposDocumentos = tipoDocumentoDAO.consultarPorID(10L);
		assertNotNull(tiposDocumentos);

		clientes.setTiposDocumentos(tiposDocumentos);

		clienteDAO.crear(clientes);

	}

	@Test
	@Transactional(readOnly = true)
	public void bTestConsultarPorId() {
		assertNotNull(tipoDocumentoDAO);
		assertNotNull(clienteDAO);

		Clientes clientes = clienteDAO.consultarPorID(cliId);
		assertNotNull(clientes);

		log.info("id:" + clientes.getCliId());
		log.info("nombre:" + clientes.getCliNombre());

	}

	@Test
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Rollback(false)
	public void cTestModificar() {
		assertNotNull(tipoDocumentoDAO);
		assertNotNull(clienteDAO);

		Clientes clientes = clienteDAO.consultarPorID(cliId);
		assertNotNull(clientes);

		clientes.setCliNombre("Homero J Simpson");

		clienteDAO.modificar(clientes);
	}

	@Test
	@Transactional(readOnly = true)
	public void dTestConsultarTodos() {
		assertNotNull(tipoDocumentoDAO);
		assertNotNull(clienteDAO);

		List<Clientes> losClientes = clienteDAO.consultarTodos();

		for (Clientes clientes : losClientes) {
			log.info("id:" + clientes.getCliId());
			log.info("nombre:" + clientes.getCliNombre());
		}
	}

	@Test
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Rollback(false)
	public void eTestBorrar() {
		assertNotNull(tipoDocumentoDAO);
		assertNotNull(clienteDAO);

		Clientes clientes = clienteDAO.consultarPorID(cliId);
		assertNotNull(clientes);

		clienteDAO.borrar(clientes);

	}

}
