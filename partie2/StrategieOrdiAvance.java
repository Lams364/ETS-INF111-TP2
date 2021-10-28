package partie2;

import partie1.Constantes;
import partie1.Coord;

import java.util.*;

public class StrategieOrdiAvance extends StrategieOrdiIntermediaire {

    boolean traitePremiereDiagonale;
    boolean traiteDeuxiemeDiagonale;
    Coord cDiag;

    /**
     * Constructeur par défaut
     */
    public StrategieOrdiAvance(){

        // appel du constructeur par défaut de StrategieOrdiIntermediaire
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
        Si la collection est vide, les tirs générés sont sur la première diagonale.
        Si toutes les cases de cette diagonale ont été visitées, on tire sur la deuxième
        diagonale. Lorsque les deux diagonales ont toutes été visées, on tire au hasard dans
        des cases non déjà touchées par un tir.
         */

        Coord pointTemporaire;

        // si la collection de cases adjacentes a des coordonnes
        if (listeCoordAdjacents.size() == 0) {
            if (traitePremiereDiagonale) {

                // tant que la coordonnee a deja ete jouer et que cDiag est dans la grille
                while (UtilitaireCollection.tableauContientCoord(listeCoupsJouees, cDiag)
                        && cDiag.colonne <= Constantes.TAILLE && cDiag.ligne <= Constantes.TAILLE) {

                    // on parcours la premiere diagonale
                    cDiag.ligne++;
                    cDiag.colonne++;
                }
                // si cDiag a atteint une des bornes de la grille
                if (cDiag.ligne >= Constantes.TAILLE || cDiag.colonne >= Constantes.TAILLE){
                    traitePremiereDiagonale = false;
                    traiteDeuxiemeDiagonale = true;
                    cDiag.colonne = 0;
                }
            }
            if (traiteDeuxiemeDiagonale) {

                // tant que la coordonnee a deja ete jouer et que cDiag est dans la grille
                while (UtilitaireCollection.tableauContientCoord(listeCoupsJouees, cDiag)
                        && cDiag.colonne <= Constantes.TAILLE && cDiag.ligne <= Constantes.TAILLE) {

                    // on parcours la deuxieme diagonale
                    cDiag.ligne--;
                    cDiag.colonne++;
                }
                // si cDiag a atteint une des bornes de la grille
                if (cDiag.ligne >= Constantes.TAILLE || cDiag.colonne >= Constantes.TAILLE){
                    traiteDeuxiemeDiagonale = false;
                }

            }
            // si les deux diagonales ont ete traites
            if (!traitePremiereDiagonale && !traiteDeuxiemeDiagonale){
                cDiag = UtilitaireCollection.obtenirCoupPasDejaJouer(listeCoupsJouees);
            }
            pointTemporaire = cDiag;
        } else {

            do {
                // on etablie la variable temporaire a un point de la collection et on efface
                // ensuite le point dans la collection
                pointTemporaire = listeCoordAdjacents.getFirst();
                listeCoordAdjacents.removeFirst();

            // tant que la coordonnee a deja ete jouee et qu'il reste des coordonnees dans la
                // collection
            } while (UtilitaireCollection.tableauContientCoord(listeCoupsJouees,pointTemporaire)
                        && listeCoordAdjacents.size() > 0);

            // s'il ne reste plus aucun point dans la collection
            if (listeCoordAdjacents.size()==0)

                //on obtient un coup aleatoirement qui n'a pas ete jouer
                pointTemporaire = UtilitaireCollection.obtenirCoupPasDejaJouer(listeCoupsJouees);
        }
        listeCoupsJouees.add(new Coord(pointTemporaire.ligne, pointTemporaire.colonne));
        return pointTemporaire;
    }

}
