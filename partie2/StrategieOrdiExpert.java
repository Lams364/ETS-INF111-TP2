package partie2;

import partie1.Constantes;
import partie1.Coord;

public class StrategieOrdiExpert extends StrategieOrdiAvance{

    /*
    Partez de la strategie avancee et essayez d’ajouter une variante qui ameliore cette
    strategie (pas besoin d’être complique).
     */

    /**
     * fonction qui sert a obtenir le prochain point a tirer
     * @return
     */
    public Coord getTir(){


        /*                                *STRATEGIE*
        Si la collection est vide, les tirs generes sont sur la première diagonale.
        Si toutes les cases de cette diagonale ont ete visitees, on tire sur la deuxième
        diagonale. Lorsque les deux diagonales ont toutes ete visees, on tire au hasard dans
        des cases non deja touchees par un tir.
         */

        Coord pointTemporaire;

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
            // si les deux diagonales ont ete traites
            if (!traitePremiereDiagonale && !traiteDeuxiemeDiagonale){
                cDiag = parcourirGrilleChessStyle();
            }
            pointTemporaire = new Coord(cDiag.ligne, cDiag.colonne);
        } else {

            do {
                // on etablie la variable temporaire a un point de la collection et on efface
                // ensuite le point dans la collection
                pointTemporaire = listeCoordAdjacents.getFirst();
                listeCoordAdjacents.removeFirst();

                // tant que la coordonnee a deja ete jouee et qu'il reste des coordonnees dans la
                // collection
            } while (UtilitaireCollection.tableauContientCoord(listeCoupsJouees, pointTemporaire)
                    && listeCoordAdjacents.size() > 0);
        }
        listeCoupsJouees.add(new Coord(pointTemporaire.ligne, pointTemporaire.colonne));
        return pointTemporaire;
    }

    /**
     * Cette fonction permet de parcourir la grille a la maniere d'un jeu d'échec
     * @return
     */
    private Coord parcourirGrilleChessStyle(){

        Coord point = new Coord();

        do {
            for (int lign = 0; lign < Constantes.TAILLE - 1; lign++) {

                for (int col = 0; col < Constantes.TAILLE - 1; col += 2) {

                    if ((lign % 2) != 0) {
                        point = new Coord(lign, col + 1);
                    } else {
                        point = new Coord(lign,col);
                    }

                    if (!UtilitaireCollection.tableauContientCoord(listeCoupsJouees,point)
                            && UtilitaireFonctions.coordEstDansLaGrille(point)){
                        return point;
                    }
                }
            }
        } while (UtilitaireCollection.tableauContientCoord(listeCoupsJouees,point));
        return point;
    }

}
