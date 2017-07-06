package service;

import org.junit.Assert;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import configuration.BiblioConfiguration;
import configuration.BiblioConfigurationJpa;
import service.Bibliotheque;
import service.impl.BibliothequeImpl;
import util.SessionThreadLocal;
import util.SessionThreadLocal.TransactionState;
import dao.AdherentDao;
import dao.EmpruntDao;
import dao.LivreDao;
import dao.hibernate.AdherentDaoHibernate;
import dao.hibernate.EmpruntDaoHibernate;
import dao.hibernate.LivreDaoHibernate;
import entity.Adherent;
import entity.BusinessException;
import entity.Emprunt;
import entity.Livre;


public class TestBibliothequePopulate {
	
	//static final ConfigurableApplicationContext spring = new ClassPathXmlApplicationContext("spring/spring.bean.xml");
	static final ConfigurableApplicationContext spring = new AnnotationConfigApplicationContext(BiblioConfigurationJpa.class);
	LivreDao livreDao;
	AdherentDao adherentDao;
	EmpruntDao empruntDao ;
	Bibliotheque bibliotheque;
	
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
		livreDao = spring.getBean(LivreDao.class);
		adherentDao = spring.getBean(AdherentDao.class);
		empruntDao= spring.getBean(EmpruntDao.class );
		bibliotheque = spring.getBean(Bibliotheque.class );
	}
	
	@Test (expected=BusinessException.class)
	public void ajouterLivre() {
		try {
			bibliotheque.ajouterLivre(new Livre("L'étranger",1942, "Albert Camus"));
			bibliotheque.ajouterLivre(new Livre("Tintin au Tibet",1952, "Hergé"));
			bibliotheque.ajouterLivre(new Livre("L'étranger",1942, "Albert Camus"));
			bibliotheque.ajouterLivre(new Livre("Tintin au Tibet",1952, "Hergé"));
			bibliotheque.ajouterLivre(new Livre("L'étranger",1942, "Albert Camus"));
			bibliotheque.ajouterLivre(new Livre("Tintin au Tibet",1952, "Hergé"));
			bibliotheque.ajouterLivre(new Livre("L'étranger",1942, "Albert Camus"));
			bibliotheque.ajouterLivre(new Livre("L'étranger",1942, "Albert Camus"));
			bibliotheque.ajouterLivre(new Livre("L'étranger",1942, "Albert Camus"));
			bibliotheque.ajouterLivre(new Livre("L'étranger",1942, "Albert Camus"));
			bibliotheque.ajouterLivre(new Livre("L'étranger",1942, "Albert Camus"));
		}
		catch (BusinessException e) {
			
			throw e;
		}
	}

}
