package dao.hibernate;

import java.util.List;

import org.hibernate.query.Query;

import dao.EmpruntDao;
import entity.Emprunt;


public class EmpruntDaoHibernate extends DaoHibernate<Integer, Emprunt> implements EmpruntDao {
	

	
	@Override
	public List<Emprunt> getAllByLivre(int livreId){
		final String LOAD_ALL_BY_LIVRE = "from Emprunt e  join fetch e.adherent join fetch e.livre where e.livre.id = :id";
		return getByCriteria(LOAD_ALL_BY_LIVRE, livreId);
	}	

	@Override
	public List<Emprunt> getAllByAdherent(int adherentId){
		final String LOAD_ALL_BY_ADHERENT = "from Emprunt e join fetch e.adherent join fetch e.livre where e.adherent.id =:id";
		return getByCriteria(LOAD_ALL_BY_ADHERENT, adherentId);
	}
	
	@Override
	public Emprunt getEmpruntEnCoursByLivre(int livreId){
		final String LOAD_CURRENT_BY_LIVRE = "from Emprunt e join fetch e.adherent join fetch e.livre where e.livre.id = :id and e.fin is null";
		List<Emprunt> emprunts = getByCriteria(LOAD_CURRENT_BY_LIVRE, livreId);
		if(emprunts.size() >1)
			throw new RuntimeException("erreur dans EmpruntDaoImplJdbc.getCurrentByLivre", null);
		return emprunts.size() == 0? null : emprunts.get(0);
	}


	@Override
	public List<Emprunt> getEmpruntsEnCoursByAdherent(int adherentId){
		final String LOAD_ALL_BY_ADHERENT = "from Emprunt e join fetch e.adherent join fetch e.livre where e.adherent.id =:id and e.fin is null";
		return getByCriteria(LOAD_ALL_BY_ADHERENT, adherentId);
	}
	
	@SuppressWarnings("unchecked")
	private List<Emprunt> getByCriteria(String requete, int id){
		List<Emprunt> emprunts = null;
		Query<Emprunt> query = session.createQuery(requete);
		query.setParameter("id", id);
		emprunts = query.getResultList();
		return emprunts;
	}

}
