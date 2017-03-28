package dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import dao.LivreDao;
import entity.Livre;
import util.DataBaseUtil;

public class LivreDaoJdbc implements LivreDao {

	
	@Override
	public void save(Livre livre) {
		Connection con = null;
		PreparedStatement pStmt = null;
		String requete = "INSERT into livre values(null, ?, ?, ?)";
		int id =0;
		
		try {
			con = DataBaseUtil.getConnexion();
			pStmt = con.prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);
			pStmt.setString(1, livre.getTitre());
			pStmt.setString(2, livre.getAuteur());
			pStmt.setInt(3, livre.getParution());
			pStmt.execute();
			ResultSet rSet = pStmt.getGeneratedKeys();
			if (rSet.next()) {
				id = rSet.getInt(1);
				livre.setId(id);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("load(int id )", e);	
		}
		finally {
			DataBaseUtil.releaseConnexion(con);
		}
	}
	
	@Override
	public void update(Livre livre) {
		Connection con = null;
		PreparedStatement pStmt = null;
		String requete = "UPDATE livre SET TITRE = ?, AUTEUR = ?, PARUTION = ?" +
				" WHERE ID_LIVRE = ?";
		
		try {
			con = DataBaseUtil.getConnexion();
			pStmt = con.prepareStatement(requete);
			pStmt.setString(1, livre.getTitre());
			pStmt.setString(2, livre.getAuteur());
			pStmt.setInt(3, livre.getParution());
			pStmt.setInt(4, livre.getId());
			pStmt.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("update(Livre livre)", e);	
		}
		finally {
			DataBaseUtil.releaseConnexion(con);
		}
	}
	
	@Override
	public Livre load(Integer id ) {
		Connection con = null;
		Livre livre = null;
		PreparedStatement pStmt = null;
		final String requete = "SELECT * FROM LIVRE WHERE ID_LIVRE = ?";
		try {
			con = DataBaseUtil.getConnexion();
			pStmt = con.prepareStatement(requete);
			pStmt.setInt(1, (Integer)id);
			ResultSet rs = pStmt.executeQuery();
			if(rs.next()) {
				livre = new Livre(rs.getString("titre"), 
						  rs.getInt("parution"),
						  rs.getString("auteur") 
						 );
				livre.setId(rs.getInt("ID_LIVRE"));
			}
		} catch (SQLException e) {
			throw new RuntimeException("load(Serializable id ", e);	
		}
		finally {
			DataBaseUtil.releaseConnexion(con);
		}
		return livre;
	}
	
	@Override
	public List<Livre> loadAll() {
		Connection con = null;
		List<Livre> livres = new ArrayList<Livre>();
		Statement pStmt = null;
		final String requete = "SELECT * FROM LIVRE";
		try {
			con = DataBaseUtil.getConnexion();
			pStmt = con.createStatement();
			ResultSet rs = pStmt.executeQuery(requete);
			while(rs.next()) {
				Livre livre = new Livre(rs.getString("titre"), 
						rs.getInt("parution"),
						rs.getString("auteur")
						  );
				livre.setId(rs.getInt("ID_LIVRE"));
				livres.add(livre);
			}
		} catch (SQLException e) {
			throw new RuntimeException("loadAll()", e);	
		}
		finally {
			DataBaseUtil.releaseConnexion(con);
		}
		return livres;
	}

	
	@Override
	public void remove(Livre entity) {
		removeById(entity.getId());
	}


	@Override
	public void removeById(Integer primaryKey) {
		Connection con = null;
		PreparedStatement pStmt = null;
		String requete = "DELETE FROM livre WHERE ID_LIVRE = ?";
		
		try {
			con = DataBaseUtil.getConnexion();
			pStmt = con.prepareStatement(requete);
			pStmt.setInt(1, (Integer)primaryKey);
			pStmt.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("delete(Serializable primaryKey)", e);
		}
		finally {
			DataBaseUtil.releaseConnexion(con);
		}
		
	}

	@Override
	public void removeAll(Collection<Livre> entities) {
		for(Livre livre : entities)
			remove(livre);
		
	}


	@Override
	public int getCount(Livre livre) {
		Connection con = null;
		Statement stmt = null; // marche pas avec un PrepareStatement !
		int nombre = 0;
		final String requete = 
				"SELECT COUNT (*) AS nombre FROM LIVRE WHERE TITRE = '"+ livre.getTitre()+"' AND AUTEUR = '"+livre.getAuteur()+"'";
		       
		try {
			con = DataBaseUtil.getConnexion();
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(requete);
			rs.next();
			nombre = rs.getInt("nombre");
		} catch (SQLException e) {
			throw new RuntimeException("getCount(Livre livre)", e);	
		}
		finally {
			DataBaseUtil.releaseConnexion(con);
		}
		return nombre;
	}

	
}
