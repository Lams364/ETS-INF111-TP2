package partie2;
import partie1.*;
/*
 * Joueur qui joue pour la premiere fois.  Sa strategie n'est pas tres bonne.
 * 
 *  * 
 * @author Pierre Belisle
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
		 * Aucune strategie.  Retourne un coup au hasard et ne regarde meme pas
		 * s'il a deja joue son coup.
		 *                               
		 * Il ne fait donc rien lorsqu'on l'avise qu'il a touche un navire.
		 */
		return  UtilitaireFonctions.coordAleatoire();
		
	}
	
	public void aviseTouche(Coord c){}	

}
