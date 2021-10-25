package partie2;
import java.util.ArrayList;
import java.util.Vector;
import partie1.*;


public class UtilitaireCollection {

	/**
	 * G�n�re al�atoirement des coordonn�es jusqu'� ce qu'une d'entre elles
	 * ne fasse pas partie du tableau re�u
	 * 
	 * @param tableau
	 * 
	 * @return Une nouvelle coordonn�e non d�j� attribu�e.
	 */
	public static Coord obtenirCoupPasDejaJouer(ArrayList tableau){

		Coord c;

		do{

			c  = UtilitaireFonctions.coordAleatoire();

			//S'arr�te avec un coup pas d�j� jou�
		}while(tableauContientCoord(tableau, c));

		return c;

	}
	
	/**
	 * �quivalent de contains sauf qu'on regarde le contenu des coordonn�es
	 *  et non seulement leur r�f�rences (deepContains)
	 * @param tableau
	 * @param c
	 * @return
	 */

	public static boolean tableauContientCoord(ArrayList tableau, Coord c){

		/*
		 * Strat�gie : On regarde chaque coup du tableau et s'il est identique
		 * au coup c re�cu.  La boucle s'arr�te au tour suivant si elle 
		 * le trouve.
		 */
		boolean existe = false;

		//it�rateur
		int  i = 0;

		
		while(i < tableau.size() && !existe){
			
			existe = c.equals((Coord)tableau.get(i));
			i++;
		}				 

		return existe;
	}
}