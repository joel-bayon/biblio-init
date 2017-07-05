package dao.jpa;

import javax.persistence.Query;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import dao.LivreDao;
import entity.Livre;


@Repository
@Primary
@Transactional
public class LivreDaoJpaSpring extends DaoJpaSpring<Integer, Livre> implements LivreDao {
	
	@Override
	public int getCount(Livre livre) {
		final String requete = "select count(l) from Livre l where l.auteur = :auteur " +
				"and l.titre = :titre and l.parution = :parution";

		Query query = entityManager.createQuery(requete);
		query.setParameter("auteur", livre.getAuteur());
		query.setParameter("titre", livre.getTitre());
		query.setParameter("parution", livre.getParution());
		return ((Number) query.getSingleResult()).intValue();
	}

	
	
}
