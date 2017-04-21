package co.edu.icesi.demo.logica;

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

import co.edu.icesi.demo.modelo.Clientes;
import co.edu.icesi.demo.modelo.TiposDocumentos;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ClienteLogicaTest {

	private final static Logger log = LoggerFactory.getLogger(ClienteLogicaTest.class);

	@Autowired
	private ITipoDocumentoLogica tipoDocumentoLogica;

	@Autowired
	private IClienteLogica clienteLogica;

	long cliId = 100L;

	@Test
	@Rollback(false)
	public void aTestCrear() throws Exception {
		assertNotNull(tipoDocumentoLogica);
		assertNotNull(clienteLogica);

		Clientes clientes = new Clientes();

		clientes.setCliDireccion("Calle 100 # 20-40");
		clientes.setCliId(cliId);
		clientes.setCliMail("dgomez@gmail.com");
		clientes.setCliNombre("Diego Gomez");
		clientes.setCliTelefono("452-89-63");

		TiposDocumentos tiposDocumentos = tipoDocumentoLogica.consultarPorID(10L);
		assertNotNull(tiposDocumentos);

		clientes.setTiposDocumentos(tiposDocumentos);

		clienteLogica.crear(clientes);

	}

	@Test
	public void bTestConsultarPorId() throws Exception {
		assertNotNull(tipoDocumentoLogica);
		assertNotNull(clienteLogica);

		Clientes clientes = clienteLogica.consultarPorID(cliId);
		assertNotNull(clientes);

		log.info("id:" + clientes.getCliId());
		log.info("nombre:" + clientes.getCliNombre());

	}

	@Test
	@Rollback(false)
	public void cTestModificar() throws Exception {
		assertNotNull(tipoDocumentoLogica);
		assertNotNull(clienteLogica);

		Clientes clientes = clienteLogica.consultarPorID(cliId);
		assertNotNull(clientes);

		clientes.setCliNombre("Homero J Simpson");

		clienteLogica.modificar(clientes);
	}

	@Test
	public void dTestConsultarTodos() throws Exception {
		assertNotNull(tipoDocumentoLogica);
		assertNotNull(clienteLogica);

		List<Clientes> losClientes = clienteLogica.consultarTodos();

		for (Clientes clientes : losClientes) {
			log.info("id:" + clientes.getCliId());
			log.info("nombre:" + clientes.getCliNombre());
		}
	}

	@Test
	@Rollback(false)
	public void eTestBorrar() throws Exception {
		assertNotNull(tipoDocumentoLogica);
		assertNotNull(clienteLogica);

		Clientes clientes = clienteLogica.consultarPorID(cliId);
		assertNotNull(clientes);

		clienteLogica.borrar(clientes);

	}

}
