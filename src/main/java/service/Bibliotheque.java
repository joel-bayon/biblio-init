package service;

import entity.Adherent;
import entity.Livre;

public interface Bibliotheque {
	
	public int getMaxLivreIdentique();
	public int getMaxEmpruntAdherent();
	
	public int ajouterLivre(Livre livre) ;
	public void retirerLivre(int idLivre) ;
	
	public int ajouterAdherent(Adherent adherent) ;
	public void retirerAdherent(int idAdherent);
	
	public void emprunterLivre(int idLivre, int idAdherent);
	public void transfererEmprunt(int idAdherentPrecedent, int idLivre, int idAdherentSuivant);
	public void restituerLivre(int idLivre, int idAdherent);

}
