package dao.jpa;

import javax.persistence.Query;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import dao.AdherentDao;
import entity.Adherent;

@Repository
@Transactional
@Primary
public class AdherentDaoJpaSpring extends DaoJpaSpring<Integer, Adherent> implements AdherentDao {
	

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
