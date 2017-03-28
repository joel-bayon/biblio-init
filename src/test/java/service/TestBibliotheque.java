package service;

import org.junit.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

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


public class TestBibliotheque {

	static final LivreDao livreDao = new LivreDaoHibernate();
	static final AdherentDao adherentDao = new AdherentDaoHibernate();
	static final EmpruntDao empruntDao = new EmpruntDaoHibernate();
	static final Bibliotheque bibliotheque = new BibliothequeImpl(3,5);
	
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
			Assert.assertEquals(bibliotheque.getMaxLivreIdentique(), livreDao.getCount(new Livre("L'étranger",1942, "Albert Camus")));
			livreDao.removeAll(livreDao.loadAll());
			Assert.assertEquals(0, livreDao.loadAll().size());
			throw e;
		}
	}
	
	
	@Test (expected=BusinessException.class)
	public void ajouterAdherent() {
		try {
			bibliotheque.ajouterAdherent(new Adherent("Durant", "Pascal", "0240563412", "pascal.durant@free.fr"));
			bibliotheque.ajouterAdherent(new Adherent("Martin", "Jean", "0240992345", "jean.martin@laposte.fr"));
			bibliotheque.ajouterAdherent(new Adherent("Martin", "Jean", "0240992355", "jean.martin@laposte.fr"));
		}
		catch (BusinessException e) {
			Assert.assertEquals(2, adherentDao.loadAll().size());
			adherentDao.removeAll(adherentDao.loadAll());
			Assert.assertEquals(0, adherentDao.loadAll().size());
			throw e;
		}
	}
	
	@Test //(expected=BusinessException.class)
	public void retirerLivre() {
		try {
			Livre l1 = new Livre("Stupeur et tremblements",1999, "Amélie Nothomb");
			Livre l2 = new Livre("Stupeur et tremblements",1999, "Amélie Nothomb");
			Livre l3 = new Livre("Stupeur et tremblements",1999, "Amélie Nothomb");
			
			livreDao.save(l1);
			livreDao.save(l2);
			livreDao.save(l3);
			bibliotheque.retirerLivre(l1.getId());
			bibliotheque.retirerLivre(l2.getId());
			bibliotheque.retirerLivre(l3.getId());
			Assert.assertEquals(0,  livreDao.getCount(new Livre("Stupeur et tremblements",1999, "Amélie Nothomb")));
		
		}
		catch(Exception e) {
			
		}
	}
		
	
	public void retirerAdherent() {
		try {
			int nbAdherent = adherentDao.loadAll().size();
	
			int id1 = bibliotheque.ajouterAdherent(new Adherent("Durant2", "Pascal", "0240563412", "pascal.durant@free.fr"));
			int id2 = bibliotheque.ajouterAdherent(new Adherent("Martin2", "Jean", "0240992345", "jean.martin@laposte.fr"));
			
			Assert.assertEquals(empruntDao.getEmpruntsEnCoursByAdherent(id1).size(), empruntDao.getEmpruntsEnCoursByAdherent(id2).size());
			
			bibliotheque.retirerAdherent(id1); 
			bibliotheque.retirerAdherent(id1);
			Assert.assertEquals(nbAdherent,adherentDao.loadAll().size());
		
		}
		catch(Exception e) {
			
		}
	}


	@Test
	public void testEmprunt() {
		int idAdherent1 = bibliotheque.ajouterAdherent(new Adherent("Durant2", "Pascal", "0240563412", "pascal.durant@free.fr"));
		int idAdherent2 = bibliotheque.ajouterAdherent(new Adherent("Martin2", "Jean", "0240992345", "jean.martin@laposte.fr"));
		int idLivre1 = bibliotheque.ajouterLivre(new Livre("L'étranger",1942, "Albert Camus"));
		int idLivre2 = bibliotheque.ajouterLivre(new Livre("Tintin au Tibet",1952, "Hergé"));
		int idLivre3 = bibliotheque.ajouterLivre(new Livre("L'étranger",1942, "Albert Camus"));
		
		Livre l1 = new Livre("Stupeur et tremblements",1999, "Amélie Nothomb");
		Livre l2 = new Livre("Stupeur et tremblements",1999, "Amélie Nothomb");
		Livre l3 = new Livre("L'herbe Des Nuits", 2012, "Patrick Modiano");
		livreDao.save(l1);
		livreDao.save(l2);
		livreDao.save(l3);
		livreDao.save(new Livre("La Fille de papier",  2011,  "Guillaume Musso"));
		
		
		
		//pas deux emprunts sur le même livre
		try {
			bibliotheque.emprunterLivre(idLivre1, idAdherent1);
			bibliotheque.emprunterLivre(idLivre1, idAdherent2);
			throw new RuntimeException(); // doit pas arriver là sinon test KO !
		}
		catch (BusinessException e) {
			Assert.assertEquals("BibliothequeImpl.emprunterLivre", e.getMessage());
			
		}
		
		//pas plus de 5 emprunts par adherent ...
		try {
			bibliotheque.emprunterLivre(idLivre2, idAdherent1);
			bibliotheque.emprunterLivre(idLivre3, idAdherent1);
			bibliotheque.emprunterLivre(l1.getId(), idAdherent1);
			bibliotheque.emprunterLivre(l2.getId(), idAdherent1);
			bibliotheque.emprunterLivre(l3.getId(), idAdherent1);
			throw new RuntimeException(); // doit pas arriver là sinon test KO !
		}
		catch (BusinessException e) {
			Assert.assertEquals("BibliothequeImpl.emprunterLivre", e.getMessage());
			
		}	
		
		
		//pas possible de retirer un livre emprunté ...
		try {
			bibliotheque.retirerLivre(idLivre1);
		}
		catch (BusinessException e) {
			Assert.assertEquals("BibliothequeImpl.retirerLivre", e.getMessage());
			
		}
		
		//pas possible de retirer un adhérent n'ayant pas restituer tous ses emprunts ...
		try {
			bibliotheque.retirerAdherent(idAdherent1);
		}
		catch (BusinessException e) {
			Assert.assertEquals("BibliothequeImpl.retirerAdherent", e.getMessage());
			
		}
		
		bibliotheque.restituerLivre(idLivre1, idAdherent1);
		bibliotheque.retirerLivre(idLivre1);
		bibliotheque.transfererEmprunt(idAdherent1, idLivre2, idAdherent2);
		bibliotheque.transfererEmprunt(idAdherent1, idLivre3, idAdherent2);
		bibliotheque.transfererEmprunt(idAdherent1, l1.getId(), idAdherent2);
		bibliotheque.transfererEmprunt(idAdherent1, l2.getId(), idAdherent2);
		
		for(Emprunt e : empruntDao.getAllByLivre(idLivre2))
			System.out.println(e);
		
		for(Emprunt e : empruntDao.getAllByAdherent(idAdherent1))
			System.out.println(e);
		
		bibliotheque.retirerAdherent(idAdherent1);
		
		empruntDao.removeAll(empruntDao.loadAll());
		adherentDao.removeAll(adherentDao.loadAll());
		livreDao.removeAll(livreDao.loadAll());
	}
}
