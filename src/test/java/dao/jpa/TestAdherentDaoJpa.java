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
import entity.Adherent;

public class TestAdherentDaoJpa {
	
	AdherentDao adherentDao = new AdherentDaoJpa();
	
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
	public  void daoTest() throws RuntimeException {
		Adherent ad1 = new Adherent("Dupond", "Jean", "0234567812", "jean.dupont.@yahoo.fr");
		Adherent ad2 = new Adherent("Durant", "Jacques", "0223674512", "jacques.durant@free.fr");
		Adherent ad3 = new Adherent("Martin", "Bernadette", "0138792012", "m.bernadette@gmail.com");
		adherentDao.save(ad1);
		adherentDao.save(ad2);
		adherentDao.save(ad3);

		for(Adherent a : adherentDao.loadAll())
			System.out.println(a);
		
		Assert.assertEquals("DupondJacques0138792012", adherentDao.load(ad1.getId()).getNom()+adherentDao.load(ad2.getId()).getPrenom()+adherentDao.load(ad3.getId()).getTel());
		adherentDao.removeById(ad1.getId());
		List<Adherent> adherents = adherentDao.loadAll();
		Assert.assertEquals(2, adherents.size());
		adherentDao.removeAll(adherents);
		Assert.assertEquals(0, adherentDao.loadAll().size());
	}
	
	@Test 
	public  void daoExceptionTest() throws RuntimeException {
		Adherent ad1 = new Adherent("Dupond", "Jean", "0234567812", "jean.dupont.@yahoo.fr");
		adherentDao.save(ad1);
		Adherent a  = adherentDao.load(ad1.getId());
		Assert.assertEquals("jean.dupont.@yahoo.fr", a.getEmail());
		adherentDao.remove(a);
		a = adherentDao.load(ad1.getId());
		Assert.assertNull(a);
	}
}
