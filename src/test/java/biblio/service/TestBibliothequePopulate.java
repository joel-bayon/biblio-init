package biblio.service;

import biblio.Application;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import biblio.entity.Adherent;
import biblio.entity.Livre;
import biblio.repository.AdherentRepository;
import biblio.repository.EmpruntRepository;
import biblio.repository.LivreRepository;


public class TestBibliothequePopulate {
	
	//static final ConfigurableApplicationContext spring = new ClassPathXmlApplicationContext("spring/spring.bean.xml");
	static final ConfigurableApplicationContext spring = new AnnotationConfigApplicationContext(Application.class);
	LivreRepository livreDao;
	AdherentRepository adherentDao;
	EmpruntRepository empruntDao ;
	Bibliotheque bibliotheque;
	
	@BeforeClass
	public static void clearContext() {
		biblio.util.DataBaseUtil.createSchema();
	}
	
	@AfterClass
	public static void afterClass() {
		spring.close();
	}
	
	@Before
	public void before() {
		livreDao = spring.getBean(LivreRepository.class);
		adherentDao = spring.getBean(AdherentRepository.class);
		empruntDao= spring.getBean(EmpruntRepository.class );
		bibliotheque = spring.getBean(Bibliotheque.class );
	}
	
	@Test 
	public void ajouterLivre() {
		
		int idLivre1  = bibliotheque.ajouterLivre(new Livre("L'étranger",1942, "Albert Camus"));
		int idLivre2  = bibliotheque.ajouterLivre(new Livre("Tintin au Tibet",1952, "Hergé"));
		int idLivre3  = bibliotheque.ajouterLivre(new Livre("L'étranger",1942, "Albert Camus"));
		int idLivre4  = bibliotheque.ajouterLivre(new Livre("Tintin au Tibet",1952, "Hergé"));
		
		int idAdherent1 = bibliotheque.ajouterAdherent(new Adherent("Durant", "Pascal", "0240563412", "pascal.durant@free.fr"));
		int idAdherent2 = bibliotheque.ajouterAdherent(new Adherent("Martin", "Jean", "0240992345", "jean.martin@laposte.fr"));
		
		bibliotheque.emprunterLivre(idLivre1, idAdherent1);
		bibliotheque.emprunterLivre(idLivre3, idAdherent1);
		bibliotheque.emprunterLivre(idLivre2, idAdherent2);
		bibliotheque.emprunterLivre(idLivre4, idAdherent2);
	}
}
