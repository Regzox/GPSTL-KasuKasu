package services;

import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;

import dao.PointPretDao;
import utils.Tools;

public class PointPret 
{
	public static JSONObject createPointPret(int id_user,String nom,Double lat,Double lon,int radius) 
			throws SQLException, JSONException
	{			

		PointPretDao.addPointPret(id_user,nom,lat,lon,radius);
		
		return Tools.serviceMessage(1);
	}

	
	public static JSONObject getPointPret(String id) 
			throws SQLException, JSONException
	{			

		return PointPretDao.getPointPrets(id);
		
	}

}
