package entities;

import com.mongodb.DBObject;

/**
 * Interface g�n�rique des entit�s
 * 
 * @author Daniel RADEAU
 *
 */

public interface Entity {

	/**
	 * Retourne la conversion de l'entit� en DBObject
	 * 
	 * @return
	 */
	
	DBObject toDBObject();
	
}
