package co.edu.icesi.demo.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import co.edu.icesi.demo.modelo.TiposDocumentos;
import co.edu.icesi.demo.modelo.TiposUsuarios;

@Repository
@Scope("singleton")
public class TiposUsuariosDAO implements ITipoUsuarioDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void crear(TiposUsuarios tipoUsiario) {

		sessionFactory.getCurrentSession().save(tipoUsiario);
	}

	@Override
	public void modificar(TiposUsuarios tipoUsuario) {

		sessionFactory.getCurrentSession().update(tipoUsuario);
	}

	@Override
	public void borrar(TiposUsuarios tipoUsuario) {

		sessionFactory.getCurrentSession().delete(tipoUsuario);
	}

	@Override
	public TiposUsuarios consultarPorID(Long id) {
		return sessionFactory.getCurrentSession().get(TiposUsuarios.class, id);
	}

	@Override
	public List<TiposUsuarios> consultarTodos() {

		String hql = "SELECT tu FROM TiposUsuarios tu";
		return sessionFactory.getCurrentSession().createQuery(hql).getResultList();
	}

}
