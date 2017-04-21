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

import co.edu.icesi.demo.modelo.TiposDocumentos;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TipoDocumentoLogicaTest {

	private final static Logger log = LoggerFactory.getLogger(TipoDocumentoLogicaTest.class);

	@Autowired
	private ITipoDocumentoLogica tipoDocumentoLogica;

	long tdocCodigo = 100L;

	@Test
	@Rollback(false)
	public void aTestCrear() throws Exception {
		assertNotNull(tipoDocumentoLogica);

		TiposDocumentos tiposDocumentos = new TiposDocumentos();
		tiposDocumentos.setTdocCodigo(tdocCodigo);
		tiposDocumentos.setTdocNombre("CARNET TEST");

		tipoDocumentoLogica.crear(tiposDocumentos);
	}

	@Test
	public void bTestConsultarPorId() throws Exception {
		assertNotNull(tipoDocumentoLogica);

		TiposDocumentos tiposDocumentos = tipoDocumentoLogica.consultarPorID(tdocCodigo);
		assertNotNull(tiposDocumentos);

		log.info("id:" + tiposDocumentos.getTdocCodigo());
		log.info("nombre:" + tiposDocumentos.getTdocNombre());

	}

	@Test
	@Rollback(false)
	public void cTestModificar() throws Exception {
		assertNotNull(tipoDocumentoLogica);

		TiposDocumentos tiposDocumentos = tipoDocumentoLogica.consultarPorID(tdocCodigo);
		assertNotNull(tiposDocumentos);

		tiposDocumentos.setTdocNombre("CARNET");

		tipoDocumentoLogica.modificar(tiposDocumentos);
	}

	@Test
	public void dTestConsultarTodos() throws Exception {
		assertNotNull(tipoDocumentoLogica);

		List<TiposDocumentos> losTiposDocumentos = tipoDocumentoLogica.consultarTodos();

		for (TiposDocumentos tiposDocumentos : losTiposDocumentos) {
			log.info("id:" + tiposDocumentos.getTdocCodigo());
			log.info("nombre:" + tiposDocumentos.getTdocNombre());
		}
	}

	@Test
	@Rollback(false)
	public void eTestBorrar() throws Exception {
		assertNotNull(tipoDocumentoLogica);

		TiposDocumentos tiposDocumentos = tipoDocumentoLogica.consultarPorID(tdocCodigo);
		assertNotNull(tiposDocumentos);

		tipoDocumentoLogica.borrar(tiposDocumentos);
	}

}
