package dao.tools;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public interface PatternsHolder {

	public static String blank=" ";
	public static String phoneNumber="\\d+";
	public static String email=".+@.+";
	public static String mrpattern="/[^\\d\\w]+/";
	
	
	/**
	 * Return a set of words contained in a string 
	 * (only one occurence of a word)
	 * @param string
	 * @param pattern
	 * @return */
	 public static Set<String> wordSet(String string,String pattern) {
		return new HashSet<String>(
				Arrays.asList(string.toLowerCase().split(pattern)));}
	
	
}
