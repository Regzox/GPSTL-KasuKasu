//package enumerations;
//
//import org.json.JSONObject;
//
//import json.Error;
//
//public enum Url {
//	
//	/*Offline mode*/
//	PORTAL("/portal.jsp"),
//	CREATE_USER("/createuser.jsp"),
//	CONNECTION("/connexion.jsp"),
//	ERROR_PAGE("/errorpage.jsp"),
//	RETRIEVE_PASSWORD("/retrievePassword.jsp"),
//	
//	/*Online mode(restricted)*/
//	DASHBOARD("/restricted/searchitems.jsp"),
//	FIND_USER("/restricted/finduser.jsp"),
//	MODIFY_USER("/restricted/modifyUser.jsp"),
//	PROFILE("/restricted/profile.jsp"),
//	SEARCHITEMS("/restricted/searchitems.jsp"),
//	MY_FRIENDS("/restricted/myfriends.jsp"),
//	PENDING_REQUESTS("/restricted/pendingrequests.jsp"),
//	UPLOAD("/restricted/upload.jsp"),
//	GROUPE("/restricted/groups.jsp"),
//	CREATEOBJECT("/restricted/createobject.jsp"),
//	DEMANDESRECUES("/demande.jsp"), /*TODO WHAT IS IT? */
//	RECHERCHEROBJECT("/restricted/searchitems.jsp"),
//	LISTOBJECTS("/restricted/useritems.jsp"),
//	LISTLOANS("/restricted/userLoans.jsp"),
//	AJOUTERLIEU("/addpoint.jsp"),
//	IMPORTLIEU("/importpoint.jsp"),
//	LISTLIEU("/restricted/listpoint.jsp"),
//	CONVERSATION("/restricted/messenger.jsp"),
//	EVALUATION("/restricted/evaluation.jsp"),
//	USER_PROFILE("/restricted/memberprofile.jsp");
//
//
//
//	;
//	
//	protected final String displayName = "/KasuKasu";
//	protected final String url;
//	
//	Url(String url) {
//		this.url = displayName + url;
//	}
//	
//	public String value() {
//		return url;
//	}
//	
//	public String localPath() {
//		return url.replace(displayName, "");
//	}
//	
//	public String toJavaScript() {
//		StringBuilder js = new StringBuilder();
//		
//		js.append("var ");
//		js.append(this.name());
//		js.append(" = '");
//		js.append(url);
//		js.append("';");
//		
//		return js.toString();
//	}
//	
//	public JSONObject toJSONObject() {
//		JSONObject json = new JSONObject();
//		
//		try {
//			json.put(this.name().toLowerCase(), url);
//		} catch (Exception e) {
//			return new Error("Url to json failure");
//		}
//		
//		return json;
//	}
//	
//	public static String allToJavaScript() {
//		StringBuilder js = new StringBuilder();
//		
//		js.append("var");
//		for (Url item : Url.class.getEnumConstants()) {
//			js.append("\t");
//			js.append(item.name());
//			js.append(" = \"");
//			js.append(item.url);
//			js.append("\",\n");
//		}
//		js.append("end");
//		
//		return js.toString().replace(",\nend", ";");
//	}
//	
//	public static JSONObject allToJSONObject() {
//		JSONObject json = new JSONObject();
//		
//		try {
//			JSONObject items = new JSONObject();
//			
//			for (Url item : Url.class.getEnumConstants()) {
//				items.put(item.name().toLowerCase(), item.url);
//			}
//			
//			json.put("url", items);
//		} catch (Exception e) {
//			return new Error("Url to json failure");
//		}
//		
//		return json;
//	}
//	
//	@Override
//	public String toString() {
//		return url;
//	}
//}
