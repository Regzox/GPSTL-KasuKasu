package services;

import org.json.JSONException;
import org.json.JSONObject;

public class Items {

	public static JSONObject search(String query) throws JSONException{
		return new JSONObject()
				.put("nom","chat bleu")
				.put("description", "un chat bleu peureux");
	}

}
