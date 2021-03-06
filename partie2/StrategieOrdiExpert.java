package partie2;

import partie1.Constantes;
import partie1.Coord;

/**
 * Fonction qui utilise une strategie qui decoule de StrategieOrdiAvance avec un petit plus.
 * Apres avoir visites les deux diagonales, la strategie est de visites les deux lignes centrales
 * (horizontale et verticale) de la grille en partant du centre.
 */
public class StrategieOrdiExpert extends StrategieOrdiAvance{

    // constante qui represente le milieu de la grille
    final private int GRILLE_MILIEU = Constantes.TAILLE / 2;

    // deux boolean qui indique si l'on doit traiter les deux lignes centrales
    boolean traiteLigneHorizontale;
    boolean traiteLigneVerticalie;


    /**
     * constructeur par defaut qui initialise tous les collections necessaire
     */
    public StrategieOrdiExpert(){
        super();

        // initialisation des deux attributs de la classe qui n'etaient pas dans la classe parent
        traiteLigneHorizontale = false;
        traiteLigneVerticalie = false;
    }

    /**
     * fonction qui sert a obtenir le prochain point a tirer
     * @return
     */
    public Coord getTir(){


        /*                                *STRATEGIE*
        Si la collection est vide, les tirs generes sont sur la première diagonale.
        Si toutes les cases de cette diagonale ont ete visitees, on tire sur la deuxième
        diagonale. Lorsque les deux diagonales ont toutes ete visees, on visite les deux lignes
        centrales de la grille (horizontale et verticale) en partant du centre.
         */

        Coord pointTemporaire;

        // si la collection de cases adjacentes a des coordonnes
        if (listeCoordAdjacents.isEmpty()) {
            if (traitePremiereDiagonale) {

                // tant que la coordonnee a deja ete jouer et que cDiag est dans la grille
                while (UtilitaireCollection.tableauContientCoord(listeCoupsJoues, cDiag)
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
                while (UtilitaireCollection.tableauContientCoord(listeCoupsJoues, cDiag)
                        && cDiag.colonne <= (Constantes.TAILLE - 1) && cDiag.ligne <= (Constantes.TAILLE - 1)) {

                    // on parcours la deuxieme diagonale
                    cDiag.ligne--;
                    cDiag.colonne++;
                }
                // si cDiag a atteint une des bornes de la grille
                if (cDiag.colonne > (Constantes.TAILLE - 1)){
                    traiteDeuxiemeDiagonale = false;
                    traiteLigneHorizontale = true;
                }

            }

            // appel de fonction afin de parcourir les deux lignes centrales
            parcourirLigneHorVert();

            // si les lignes centrales et diagonales sont fait
            if (!traitePremiereDiagonale && !traiteDeuxiemeDiagonale
                    && !traiteLigneVerticalie && !traiteLigneHorizontale){

                // on tir des coups aleatoirement
                cDiag = UtilitaireCollection.obtenirCoupPasDejaJouer(listeCoupsJoues);
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
            } while (UtilitaireCollection.tableauContientCoord(listeCoupsJoues, pointTemporaire)
                    && listeCoordAdjacents.size() > 0);
        }
        // on ajoute le point qui sera joue dans la la collection qui comprend tous les cases
        // deja jouees
        listeCoupsJoues.add(new Coord(pointTemporaire.ligne, pointTemporaire.colonne));
        return pointTemporaire;
    }

    /**
     * Cette fonction permet de sortir les points non-joues des lignes centrales (horizontale et
     * verticale).
     * @return
     */
    private void parcourirLigneHorVert(){

        Coord pointTemp;

        // table avec les points/colonne a visiter
        int[] tableauAVisiter = {3,5,2,6,1,7,0,8,9};

        // pour tous les coord dans le tableau
        for (int i : tableauAVisiter){

            if (traiteLigneHorizontale){

                // on assigne le point temporaire a la ligne milieu (4) et a la colonne i
                pointTemp = new Coord(GRILLE_MILIEU,i);

                // s'il n'a pas deja ete joue
                if (!UtilitaireCollection.tableauContientCoord(listeCoupsJoues,pointTemp)){

                    // on assigne cDiag au coordonne du point temporaire
                    cDiag = new Coord(pointTemp.ligne, pointTemp.colonne);
                    return;
                }

                // si on a fait tous les points a visiter
                if (i == tableauAVisiter[tableauAVisiter.length - 1]){
                    traiteLigneHorizontale = false;
                    traiteLigneVerticalie = true;
                }

            }
            if (traiteLigneVerticalie){

                // on assigne le point temporaire a la colonne milieu (4) et a la ligne i
                pointTemp = new Coord(i,GRILLE_MILIEU);

                // s'il n'a pas deja ete joue
                if (!UtilitaireCollection.tableauContientCoord(listeCoupsJoues,pointTemp)){

                    // on assigne cDiag au coordonne du point temporaire
                    cDiag = new Coord(pointTemp.ligne, pointTemp.colonne);
                    return;
                }

                // si on a fait tous les points a visiter
                if (i == tableauAVisiter[tableauAVisiter.length - 1]){
                    traiteLigneHorizontale = false;
                    traiteLigneVerticalie = false;
                }
            }
        }
    }

}
