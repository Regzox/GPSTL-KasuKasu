package dao.sql.tools;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import exceptions.DatabaseException;

/**
 * CSR is an acronym for Connection Statement Resultset
 * ShuttleBus (Navette in french) for Data transportation
 * @author AJoan */
public class CSRShuttleBus {
	
	public CSRShuttleBus(Connection con, Statement sta, ResultSet res) {
		this.con=con;
		this.sta=sta;
		this.res=res;
	}
	
	private final Connection con;
	private final Statement sta;
	private final ResultSet res;
	
	
	public Connection getCon() {return con;}
	public Statement getSta() {return sta;}
	public ResultSet getResultSet() {return res;}
	
	public void close() throws DatabaseException{
		try{res.close();		 sta.close(); 		con.close();}
		catch(SQLException sqle){
			throw new DatabaseException(
					"CSRShuttleBus.close : Error while trying to close SQL talk : "
			+sqle.getMessage());}}
	
	//No setters attributes shouldn't be changed
}
