package biblio.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import biblio.entity.Adherent;
import biblio.entity.BusinessException;
import biblio.entity.Emprunt;
import biblio.entity.Livre;
import biblio.repository.AdherentRepository;
import biblio.repository.EmpruntRepository;
import biblio.repository.LivreRepository;
import biblio.service.Bibliotheque;

@Service
@Transactional
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
		if(livreDao.countByTitreAndAuteur(livre.getTitre(), livre.getAuteur()) == maxLivreIdentique ) 
		//Livre example = new Livre(livre.getTitre(),livre.getParution(), livre.getAuteur());
		//if(livreDao.count(Example.of(example)) == maxLivreIdentique ) 
			throw new BusinessException("BibliothequeImpl.ajouterLivre", null);
		return livreDao.save(livre).getId();
		//return livre.getId();
	}


	@Override
	public void retirerLivre(int idLivre) {
		
		//RG : livre vacant ?
		if(empruntDao.getEmpruntEnCoursByLivre(idLivre) != null) 
			throw new BusinessException("BibliothequeImpl.retirerLivre", null); 
		//détruire d'abord les anciens emprunts puis le live ....
		empruntDao.delete(empruntDao.getAllByLivre(idLivre));
		livreDao.delete(idLivre);
	}

	@Override
	public int ajouterAdherent(Adherent adherent) {
		
		//RG est déjé Present ?
		if(adherentDao.isPresent(adherent))
			throw new BusinessException("BibliothequeImpl.ajouterAdherent", null); 
		return adherentDao.save(adherent).getId();
		//return adherent.getId();
	}


	@Override
	public void retirerAdherent(int idAdherent) {
		
		//RG : adherent vacant ?
		if(empruntDao.getEmpruntsEnCoursByAdherent(idAdherent).size() > 0)
			throw new BusinessException("BibliothequeImpl.retirerAdherent", null); 
		//détruire d'abord les anciens emprunts puis l'adhérent ....
		empruntDao.delete(empruntDao.getAllByAdherent(idAdherent));
		adherentDao.delete(idAdherent);	
	}

	@Override
	public void emprunterLivre(int idLivre, int idAdherent) {
		//RG : maxEmpruntAdherent ?
		if( empruntDao.getAllByAdherent(idAdherent).size() == maxEmpruntAdherent)
			throw new BusinessException("BibliothequeImpl.emprunterLivre", null); 
		//RG : livre déjé emprunté !
		if(empruntDao.getEmpruntEnCoursByLivre(idLivre) != null)
			throw new BusinessException("BibliothequeImpl.emprunterLivre", null); 

		empruntDao.save(new Emprunt(livreDao.findOne(idLivre), adherentDao.findOne(idAdherent)));
	}
	
	@Override
	public void restituerLivre(int idLivre, int idAdherent) {
		// RG : un emprunt doit existé avec le couple idLivre/idAdherent
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
