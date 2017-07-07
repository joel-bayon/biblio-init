package biblio.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import biblio.entity.Adherent;

public interface AdherentRepository extends JpaRepository<Adherent, Integer>{
	  @Query("select case when count(a) > 0 then true else false end from Adherent a "
				+ "where a.nom = :#{#adherent.nom} and a.prenom = " 
	               + " :#{#adherent.prenom} and a.email = :#{#adherent.email}")
	  public boolean isPresent(@Param("adherent") Adherent adherent);

}
