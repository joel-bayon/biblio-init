package biblio.dao.jpa;

import java.util.List;
import javax.persistence.Query;
import biblio.dao.EmpruntDao;
import biblio.entity.Emprunt;


public class EmpruntDaoJpa extends DaoJpa<Integer, Emprunt> implements EmpruntDao {
	
	@SuppressWarnings("unchecked")
	public boolean estEmprunte(int idLivre) {
		final String requete = "from Emprunt e where e.livre.id = :idLivre and e.fin = null";
		
		Query query = entityManager.createQuery(requete);
		query.setParameter("idLivre", idLivre);
		List<Emprunt> emprunts = query.getResultList();
		return emprunts.size() != 0 ? true : false;
	}

	public void deleteEmpruntsByAdherent(int idAdherent) {
		final String requete ="delete from Emprunt e where e.adherent.id = :idAdherent";
		
		Query query = entityManager.createQuery(requete);
		query.setParameter("idAdherent", idAdherent);
		query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Emprunt getEmpruntEnCoursByLivre(int idLivre) {
		final String requete ="from Emprunt e where e.livre.id = :idLivre and e.fin is null";
		
		Query query = entityManager.createQuery(requete);
		query.setParameter("idLivre", idLivre);
		List<Emprunt> emprunts = query.getResultList();
		return  emprunts.size() == 0 ? null : emprunts.get(0);
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Emprunt> getAllByLivre(int idLivre) {
		final String requete = "from Emprunt e join fetch e.livre join fetch e.adherent where e.livre.id = :idLivre";
		Query query = entityManager.createQuery(requete);
		query.setParameter("idLivre", idLivre);
		return  query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Emprunt> getAllByAdherent(int idAdherent) {
		final String requete ="from Emprunt e join fetch e.livre join fetch e.adherent where e.adherent.id = :idAdherent";
		
		Query query = entityManager.createQuery(requete);
		query.setParameter("idAdherent", idAdherent);
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Emprunt> getEmpruntsEnCoursByAdherent(int idAdherent) {
		final String requete ="from Emprunt e where e.adherent.id = :idAdherent and e.dateFin is null";
		
		Query query = entityManager.createQuery(requete);
		query.setParameter("idAdherent", idAdherent);
		return query.getResultList();
	}
}
