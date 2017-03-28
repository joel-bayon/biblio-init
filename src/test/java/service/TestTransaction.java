package service;



import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

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
import entity.Livre;

public class TestTransaction {

	
	static  LivreDao livreDao			 = new LivreDaoHibernate();
	static  AdherentDao adherentDao 	 = new AdherentDaoHibernate();
	static  EmpruntDao empruntDao		 = new EmpruntDaoHibernate();
	static  Bibliotheque bibliotheque 	 = new BibliothequeImpl(3,5);
	
	@BeforeClass
	public static void clearContext() {
		
		util.DataBaseUtil.createSchema();
	}
	
	@Before
	public void before() {
		SessionThreadLocal.setTransaction(TransactionState.BEGIN);
	}
	
	@After
	public void after() {
		SessionThreadLocal.setTransaction(TransactionState.COMMIT);
	}

	@Test
	public void testEmprunt() {
		int idAdherent1 = bibliotheque.ajouterAdherent(new Adherent("Durant2", "Pascal", "0240563412", "pascal.durant@free.fr"));
		int idAdherent2 = bibliotheque.ajouterAdherent(new Adherent("Martin2", "Jean", "0240992345", "jean.martin@laposte.fr"));
		
		int idLivre1 = bibliotheque.ajouterLivre(new Livre("L'étranger",1942, "Albert Camus"));
		int idLivre2 = bibliotheque.ajouterLivre(new Livre("Tintin au Tibet",1952, "Hergé"));
		int idLivre3 = bibliotheque.ajouterLivre(new Livre("L'étranger",1942, "Albert Camus"));
		
		int idLivre4 = bibliotheque.ajouterLivre(new Livre("Stupeur et tremblements",1999, "Amélie Nothomb"));
		int idLivre5 = bibliotheque.ajouterLivre(new Livre("Stupeur et tremblements",1999, "Amélie Nothomb"));
		int idLivre6 = bibliotheque.ajouterLivre(new Livre("L'herbe Des Nuits", 2012, "Patrick Modiano"));
		
		bibliotheque.emprunterLivre(idLivre1, idAdherent1);
		bibliotheque.emprunterLivre(idLivre2, idAdherent2);
		bibliotheque.emprunterLivre(idLivre3, idAdherent2);
		bibliotheque.emprunterLivre(idLivre4, idAdherent2);
		bibliotheque.emprunterLivre(idLivre5, idAdherent2);
		bibliotheque.emprunterLivre(idLivre6, idAdherent2);
		
		
		try {
			bibliotheque.transfererEmprunt(idAdherent1, idLivre1, idAdherent2);
		}
		catch (BusinessException e) {
			Assert.assertEquals(1, empruntDao.getEmpruntsEnCoursByAdherent(idAdherent1).size());
		}
			
	}
}
