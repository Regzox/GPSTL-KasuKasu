package services;
import java.util.ArrayList;
import org.json.JSONObject;
import com.mongodb.DBCursor;
import dao.LoaningDao;
import exceptions.UserNotFoundException;
import exceptions.UserNotUniqueException;

/**
 * @author Anagbla Joan */
public class Loaning {

	/**
	 * Return a json object containing item's applicants list
	 * @param id_objet
	 * @return
	 * @throws UserNotUniqueException 
	 * @throws UserNotFoundException */
	public static JSONObject itemApplicants(String id_objet) throws UserNotFoundException, UserNotUniqueException{
		DBCursor dbc = LoaningDao.itemApplicants(id_objet);
		ArrayList<String> applicantIDs = new ArrayList<String>();
		while(dbc.hasNext())
			applicantIDs.add((String)dbc.next().get("id_applicant"));
		return User.getUsersJSONProfileFromIds(applicantIDs);
	}

}
