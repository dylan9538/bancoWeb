package co.edu.icesi.demo.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import co.edu.icesi.demo.modelo.Consignaciones;
import co.edu.icesi.demo.modelo.ConsignacionesId;

@Repository
@Scope("singleton")
public class ConsignacionesDAO implements IConsignacionesDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void crear(Consignaciones consignaciones) {

		sessionFactory.getCurrentSession().save(consignaciones);
	}

	@Override
	public void modificar(Consignaciones consignaciones) {

		sessionFactory.getCurrentSession().update(consignaciones);
	}

	@Override
	public void borrar(Consignaciones consignaciones) {

		sessionFactory.getCurrentSession().delete(consignaciones);
	}

	@Override
	public Consignaciones consultarPorID(ConsignacionesId id) {
		return sessionFactory.getCurrentSession().get(Consignaciones.class, id);
	}

	@Override
	public List<Consignaciones> consultarTodos() {

		String hql = "SELECT c FROM Consignaciones c";
		return sessionFactory.getCurrentSession().createQuery(hql).getResultList();
	}
}
