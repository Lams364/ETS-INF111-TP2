package partie2;
import java.awt.*;

import partie1.*;


import javax.swing.JOptionPane;

/**
 * Simule l'intelligence de resolution d'un jeu de bataille navale (Battleship)
 * 
 * Ce programme implemente quelques strategies possible pour trouver les navires
 * caches d'un jeu.
 *  
 *@author Pierre Belisle
 *@version Copyright A2021
 *
 */

public class DemarrerBatailleNavale {

	/*
	 * Strategie globale : On utilise une GrilleGui pour obtenir une
	 * grille pour afficher la flotte du joueur que l'ordinateur doit trouver.
	 *                              
	 *     Classes necessaires de votre tp2 :
	 *     
	 *     Navire : Les donnees et les methodes concernant un navire.
	 *     GrilleGui : grille d'affichage et de saisie graphique
	 *     UtilitaireGrilleGui : Interface entre le programme et la grilleGui.
	 *     Flotte : Contient tous les navires du joueur
	 *            
	 *     Classe fournies :
	 *     
	 *     StrategieOrdiPremiereFois : Joue n'importe comment
	 *     StrategieOrdiDebutant : Ne jou epas 2 fois au meme endroit.
	 *                                  
	 *     UtilitaireCollection : Recherche d'une coordonnee dans une collection.
	 *     UtilitaireFonctions : Permet d'obtenir des tirs au hasard.
	 *                           
	 *                               
	 */

	static int nbRepetitions = 0;
	static int nbTirs = 0;
	static int tempsPause = 100;

	public static void main(String[] args) {

		// Creation de l'interface graphique qui permet de jouer.
		GrilleGui gui = new GrilleGui(Constantes.TAILLE, Constantes.TAILLE,
				Constantes.COULEUR_TEXTE, 
				Constantes.COULEUR_FOND,
				Constantes.OPTIONS,
				GrilleGui.QUITTE);

		// Laisse le temps de creer le gui.
		UtilitaireGrilleGui.pause(250);

		// Boucle virtuellement infinie.  Le programme quitte sur X du gui.
		boolean quitter = false;

		// tant que quitter n'est pas true
		while(!quitter){

			// Retiendra le nombre total de fois que les tirs sont alles
			// sur des cases deja tirees.
			nbRepetitions = 0;

			//Retient le nombre total de tirs.
			nbTirs = 0;

			// on appel la fonction qui demarre la partie
			demarrerModeOrdi(gui);

			// Petit message qui donne le temps de voir ce qui s'est passee.
			JOptionPane.showMessageDialog(null,"Solution trouvee en " +
			nbTirs 	+ 	" coups avec " + nbRepetitions + " repetition de tirs");

			// on reinitialise la grille a la fin de la partie
			UtilitaireGrilleGui.reinitialiserGui(gui);

		}
	}

	/**
	 * Procedure qui creer une flotte aleatoire et qui fait demarrer la partie
	 * @param gui
	 */
	public static void demarrerModeOrdi(GrilleGui gui){

		// On cree une nouvelle flotte aleatoire et on la montre.
		Flotte flotteOrdi = Flotte.obtenirFlotteAleatoire();

		// appel de fonction
		UtilitaireGrilleGui.montrerFlotte(flotteOrdi, gui);

		// On attend que l'utilisateur clique sur un des boutons d'options.
		while(!gui.optionMenuEstCliquee()){
			UtilitaireGrilleGui.pause(1);	
		};

		// On remet la page blanche pour verifier que l'application trouve bien
		// les navires.
		UtilitaireGrilleGui.reinitialiserGui(gui);

		// On obtient l'option du menu qui a ete clique.
		String menu = gui.getOptionMenuClique();

		// On compare les String pour trouver la strategie choisie.
		// Selon la selection, on cree le joueur avec la bonne strategie
		// et on demarre la partie en lui passant le parametre approprie.
		if(menu.equals(
				Constantes.OPTIONS[Constantes.PREMIERE_FOIS])){

			JoueurPremiereFois ordi =  new JoueurPremiereFois();
			demarrerPartie(ordi, flotteOrdi, Constantes.PREMIERE_FOIS, gui);

		}
		else if(menu.equals(
				Constantes.OPTIONS[Constantes.DEBUTANT])){

			JoueurDebutant ordi = new JoueurDebutant();
			demarrerPartie(ordi, flotteOrdi, Constantes.DEBUTANT, gui);
		}
		else if(menu.equals(
				Constantes.OPTIONS[Constantes.INTERMEDIAIRE])){

			StrategieOrdiIntermediaire ordi = new StrategieOrdiIntermediaire();
			demarrerPartie(ordi, flotteOrdi, Constantes.INTERMEDIAIRE, gui);
		}		

		else if(menu.equals(
				Constantes.OPTIONS[Constantes.AVANCE])){

			StrategieOrdiAvance ordi = new StrategieOrdiAvance();
			demarrerPartie(ordi, flotteOrdi, Constantes.AVANCE, gui);
		}

		else if(menu.equals(
				Constantes.OPTIONS[Constantes.EXPERT])){

			StrategieOrdiExpert ordi = new StrategieOrdiExpert();
			demarrerPartie(ordi, flotteOrdi, Constantes.EXPERT, gui);
		}
	}

