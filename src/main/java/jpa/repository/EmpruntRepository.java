package jpa.repository;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import entity.Emprunt;
public interface EmpruntRepository extends JpaRepository<Emprunt, Integer>{
	  @Query("select e from Emprunt e join e.livre l where l.id = :id")
	  public List<Emprunt> getAllByLivre(@Param("id") int livreId) ;
	  @Query("select e from Emprunt e join e.livre l where l.id = :id " 
	          + "and e.fin = null")
	  public Emprunt getEmpruntEnCoursByLivre(@Param("id") int livreId);
	  @Query("select e from Emprunt e join e.adherent a where a.id = :id")
	  public List<Emprunt> getAllByAdherent(@Param("id") int adherentId);
	  @Query("select e from Emprunt e join e.adherent a where a.id = :id " 
	          + "and e.fin = null")
	  public List<Emprunt> getEmpruntsEnCoursByAdherent(@Param("id") int adherentId);
	  
	  @Modifying
	  @Query("update Emprunt e set e.debut = :#{#a.debut}, e.fin = :#{#a.fin}, e.livre = :#{#a.livre}, " 
	          + "e.adherent = :#{#a.adherent} where e.id = :#{#a.id}")
	  public void update(@Param("a")  Emprunt emprunt);

}
