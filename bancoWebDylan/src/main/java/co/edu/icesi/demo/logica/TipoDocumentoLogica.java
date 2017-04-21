package co.edu.icesi.demo.logica;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.edu.icesi.demo.dao.ITipoDocumentoDAO;
import co.edu.icesi.demo.modelo.TiposDocumentos;

@Scope("singleton")
@Service("tipoDocumentoLogica")
public class TipoDocumentoLogica implements ITipoDocumentoLogica {

	@Autowired
	private ITipoDocumentoDAO tipoDocumentoDAO;

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void crear(TiposDocumentos tipoDocumento) throws Exception {
		if (tipoDocumento == null) {
			throw new Exception("El tipo de documento no puede ser nulo.");
		}
		if (tipoDocumento.getTdocCodigo() == 0) {
			throw new Exception("El código es obligatorio.");
		}
		if (tipoDocumento.getTdocNombre() == null || tipoDocumento.getTdocNombre().trim().equals("") == true) {
			throw new Exception("El nombre es obligatorio.");
		}
		TiposDocumentos entity = tipoDocumentoDAO.consultarPorID(tipoDocumento.getTdocCodigo());

		if (entity != null) {
			throw new Exception("Ya existe el tipo documento con ID: " + tipoDocumento.getTdocCodigo());
		}

		tipoDocumentoDAO.crear(tipoDocumento);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void modificar(TiposDocumentos tipoDocumento) throws Exception {
		if (tipoDocumento == null) {
			throw new Exception("El tipo de documento no puede ser nulo.");
		}
		if (tipoDocumento.getTdocCodigo() == 0) {
			throw new Exception("El código es obligatorio.");
		}
		if (tipoDocumento.getTdocNombre() == null || tipoDocumento.getTdocNombre().trim().equals("") == true) {
			throw new Exception("El nombre es obligatorio.");
		}

		tipoDocumentoDAO.modificar(tipoDocumento);

	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void borrar(TiposDocumentos tipoDocumento) throws Exception {
		if (tipoDocumento == null) {
			throw new Exception("El tipo de documento no puede ser nulo.");
		}

		tipoDocumentoDAO.borrar(tipoDocumento);

	}

	@Override
	@Transactional(readOnly = true)
	public TiposDocumentos consultarPorID(Long id) throws Exception {
		if (id == 0) {
			throw new Exception("El ID es obligatorio.");
		}
		return tipoDocumentoDAO.consultarPorID(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<TiposDocumentos> consultarTodos() throws Exception {
		return tipoDocumentoDAO.consultarTodos();
	}

}
