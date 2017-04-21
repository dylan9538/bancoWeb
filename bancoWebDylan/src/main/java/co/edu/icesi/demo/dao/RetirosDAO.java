package co.edu.icesi.demo.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import co.edu.icesi.demo.modelo.Retiros;
import co.edu.icesi.demo.modelo.RetirosId;

@Repository
@Scope("singleton")
public class RetirosDAO implements IRetirosDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void crear(Retiros retiros) {

		sessionFactory.getCurrentSession().save(retiros);
	}

	@Override
	public void modificar(Retiros retiros) {

		sessionFactory.getCurrentSession().update(retiros);
	}

	@Override
	public void borrar(Retiros retiros) {

		sessionFactory.getCurrentSession().delete(retiros);
	}

	@Override
	public Retiros consultarPorID(RetirosId id) {
		return sessionFactory.getCurrentSession().get(Retiros.class, id);
	}

	@Override
	public List<Retiros> consultarTodos() {

		String hql = "SELECT r FROM Retiros r";
		return sessionFactory.getCurrentSession().createQuery(hql).getResultList();
	}
}
