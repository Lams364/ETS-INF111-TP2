package partie2;

import partie1.Constantes;
import partie1.Coord;

public class StrategieOrdiExpert extends StrategieOrdiAvance{

    /*
    Partez de la strategie avancee et essayez d’ajouter une variante qui ameliore cette
    strategie (pas besoin d’être complique).
     */

    boolean traiteLigneHorizontale = false;
    boolean traiteLigneVerticalie = false;



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
                    traiteLigneHorizontale = true;
                }

            }
            if (traiteLigneHorizontale){
                cDiag = parcourirLigneHorizontale();
            }
            if (traiteLigneVerticalie){
                cDiag = parcourirLigneVerticale();
            }
            if (!traitePremiereDiagonale && !traiteDeuxiemeDiagonale
                    && !traiteLigneVerticalie && !traiteLigneHorizontale){
                cDiag = UtilitaireCollection.obtenirCoupPasDejaJouer(listeCoupsJouees);
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
     * Cette fonction permet de sortir les points non-joues de la ligne horizontale
     * @return
     */
    private Coord parcourirLigneHorizontale(){

        Coord point;

        //lookup table
        int[] tableauColonneAExaminer = {3,5,2,6,1,7,0,8,9};


        for (int i : tableauColonneAExaminer){

            point = new Coord(4,i);

            if (!UtilitaireCollection.tableauContientCoord(listeCoupsJouees,point)) {
                return point;
            }
        }
        traiteLigneHorizontale = false;
        traiteLigneVerticalie = true;
        return null;
    }


    /**
     * Cette fonction permet de sortir les points non-joues de la ligne verticale
     * @return
     */
    private Coord parcourirLigneVerticale(){

        Coord point;

        //lookup table
        int[] tableauLigneAExaminer = {3,5,2,6,1,7,0,8,9};

        for (int i : tableauLigneAExaminer){

            point = new Coord(i,4);

            if (!UtilitaireCollection.tableauContientCoord(listeCoupsJouees,point)) {
                return point;
            }
        }
        traiteLigneVerticalie = false;
        return null;
    }











}
