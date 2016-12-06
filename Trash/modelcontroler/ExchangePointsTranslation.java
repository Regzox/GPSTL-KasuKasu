package services.tools;

import com.mongodb.BasicDBObject;

import services.tools.modelcontroler.MCTranslation;

public class ExchangePointsTranslation extends MCTranslation {

	
	public ExchangePointsTranslation() {
		BasicDBObject javaToMongo = new BasicDBObject();
		BasicDBObject MongoToJava = new BasicDBObject();
		
		
	}
	
	public ExchangePointsTranslation(BasicDBObject controlerToModelTranslation,
			BasicDBObject modelToControlerTranslation) {
		super(controlerToModelTranslation, modelToControlerTranslation);
	}
	
	
	
	
}
