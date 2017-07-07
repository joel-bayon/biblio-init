package biblio.dao.jpa;

import javax.persistence.Query;
import biblio.dao.AdherentDao;
import biblio.entity.Adherent;


public class AdherentDaoJpa extends DaoJpa<Integer, Adherent> implements AdherentDao {
	

	@Override
	public boolean isPresent(Adherent adherent) {
		final String requete = "from Adherent a where a.nom = :nom and a.prenom = :prenom and a.email = :email";
		
		Query query = entityManager.createQuery(requete);
		query.setParameter("nom", adherent.getNom());
		query.setParameter("prenom", adherent.getPrenom());
		query.setParameter("email", adherent.getEmail());
		return query.getResultList().size() != 0 ? true : false;
	}
}
