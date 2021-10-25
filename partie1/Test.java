package partie1;

import java.util.ArrayList;

/**
 * Programmes de test des classes:
 * partie1.Flotte
 * partie1.Navire
 * partie1.UtilitaireGrilleGui
 * 
 * @author Marc-Olivier Champagne
 * @author Jacob Lamarche
 * @author Daniel Ouambo
 *
 * @version : 21 octobre 2021
 */

public class Test {

    public static void main(String[] args) {
    	testMontrerFlotte();
    }

    /**
     * testMontrerFlotte : test en trois parties:
     * 1 - creation d'une flotte et affichage de ses informations
     * 2 - creation et remplissage d'une grille
     * 3 - appel de la methode montrerFlotte
     */
    public static void testMontrerFlotte() {
    	
    	// Creation de la flotte
    	Flotte flotteAleatoire = Flotte.obtenirFlotteAleatoire();

    	// Pour tous les navires de la flotte, affichage de leurs informations
        for(Navire navireCourant : flotteAleatoire.collectionDeNavires){
            System.out.println(navireCourant.toString());
        }
        
        // Creation d'une grille
        
        GrilleGui grille = new GrilleGui(Constantes.TAILLE,Constantes.TAILLE,
                Constantes.COULEUR_TEXTE
                ,Constantes.COULEUR_FOND, Constantes.OPTIONS, GrilleGui.DISPOSE);
        
        // Si probleme d'affichage, essayer code en commentaire ci-bas
        /* 
        partie1.GrilleGui grille = new partie1.GrilleGui(partie1.Constantes.TAILLE,partie1.Constantes.TAILLE,
                partie1.Constantes.COULEUR_TEXTE
                ,partie1.Constantes.COULEUR_FOND, null, partie1.GrilleGui.DISPOSE);
        */
        
        
        // Pause pour permettre le transfert d'information
        UtilitaireGrilleGui.pause(100);
        
        // Appel de montrerFlotte
        UtilitaireGrilleGui.montrerFlotte(flotteAleatoire,grille);
    }


}
