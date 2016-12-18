package dao.tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author ANAGBLA  Joan 
 * TODO A REVOIR ET CORRIGER SURTT MISSING ET TRANSPOSED ET Surtout buildSwapedWord
 * */
public class PatternsHolder {

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



	public static Set<String> designPattern(Collection<String> words,int indulgence) {
		System.out.println("design pattern from query="+words+" with indulgence level="+indulgence);
		Set<String> merged= new HashSet<>();
		for(String word : words)
			merged.addAll(designPattern(word,indulgence));
		return merged;
	}



	public static Set<String> designPattern(String word,int indulgence) {
		Set<String> compiled= new HashSet<>();
		compiled=extra_modified(missing(word,indulgence),indulgence);
		compiled.addAll(transposed(word,indulgence));
		return compiled; 
	}



	private static Set<String> transposed(Collection<String> words,int indulgence){
		Set<String> merged= new HashSet<>();
		for(String word : words)
			merged.addAll(transposed(word,indulgence));
		return merged;
	}


	private static Set<String> transposed(String word, int indulgence){
		Set<String> compiled= new HashSet<>();
		for(int i=0;i<word.length()-1;i++)
			compiled.add(buildSwapedWord(word,i,indulgence));
		return compiled;
	}

	private static String buildSwapedWord(String word, int i,int indulgence){
		if(!(i+indulgence<word.length()-1)) return word; //silent error management 
		//(return the word int the spirit of adding it to a set 
		//(no duplication of word (it is like if it doesn't return anything (new))))
		char [] warr = word.toCharArray();
		char []tmp = new char[indulgence];
		for(int j=0; j<indulgence;j++){
			tmp[j]=warr[i+j];
			warr[i+j]=warr[i+1+j];
			warr[i+1+j]=tmp[j];
		}
		return new String(warr); //indulgence =0 return the same word
	}

	private static Set<String> missing(Collection<String> words,int indulgence){
		Set<String> merged= new HashSet<>();
		for(String word : words)
			merged.addAll(missing(word,indulgence));
		return merged;
	}

	private static Set<String> missing(String word,int indulgence){
		Set<String> compiled= new HashSet<>();
		for(int j=0;j<=indulgence;j++)
			for(int i=0;i<=word.length()-j;i++)
				//System.out.println("["+i+","+j+"] : "+word.substring(0,i)+word.substring(i+j));
				compiled.add(word.substring(0,i)+word.substring(i+j));
		return compiled;
	}


	private static Set<String> extra_modified(Collection<String> words,int indulgence){
		Set<String> merged= new HashSet<>();
		for(String word : words)
			merged.addAll(extra_modified(word,indulgence));
		return merged;
	}

	private static Set<String> extra_modified(String word,int indulgence){
		Set<String> compiled= new HashSet<>();
		char []gaps = new char[indulgence];
		for(int c=0;c<indulgence;c++)
			gaps[c]='.';
		for(int i=0;i<=word.length();i++)//("^" en dur): entorse a la genericite (mais bon effcicacite preferee)
			compiled.add(word.substring(0,i)+new String(gaps)+word.substring(i));
		return compiled;
	}

	public static void main(String[] args) {
		List<String> sample= Arrays.asList(new String[]{/*"JOANNE",*/"JOE"}); //manque JOANEN
		//System.out.println("extra_modified : "+extra_modified("JOE",1));
		//System.out.println("missing :" +missing(sample,1));
		//System.out.println("extra_modified_missing :" +extra_modified(missing(sample,1),1));
		//System.out.println("transposed : "+transposed(sample,2));//TODO  revoir
		System.out.println("designPattern : "+designPattern(sample,1));
	}
}