package services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.json.JSONObject;

import dao.LoaningDao;
import dao.sql.tools.CSRShuttleBus;
import dao.sql.tools.DAOToolBox;
import exceptions.DatabaseException;

/**
 * @author Anagbla Joan */
public class Loaning {

	/**
	 * Return a json object containing item's applicants list
	 * @param id_objet
	 * @return
	 * @throws DatabaseException */
	public static JSONObject itemApplicants(String id_objet) throws DatabaseException {
		CSRShuttleBus bus =LoaningDao.itemApplicants(id_objet);
		ResultSet rs = bus.getResultSet();
		ArrayList<Integer> applicantIDs = new ArrayList<Integer>();
		try {
			while(rs.next())
				applicantIDs.add(Integer.parseInt(rs.getString("id_emprunteur")));
			bus.close();
		} catch (SQLException e) {
			throw new DatabaseException(DAOToolBox.getStackTrace(e));}
		return User.getUsersJSONProfileFromIds(applicantIDs);
	}

}