	/**
	 * Demarre la partie avec l'ordi recu qui peut avoir une strategie
	 * differente.
	 * 
	 * Principalement pour eviter la repetition de code.
	 * 
	 * @param ordi
	 * @param flotte
	 * @param lequel
	 * @param gui
	 */
	public static void demarrerPartie(Object ordi, 
			Flotte flotte, 
			int lequel, 
			GrilleGui gui){

		while(!flotte.jeuTermine()){
		

			Coord tir = getTirOrdi(ordi, lequel);
			
			// Un tir de plus pour les stats.
			nbTirs++;

			// Compte les repetitions de tirs enregardant si dans
			// le gui c'est touche.
			if(gui.getValeur(tir) == Constantes.TOUCHE) {
				nbRepetitions++;
			}

			// On montre le tir.
			UtilitaireGrilleGui.afficherTir(gui, tir);
			
			// Donne le temps de voir le tir (debug).
			UtilitaireGrilleGui.pause(tempsPause);

			// Si le tir a touche mais a une nouvelle position
			// On affiche la case touchee.
			if(!flotte.dejaRecuCoup(tir)){
				
				if(flotte.leTirTouche(tir)){
					UtilitaireGrilleGui.afficherNavireTouche(gui, tir);
					aviserOrdi(ordi, lequel, tir);
				}				
			}
		}
	}

	/*
	 * Retourne le tir selon le niveau du joueur fourni
	 * avec le deuxieme parametre.
	 * 
	 * @param ordi
	 * @param lequel Le niveau du joueur
	 * 
	 * @return La coord du tir de l'ordi.
	 */
	private static Coord getTirOrdi(Object ordi, int lequel){
		
		//Tir a retourner
		Coord tir = null;
		
		// On presume que le parametre est valide.
		switch (lequel){

			case Constantes.PREMIERE_FOIS : {

				tir =((JoueurPremiereFois) ordi).getTir();

			}break;

			case Constantes.DEBUTANT :  {

				tir =((JoueurDebutant) ordi).getTir();

			}break;

			case Constantes.INTERMEDIAIRE : {

				tir =((StrategieOrdiIntermediaire) ordi).getTir();

			}break;

			case Constantes.AVANCE : {

				tir =((StrategieOrdiAvance) ordi).getTir();

			}break;

			case Constantes.EXPERT :  {

				tir =((StrategieOrdiExpert) ordi).getTir();

			}break;

		}
		return tir;		
	}
	/*
	 * Selectionne la bonne strategie selon le deuxieme parametre
	 * qui represente le niveau du joueur.
	 */	
	private static void aviserOrdi(Object ordi, int lequel, Coord tir){
		
		switch (lequel){

			case Constantes.PREMIERE_FOIS : {
						
				((JoueurPremiereFois) ordi).aviseTouche(tir);
				
			}break;
	
			case Constantes.DEBUTANT :  {
				
				((JoueurDebutant) ordi).aviseTouche(tir);
				
			}break;
				
			case Constantes.INTERMEDIAIRE :{

				((StrategieOrdiIntermediaire) ordi).aviserTir(tir);

			}break;
	
			case Constantes.AVANCE :{

				((StrategieOrdiAvance) ordi).aviserTir(tir);
				
			}break;
			
			case Constantes.EXPERT :  {

				((StrategieOrdiExpert) ordi).aviserTir(tir);
	
			}break;
			
		}
	}
}