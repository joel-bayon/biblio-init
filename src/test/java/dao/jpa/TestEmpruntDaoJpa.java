package dao.jpa;

import java.util.List;
import org.junit.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import util.DataBaseUtil;
import util.PersistenceContextThreadLocal;
import util.PersistenceContextThreadLocal.TransactionState;
import dao.AdherentDao;
import dao.EmpruntDao;
import dao.LivreDao;
import dao.jpa.AdherentDaoJpa;
import dao.jpa.EmpruntDaoJpa;
import dao.jpa.LivreDaoJpa;
import entity.Adherent;
import entity.Emprunt;
import entity.Livre;


public class TestEmpruntDaoJpa {

	LivreDao livreDao = new LivreDaoJpa();
	AdherentDao adherentDao = new AdherentDaoJpa();
	EmpruntDao empruntDao = new EmpruntDaoJpa();
	
	@BeforeClass
	public static void clearContext() {
		DataBaseUtil.createSchema();
	}
	
	@Before
	public void before() {
		PersistenceContextThreadLocal.setTransaction(TransactionState.BEGIN);
	}
	
	@After
	public void after() {
		PersistenceContextThreadLocal.setTransaction(TransactionState.COMMIT);
	}
	
	@Test
	public  void run() {
		Livre l1 = new Livre("L'étranger",1942, "Albert Camus");
		Livre l2 = new Livre("Tintin au Tibet",1952, "Hergé");

		livreDao.save(l1);
		livreDao.save(l2);
		
		Adherent ad1 = new Adherent("Durant", "Pascal", "0240563412", "pascal.durant@free.fr");
		Adherent ad2 = new Adherent("Martin", "Jean", "0240992345", "jean.martin@laposte.fr");

		adherentDao.save(ad1);
		adherentDao.save(ad2);
		System.out.println(l1.getId());
		livreDao.load(l1.getId());
		livreDao.load(l2.getId());
		Adherent a1 = adherentDao.load(ad1.getId());
		Adherent a2 = adherentDao.load(ad2.getId());
		System.out.println(l1 + " " + l2);
		System.out.println(a1 + " " + a2);
		
		Emprunt e1 = new Emprunt(l1,a1);
		empruntDao.save(e1);
		
		empruntDao.save(new Emprunt(livreDao.load(l2.getId()), adherentDao.load(ad1.getId())));
		empruntDao.save(new Emprunt(livreDao.load(l2.getId()), adherentDao.load(ad2.getId())));
		
		Assert.assertEquals(3, empruntDao.loadAll().size());
		Assert.assertEquals(2, empruntDao.getAllByLivre(l2.getId()).size());
		Assert.assertEquals(2, empruntDao.getAllByAdherent(ad1.getId()).size());
		Assert.assertEquals(1, empruntDao.getAllByAdherent(ad2.getId()).size());
		
		empruntDao.remove(empruntDao.load(e1.getId()));
		Assert.assertEquals(2, empruntDao.loadAll().size());
		Assert.assertEquals(0, empruntDao.getAllByLivre(l1.getId()).size());
		Assert.assertEquals(1, empruntDao.getAllByAdherent(ad1.getId()).size());
		
		List<Emprunt> emprunts = empruntDao.loadAll();
		empruntDao.removeAll(emprunts);
		Assert.assertEquals(0, empruntDao.loadAll().size());
		Assert.assertEquals(0, empruntDao.getAllByLivre(l1.getId()).size());
		Assert.assertEquals(0, empruntDao.getAllByAdherent(ad1.getId()).size());
	
	}
	
}
