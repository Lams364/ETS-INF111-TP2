package partie2;
import java.util.ArrayList;

import partie1.*;

/**
 * Joueur qui a deja joue.  Sa strategie est un peu meilleur que la
 * premiere fois qu'il a  joue.
 *
 * Il retient ses coups et ne joue pas deux fois le meme.  Il ne fait rien de
 * special s'il est avise d'avoir touche un navire.
 * 
 * @author Pierre Belisle
 * @version Copyright A2021
 *
 */
public class StrategieOrdiDebutant {

	// collection qui retient les coups qui ont ete joues
	private ArrayList<Coord> listeCoupsJoues;

	/**
	 * constructeur par defaut qui initialise la collection de coup touches
	 */
	public StrategieOrdiDebutant(){
		listeCoupsJoues = new ArrayList<>();
	}

	/**
	 * cette fonction permet d'obtenir le tir de la strategie
	 * @return le tir
	 */
	public Coord getTir(){
		
		/*
		 * Strategie : On obtient un coup pas deja joue du module de collection
		 * et on l'ajoute a la collection.
		 *                  
		 * La methode a ete mise dans UtilitaireCollection pour reutilisation
		 */

		Coord tir = UtilitaireCollection.obtenirCoupPasDejaJouer(listeCoupsJoues);
				
		listeCoupsJoues.add(tir);
		
		return tir;
		
	}

	/**
	 * cette fonction n'a aucune effet, elle est seulement en place afin de prevenir par soucis
	 * de compatibilite
	 * @param c
	 */
	public void aviseTouche(Coord c){}	
}
