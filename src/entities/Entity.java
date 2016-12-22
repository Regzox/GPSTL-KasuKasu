package entities;

import java.lang.reflect.Field;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * Interface g�n�rique des entit�s
 * 
 * @author Daniel RADEAU
 *
 */

public interface Entity {

	/**
	 * Retourne la conversion de l'entit� en DBObject.
	 * Ne fonctionne que dans la cas o� les champs sont de type String.
	 * 
	 * @return
	 */
	
	default DBObject toDBObject() {
		BasicDBObject object = new BasicDBObject();
		
		try {
			for (Field field : EvaluationRequest.class.getDeclaredFields())
				if (field.get(this) != null && field.get(this) instanceof String)
					object.append(field.getName(), field.get(this));
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		
		return object;
	}
	
}
