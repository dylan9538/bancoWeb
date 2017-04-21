package co.edu.icesi.demo.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import co.edu.icesi.demo.modelo.Clientes;
import co.edu.icesi.demo.modelo.Usuarios;

@Repository
@Scope("singleton")
public class UsuarioDAO implements IUsuarioDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void crear(Usuarios usuario) {

		sessionFactory.getCurrentSession().save(usuario);
	}

	@Override
	public void modificar(Usuarios usuario) {

		sessionFactory.getCurrentSession().update(usuario);
	}

	@Override
	public void borrar(Usuarios usuario) {

		sessionFactory.getCurrentSession().delete(usuario);
	}

	@Override
	public Usuarios consultarPorID(long id) {
		return sessionFactory.getCurrentSession().get(Usuarios.class, id);
	}

	@Override
	public List<Usuarios> consultarTodos() {

		String hql = "SELECT u FROM Usuarios u";
		return sessionFactory.getCurrentSession().createQuery(hql).getResultList();
	}

	@Override
	public Usuarios consultarUsuario(String usuLogin) {
		String hql = "SELECT u FROM Usuarios u WHERE u.usuLogin = :login";
		return (Usuarios) sessionFactory.getCurrentSession().createQuery(hql).setParameter("login", usuLogin).getSingleResult();
	}

}
