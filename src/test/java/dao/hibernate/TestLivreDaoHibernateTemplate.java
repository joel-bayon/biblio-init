package dao.hibernate;

import java.util.List;
import org.junit.Assert;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import configuration.BiblioConfiguration;
import configuration.BiblioConfigurationHibernate;
import util.DataBaseUtil;
import util.SessionThreadLocal;
import util.SessionThreadLocal.TransactionState;
import dao.AdherentDao;
import dao.EmpruntDao;
import dao.LivreDao;
import dao.hibernate.LivreDaoHibernate;
import entity.Livre;
import service.Bibliotheque;

public class TestLivreDaoHibernateTemplate {
	
	//static final ConfigurableApplicationContext spring = new ClassPathXmlApplicationContext("spring/spring.bean.xml");
		static final ConfigurableApplicationContext spring = new AnnotationConfigApplicationContext(BiblioConfigurationHibernate.class);
		LivreDao livreDao;
		
		@BeforeClass
		public static void clearContext() {
			util.DataBaseUtil.createSchema();	
		}
		
		@AfterClass
		public static void afterClass() {
			spring.close();
		}
		
		@Before
		public void before() {
			//SessionThreadLocal.setTransaction(TransactionState.BEGIN);
			livreDao = spring.getBean("livreDaoHibernateTemplate",LivreDao.class);
		}
	
	@Test
	public void updateTest() {
		Livre livre   = new Livre("Stupeur et tremblements",1999, "Amééélie Nothomb");
		livreDao.save(livre);
		livre = livreDao.load(livre.getId());
		livre.setAuteur("Amélie Nothomb");
		livreDao.update(livre);
		Assert.assertEquals("Amélie Nothomb", livreDao.load(livre.getId()).getAuteur());
		
		livreDao.removeAll(livreDao.loadAll());
		Assert.assertEquals(0, livreDao.loadAll().size());
	}
	
	@Test
	public  void daoTest() {
		Livre livre   = new Livre("Stupeur et tremblements",1999, "Amélie Nothomb");
		livreDao.save(livre);
		System.out.println(livre.getId());
		livreDao.save(new Livre("L'étranger",1942, "Albert Camus"));
		livreDao.save(new Livre("Réglez-lui son compte !",1949, "Frédéric Dard"));
		livreDao.save(new Livre("Tintin au Tibet",1960, "Hergé"));
		
		for(Livre l : livreDao.loadAll())
			System.out.println(l);
		
		Assert.assertEquals("Amélie Nothomb", livreDao.load(livre.getId()).getAuteur());
		livreDao.removeById(livre.getId());
		List<Livre> livres = livreDao.loadAll();
		Assert.assertEquals(3, livres.size());
		
		livre = new Livre("Tintin au Tibet",1960, "Hergé");
		livreDao.save(livre);
		Assert.assertEquals(2, livreDao.getCount(livreDao.load(livre.getId())));
		
		
		livreDao.removeAll(livreDao.loadAll());
		Assert.assertEquals(0, livreDao.loadAll().size());

	}
	
	@Test 
	public  void removeTest() {
		Livre livre = new Livre("Stupeur et tremblements",1999, "Amélie Nothomb");
		livreDao.save(livre);
		Livre l  = livreDao.load(livre.getId());
		Assert.assertEquals("Amélie Nothomb", l.getAuteur());
		livreDao.remove(l);
		l = livreDao.load(livre.getId());
		Assert.assertNull(l);
	}

}
