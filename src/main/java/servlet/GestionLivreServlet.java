package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.Bibliotheque;
import dao.LivreDao;
import entity.BusinessException;
import entity.Livre;


@SuppressWarnings("serial")
public class GestionLivreServlet extends HttpServlet {

	LivreDao dao;
	Bibliotheque biblio;
	
	@Override
	public void init() throws ServletException {
		dao = (LivreDao)getServletContext().getAttribute("livreDao");
		biblio = (Bibliotheque)getServletContext().getAttribute("bibliotheque");

	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String next = null;
		String url = req.getServletPath();
		
		if("/gestionLivre/exception".equals(url)) {
			throw new RuntimeException();
		}
		
		if("/gestionLivre/listerLivre".equals(url)) {

			req.setAttribute("livres", dao.loadAll());
			next = "/WEB-INF/jsp/listerLivre.jsp";
		}
		if("/gestionLivre/execute".equals(url)) {
			String creer = req.getParameter("creer");
			String supprimer = req.getParameter("supprimer");
			String modifier = req.getParameter("modifier");
			String retour = req.getParameter("retour");
			
			int id = Integer.parseInt(req.getParameter("id"));
			if(creer != null) {
				Livre l = new Livre(req.getParameter("titre"), 
						Integer.parseInt(req.getParameter("dateParution")),
						req.getParameter("auteur"));
				try { 
				biblio.ajouterLivre(l);
				next = "../gestionLivre/listerLivre";
				}
				catch (BusinessException e) {//traitement violation de RG ...
					req.setAttribute("erreur", e.getMessage());
					req.setAttribute("livre", l);
					next = "/WEB-INF/jsp/editerLivre.jsp";
				}
			}
			else if(supprimer != null) {
				biblio.retirerLivre(id);
				next = "../gestionLivre/listerLivre";
			}
			else if(modifier != null) {
				Livre l = new Livre(req.getParameter("titre"), 
						Integer.parseInt(req.getParameter("dateParution")),
						req.getParameter("auteur"));
				l.setId(id);
				dao.update(l);
				next = "../gestionLivre/listerLivre";
			}
			else if(retour != null) {
				next = "../gestionLivre/listerLivre";
			}
				
		}
		if("/gestionLivre/editerLivre".equals(url)) {
			int id = Integer.parseInt(req.getParameter("id"));
			if(id != 0) {
				req.setAttribute("livre", dao.load(id));
			}
			else {
				req.setAttribute("livre", new Livre());
			}
			
			next = "/WEB-INF/jsp/editerLivre.jsp";	
		}
		// A finir ....
		
		RequestDispatcher rd = req.getRequestDispatcher(next);
		rd.forward(req, resp);
		return;
	}
}
