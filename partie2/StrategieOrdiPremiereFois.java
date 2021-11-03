package partie2;
import partie1.*;
/**
 * Strategie d'ordi simule un joueur qui joue pour la premiere fois
 * La strategie est de tirer un coup aleatoire a chaque fois.
 * Il peut avoir plusieurs tirs sur la meme case
 * *
 * @author Pierre Belisle
 * @version Copyright A2021
 */
public class StrategieOrdiPremiereFois {

	/**
	 * Retourne le coup du joueur
	 * 
	 * @return
	 */
	public Coord getTir(){
		
		// aucune strategie en place, donne un coup aleatoire a chaque fois
		return  UtilitaireFonctions.coordAleatoire();
		
	}

	/**
	 * cette fonction n'a aucune effet, elle est seulement en place afin de prevenir par soucis
	 * de compatibilite
	 * @param c
	 */
	public void aviseTouche(Coord c){}	

}
