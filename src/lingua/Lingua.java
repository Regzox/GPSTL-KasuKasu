package lingua;

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
	private static final Map<String,String> quodWMS = new HashMap<String, String>();
	static 
	{
		quodWMS.put("fr-FR","[kasukasu] Confirmez votre compte.");
		quodWMS.put("en-GB","[kasukasu] Welcome to kasu-kasu.");
		quodWMM.put("fr-FR","Bienvenue sur kasu-kasu. \nMerci de confirmer votre compte en cliquant le lien ci-dessous : \n ");
		quodWMM.put("en-GB","Welcome to kasu-kasu.\nPlease, confirm your account by clicking the link below : \n");

	}

	/**
	 * "latin" means translator in latin 
	 * This is a home made multi-language web-app dictionary.
	 * TODO please fill this dictionary with your Strings */
	private static Map<String,Map<String,String>> latin = new HashMap<String, Map<String, String>>();
	static
	{
		latin.put("welcomeMailSubject",quodWMS);
		latin.put("welcomeMailMessage",quodWMM);
	}

	/**
	 * Field Getter by dialect 
	 * @param field
	 * @param dialect
	 * @return
	 * @throws StringNotFoundException
	 */
	public static String get(String field,String dialect) throws StringNotFoundException{
		if(latin.containsKey(field))
			if(latin.get(field).containsKey(dialect))
				return latin.get(field).get(dialect);
			else
				throw new StringNotFoundException("Dictionary : unknown dialect");
		else 
			throw new StringNotFoundException("Dictionary : unknown field");
	}

	/**
	 * local test
	 * @param args
	 * @throws StringNotFoundException 
	 */
	public static void main(String[] args) throws StringNotFoundException {
		System.out.println(latin.toString());
		System.out.println(get("welcomeMailSubject","fr-FR"));
		System.out.println(get("welcomeMailMessage","fr-FR"));
		
		System.out.println(get("welcomeMailSubject","en-GB"));
		System.out.println(get("welcomeMailMessage","en-GB"));
	}
}
