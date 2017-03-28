package dao.hibernate;

import org.hibernate.query.Query;
import dao.LivreDao;
import entity.Livre;


public class LivreDaoHibernate extends DaoHibernate<Integer, Livre> implements LivreDao {
	
	@Override
	public int getCount(Livre livre){
		final String HQL_QUERY = "select count(l) from Livre l  where l.titre = :titre and l.auteur = :auteur";

		Query<Number> query = session.createQuery(HQL_QUERY, Number.class);
		query.setParameter("titre", livre.getTitre());
		query.setParameter("auteur", livre.getAuteur());
		return query.getSingleResult().intValue();
	}
}
