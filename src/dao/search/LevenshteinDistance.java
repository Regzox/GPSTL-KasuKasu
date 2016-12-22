package dao.search;

/**
 * @see https://fr.wikipedia.org/wiki/Distance_de_Levenshtein
 * @see https://en.wikipedia.org/wiki/Levenshtein_distance
 * @author ANAGBLA Joan */
public class LevenshteinDistance {

	/**
	 * for all i and j, d[i,j] will hold the Levenshtein distance between
	 * the first i characters of s and the first j characters of t
	 * note that d has (m+1)*(n+1) values.
	 * rmq perso : la methode est intuitivement recursive(voir en.wikipedia)
	 * ici (fr.wikipedia) on utilise une matrice pour simuler la pile memoire 
	 * des appels recursifs (probablement moins couteux qu'un recursif reel).
	 * Je ne sais pas si c'est reelement justifie mais j'ai prefere utiliser la 
	 * methode iterative dans un souci d'optimisation de memoire/temps de reponse
	 * (appels de fonctions (qui plus est recursivement)sont couteux)
	 * dans un contexte Web ou cette methode pourrait etre sollicitee a 
	 * plusieures reprises (par plusieurs clients) en paralelle, plusieures fois 
	 * par recherche de chaque client car il faudra parcourir la base de donnees
	 * des mots pour trouver le mot le plus proche de la query recherchee.    
	 * @param mot1
	 * @param mot2
	 * @return */
	public static int LD(String mot1, String mot2 ){
		if(mot1.equalsIgnoreCase(mot2)) return 0;
		
		int m=mot1.length(); int n=mot2.length(); 
		
		if (n == 0) return m; else if (m == 0)	return n;
		
		int [][]d= new int[m+1][n+1];

		for(int i=0;i<=m;i++) d[i][0]=i; //remplissage 1ere ligne

		for(int j=0;j<=n;j++) d[0][j]=j; //remplissage 1ere colone

		//ordre d'iteration  @see slack
		
		int substitutionCost;
		for(int i=1;i<=m;i++) 
			for(int j=1;j<=n;j++){ //System.out.println("word1["+i+"]:"+word1[0].charAt(i-1) +"  word2["+j+"]:"+word2[0].charAt(j-1)); //debug
				if((mot1).charAt(i-1) == (mot2).charAt(j-1))
					substitutionCost = 0;
				else 
					substitutionCost = 2/*1*/;//2 instead of 1 because a substitution must cost more 
				
					//System.out.println("d["+i+"-1]["+j+"] + 1 = "+(d[i-1][j] + 1));//debug
					//System.out.println("d["+i+"]["+j+"-1] + 1 = "+(d[i][j-1] + 1));//debug
					//System.out.println("d["+i+"-1]["+j+"-1] + 1 = "+(d[i-1][j-1] + 1));
					d[i][j] = Math.min(
							Math.min(
									d[i-1][j] + 1, //effacement du nouveau caractere de chaine1
									d[i][j-1] + 1), //insertion dans chaine1 du nouveau caractere de chaine2
							d[i-1][j-1] + substitutionCost);//substitution  
				//showDebug(d);	
			}
		return d[m][n];
	}


	 static void showDebug(int [][] M){
		for(int i=0;i<M.length;i++) {
			for(int j=0;j<M[i].length;j++)
				System.out.print(M[i][j]);
			System.out.println();
		}
		System.out.println();
	}

	public static void main(String[] args) {
		System.out.println("LD="+LD("niche","chiens"));
		System.out.println("LD="+LD("kitten","sitting"));
		System.out.println("LD="+LD("saturday","sunday"));
		System.out.println("LD="+LD("jo","lol"));
	}
}