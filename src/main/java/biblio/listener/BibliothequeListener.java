package biblio.listener;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;



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
		String fileName = ctx.getInitParameter("biblio/users");
		String filePath = ctx.getRealPath(fileName);
		System.out.println("path du fichier biblio.users="+filePath);
		try {
			InputStream input = new FileInputStream(filePath);
			Properties users = new Properties();
			users.load(input);
			ctx.setAttribute("biblio/users", users);
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
