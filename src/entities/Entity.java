package entities;

import com.mongodb.DBObject;

/**
 * Interface générique des entités
 * 
 * @author Daniel RADEAU
 *
 */

public interface Entity {

	/**
	 * Retourne la conversion de l'entité en DBObject
	 * 
	 * @return
	 */
	
	DBObject toDBObject();
	
}
