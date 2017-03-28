package dao;

import java.util.List;

import entity.Emprunt;

public interface EmpruntDao extends Dao<Integer, Emprunt> {
	public List<Emprunt> getAllByLivre(int livreId) ;
	public Emprunt getEmpruntEnCoursByLivre(int livreId);
	public List<Emprunt> getAllByAdherent(int adherentId);
	public List<Emprunt> getEmpruntsEnCoursByAdherent(int adherentId);

}
