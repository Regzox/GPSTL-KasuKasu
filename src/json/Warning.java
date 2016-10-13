package json;

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

}
