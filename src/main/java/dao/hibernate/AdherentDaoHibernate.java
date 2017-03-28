package dao.hibernate;




import org.hibernate.query.Query;

import dao.AdherentDao;
import entity.Adherent;

public class AdherentDaoHibernate extends DaoHibernate<Integer, Adherent> implements AdherentDao {
	

	@Override
	public boolean isPresent(Adherent adherent) {
		final String HQL_SELECT = "from Adherent a where a.nom = :nom and a.prenom = :prenom and a.email = :email";

		
		Query<Adherent> query = session.createQuery(HQL_SELECT, Adherent.class);
		query.setParameter("nom", adherent.getNom());
		query.setParameter("prenom", adherent.getPrenom());
		query.setParameter("email", adherent.getEmail());
		return query.getResultList().size()!= 0 ;
	}
}
