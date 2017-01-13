package lingua;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import exceptions.StringNotFoundException;

/**
 * @author AJoan, Ouiza
 */
//just for now 
//TODO replace by better or service api @ouiza
public class Lingua {
	/**
	 * "quod" means meaning in latin
	 * This quod is welcomeMailMessage quod (quodWMM) 
	 *  fr-FR :
	 * 	** fr reprensents the global language (french)  
	 * 	** FR represents the region of language (France)*/
	private static final ArrayList<String> quodWMM = new ArrayList<String>();
	private static final ArrayList<String> quodWMS = new ArrayList<String>();
	private static final ArrayList<String> quodRetPswMM = new ArrayList<String>();
	private static final ArrayList<String> quodRetPswMS = new ArrayList<String>();
	private static final ArrayList<String> quodModifMM = new ArrayList<String>();
	private static final ArrayList<String> quodModifMS = new ArrayList<String>();
	private static final ArrayList<String> quodLoanMM = new ArrayList<String>();
	private static final ArrayList<String> quodLoanMS = new ArrayList<String>();
	static 
	{
		//welcome mail subject
		quodWMS.add("[kasukasu] Confirmez votre compte.");
		quodWMS.add("[kasukasu] Confirm your account.");
		
		// retrieve password mail subject
		quodRetPswMS.add("[kasukasu] Récupération de votre mot de passe.");
		quodRetPswMS.add("[kasukasu] Retrieving your password.");
		
		// modif password mail subject
		quodModifMS.add("[kasukasu] Modification de votre mot de passe.");
		quodModifMS.add("[kasukasu] Change of your password.");
		
		// loaning request
//		quodLoanMS.add("[kasukasu] Demande d'emprunt pour l'objet : ");
//		quodLoanMS.add("[kasukasu] Loan request for the item : ");

		//welcome mail message
		quodWMM.add("Bienvenue sur kasu-kasu. \nMerci de confirmer votre compte en cliquant sur le lien ci-dessous : \n ");
		quodWMM.add("Welcome to kasu-kasu.\nPlease, confirm your account by clicking the link below : \n");

		//retrieve password mail message
		quodRetPswMM.add("Voici votre mot de passe : \n ");
		quodRetPswMM.add("Here is your password : \n");
		
		//modif password mail message
		quodModifMM.add("Bonjour,\n\n Votre mot de passe a récement fait l'objet d'une modification."
				+ " Contactez au plus vite un administrateur si cette demande de changement "
				+ "de mot de passe n'a pas été initié par vous.\n\n Cordialement,\n "
				+ "L'équipe KasuKasu");
		quodModifMM.add("Hello,\n\n Your password has been recently changed."
				+ " If this change password request has not been initiated by you, please contact an administrator "
				+ "as soon as possible.\n\n Cordially,\n "
				+ "Team KasuKasu");

		//loaning request 
		//TODO à améliorer ??!
//		quodLoanMM.add("Voici votre mot de passe : \n ");
//		quodLoanMM.add("Here is your password : \n");
	}

	/**
	 * "latin" means translator in latin 
	 * This is a home made multi-language web-app dictionary.
	 * TODO please fill this dictionary with your Strings */
	private static Map<String,ArrayList<String>> latin = new HashMap<String, ArrayList<String>>();
	static
	{
		latin.put("welcomeMailSubject",quodWMS);
		latin.put("welcomeMailMessage",quodWMM);
		latin.put("retMailSubject",quodRetPswMS);
		latin.put("retMailMessage",quodRetPswMM);
		latin.put("modifMailSubject",quodRetPswMS);
		latin.put("modifMailMessage",quodRetPswMM);
//		latin.put("LoanMailSubject",quodModifMS);
//		latin.put("LoanMailMessage",quodRetPswMM);
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
			if(dialect.equals("fr"))
				return latin.get(field).get(0);
			else if(dialect.equals("en"))
				return latin.get(field).get(1);
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
		System.out.println(get("retMailSubject","fr"));
		System.out.println(get("retMailMessage","fr"));
		
		System.out.println(get("retMailSubject","en"));
		System.out.println(get("retMailMessage","en"));
	}
}
