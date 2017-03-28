package servlet;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import users.User;

@SuppressWarnings("serial")
public class AuthentificationServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String next = null;
		String url = req.getServletPath();
		System.out.println(url);
		
		if("/authentification/login".equals(url)) {
			next = "/WEB-INF/jsp/login.jsp";
		}
		if("/authentification/execute".equals(url)) {
			String identifiant = req.getParameter("identifiant");
			String motDePasse = req.getParameter("motDePasse");
			Properties users  = (Properties)getServletContext().getAttribute("users");
			if(users.getProperty(identifiant) != null && users.getProperty(identifiant).equals(motDePasse) ) { //authentification OK
				HttpSession session = req.getSession();
				session.setAttribute("user", new User(identifiant, motDePasse, null));
				session.setAttribute("dateConnexion", new Date());
				resp.sendRedirect("../home/accueil");
				return;
			}
			else { // //authentification KO
				next ="/WEB-INF/jsp/login.jsp";
			}
		}
		
		if("/authentification/deconnexion".equals(url)) {
			req.getSession().invalidate();
			next ="/WEB-INF/jsp/login.jsp";
		}
		
		RequestDispatcher rd = req.getRequestDispatcher(next);
		rd.forward(req, resp);
		return;
	}
}
