package co.edu.icesi.demo.config;

import static org.junit.Assert.*;

import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import co.edu.icesi.demo.modelo.Clientes;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class SessionFactoryTest {
	
	private final static Logger log=LoggerFactory.getLogger(SessionFactoryTest.class);
	
	
	@Autowired
	private SessionFactory sessionFactory;

	@Test
	@Transactional(readOnly=true)
	public void test() {
		assertNotNull(sessionFactory);
		
		Clientes clientes=sessionFactory.getCurrentSession().get(Clientes.class, 101234L);
		
		assertNotNull(clientes);
		
		log.info("id:"+clientes.getCliId());
		log.info("Nombre:"+clientes.getCliNombre());
		
		
		
	}

}
