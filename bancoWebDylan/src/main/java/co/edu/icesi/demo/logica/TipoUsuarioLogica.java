package co.edu.icesi.demo.logica;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.edu.icesi.demo.dao.ITipoUsuarioDAO;
import co.edu.icesi.demo.modelo.TiposUsuarios;

@Scope("singleton")
@Service("tipoUsuarioLogica")
public class TipoUsuarioLogica implements ITipoUsuarioLogica {

	@Autowired
	private ITipoUsuarioDAO tipoUsuarioDAO;

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void crear(TiposUsuarios tipoUsuario) throws Exception {
		if (tipoUsuario == null) {
			throw new Exception("El tipo de documento no puede ser nulo.");
		}
		if (tipoUsuario.getTusuCodigo() == 0) {
			throw new Exception("El código es obligatorio.");
		}
		if (tipoUsuario.getTusuNombre() == null || tipoUsuario.getTusuNombre().trim().equals("") == true) {
			throw new Exception("El nombre es obligatorio.");
		}
		TiposUsuarios entity = tipoUsuarioDAO.consultarPorID(tipoUsuario.getTusuCodigo());

		if (entity != null) {
			throw new Exception("Ya existe el tipo usuario con ID: " + tipoUsuario.getTusuCodigo());
		}

		tipoUsuarioDAO.crear(tipoUsuario);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void modificar(TiposUsuarios tipoUsuario) throws Exception {
		if (tipoUsuario == null) {
			throw new Exception("El tipo de documento no puede ser nulo.");
		}
		if (tipoUsuario.getTusuCodigo() == 0) {
			throw new Exception("El código es obligatorio.");
		}
		if (tipoUsuario.getTusuNombre() == null || tipoUsuario.getTusuNombre().trim().equals("") == true) {
			throw new Exception("El nombre es obligatorio.");
		}

		tipoUsuarioDAO.modificar(tipoUsuario);

	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void borrar(TiposUsuarios tipoUsuario) throws Exception {
		if (tipoUsuario == null) {
			throw new Exception("El tipo de documento no puede ser nulo.");
		}

		tipoUsuarioDAO.borrar(tipoUsuario);

	}

	@Override
	@Transactional(readOnly = true)
	public TiposUsuarios consultarPorID(Long id) throws Exception {
		if (id == 0) {
			throw new Exception("El ID es obligatorio.");
		}
		return tipoUsuarioDAO.consultarPorID(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<TiposUsuarios> consultarTodos() throws Exception {
		return tipoUsuarioDAO.consultarTodos();
	}
}
