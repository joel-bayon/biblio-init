package jpa.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import entity.Emprunt;
public interface EmpruntRepository extends JpaRepository<Emprunt, Integer>{
	  //@Query("select e from Emprunt e join fetch e.livre l where l.id = :id")
	  @Query("select e from Emprunt e join fetch e.livre join fetch e.adherent where e.livre.id = :livreId")
	  public List<Emprunt> getAllByLivre(@Param("livreId") int livreId) ;
	  
	  @Query("select e from Emprunt e join fetch e.livre l where l.id = :id " 
	          + "and e.fin = null")
	  public Emprunt getEmpruntEnCoursByLivre(@Param("id") int livreId);
	  
	  //@Query("select e from Emprunt e join fetch e.adherent a where a.id = :id")
	  @Query("select e from Emprunt e join fetch e.livre join fetch e.adherent where e.adherent.id = :adherentId")
	  public List<Emprunt> getAllByAdherent(@Param("adherentId") int adherentId);
	  
	  @Query("select e from Emprunt e join fetch e.adherent a join fetch e.livre l where a.id = :adherentId " 
	          + "and e.fin = null")
	  public List<Emprunt> getEmpruntsEnCoursByAdherent(@Param("adherentId") int adherentId);
	  
	  @Modifying
	  @Query("update Emprunt e set e.debut = :#{#a.debut}, e.fin = :#{#a.fin}, e.livre = :#{#a.livre}, " 
	          + "e.adherent = :#{#a.adherent} where e.id = :#{#a.id}")
	  public void update(@Param("a")  Emprunt emprunt);

}
