package partie2;
import java.util.ArrayList;
import java.util.Vector;
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
public class JoueurDebutant {
	
	/*
	 * Strategie :
	 */
	
	// On aurait pu prendre n'importe quelle collection.
	private ArrayList<Coord> tabCoups;
	
	public JoueurDebutant(){
		tabCoups = new ArrayList<>();
	}

	/**
	 * 
	 * @return
	 */
	public Coord getTir(){
		
		/*
		 * Strategie : On obtient un coup pas deja joue du module de collection
		 * et on l'ajoute a la collection.
		 *                  
		 * La methode a ete mise dans UtilitaireCollection pour reutilisation
		 */
		Coord c = UtilitaireCollection.obtenirCoupPasDejaJouer(tabCoups);  
				
		tabCoups.add(c);
		
		return c; 
		
	}
	
	public void aviseTouche(Coord c){}	
}
