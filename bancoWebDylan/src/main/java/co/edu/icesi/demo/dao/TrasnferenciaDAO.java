package co.edu.icesi.demo.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import co.edu.icesi.demo.modelo.Transferencias;

@Repository
@Scope("singleton")
public class TrasnferenciaDAO implements ITransferenciaDAO{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void crear(Transferencias transferencia) {
		sessionFactory.getCurrentSession().save(transferencia);
		
	}

}
