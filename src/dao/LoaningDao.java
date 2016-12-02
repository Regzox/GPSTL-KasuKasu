package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import dao.sql.tools.CSRShuttleBus;
import dao.sql.tools.DAOToolBox;
import exceptions.DatabaseException;
import kasudb.KasuDB;

/**
 * @author ANAGBLA Joan */
public class LoaningDao {

	private static final String table_name = "demande_emprunt";

	public static CSRShuttleBus itemApplicants(String id_objet) throws DatabaseException {
		String sql = "SELECT * FROM "+table_name +" WHERE id_objet= '"+id_objet+"' ;";
		try {
			Connection c = KasuDB.SQLConnection();
			Statement s = c.createStatement();
			return new CSRShuttleBus(c, s, s.executeQuery(sql));}
		catch (SQLException e) {
			throw new DatabaseException(DAOToolBox.getStackTrace(e));}
	}
}
