package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import kasudb.KasuDB;
import utils.Tools;

public class PointEmpruntDao 
{
	
	public static void addPointEmprunt(int id_user,String nom,Double lat,Double lon,int radius)
			throws SQLException	
	{ 
		String sql = "INSERT INTO POINT_EMPRUNT(id_user,nom,lat,lon,radius) VALUES ("
				+ "'"+id_user+"' , '"+nom+"' , '"+lat+"' , '"+lon+"' , '"+radius+ "' ) ;";
		Connection c = KasuDB.SQLConnection();
		Statement s = c.createStatement();
		s.executeUpdate(sql);
		s.close();
		c.close();
	}


}
