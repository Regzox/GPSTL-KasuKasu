package dao.config;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import kasudb.KasuDB;

public class configDB {

	protected static DBCollection config = KasuDB.getMongoCollection("config");

	public static DBObject getMailConfig(){
		DBObject mailconfig;
		try{
			DBCursor dbc = config.find(
					new BasicDBObject()
					.append("email","mail"));
			mailconfig = dbc.next();
		}catch(Exception e){
			mailconfig=null;
		}
		return mailconfig;
	}
	
	public static void setMailConfig(String mail,String pass,String host,String port,String domain){
		config.update(
				new BasicDBObject()
				.append("email","mail")
				,new BasicDBObject()
				.append("$set",
						new BasicDBObject()						
						.append("mail",mail)
						.append("pass",pass)
						.append("host",host)
						.append("port",port)
						.append("domain", domain)),true,false);
	}
}
