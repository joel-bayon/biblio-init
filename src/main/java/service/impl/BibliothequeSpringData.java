package service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import entity.Adherent;
import entity.BusinessException;
import entity.Emprunt;
import entity.Livre;
import jpa.repository.AdherentRepository;
import jpa.repository.EmpruntRepository;
import jpa.repository.LivreRepository;
import service.Bibliotheque;

//@Service
@Transactional
@Primary
public class BibliothequeSpringData implements Bibliotheque {
	final int maxLivreIdentique;
	final int maxEmpruntAdherent;
	
	@Autowired LivreRepository livreDao ;
	@Autowired  AdherentRepository adherentDao;
	@Autowired  EmpruntRepository empruntDao;
	
	@Autowired
	public BibliothequeSpringData(
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
		//if(livreDao.countByTitreAndAuteur(livre.getTitre(), livre.getAuteur()) == maxLivreIdentique ) 
		Livre example = new Livre(livre.getTitre(),livre.getParution(), livre.getAuteur());
		if(livreDao.count(Example.of(example)) == maxLivreIdentique ) 
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
		empruntDao.delete(empruntDao.getAllByLivre(idLivre));
		livreDao.delete(idLivre);
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
		empruntDao.delete(empruntDao.getAllByAdherent(idAdherent));
		adherentDao.delete(idAdherent);	
	}

	@Override
	public void emprunterLivre(int idLivre, int idAdherent) {
		//RG : maxEmpruntAdherent ?
		if( empruntDao.getAllByAdherent(idAdherent).size() == maxEmpruntAdherent)
			throw new BusinessException("BibliothequeImpl.emprunterLivre", null); 
		//RG : livre d�j� emprunt� !
		if(empruntDao.getEmpruntEnCoursByLivre(idLivre) != null)
			throw new BusinessException("BibliothequeImpl.emprunterLivre", null); 

		empruntDao.save(new Emprunt(livreDao.findOne(idLivre), adherentDao.findOne(idAdherent)));
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
