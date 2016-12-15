package json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Error extends JSONObject {
	
	public Error(String message) {
		try {
			this.put("error", message);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public Error(JSONObject object) {
		try {
			this.put("error", object);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public Error(JSONArray object) {
		try {
			this.put("error", object);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
