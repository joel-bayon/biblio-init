package service.impl;

import java.util.Date;

import org.apache.log4j.Logger;

import service.Bibliotheque;
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



public class BibliothequeImpl implements Bibliotheque {
	final int maxLivreIdentique;
	final int maxEmpruntAdherent;
	
	static Logger logger  = Logger.getLogger(Bibliotheque.class);
	
	LivreDao livreDao = new LivreDaoHibernate();
	AdherentDao adherentDao = new  AdherentDaoHibernate();
	EmpruntDao empruntDao = new EmpruntDaoHibernate();
	
	public BibliothequeImpl(Integer maxLivreIdentique, Integer maxEmpruntAdherent) {
		this.maxLivreIdentique = maxLivreIdentique; 
		this.maxEmpruntAdherent = maxEmpruntAdherent;
	}

	@Override
	public int getMaxEmpruntAdherent() {
		return maxEmpruntAdherent;
	}
	
	@Override
	public int getMaxLivreIdentique() {
		return maxLivreIdentique;
	}
	
	@Override
	public int ajouterLivre(Livre livre)  {
		if(logger.isInfoEnabled())
			logger.info("ajouterLivre(Livre livre)");
		//RG : maxLivreIdentique ?
		if(livreDao.getCount(livre) == maxLivreIdentique ) 
			throw new BusinessException("BibliothequeImpl.ajouterLivre", null);
		livreDao.save(livre);
		return livre.getId();
	}


	@Override
	public void retirerLivre(int idLivre) {
		if(logger.isInfoEnabled())
			logger.info("retirerLivre(int idLivre)");
		//RG : livre vacant ?
		if(empruntDao.getEmpruntEnCoursByLivre(idLivre) != null) 
			throw new BusinessException("BibliothequeImpl.retirerLivre", null); 
		//d�truire d'abord les anciens emprunts puis le live ....
		empruntDao.removeAll(empruntDao.getAllByLivre(idLivre));
		livreDao.removeById(idLivre);
	}

	@Override
	public int ajouterAdherent(Adherent adherent) {
		if(logger.isInfoEnabled())
			logger.info("ajouterAdherent(Adherent adherent)");
		//RG est d�j� Present ?
		if(adherentDao.isPresent(adherent))
			throw new BusinessException("BibliothequeImpl.ajouterAdherent", null); 
		adherentDao.save(adherent);
		return adherent.getId();
	}


	@Override
	public void retirerAdherent(int idAdherent) {
		if(logger.isInfoEnabled())
			logger.info("retirerAdherent(int idAdherent)");
		//RG : adherent vacant ?
		if(empruntDao.getEmpruntsEnCoursByAdherent(idAdherent).size() > 0)
			throw new BusinessException("BibliothequeImpl.retirerAdherent", null); 
		//d�truire d'abord les anciens emprunts puis l'adh�rent ....
		empruntDao.removeAll(empruntDao.getAllByAdherent(idAdherent));
		adherentDao.removeById(idAdherent);	
	}

	@Override
	public void emprunterLivre(int idLivre, int idAdherent) {
		if(logger.isInfoEnabled())
			logger.info("emprunterLivre(int idLivre, int idAdherent)");
		//RG : maxEmpruntAdherent ?
		if( empruntDao.getAllByAdherent(idAdherent).size() == maxEmpruntAdherent)
			throw new BusinessException("BibliothequeImpl.emprunterLivre", null); 
		//RG : livre d�j� emprunt� !
		if(empruntDao.getEmpruntEnCoursByLivre(idLivre) != null)
			throw new BusinessException("BibliothequeImpl.emprunterLivre", null); 

		empruntDao.save(new Emprunt(livreDao.load(idLivre), adherentDao.load(idAdherent)));
	}
	
	@Override
	public void restituerLivre(int idLivre, int idAdherent) {
		if(logger.isInfoEnabled())
			logger.info("restituerLivre(int idLivre, int idAdherent)");
		// RG : un emprunt doit exist� avec le couple idLivre/idAdherent
		Emprunt emprunt = empruntDao.getEmpruntEnCoursByLivre(idLivre);
		if(emprunt == null || emprunt.getAdherent().getId() != idAdherent)
			throw new BusinessException("BibliothequeImpl.restituerLivre", null);  /// A finir ...
		emprunt.setFin(new Date());
		empruntDao.update(emprunt);
		
	}

	@Override
	public void transfererEmprunt(int idAdherentPrecedent, int idLivre,
			int idAdherentSuivant) {
		if(logger.isInfoEnabled())
			logger.info("transfererEmprunt(int idAdherentPrecedent, int idLivre, int idAdherentSuivant)");
		restituerLivre(idLivre, idAdherentPrecedent);
		emprunterLivre(idLivre, idAdherentSuivant);
		
	}

	
}
