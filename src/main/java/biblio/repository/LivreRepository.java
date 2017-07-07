package biblio.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import biblio.entity.Livre;

public interface LivreRepository extends JpaRepository<Livre, Integer>{
	public Long countByTitreAndAuteur(String titre, String auteur);
}
