package services;

import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;

import dao.PointEmpruntDao;
import utils.Tools;

public class PointEmprunt 
{
	
	public static JSONObject createPointEmprunt(int id_user,String nom,Double lat,Double lon,int radius) 
			throws SQLException, JSONException
	{			

		PointEmpruntDao.addPointEmprunt(id_user,nom,lat,lon,radius);
		
		return Tools.serviceMessage(1);
	}

}
