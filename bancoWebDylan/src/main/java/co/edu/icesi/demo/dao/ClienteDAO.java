package co.edu.icesi.demo.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import co.edu.icesi.demo.modelo.Clientes;

@Repository
@Scope("singleton")
public class ClienteDAO implements IClienteDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void crear(Clientes cliente) {

		sessionFactory.getCurrentSession().save(cliente);
	}

	@Override
	public void modificar(Clientes cliente) {

		sessionFactory.getCurrentSession().update(cliente);
	}

	@Override
	public void borrar(Clientes cliente) {

		sessionFactory.getCurrentSession().delete(cliente);
	}

	@Override
	public Clientes consultarPorID(Long id) {
		return sessionFactory.getCurrentSession().get(Clientes.class, id);
	}

	@Override
	public List<Clientes> consultarTodos() {

		String hql = "SELECT c FROM Clientes c";
		return sessionFactory.getCurrentSession().createQuery(hql).getResultList();
	}

}
