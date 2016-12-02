package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import kasudb.KasuDB;

public class DemandeDao {

	
	public static void  AjouterDemande(String idSend,String idRecep,String idObject)throws SQLException
	{
		String sqlQuery = "INSERT INTO DEMANDE values ('"+idSend+"','"+idRecep+"','"+idObject+"');";
		Connection c = KasuDB.SQLConnection();
		Statement s = c.createStatement();
		s.executeUpdate(sqlQuery);
		s.close();
		c.close();	
	}
	public static ArrayList<Integer> myDemande(String user)throws SQLException
	{
		ArrayList<Integer> myDemandes = new ArrayList<Integer>();
		String sql = "SELECT idSend FROM DEMANDE WHERE (idRecep='"+user+"');";
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
