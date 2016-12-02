package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import kasudb.KasuDB;

public class DemandeDao {

	private static final String table_name = "demande_emprunt";
	
	public static void  AjouterDemande(int idSend,String idObject)throws SQLException
	{
		String sqlQuery = "INSERT INTO "+table_name +" values ('"+idSend+"','"+idObject+"',null,null,NOW());";
		Connection c = KasuDB.SQLConnection();
		Statement s = c.createStatement();
		s.executeUpdate(sqlQuery);
		s.close();
		c.close();	
	}
	
	public static void main(String[] args) throws SQLException {
		AjouterDemande(1,"idObject");
	}
	
	
	
	public static ArrayList<Integer> myDemande(String user)throws SQLException
	{
		ArrayList<Integer> myDemandes = new ArrayList<Integer>();
		String sql = "SELECT idSend FROM "+table_name +" WHERE (idRecep='"+user+"');";
		Connection connection = KasuDB.SQLConnection();
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);

		while (resultSet.next())
			myDemandes.add(resultSet.getInt(1));

		resultSet.close();
		statement.close();
		connection.close();

		return myDemandes;
	}
}
