package jpa.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import entity.Livre;

public interface LivreRepository extends JpaRepository<Livre, Integer>{
	public Long countByTitreAndAuteur(String titre, String auteur);
}
