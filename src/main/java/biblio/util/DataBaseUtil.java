package biblio.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DataBaseUtil {
	
	static private ComboPooledDataSource cpds = new ComboPooledDataSource();
	
	static public  void createSchema() {
		final String CREATE_TABLE_ADHERENT = "CREATE TABLE ADHERENT (ID_ADHERENT INT AUTO_INCREMENT,NOM VARCHAR(45) NOT NULL," +
											   "PRENOM VARCHAR(45) NOT NULL,EMAIL VARCHAR(120),TEL VARCHAR(12))";
		final String PRIMARY_KEY_ADHERENT = "ALTER TABLE ADHERENT ADD CONSTRAINT ID_ADHERENT PRIMARY KEY (ID_ADHERENT)";
		
		final String CREATE_TABLE_LIVRE = "CREATE TABLE LIVRE (ID_LIVRE INT AUTO_INCREMENT,TITRE VARCHAR(120) NOT NULL," +
				   						    "AUTEUR VARCHAR(45) NOT NULL, PARUTION INT)";
		final String PRIMARY_KEY_LIVRE = "ALTER TABLE LIVRE ADD CONSTRAINT ID_LIVRE PRIMARY KEY (ID_LIVRE)";
		
		final String CREATE_TABLE_EMPRUNT = "CREATE TABLE EMPRUNT(ID_EMPRUNT INT AUTO_INCREMENT, DEBUT DATE NOT NULL, FIN DATE," +
											  "LIVRE_ID INT NOT NULL, ADHERENT_ID INT NOT NULL)";
		final String PRIMARY_KEY_EMPRUNT = "ALTER TABLE EMPRUNT ADD CONSTRAINT ID_EMPRUNT PRIMARY KEY (ID_EMPRUNT)";
		final String FOREIGN_KEY_ADHERENT = "ALTER TABLE EMPRUNT ADD CONSTRAINT FK_ADHERENT FOREIGN KEY (ADHERENT_ID) REFERENCES ADHERENT (ID_ADHERENT)";
		final String FOREIGN_KEY_LIVRE = "ALTER TABLE EMPRUNT ADD CONSTRAINT FK_LIVRE FOREIGN KEY (LIVRE_ID) REFERENCES LIVRE (ID_LIVRE)";		
		
		Connection cx = null;
		Statement stmt = null;
		try {
			cx=getConnexion();
			stmt = cx.createStatement();
			stmt.execute("DROP TABLE IF EXISTS LIVRE");
			stmt.execute("DROP TABLE IF EXISTS ADHERENT");
			stmt.execute("DROP TABLE IF EXISTS EMPRUNT");
			
			stmt.execute(CREATE_TABLE_ADHERENT);
			stmt.execute(PRIMARY_KEY_ADHERENT);
			
			stmt.execute(CREATE_TABLE_LIVRE);
			stmt.execute(PRIMARY_KEY_LIVRE);
			
			stmt.execute(CREATE_TABLE_EMPRUNT);
			stmt.execute(PRIMARY_KEY_EMPRUNT);
			stmt.execute(FOREIGN_KEY_ADHERENT);
			stmt.execute(FOREIGN_KEY_LIVRE);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			if(cx != null)
				releaseConnexion(cx);
		}
	}
	
	static public void populateDataBase() {
		Connection cx = null;
		Statement stmt = null;
		
		try {
			cx=getConnexion();
			stmt = cx.createStatement();
			stmt.execute("INSERT INTO  LIVRE VALUES(NULL, 'Stupeur et tremblements', 'Amééélie Nothomb' , 1999)");
			stmt.execute("INSERT INTO LIVRE VALUES(NULL, 'Albert Camus', 'L''étranger', 1942)");
			stmt.execute("INSERT INTO LIVRE VALUES(NULL, 'Frédéric Dard', 'Réglez-lui son compte', 1949)");
			stmt.execute("INSERT INTO LIVRE VALUES(NULL, 'Hergé', 'Tintin au Tibet', 1960)");
			
			
			stmt.execute("INSERT INTO ADHERENT VALUES(NULL, 'Dupond', 'Jean', 'jean.dupont.@yahoo.fr', '0234567812')");
			stmt.execute("INSERT INTO ADHERENT VALUES(NULL, 'Durant', 'Jacques', 'jacques.durant@free.fr', '0223674512')");
			stmt.execute("INSERT INTO ADHERENT VALUES(NULL, 'Martin', 'Bernadette', 'm.bernadette@gmail.com', '0138792012')");
			
			stmt.execute("INSERT INTO EMPRUNT VALUES(NULL, '2012-01-10', '2012-01-20', 1, 1)");
			stmt.execute("INSERT INTO EMPRUNT VALUES(NULL, '2012-01-10', '2012-01-25', 2, 2)");
			stmt.execute("INSERT INTO EMPRUNT VALUES(NULL, '2012-10-01', NULL, 3, 1)");
			stmt.execute("INSERT INTO EMPRUNT VALUES(NULL, '2012-10-10', NULL, 4, 2)");
			
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			if(cx != null)
				releaseConnexion(cx);
		}
		
	}
	
	public static Connection getConnexion() {
		Connection cx = null;
		try {
			cx=cpds.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();	
		}
		return cx;
	}
	
	public static void releaseConnexion(Connection cx) {
		try {
			cx.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String arg[]) {
		createSchema();
		populateDataBase();
	}
}
