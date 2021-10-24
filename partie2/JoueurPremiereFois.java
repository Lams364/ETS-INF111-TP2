package partie2;
import partie1.*;
/*
 * Joueur qui joue pour la premi�re fois.  Sa strat�gie n'est pas tr�s bonne.
 * 
 *  * 
 * @author Pierre B�lisle
 * @version Copyright A2021

 */
public class JoueurPremiereFois {

	/**
	 * Retourne le coup du joueur
	 * 
	 * @return
	 */
	public Coord getTir(){
		
		/*
		 * Aucune strat�gie.  Retourne un coup au hasard et ne regarde m�me pas 
		 * s'il a d�j� jou� son coup.
		 *                               
		 * Il ne fait donc rien lorsqu'on l'avise qu'il a touch� un navire.
		 */
		return  UtilitaireFonctions.coordAleatoire();
		
	}
	
	public void aviseTouche(Coord c){}	

}
