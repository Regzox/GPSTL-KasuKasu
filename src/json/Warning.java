package json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Warning extends JSONObject {
	
	public Warning(String message) {
		try {
			this.put("warning", message);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public Warning(JSONObject object) {
		try {
			this.put("warning", object);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public Warning(JSONArray object) {
		try {
			this.put("warning", object);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
