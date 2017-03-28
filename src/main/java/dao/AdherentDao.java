package dao;
import entity.Adherent;

public interface AdherentDao extends Dao<Integer, Adherent> {
	public boolean isPresent(Adherent adherent) ;
}
