package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;

import fr.upmc.file.Resource;
import services.ExchangePoints;
import services.Groups;
import services.Items;

public class ObjectManagementServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	private Resource resource;

	@Override
	public void init() throws ServletException {
		super.init();
		resource = ((Resource) this.getServletContext().getAttribute("resource"));
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session=request.getSession();
		PrintWriter out = response.getWriter(); 
		response.setCharacterEncoding("UTF-8");
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
			String data = (String) request.getParameter("data");
			if( data == null ) {
				out.write( new JSONObject().put("error", "No data Specified.").toString() );
				return;
			}
			switch(data){
			case "data":

				JSONObject item =Items.getItem(objectId,userId);

				JSONObject groups = Groups.userGroups(userId);
				JSONObject userExPoints = ExchangePoints.userPoints(userId);

				/*Array à parcourir pour récupérer les noms */
				JSONArray iexpts=userExPoints.getJSONArray("expts");
				JSONArray igrps=groups.getJSONArray("groups");

				/*Array avec ids et noms */
				JSONArray itemExPoints = ExchangePoints.getItemExchangePoints(objectId,userId);
				JSONArray itemgroups = Items.getGroupsFromItem(objectId,userId);

				System.out.println(iexpts.toString());
				System.out.println("--------");
				System.out.println(igrps.toString());
				System.out.println("---------");
				System.out.println(itemExPoints.toString());
				System.out.println("----------");
				System.out.println(itemgroups.toString());

				JSONArray userPoint=new JSONArray();
				JSONArray userGroup=new JSONArray();
				JSONArray itemPoint=new JSONArray();
				JSONArray itemGroup=igrps;

				for(int i =0;i<iexpts.length();i++){
					JSONObject o = new JSONObject();
					o.put("nom", ((JSONObject)iexpts.get(i)).get("name"));
					o.put("id", ((JSONObject)iexpts.get(i)).get("id"));
					userPoint.put(o);

				}

				for(int i =0;i<igrps.length();i++){
					JSONObject o = new JSONObject();
					o.put("nom", ((JSONObject)igrps.get(i)).get("name"));
					o.put("id", ((JSONObject)igrps.get(i)).get("id"));
					userGroup.put(o);
				}

				for(int i=0;i<itemExPoints.length();i++){
					JSONObject o = new JSONObject();
					o.put("nom", ((JSONObject)
							((JSONArray)
									((JSONObject)itemExPoints.get(i)).get("subscribers")).get(0)).get("name"));
					o.put("id", ((JSONObject)((JSONObject)itemExPoints.get(i)).get("_id")).get("$oid"));
					itemPoint.put(o);
				}
				System.out.println(userGroup.toString());
				System.out.println(userPoint.toString());
				System.out.println(itemGroup.toString());
				System.out.println(itemPoint.toString());

				JSONObject res=new JSONObject();
				res.put("item", item);
				res.put("usergroup", userGroup);
				res.put("userpoint",userPoint);
				res.put("itemgroup",itemGroup);
				res.put("itempoint", itemPoint);
				response.getWriter().print(res);
				break;
			default:
				request.setAttribute("objectId", objectId);
				request.getRequestDispatcher(resource.relativePath("itemmanagement_jsp")).forward(request, response);
			}
			//request.getRequestDispatcher("restricted/itemmanagement.jsp").forward(request, response);
		}catch (Exception e) {
			// Redirection vers une page d'erreur
			e.printStackTrace();
			request.setAttribute("error", e); //remote debug
			request.getRequestDispatcher(resource.relativePath("errorpage_jsp")).forward(request, response);

		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session=request.getSession();
		PrintWriter out = response.getWriter(); 
		response.setCharacterEncoding("UTF-8");
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
				out.write( new JSONObject().put("error", "No Object Specified .").toString() );
				return;
			}
			String target = (String) request.getParameter("target");
			if( target == null ) {
				out.write( new JSONObject().put("error", "No target Specified.").toString() );
				return;
			}

			String action = (String) request.getParameter("action");
			if( action == null ) {
				out.write( new JSONObject().put("error", "No action Specified.").toString() );
				return;
			}

			switch(target){
			case "expoints":{
				String targetId = (String) request.getParameter("targetid");
				if( targetId == null ) {
					out.write( new JSONObject().put("error", "No target Id Specified.").toString() );
					return;
				}
				switch(action){
				case "update" :
					ExchangePoints.addItemToExPoint(objectId, targetId,userId);
					break;
				case "delete" :
					ExchangePoints.removeItemFromExPoint(objectId, targetId,userId);
					break;
				default:
				}
			}
			case "groups":{
				String targetId = (String) request.getParameter("targetid");
				if( targetId == null ) {
					out.write( new JSONObject().put("error", "No target Id Specified.").toString() );
					return;
				}
				switch(action){
				case "update" :
					Items.addGroupToItem(objectId, targetId,userId);
					break;
				case "delete" :
					Items.removeGroupFromItem(objectId, targetId,userId);
					break;
				default:
				}
			}
			break;
			case "item":{
				String title = (String) request.getParameter("title");
				if( title == null ) {
					out.write( new JSONObject().put("error", "No title Specified.").toString() );
					return;
				}
				String description = (String) request.getParameter("description");
				if( description == null ) {
					out.write( new JSONObject().put("error", "No description Specified.").toString() );
					return;
				}
				switch(action){
				case "update" :
					Items.updateItem(userId, objectId, title, description);
					break;
				case "delete" :
					break;
				default:
				}
			}
			break;
			default:
			}
			JSONObject o=new JSONObject();
			o.put("res", "Action Effectué");
			response.getWriter().print(o);
		}catch (Exception e) {
			// Rédiréction vers une page d'erreur
			e.printStackTrace();
			request.setAttribute("error", e); //remote debug
			request.getRequestDispatcher(resource.relativePath("errorpage_jsp")).forward(request, response);

		}
	}

}
