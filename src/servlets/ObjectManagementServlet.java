package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.mongodb.DBCursor;

import dao.mongo.ItemsDB;
import entities.Group;
import entities.Item;
import entities.Zone;

public class ObjectManagementServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*TODO Get the html/jsp page of Object to modify*/
		HttpSession session=request.getSession();
		PrintWriter out = response.getWriter(); 
		response.setContentType("application/json;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		try {
			// Récuperation de l'ID de session
	        String userId = (String) session.getAttribute("userId");
	        if( userId == null ) {
	        	out.write( new JSONObject().put("error", "User ID is empty.").toString() );
	        	return;
	        }
	        String objectId = (String) request.getParameter("objectId");
	        if( objectId == null ) {
	        	out.write( new JSONObject().put("error", "No Object Specified.").toString() );
	        	return;
	        }
	        
	       // DBCursor items = ItemsDB.userItems(userId);
	        /*TODO
	         * Get The Item
	         * Get the Zones linked to the item
	         * Get the Groups linked to the item
	         */
	        List<Zone> zones=new ArrayList<Zone>(); // zones=zonesOfUser(id);
	        List<Group> groups=new ArrayList<Group>(); // groups=groupsOfUser(id);
	        
	        zones.add(new Zone(0,"UPMC"));
	        zones.add(new Zone(1,"Maison"));
	        zones.add(new Zone(2,"Metro"));
	        zones.add(new Zone(3,"Maison 2"));
	        zones.add(new Zone(4,"Voisin"));
	        
	        groups.add(new Group(0,"Collègues"));
	        groups.add(new Group(1,"Famille"));
	        String description = "Téléphone Xperia \n Carte SD dedans \n pas trop abimé donc attention \n Merci.";
	        Item item=new Item(0,0,"Xperia Z3 - comme neuf", description,groups,zones);
	        
	        request.setAttribute("item", item);
	        request.setAttribute("zones",zones);
	        request.setAttribute("groups", groups);
	        request.getRequestDispatcher("restricted/itemmanagement.jsp").forward(request, response);
	        
	        
		}catch (Exception e) {
			// Rédiréction vers une page d'erreur
			e.printStackTrace();
			request.setAttribute("error", e); //remote debug
			request.getRequestDispatcher("errorpage.jsp").forward(request, response);
			
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/* TODO
		 *  action=zone  => Modify the emplacement of the object
		 *  action=update => update the information of the object (name,description,title ...)
		 */
	}

}
