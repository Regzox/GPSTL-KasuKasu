package services;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.DBCursor;

import dao.DemandeDao;

public class Demande {

	
	public static void  addDemande(String idApplicant,String idItem){
		DemandeDao.AjouterDemande(idApplicant, idItem);
	}
	
	public static JSONObject demande(String idApplicant)throws JSONException {
		JSONArray jar = new JSONArray();
		DBCursor dbc = DemandeDao.myDemande(idApplicant);
		
		while(dbc.hasNext())
			jar.put(
					new JSONObject()
					.put("loan_request", dbc.next().get("_id"))
					.put("applicant", dbc.next().get("id_applicant"))
					.put("item",  dbc.next().get("id_item"))
					.put("when",dbc.next().get("when"))
					.put("debut", dbc.next().get("debut")) //TODO
					.put("fin", dbc.next().get("fin"))//TODO
					);
		return new JSONObject().put("requests",jar);
	}
}
