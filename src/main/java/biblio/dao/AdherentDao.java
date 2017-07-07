package biblio.dao;
import biblio.entity.Adherent;

public interface AdherentDao extends Dao<Integer, Adherent> {
	public boolean isPresent(Adherent adherent) ;
}
