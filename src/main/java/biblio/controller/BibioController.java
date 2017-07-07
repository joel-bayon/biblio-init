package biblio.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BibioController {
	
	
	// .../biblio/hello/dali
	@RequestMapping("/hello/{nom}")
	public String hello(@PathVariable String nom, Model model) {
		nom = nom.toUpperCase(); // simulation traitement métier ...
		model.addAttribute("nom", nom);
		System.err.println("Hello controller");
		return "hello";
	}
	

}
