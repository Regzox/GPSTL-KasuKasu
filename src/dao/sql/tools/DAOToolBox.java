package dao.sql.tools;

import java.io.PrintWriter;
import java.io.StringWriter;


/**
 * useful tools for SQL database
 * @author joan
 */
public class DAOToolBox{

	/**
	 * Return the complete StackTrace of the throwable as String 
	 * @param thr
	 * @return */
	public static String getStackTrace(Throwable thr){
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		thr.printStackTrace(pw);
		return sw.toString(); // stack trace as a string
	}
}
