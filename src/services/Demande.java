package services;

import java.sql.SQLException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import dao.DemandeDao;
import dao.FriendsDao;


public class Demande {

	
	public static void  addDemande(String idSend,String idRecep,String idObject)throws SQLException
	{
		DemandeDao.AjouterDemande(idSend, idRecep, idObject);
	}
	
	public static JSONObject demande(String user)throws SQLException, JSONException
	{
		JSONObject demandesJSON=new JSONObject();
		JSONArray ids = new JSONArray();
		ArrayList<Integer> list=DemandeDao.myDemande(user);
		for(Integer i : list){
			ids.put(i);
		}
		demandesJSON.put("requests",ids);
		return demandesJSON;
	}
	public static ArrayList<Integer> DemandesArray(String user)throws SQLException, JSONException
	{
		return DemandeDao.myDemande(user);
	}
}
