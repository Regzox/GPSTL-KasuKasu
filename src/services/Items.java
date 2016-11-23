package services;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import dao.mongo.ItemsDB;
import dao.mongo.ItemsMR;
import dao.mongo.ObjetRSV;
import exceptions.DatabaseException;

/**
 *@author ANAGBLA Joan */
public class Items {

	
	/**
	 * Search an Item by query
	 * @param query
	 * @return
	 * @throws JSONException
	 * @throws DatabaseException */
	public static JSONObject search(String query) throws JSONException, DatabaseException{		
		JSONArray jar =new JSONArray();
		for(ObjetRSV orsv :ItemsMR.search(query))
			jar.put(new JSONObject()
					.put("id",orsv.getDbo().get("_id"))
					.put("type","item")
					.put("owner",orsv.getDbo().get("owner"))
					.put("title",orsv.getDbo().get("title"))
					.put("group",orsv.getDbo().get("group"))
					.put("longitude",orsv.getDbo().get("longitude"))
					.put("latitude",orsv.getDbo().get("latitude"))
					.put("date",orsv.getDbo().get("date"))
					.put("description", orsv.getDbo().get("description")));
		return new JSONObject().put("items",jar);}
	
	
	/**
	 * return a list of items owned by an user 
	 * @param userID
	 * @return
	 * @throws JSONException
	 * @throws DatabaseException */
	public static JSONObject userItems(int userID) throws JSONException, DatabaseException{
		JSONArray jar =new JSONArray();
		DBCursor dbc = ItemsDB.userItems(userID);
		while (dbc.hasNext()){
			DBObject dbo=dbc.next();
			jar.put(new JSONObject()
					.put("id",dbo.get("_id"))
					.put("type","item")
					.put("owner",dbo.get("owner"))
					.put("title",dbo.get("title"))
					.put("group",dbo.get("group"))
					.put("longitude",dbo.get("longitude"))
					.put("latitude",dbo.get("latitude"))
					.put("date",dbo.get("date"))
					.put("description",dbo.get("description")));}
		return new JSONObject().put("items",jar);}

	
	
	public static void main(String[] args) throws JSONException, IOException, DatabaseException {
		ItemsMR.collection.drop();
		//http://www.cdiscount.com/telephonie/telephone-mobile/samsung-galaxy-j5-2016-blanc/f-1440402-sam8806088323503.html#mpos=2|cd
		ItemsMR.collection.insert(
				new BasicDBObject()
				.append("title","SAMSUNG Galaxy J5 2016 Blanc")
				.append("owner",1)
				.append("group","friends")
				.append("longitude",Math.random())
				.append("latitude",Math.random())
				.append("date",new Date())
				.append("description",
						" Taille d'écran : 5.2 pouces 1.5 GHz -"
						+ " Quadruple coeur 4G Résolution du capteur : "
						+ " 13 mégapixels Capacité de la mémoire interne : 16 Go "
						+ " Système d'exploitation : Android 6.0 (Marshmallow) Composants intégrés :"
						+ " Caméra arrière, caméra avant, radio FM, lecteur audio, enregistreur vocal, navigation"
						+ " Cartes mémoire flash prises en charge : microSDXC - jusqu'à 128 Go"));

		//http://www.cdiscount.com/telephonie/telephone-mobile/samsung-galaxy-j5-or/f-1440402-samsungsmj500o.html?idOffre=117989573#mpos=3|mp
		ItemsMR.collection.insert(
				new BasicDBObject()
				.append("title","Samsung Galaxy J5 Or")
				.append("owner",3)
				.append("group","friends")
				.append("longitude",Math.random())
				.append("latitude",Math.random())
				.append("date",new Date())
				.append("description",
						" Taille d'écran : 5 pouces 1.2 GHz -"
						+ " Quadruple coeur 3G Résolution du capteur : "
						+ " 13 mégapixels Capacité de la mémoire interne : 8 Go "
						+ " Système d'exploitation : Android 5.1 (Lollipop)"
						+ " Composants intégrés : Caméra arrière, caméra avant, "
						+ " radio FM, lecteur audio, enregistreur vocal, navigation "
						+ " Cartes mémoire flash prises en charge : microSDXC - jusqu'à 64 Go"));

		//http://www.cdiscount.com/telephonie/telephone-mobile/samsung-galaxy-s7-or/f-1440402-samsunggalaxys7o.html#mpos=2|cd
		ItemsMR.collection.insert(
				new BasicDBObject()
				.append("title","Samsung Galaxy S5 Or")
				.append("owner",2)
				.append("group","friends")
				.append("longitude",Math.random())
				.append("latitude",Math.random())
				.append("date",new Date())
				.append("description",
						" Taille d'écran : 5.1 pouces 2.3 GHz - 8 coeurs 4G "
						+ " Résolution du capteur : "
						+ " 12 mégapixels Capacité de la mémoire interne : 32 Go"
						+ " Système d'exploitation : Android 6.0 (Marshmallow) "
						+ " Composants intégrés : Caméra arrière, caméra avant, "
						+ " lecteur audio, enregistreur vocal, "
						+ " capteur de fréquence cardiaque, navigation "
						+ " Cartes mémoire flash prises en charge : microSDXC - jusqu'à 200 Go"));

		//http://www.cdiscount.com/telephonie/telephone-mobile/apple-iphone-5s-16-go-argent-4g/f-1440402-iphone5s16gsi.html#mpos=5|cd
		ItemsMR.collection.insert(
				new BasicDBObject()
				.append("title","APPLE iPhone 5S 16 Go Blanc 4G")
				.append("owner",3)
				.append("group","friends")
				.append("longitude",Math.random())
				.append("latitude",Math.random())
				.append("date",new Date())
				.append("description","Taille d'écran : 4 "
						+ " Type : Apple A7 4G "
						+ " Résolution du capteur : 8 Megapixel"
						+ "	Capacité de la mémoire interne : 16 Go"
						+ "	Système d'exploitation : iOS 7"
						+ "	Composants intégrés : Caméra arrière, caméra avant, "
						+ " enregistreur vocal, navigation Fonctions du téléphone :"
						+ " Téléphone à haut parleur, commande vocale, compteur d'appels, "
						+ " téléconférence, numérotation vocale, vibreur"));

		String line ="";
		System.out.println("Que recherchez vous ? : ");
		while(!(line = new BufferedReader(new InputStreamReader(System.in)).readLine().toLowerCase())
				.equals("!")) {
			System.out.println("Search : "+line);
			JSONArray res=((JSONArray)search(line).get("items"));
			for(int i=0; i< res.length();i++)
				System.out.println("Results["+i+"] : "
						+((JSONObject)res.get(i)).get("title"));
		}
	}
}