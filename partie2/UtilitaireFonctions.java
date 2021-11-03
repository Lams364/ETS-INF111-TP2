package partie2;
import partie1.*;


public class UtilitaireFonctions {
	
    public static int nbAlea(int min, int max){
  	    /*
  	     * Strategie, on utilise le generateur de Java qui retourne une valeur
  	     * reelle entre 0 et 1[  ensuite, on le ramene dans l'intervalle
  	     * min..max et on la transforme en entier.
  	     */

    	return (int) (Math.random() * (max - min + 1) + min); 
  	}
    
   //************************
   // POSITION ALEATOIRE
   //************************
   /*
    * Retourne un nombre entre 0 + longueurNavier -1 et 
    * Constantes.TAILLE - longueurNavire
    */
   public static Coord coordAleatoire(int longueurNavire){
  	 
  	 return new Coord(
  			 nbAlea(longueurNavire - 1, Constantes.TAILLE - longueurNavire), 
  			 nbAlea(longueurNavire - 1, Constantes.TAILLE - longueurNavire));
   }
   

   //************************
   // COORDONNEE ALEATOIRE
   //************************
   /*
    * Retourne une coordonnee dans la grille entre 0 et  Constantes.TAILLE - 1
    */
   public static Coord coordAleatoire(){
  	 
  	 return new Coord(
  			 nbAlea(0, Constantes.TAILLE - 1), 
  			 nbAlea(0, Constantes.TAILLE - 1));
   }

	/**
	 * fonction qui sert a indiquer si un point est dans la grille
	 * @param coord
	 * @return
	 */
	public static boolean coordEstDansLaGrille(Coord coord){
	   return coord.colonne <= (Constantes.TAILLE - 1) && coord.ligne <= (Constantes.TAILLE - 1)
			   && coord.colonne >= 0 && coord.ligne >= 0;
   }


}
