package partie2;
import java.util.ArrayList;
import java.util.Vector;
import partie1.*;


public class UtilitaireCollection {

	/**
	 * Genere aleatoirement des coordonnees jusqu'a ce qu'une d'entre elles
	 * ne fasse pas partie du tableau recu
	 * 
	 * @param tableau
	 * 
	 * @return Une nouvelle coordonnee non deja attribuee.
	 */
	public static Coord obtenirCoupPasDejaJouer(ArrayList tableau){

		Coord c;

		do{

			c  = UtilitaireFonctions.coordAleatoire();

			//S'arrete avec un coup pas deja joue
		}while(tableauContientCoord(tableau, c));

		return c;

	}
	
	/**
	 * equivalent de contains sauf qu'on regarde le contenu des coordonnees
	 *  et non seulement leur references (deepContains)
	 * @param tableau
	 * @param c
	 * @return
	 */

	public static boolean tableauContientCoord(ArrayList tableau, Coord c){

		/*
		 * Strategie : On regarde chaque coup du tableau et s'il est identique
		 * au coup c recu.  La boucle s'arrete au tour suivant si elle
		 * le trouve.
		 */
		boolean existe = false;

		//iterateur
		int  i = 0;

		
		while(i < tableau.size() && !existe){
			
			existe = c.equals((Coord)tableau.get(i));
			i++;
		}				 

		return existe;
	}
}