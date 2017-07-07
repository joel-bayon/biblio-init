package controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import entity.Livre;
import jpa.repository.LivreRepository;

@Controller
	@RequestMapping("/livres")
	public class LivreRestController {
	   @Autowired
	   LivreRepository repository;
	   
	   @RequestMapping(value="/{id}", method=RequestMethod.GET, 
		           produces={MediaType.APPLICATION_XML_VALUE,
	 				           MediaType.APPLICATION_JSON_VALUE})
	   public ResponseEntity<?> getLivreById(@PathVariable Integer id ) {
			System.out.println("/rest/livres/"+id);
			Livre livre   =  repository.findOne(id);
			if(livre == null)
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			return new ResponseEntity<>(livre, HttpStatus.OK);
		}
	   
	   @RequestMapping(value="", method=RequestMethod.GET, 
	           produces={MediaType.APPLICATION_JSON_VALUE})
	  @ResponseBody public List<Livre> getLivreAll() {
			System.out.println("/rest/livres/");
			List<Livre> livres   =  repository.findAll();
			return livres;
			/*if(livres == null)
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			return new ResponseEntity<>(livres, HttpStatus.OK);*/
		}
	}

