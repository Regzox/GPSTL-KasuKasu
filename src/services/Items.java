package services;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

public class Items {

	public static JSONObject search(String query) throws JSONException{
		return new JSONObject()
				.put("idItem","777")
				.put("idOwner","777")
				.put("title","chat bleu")
				.put("group","amis")
				.put("longitude",Math.random()*10+1)
				.put("latitude",Math.random()*10+1)
				.put("pubdate",new Date())
				.put("description", "un chat bleu peureux");
	}

}
