package partie2;

import partie1.Constantes;
import partie1.Coord;

import java.util.*;

public class StrategieOrdiAvance extends StrategieOrdiIntermediaire {

    boolean traitePremiereDiagonale;
    boolean traiteDeuxiemeDiagonale;
    Coord cDiag;

    /**
     * Constructeur par defaut
     */
    public StrategieOrdiAvance(){

        // appel du constructeur par defaut de StrategieOrdiIntermediaire
        super();
        cDiag = new Coord(0,0);
        traitePremiereDiagonale = true;
        traiteDeuxiemeDiagonale = false;
    }

    /**
     * fonction qui sert a obtenir le prochain point a tirer
     * @return
     */
    public Coord getTir(){


        /*                                *STRATEGIE*
        Si la collection est vide, les tirs generes sont sur la premiere diagonale.
        Si toutes les cases de cette diagonale ont ete visitees, on tire sur la deuxieme
        diagonale. Lorsque les deux diagonales ont toutes ete visees, on tire au hasard dans
        des cases non deja touchees par un tir.
         */

        Coord pointTemporaire = new Coord();

        // si la collection de cases adjacentes a des coordonnes
        if (listeCoordAdjacents.isEmpty()) {
        	
            if (traitePremiereDiagonale) {

                // tant que la coordonnee a deja ete jouer et que cDiag est dans la grille
                while (UtilitaireCollection.tableauContientCoord(listeCoupsJouees, cDiag)
                        && cDiag.colonne <= (Constantes.TAILLE - 1) && cDiag.ligne <= (Constantes.TAILLE - 1)) {

                    // on parcours la premiere diagonale
                    cDiag.ligne++;
                    cDiag.colonne++;
                }
                
                // si cDiag a atteint une des bornes de la grille
                if (cDiag.ligne > (Constantes.TAILLE - 1) || cDiag.colonne > (Constantes.TAILLE - 1)){
                    traitePremiereDiagonale = false;
                    traiteDeuxiemeDiagonale = true;
                    
                    // Remise des points dans la grille dans l'autre diagonale
                    cDiag.colonne = 0;
                    cDiag.ligne = (Constantes.TAILLE - 1);
                }
            }
            
            if (traiteDeuxiemeDiagonale) {

                // tant que la coordonnee a deja ete jouer et que cDiag est dans la grille
                while (UtilitaireCollection.tableauContientCoord(listeCoupsJouees, cDiag)
                        && cDiag.colonne <= (Constantes.TAILLE - 1) && cDiag.ligne <= (Constantes.TAILLE - 1)) {

                    // on parcours la deuxieme diagonale
                    cDiag.ligne--;
                    cDiag.colonne++;
                }
                // si cDiag a atteint une des bornes de la grille
                if (cDiag.colonne > (Constantes.TAILLE -1)){
                    traiteDeuxiemeDiagonale = false;
                }

            }
            // si les deux diagonales ont ete traites, on prend une valeur aleatoire non jouee
            if (!traitePremiereDiagonale && !traiteDeuxiemeDiagonale){
                cDiag = UtilitaireCollection.obtenirCoupPasDejaJouer(listeCoupsJouees);
            }
            
            // les valeurs de cDiag sont donnes au point temporaire
            pointTemporaire = new Coord(cDiag.ligne, cDiag.colonne);
            
        } else {

            // tant que la coordonnee a deja ete jouee et qu'il reste des coordonnees dans la
            // collection
        	while (UtilitaireCollection.tableauContientCoord(listeCoupsJouees, pointTemporaire)
                    && listeCoordAdjacents.size() > 0) {
        		
                // on etablie la variable temporaire a un point de la collection et on efface
                // ensuite le point dans la collection
        		pointTemporaire = listeCoordAdjacents.getFirst();
                listeCoordAdjacents.removeFirst();
        	}
        	
        	// si la file d'adjacents est maintenant vide et qu'aucun point n'est selectionne
            if (listeCoordAdjacents.isEmpty() && pointTemporaire == null) {
            	
                //on obtient un coup aleatoirement qui n'a pas ete jouer
                pointTemporaire = UtilitaireCollection.obtenirCoupPasDejaJouer(listeCoupsJouees);
                
            }
        }
        
        // le point est ajoute a la liste des points retournes avant d'etre retourne
        listeCoupsJouees.add(new Coord(pointTemporaire.ligne, pointTemporaire.colonne));
        return pointTemporaire;
    }

}
