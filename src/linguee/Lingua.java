package linguee;

import java.util.HashMap;
import java.util.Map;

/**
 * @author AJoan
 */
//just for now 
//TODO replace by better or service api
public class Lingua {
	/**
	 * "quod" means meaning in latin
	 * This quod is welcomeMailMessage quod (quodWMM) 
	 *  fr-FR :
	 * 	** fr reprensents the global language (french)  
	 * 	** FR represents the region of language (France)*/
	private static final Map<String,String> quodWMM = new HashMap<String, String>();
	static 
	{
		quodWMM.put("fr-FR","Bienvenue sur kasu-kasu");
		quodWMM.put("en-GB","Welcome to kasu-kasu");
	}

	/**
	 * "latin" means translator in latin 
	 * This is a home made multi-language web-app dictionary.
	 * TODO please fill this dictionary with your Strings */
	public static Map<String,Map<String,String>> latin = new HashMap<String, Map<String, String>>();
	static
	{
		latin.put("welcomeMailMessage",quodWMM);
	}

	/**
	 * local test
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(latin.toString());
		System.out.println(latin.get("welcomeMailMessage").get("fr-FR"));
		System.out.println(latin.get("welcomeMailMessage").get("en-GB"));
	}
}
