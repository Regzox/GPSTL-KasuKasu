package services.modelcontroler;

import java.util.HashMap;
import java.util.Map;

import com.mongodb.BasicDBObject;


public class MCTranslator {

	public static String canonicalControlerSourcePackage;	
	public static final Map<String,MCTranslation> dictionary =  new HashMap<>();

	public String getCanonicalControlerSourcePackage() {
		return canonicalControlerSourcePackage;	
	}

	public void setCanonicalControlerSourcePackage(
			String canonicalControlerSourcePackage
			) {
		MCTranslator.canonicalControlerSourcePackage 
		= canonicalControlerSourcePackage;
	}

	public void setControlerToModelTranslation(
			String canonicalClassName, BasicDBObject translation){
		dictionary.get("canonicalClassName")
		.setControlerToModelTranslation(translation);
	}

	public void setModelToControlerTranslation(
			String canonicalClassName, BasicDBObject translation){
		dictionary.get("canonicalClassName")
		.setModelToControlerTranslation(translation);
	}


	public static void main(String[] args) {
		System.out.println(new MCTranslator().getClass().getCanonicalName());
	}

}
