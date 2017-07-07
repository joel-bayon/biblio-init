package biblio.dao;
import biblio.entity.Livre;

public interface LivreDao extends Dao<Integer, Livre> {
	public int getCount(Livre livre);
}
