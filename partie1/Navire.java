package partie1;

import java.awt.*;
import java.util.*;

/**
 * Classe qui construit un navire avec les informations fournies.
 * Permet de verifier ses coordonnees pour assurer qu'il est dans la grille, sans superposition.
 * 
 * @author Marc-Olivier Champagne
 * @author Jacob Lamarche
 * @author Daniel Ouambo
 *
 * @version : 21 octobre 2021
 */
public class Navire {

	//partie1.Constantes de direction possible d'un navire
    private final String NS = "NORD-SUD";
    private final String EO = "EST-OUEST";

    // les attributs necessaire au navire
    String nom;
    int longueur;
    Color couleur;
    ArrayList<Coord> listeCoupsTouches = new ArrayList<Coord>();
    String orientation;
    ArrayList<Coord> listeCoordonnes = new ArrayList<Coord>();

    
    /**
     * partie1.Navire: constructeur par parametre d'un navire. Remplis aussi les cases concern�es.
     * @param nom: nom du type de navire
     * @param coordDebut: partie1.Coord qui represente la premiere valeur du navire en partant du coin
     * haut-gauche
     * @param coordFin: partie1.Coord qui represente l'autre extremite du navire
     * @param couleur: couleur utilise pour represente le navire
     */
    public Navire (String nom, Coord coordDebut, Coord coordFin, Color couleur){

        // calcul des nombres de colonnes et de lignes du navire
        int nbColonne = coordFin.colonne - coordDebut.colonne +1;
        int nbLigne = coordFin.ligne - coordDebut.ligne +1;

        // Les messages d'exception possibles sont :
        
        // Le nombre de lignes est plus grand que 1 et les colonnes sont differentes
        // entre le debut et la fin.
        if (nbLigne > 1 && (coordDebut.colonne != coordFin.colonne)){
            throw new IllegalArgumentException("Coordonnees NORD_SUD invalide");
             
        // Le nombre de colonnes est plus grand que 1 et les lignes sont differentes
        // entre le debut et la coordFin.
        } else if (nbColonne > 1 && (coordDebut.ligne != coordFin.ligne)){
            throw new IllegalArgumentException("Coordonnees EST_OUEST invalide");

        // La ligne n�est pas dans l'intervalle de la grille ou la ligne de d�but est plus
        // grande que la ligne de coordFin.
        } else if ((coordDebut.ligne > coordFin.ligne) || (coordDebut.ligne >= Constantes.TAILLE
                || coordFin.ligne >= Constantes.TAILLE)){
            throw new IllegalArgumentException("Ligne invalide");
        
        // La colonne n�est pas dans l�intervalle de la grille ou la colonne de d�but est
        // plus grande que la colonne de coordFin.
        } else if ((coordDebut.colonne > coordFin.colonne) || (coordDebut.colonne >= Constantes.TAILLE
                || coordFin.colonne >= Constantes.TAILLE)){
            throw new IllegalArgumentException("Colonne invalide");
        }

        // Copie des attributs directs
        this.nom = nom;
        this.couleur = couleur;

        // Copie de l'orientation et de la longueur selon la direction du navire
        if (nbLigne > nbColonne){
            orientation = NS;
            longueur = nbLigne;
        } else {
            orientation = EO;
            longueur = nbColonne;
        }

        // Pour toute la longueur du navire,
        for (int i=0;i<longueur;i++){
        	
        	// Dependamment de son orientation, on remplis son ArrayList avec les points couverts
            if(orientation.equals(NS)){
                Coord coordTemporaire = new Coord(coordDebut.ligne + i,coordDebut.colonne);
                listeCoordonnes.add(coordTemporaire);
            }else{
                Coord coordTemporaire = new Coord(coordDebut.ligne,coordDebut.colonne + i);
                listeCoordonnes.add(coordTemporaire);
            }
        }
    }

    
    /**
     * estCoule: retourne vrai si le navire est coule
     * @return: boolean navire coule ou non
     */
    public boolean estCoule(){

        // creation d'une nouvelle liste temporaire afin d'enregistrer les differentes 
    	// cases de listeCoupsTouches.
        ArrayList<Coord> listeTemporaireCoupsTouche = new ArrayList<Coord>();
        
        if(listeCoupsTouches.size() == 0) {
        	return false;
        }
        
        listeTemporaireCoupsTouche.add(listeCoupsTouches.get(0));

        // Pour tous les coups touches du navire, on ajoute ceux uniques a la liste temporaire
        for (Coord pointCoupsTouches : listeCoupsTouches) {

                // Si le point de coupsTouche n'est pas dans la liste temporaire, on l'ajoute
                if(!listeTemporaireCoupsTouche.contains(pointCoupsTouches)){
                    listeTemporaireCoupsTouche.add(pointCoupsTouches);
                }
        }

        // On evalue la quantite de coups dans la liste temporaire compare a la longueur du navire
        return listeTemporaireCoupsTouche.size() == longueur; // >= ou == ???
    }

