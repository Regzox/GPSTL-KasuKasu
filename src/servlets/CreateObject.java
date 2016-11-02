package servlets;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class CreateObject extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public CreateObject() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//TODO
		System.out.println(request.getParameter("nom"));
		System.out.println(request.getParameter("description"));
		System.out.println(request.getParameter("datedebut"));
		System.out.println(request.getParameter("datefin"));
		System.out.println(request.getParameter("groupe"));
		System.out.println(request.getParameter("coordonnees"));




	}

}
