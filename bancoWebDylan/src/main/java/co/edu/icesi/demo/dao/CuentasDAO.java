package co.edu.icesi.demo.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import co.edu.icesi.demo.modelo.Consignaciones;
import co.edu.icesi.demo.modelo.Cuentas;
import co.edu.icesi.demo.modelo.CuentasRegistradas;
import co.edu.icesi.demo.modelo.Retiros;
import co.edu.icesi.demo.modelo.Transferencias;

@Repository
@Scope("singleton")
public class CuentasDAO implements ICuentasDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void crear(Cuentas cuenta) {

		sessionFactory.getCurrentSession().save(cuenta);
	}

	@Override
	public void modificar(Cuentas cuentas) {

		sessionFactory.getCurrentSession().update(cuentas);
	}

	@Override
	public void borrar(Cuentas cuentas) {

		sessionFactory.getCurrentSession().delete(cuentas);
	}

	@Override
	public Cuentas consultarPorID(String id) {
		return sessionFactory.getCurrentSession().get(Cuentas.class, id);
	}

	@Override
	public List<Cuentas> consultarTodos() {

		String hql = "SELECT c FROM Cuentas c";
		return sessionFactory.getCurrentSession().createQuery(hql).getResultList();
	}

	@Override
	public List<Cuentas> consultarCuentasDeUnCliente(long idCliente) throws Exception {
		String hql = "SELECT c FROM Cuentas c WHERE c.clientes.cliId = :parametro";
		return sessionFactory.getCurrentSession().createQuery(hql).setParameter("parametro", idCliente).getResultList();
	
	}

	@Override
	public void crearCuentaRegistrada(CuentasRegistradas nCuentaReg) {
		
		sessionFactory.getCurrentSession().save(nCuentaReg);
		
	}

	@Override
	public List<CuentasRegistradas> consultarCuentasRegistradasDeUnCliente(long cedulaCliente) {
		String hql = "SELECT c FROM CuentasRegistradas c WHERE c.clientes.cliId = :parametro";
		return sessionFactory.getCurrentSession().createQuery(hql).setParameter("parametro", cedulaCliente).getResultList();
	
	}

	@Override
	public List<Transferencias> consultarTraslados(String cueNumero) {
		
		String hql = "SELECT t FROM Transferencias t WHERE t.cuentasByCueNumeroDestino.cueNumero = :parametro";
		return sessionFactory.getCurrentSession().createQuery(hql).setParameter("parametro", cueNumero).getResultList();
	
	}

	@Override
	public List<Consignaciones> consultarConsignaciones(String cueNumero) {
		String hql = "SELECT c FROM Consignaciones c WHERE c.cuentas.cueNumero = :parametro";
		return sessionFactory.getCurrentSession().createQuery(hql).setParameter("parametro", cueNumero).getResultList();
	
	}

	@Override
	public List<Retiros> consultarRetiros(String cueNumero) {
		String hql = "SELECT r FROM Retiros r WHERE r.cuentas.cueNumero = :parametro";
		return sessionFactory.getCurrentSession().createQuery(hql).setParameter("parametro", cueNumero).getResultList();
	
	}

}
