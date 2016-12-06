package services.tools.modelcontroler;

import com.mongodb.BasicDBObject;

public class MCTranslation {

	protected BasicDBObject modelToControlerTranslation;
	protected BasicDBObject controlerToModelTranslation;

	public MCTranslation(
			BasicDBObject controlerToModelTranslation,
			BasicDBObject modelToControlerTranslation) {
		this.modelToControlerTranslation=modelToControlerTranslation;
		this.controlerToModelTranslation=controlerToModelTranslation;
	}

	public BasicDBObject getModelToControlerTranslation() {
		return modelToControlerTranslation;
	}

	public void setModelToControlerTranslation(
			BasicDBObject modelToControlerTranslation) {
		this.modelToControlerTranslation = modelToControlerTranslation;
	}

	public BasicDBObject getControlerToModelTranslation() {
		return controlerToModelTranslation;
	}

	public void setControlerToModelTranslation(
			BasicDBObject controlerToModelTranslation) {
		this.controlerToModelTranslation = controlerToModelTranslation;
	}


	
	
}

