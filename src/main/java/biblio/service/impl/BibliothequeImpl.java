package biblio.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import biblio.service.Bibliotheque;
import biblio.dao.AdherentDao;
import biblio.dao.EmpruntDao;
import biblio.dao.LivreDao;
import biblio.entity.Adherent;
import biblio.entity.BusinessException;
import biblio.entity.Emprunt;
import biblio.entity.Livre;

//@Service
//@Transactional
public class BibliothequeImpl implements Bibliotheque {
	final int maxLivreIdentique;
	final int maxEmpruntAdherent;
	
	@Autowired LivreDao livreDao ;
	@Autowired  AdherentDao adherentDao;
	@Autowired  EmpruntDao empruntDao;
	
	@Autowired
	public BibliothequeImpl(
			@Value("${maxLivreIdentique}") Integer maxLivreIdentique, 
			@Value("${maxEmpruntAdherent}") Integer maxEmpruntAdherent) {
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
		//RG : maxLivreIdentique ?
		if(livreDao.getCount(livre) == maxLivreIdentique ) 
			throw new BusinessException("BibliothequeImpl.ajouterLivre", null);
		livreDao.save(livre);
		return livre.getId();
	}


	@Override
	public void retirerLivre(int idLivre) {
		
		//RG : livre vacant ?
		if(empruntDao.getEmpruntEnCoursByLivre(idLivre) != null) 
			throw new BusinessException("BibliothequeImpl.retirerLivre", null); 
		//d�truire d'abord les anciens emprunts puis le live ....
		empruntDao.removeAll(empruntDao.getAllByLivre(idLivre));
		livreDao.removeById(idLivre);
	}

	@Override
	public int ajouterAdherent(Adherent adherent) {
		
		//RG est d�j� Present ?
		if(adherentDao.isPresent(adherent))
			throw new BusinessException("BibliothequeImpl.ajouterAdherent", null); 
		adherentDao.save(adherent);
		return adherent.getId();
	}


	@Override
	public void retirerAdherent(int idAdherent) {
		
		//RG : adherent vacant ?
		if(empruntDao.getEmpruntsEnCoursByAdherent(idAdherent).size() > 0)
			throw new BusinessException("BibliothequeImpl.retirerAdherent", null); 
		//d�truire d'abord les anciens emprunts puis l'adh�rent ....
		empruntDao.removeAll(empruntDao.getAllByAdherent(idAdherent));
		adherentDao.removeById(idAdherent);	
	}

	@Override
	public void emprunterLivre(int idLivre, int idAdherent) {
		//RG : maxEmpruntAdherent ?
		if( empruntDao.getAllByAdherent(idAdherent).size() == maxEmpruntAdherent)
			throw new BusinessException("BibliothequeImpl.emprunterLivre", null); 
		//RG : livre d�j� emprunt� !
		if(empruntDao.getEmpruntEnCoursByLivre(idLivre) != null)
			throw new BusinessException("BibliothequeImpl.emprunterLivre", null); 

		empruntDao.save(new Emprunt(livreDao.load(idLivre), adherentDao.load(idAdherent)));
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void restituerLivre(int idLivre, int idAdherent) {
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
		
		restituerLivre(idLivre, idAdherentPrecedent);
		emprunterLivre(idLivre, idAdherentSuivant);
		
	}

	
}
