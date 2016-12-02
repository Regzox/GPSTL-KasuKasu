package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import kasudb.KasuDB;

public class PointPretDao 
{
	
	public static void addPointPret(int id_user,String nom,Double lat,Double lon,int radius)
			throws SQLException	
	{ 
		String sql = "INSERT INTO POINT_PRET(id_user,nom,lat,lon,radius) VALUES ("
				+ "'"+id_user+"' , '"+nom+"' , '"+lat+"' , '"+lon+"' , '"+radius+ "' ) ;";
		Connection c = KasuDB.SQLConnection();
		Statement s = c.createStatement();
		s.executeUpdate(sql);
		s.close();
		c.close();
	}
	
	public static JSONObject getPointPrets(String user)throws SQLException, JSONException
	{
		JSONArray myPointsPret = new JSONArray();
		String sql = "SELECT nom FROM POINT_PRET WHERE id_user='"+user+"';";
		Connection connection = KasuDB.SQLConnection();
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);

		while (resultSet.next())
		{
			myPointsPret.put(new JSONObject()
					.put("nom",resultSet.getString(1)));
		}	

		resultSet.close();
		statement.close();
		connection.close();

		return new JSONObject().put("points",myPointsPret);
	}

}