    /**
     * dejaRecuTir: retourne vrai si la coordonnee recue a deja touche au navire.
     * @param tir: partie1.Coord du tir
     * @return: boolean de l'existence du tir sur une partie1.Coord du navire
     */
    public boolean dejaRecuTir(Coord tir){

        // pour tous les points dans la liste de coups touches de this
        for(Coord pointCourant : listeCoupsTouches){

            // si le pointCourant est egal au tir, on retourne true
            if (pointCourant.equals(tir)) return true;
        }
        // si on est sorti de la boucle, c'est qu'aucun point est egal au tir
        return false;
    }

    /**
     * tirAtouche: retourne vrai si la coordonnee recue touche au navire actuel (this) et faux 
     * autrement (implique une boucle de recherche). Retient aussi la coordonnee si elle l�a touche
     * @param tir: partie1.Coord du tir
     * @return: boolean de la presence du tir sur une partie1.Coord du navire
     */
    public boolean tirAtouche(Coord tir){

        boolean aToucheNavire = false;

        //Si le navire n'est pas coule
        if (!estCoule()){
        	
        	// Si le navire actuel n'a pas deja recu ce tir
            if(!dejaRecuTir(tir)){
            		
            	// Si le tir touche au navire actuel 
            	if(positionTouche(tir)) {
            		
            		// On retient la coordonnee dans la collection et on met le boolean a vrai
            		listeCoupsTouches.add(tir);
                    aToucheNavire = true;
                }
            }
        }
        return aToucheNavire;
    }
    
    /**
     * chevauche: retourne vrai si une des positions du navire recu touche a une des positions 
     * du navire actuel (en ligne ou en colonne).
     * @param navireAutre: partie1.Navire auquel verifie s'il existe un chevauchement
     * @return boolean du chevauchement
     */
    public boolean chevauche(Navire navireAutre){
    	
    	// Pour tous les points de navireAutre et de this
        for (Coord pointNavireAutre : navireAutre.listeCoordonnes){
            for (Coord pointThis : this.listeCoordonnes){
            	
            	// Si les deux points sont egaux, on retourne true
                if(pointNavireAutre.equals(pointThis)) return true;
            }
         }
        // Si on est sortie de la boucle, cela veut dire qu'aucun point ne se chevauchait
        return false;
    }

    /**
     * positionTouche: retourne vrai si la coordonnee du tir est entre le debut et la fin du navire
     * @param tir : partie1.Coord du tir
     * @return boolean 
     */
    private boolean positionTouche(Coord tir){

        // Pour tous les points couvert par le navire
        for (Coord point : listeCoordonnes){

            // si le tir est égal au point du navire, on retourne true
            if(point.equals(tir)) return true;
        }
        // si on sort de la boucle, cela veut dire qu'aucun point etait egal au tir.
        // On retourne donc false
        return false;
    }

    /**
     * estDansLaGrille: verifie si tous les points du navire sont dans la grille
     * @return boolean de la position des points dans la grille
     */
    public boolean estDansLaGrille(){

    	// Pour tous les points couverts par le navire
        for (Coord point : listeCoordonnes){
        	
        	// Retourne faux si le point depasse les bornes
            if (point.colonne > Constantes.TAILLE || point.ligne > Constantes.TAILLE) return false;
        }
        return true;
    }

    /**
     * toString: Transforme les informations en String
     * @return String contenant un message avec les informations du navire
     */
    public String toString(){

        String message = new String();

        message += "Nom : " + nom;
        message += "\nLongueur : " + longueur;
        message += "\nOrientation : " + orientation;
        message += "\nCoordonneDebut : " + listeCoordonnes.get(0);
        message += "\nCoordonneFin : " + listeCoordonnes.get(longueur - 1);
        message += "\nListe de coups touches : " + listeCoupsTouches.toString();
        message += "\nCouleur : " + couleur+"\n\n";

        return message;
    }
}
