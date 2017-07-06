package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import dao.LivreDao;
import jpa.repository.LivreRepository;
import service.Bibliotheque;

@Controller
@RequestMapping("/livre")
public class LivreController {
	
	@Autowired
	LivreRepository dao;
	
	@RequestMapping("")
	public String getLivres(Model model) {
		model.addAttribute("livres", dao.findAll());
		return "livres";
	}

}
