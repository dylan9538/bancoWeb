package co.edu.icesi.demo.dao;

import static org.junit.Assert.*;

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

import co.edu.icesi.demo.modelo.TiposDocumentos;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TipoDocumentoDAOTest {

	private final static Logger log = LoggerFactory.getLogger(TipoDocumentoDAOTest.class);

	@Autowired
	private ITipoDocumentoDAO tipoDocumentoDAO;

	long tdocCodigo = 100L;

	@Test
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Rollback(false)
	public void aTestCrear() {
		assertNotNull(tipoDocumentoDAO);

		TiposDocumentos tiposDocumentos = new TiposDocumentos();
		tiposDocumentos.setTdocCodigo(tdocCodigo);
		tiposDocumentos.setTdocNombre("CARNET TEST");

		tipoDocumentoDAO.crear(tiposDocumentos);
	}

	@Test
	@Transactional(readOnly = true)
	public void bTestConsultarPorId() {
		assertNotNull(tipoDocumentoDAO);

		TiposDocumentos tiposDocumentos = tipoDocumentoDAO.consultarPorID(tdocCodigo);
		assertNotNull(tiposDocumentos);

		log.info("id:" + tiposDocumentos.getTdocCodigo());
		log.info("nombre:" + tiposDocumentos.getTdocNombre());

	}

	@Test
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Rollback(false)
	public void cTestModificar() {
		assertNotNull(tipoDocumentoDAO);

		TiposDocumentos tiposDocumentos = tipoDocumentoDAO.consultarPorID(tdocCodigo);
		assertNotNull(tiposDocumentos);

		tiposDocumentos.setTdocNombre("CARNET");

		tipoDocumentoDAO.modificar(tiposDocumentos);
	}

	@Test
	@Transactional(readOnly = true)
	public void dTestConsultarTodos() {
		assertNotNull(tipoDocumentoDAO);

		List<TiposDocumentos> losTiposDocumentos = tipoDocumentoDAO.consultarTodos();

		for (TiposDocumentos tiposDocumentos : losTiposDocumentos) {
			log.info("id:" + tiposDocumentos.getTdocCodigo());
			log.info("nombre:" + tiposDocumentos.getTdocNombre());
		}
	}

	@Test
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Rollback(false)
	public void eTestBorrar() {
		assertNotNull(tipoDocumentoDAO);

		TiposDocumentos tiposDocumentos = tipoDocumentoDAO.consultarPorID(tdocCodigo);
		assertNotNull(tiposDocumentos);

		tipoDocumentoDAO.borrar(tiposDocumentos);
	}

}
