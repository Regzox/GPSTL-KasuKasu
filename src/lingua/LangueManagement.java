package lingua;

import java.util.Locale;

public class LangueManagement {
	
public void testLangue()
{
	Locale local_US = new Locale("en","US");
	Locale local_FR = new Locale("fr","FR");
}

public static void main(String[] args)
{
	//création dynamique, à utiliser par exemple si on appuie sur un bouton pour identifier la langue
	// ou bien selon la localisation
	
//	String langue = new String(args[0]);
//	String pays = new String(args[1]);
//	Locale locale = new Locale(langue, pays);
}
}
