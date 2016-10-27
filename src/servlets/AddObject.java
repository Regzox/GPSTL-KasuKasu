package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import dao.mongo.ObjectTools;

/**
 * Servlet implementation class AddObject
 */
public class AddObject extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddObject() {
        super();
        // TODO Auto-generated constructor stub
    }
    
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String objet = request.getParameter("object").toString();
		
		if( objet.isEmpty() ){
			System.out.println("Object is empty.");	return;
		
		}
			
		try {
		        JSONObject jsonObj = new JSONObject(objet);          
		        ObjectTools.addObject(jsonObj);
		        System.out.println("Object has been treated.");
		        
		    } catch (JSONException e1) {
		        // TODO Auto-generated catch block
		        e1.printStackTrace();
		    }
		

	}

}
