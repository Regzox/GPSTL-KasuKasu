package servlets;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import exceptions.UserNotFoundException;
import exceptions.UserNotUniqueException;
import services.User;

/**
 * 
 * @author ouiza
 *
 */
public class SaveLanguageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

		try {
			if(User.isAdmin((String)request.getSession().getAttribute("userId")))
			{
				//TODO save the json object in the traduction.json file
				File file = new File("/home/ouiza/Bureau/file.txt");
				FileOutputStream f = new FileOutputStream(file);

				BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
				String json = "";
				if(br != null){
					json = br.readLine();
				}
				for(int i=0 ; i<json.length();i++)
				{
					f.write(json.charAt(i));
				}
				f.close();
				//System.out.println(json);
			}else
				return;
		} catch (UserNotFoundException | UserNotUniqueException e) {
			e.printStackTrace();
		}
	}
}