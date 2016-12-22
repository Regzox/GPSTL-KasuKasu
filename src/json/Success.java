package json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Success extends JSONObject {
	
	public Success(String message) {
		try {
			this.put("success", message);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public Success(JSONObject object) {
		try {
			this.put("success", object);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public Success(JSONArray object) {
		try {
			this.put("success", object);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
