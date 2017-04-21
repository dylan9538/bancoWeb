package co.edu.icesi.demo.logica;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.edu.icesi.demo.dao.IUsuarioDAO;
import co.edu.icesi.demo.modelo.TiposUsuarios;
import co.edu.icesi.demo.modelo.Usuarios;

@Scope("singleton")
@Service("usuarioLogica")
public class UsuarioLogica implements IUsuarioLogica {

	@Autowired
	private ITipoUsuarioLogica tipoUsuarioLogica;

	@Autowired
	private IUsuarioDAO usuarioDAO;

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void crear(Usuarios usuario) throws Exception {

		if (usuario == null) {
			throw new Exception("El usuario es nulo.");
		}
		if (usuario.getUsuLogin() == null || usuario.getUsuLogin().trim().equals("") == true) {
			throw new Exception("El login es obligatorio.");
		}
		if (usuario.getUsuClave() == null || usuario.getUsuClave().trim().equals("") == true) {
			throw new Exception("La clave es obligatoria.");
		}
		if (usuario.getUsuCedula() == 0) {
			throw new Exception("La cedula es obligatoria.");
		}
		if (usuario.getUsuNombre() == null || usuario.getUsuNombre().trim().equals("") == true) {
			throw new Exception("El nombre es obligatorio.");
		}
		if (usuario.getTiposUsuarios() == null) {
			throw new Exception("El tipo de usuario no puede ser nulo.");
		}
		TiposUsuarios tiposUsuarios = tipoUsuarioLogica.consultarPorID(usuario.getTiposUsuarios().getTusuCodigo());
		if (tiposUsuarios == null) {
			throw new Exception(
					"El tipo de usuario con ID: " + usuario.getTiposUsuarios().getTusuCodigo() + " no existe.");
		}
		usuarioDAO.crear(usuario);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void modificar(Usuarios usuario) throws Exception {
		if (usuario == null) {
			throw new Exception("El usuario es nulo.");
		}
		if (usuario.getUsuLogin() == null || usuario.getUsuLogin().trim().equals("") == true) {
			throw new Exception("El login es obligatorio.");
		}
		if (usuario.getUsuClave() == null || usuario.getUsuClave().trim().equals("") == true) {
			throw new Exception("La clave es obligatoria.");
		}
		if (usuario.getUsuCedula() == 0) {
			throw new Exception("La cedula es obligatoria.");
		}
		if (usuario.getUsuNombre() == null || usuario.getUsuNombre().trim().equals("") == true) {
			throw new Exception("El nombre es obligatorio.");
		}
		if (usuario.getTiposUsuarios() == null) {
			throw new Exception("El tipo de usuario no puede ser nulo.");
		}
		TiposUsuarios tiposUsuarios = tipoUsuarioLogica.consultarPorID(usuario.getTiposUsuarios().getTusuCodigo());
		if (tiposUsuarios == null) {
			throw new Exception(
					"El tipo de usuario con ID: " + usuario.getTiposUsuarios().getTusuCodigo() + " no existe.");
		}
		usuarioDAO.modificar(usuario);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void borrar(Usuarios usuario) throws Exception {
		if (usuario == null) {
			throw new Exception("El usuario es nulo.");
		}
		usuarioDAO.borrar(usuario);

	}

	@Override
	@Transactional(readOnly = true)
	public Usuarios consultarPorID(Long cedula) throws Exception {
		if (cedula == null) {
			throw new Exception("La cedula es nula.");
		}
		return usuarioDAO.consultarPorID(cedula);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Usuarios> consultarTodos() throws Exception {

		return usuarioDAO.consultarTodos();
	}

	@Override
	@Transactional(readOnly = true)
	public Usuarios validarUsuario(String usuLogin, String usuClave) throws Exception {

		Usuarios usuario = usuarioDAO.consultarUsuario(usuLogin);

		if (usuario == null) {
			throw new Exception("El usuario no Existe!");
		}

		if (usuario.getUsuClave().equals(usuClave)) {
			
			//lazy load
			usuario.getTiposUsuarios().getTusuNombre();

			return usuario;

		} else {
			throw new Exception("Password no valido.");
		}
	}

}
