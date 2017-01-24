package servlets;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.mongodb.util.JSON;

import servlets.tools.templates.offline.OfflinePostServlet;
import servlets.tools.templates.online.OnlinePostServlet;
/**
 * 
 * @author ouiza
 *
 */
public class SaveLanguageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
	
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
	    //Files.write("/home/ouiza/Bureau/file.txt", json, );  
	    System.out.println(json);
//		try (FileWriter file = new FileWriter("/home/ouiza/Documents/file.txt")) {
//			file.write("ouiza");
//			file.write(obj.toString());
//		} catch (IOException e) {
//		   // do something
//		}
	}
}