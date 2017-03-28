package dao.jdbc;

import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import util.DataBaseUtil;
import dao.LivreDao;
import dao.jdbc.LivreDaoJdbc;
import entity.Livre;

public class TestLivreDaoJdbc {
	
	
	LivreDao livreDao = new LivreDaoJdbc();
	
	@BeforeClass
	public static void clearContext() {
		DataBaseUtil.createSchema();
	}
	
	@Test
	public void updateTest() {
		Livre livre = new Livre("Stupeur et tremblements",1999, "Améééélie Nothomb");
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
		Livre livre = new Livre("Stupeur et tremblements",1999, "Amélie Nothomb");
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
	public  void daoExceptionTest() {
		Livre livre = new Livre("Stupeur et tremblements",1999, "Amélie Nothomb");
		livreDao.save(livre);
		Livre l  = livreDao.load(livre.getId());
		Assert.assertEquals("Amélie Nothomb", l.getAuteur());
		livreDao.remove(l);
		l = livreDao.load(livre.getId());
		Assert.assertNull(l);
	}
	
	@Test (expected=RuntimeException.class)
	public  void constraintViolation()  {
		Livre l = new Livre();
		livreDao.save(l); //violation de la contrainte NOT NULL --> trace d'exception sur la console !
		Assert.fail();
	}

}
