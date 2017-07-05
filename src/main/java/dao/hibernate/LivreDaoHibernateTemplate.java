package dao.hibernate;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import dao.LivreDao;
import entity.Livre;


@Repository
@Transactional
public class LivreDaoHibernateTemplate extends DaoHibernateTemplate<Integer, Livre> implements LivreDao {
	
	@Override
	public int getCount(Livre livre){
		final String HQL_QUERY = "select count(l) from Livre l  where l.titre = :titre and l.auteur = :auteur";

		return 
				((Number)template
				.findByNamedParam(HQL_QUERY, new String[]{"titre","auteur"}, new Object[]{livre.getTitre(), livre.getAuteur()})
				.get(0)).intValue();
	}
}
