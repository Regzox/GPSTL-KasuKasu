package json;

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

}
