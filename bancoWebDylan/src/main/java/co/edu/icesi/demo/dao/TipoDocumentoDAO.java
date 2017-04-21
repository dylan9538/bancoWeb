package co.edu.icesi.demo.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import co.edu.icesi.demo.modelo.TiposDocumentos;

@Repository
@Scope("singleton")
public class TipoDocumentoDAO implements ITipoDocumentoDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void crear(TiposDocumentos tipoDocumento) {

		sessionFactory.getCurrentSession().save(tipoDocumento);
	}

	@Override
	public void modificar(TiposDocumentos tipoDocumento) {

		sessionFactory.getCurrentSession().update(tipoDocumento);
	}

	@Override
	public void borrar(TiposDocumentos tipoDocumento) {

		sessionFactory.getCurrentSession().delete(tipoDocumento);
	}

	@Override
	public TiposDocumentos consultarPorID(Long id) {
		return sessionFactory.getCurrentSession().get(TiposDocumentos.class, id);
	}

	@Override
	public List<TiposDocumentos> consultarTodos() {

		String hql = "SELECT td FROM TiposDocumentos td";
		return sessionFactory.getCurrentSession().createQuery(hql).getResultList();
	}

}
