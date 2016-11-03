package utils;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Outils pour vérifier les paramètres.
 * @author Giuseppe FEDERICO
 *
 */
public class ParametersChecker {
	private static final String ONLY_LETTERS_PATTERN =
			"^[A-Za-z]+$";
	
	/**
	 * Teste si la longueur d'une string est comprise entre deux
	 * valeurs (inclus).
	 * @param lenght
	 * @param argument
	 */
	public static boolean lenght(int min, int max, String argument){
		return argument.length() <= max && argument.length() >= min;
		
	}
	
	/**
	 * Teste si les paramètres d'une reqûete sont présents et de longueur non nul.
	 * @param requestMap - La ParameterMap de la reqûete.
	 * @param Les paramètres de la reqûete.
	 */
	public static boolean testMultipleNonEmpty( Map<String,String[]> requestMap, String... arguments){
		boolean resu = true;
	
		for (String arg: arguments)
			if (requestMap.containsKey(arg) && 
					!requestMap.get(arg)[0].isEmpty()) 
				resu = false;
		
		return resu;
		
	}
	
	/**
	 * Teste la longueur de plusieurs arguments.
	 * @param min
	 * @param max
	 * @param arguments
	 * @return
	 */
	public static boolean testMultipleLenght(int min, int max, 
			String... arguments){
		boolean resu = true;
		for (String arg: arguments)
			if( !lenght(min, max, arg) ) resu = false;
		
		return resu;
		
	}
	

	/**
	 * Teste la longueur de plusieurs arguments.
	 * @param min
	 * @param max
	 * @param arguments
	 * @return
	 */
	public static boolean testMultipleOnlyLetters(String... arguments){
		boolean resu = true;
		for (String arg: arguments)
			if( !onlyLetters( arg) ) resu = false;
		
		return resu;
		
	}
	

	
	/**
	 * Teste si l'argument contient seulment des lettres.
	 * @param argument
	 * @return
	 */
	public static boolean onlyLetters(String argument){
		Pattern p = Pattern.compile(ONLY_LETTERS_PATTERN);
		 Matcher m = p.matcher(argument);
		 return  m.matches();
		
	}


}
