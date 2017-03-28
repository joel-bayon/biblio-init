package listener;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import dao.hibernate.AdherentDaoHibernate;
import dao.hibernate.EmpruntDaoHibernate;
import dao.hibernate.LivreDaoHibernate;

import service.impl.BibliothequeImpl;
import util.DataBaseUtil;


/**
 * Application Lifecycle Listener implementation class BibliothequeListener
 *
 */
public class BibliothequeListener implements ServletContextListener {

    /**
     * Default constructor. 
     */
    public BibliothequeListener() { 
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent event) {
    	ServletContext ctx = event.getServletContext();
		String fileName = ctx.getInitParameter("users");
		String filePath = ctx.getRealPath(fileName);
		System.out.println("path du fichier users="+filePath);
		try {
			InputStream input = new FileInputStream(filePath);
			Properties users = new Properties();
			users.load(input);
			ctx.setAttribute("users", users);
			DataBaseUtil.createSchema();
			DataBaseUtil.populateDataBase();
			// cr�ation de l'objet de service Bibilotheque dans le servletContext
			ctx.setAttribute("bibliotheque", new BibliothequeImpl(3, 5));
			// puis des 3 DAOs ...dans le servletContext
			ctx.setAttribute("livreDao", new LivreDaoHibernate());
			ctx.setAttribute("adherentDao", new AdherentDaoHibernate());
			ctx.setAttribute("empruntDao", new EmpruntDaoHibernate());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0) {}
	
}
