package partie2;

import partie1.Constantes;
import partie1.Coord;

import java.util.*;

public class StrategieOrdiAvance {

    private ArrayList<Coord> listeCoupsJouees;
    private LinkedList<Coord> listeCoordAdjacents;
    boolean traitePremiereDiagonale = true;
    boolean traiteDeuxiemeDiagonale = false;
    Coord cDiag = new Coord(0,0);

    /**
     * Constructeur par défaut
     */
    public StrategieOrdiAvance(){

        listeCoupsJouees = new ArrayList<>();
        listeCoordAdjacents = new LinkedList<>();

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

        Coord point = new Coord();

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
            point = cDiag;
        } else {

            do {
                // on etablie la variable temporaire a un point de la collection et on efface
                // ensuite le point dans la collection
                point = listeCoordAdjacents.getFirst();
                listeCoordAdjacents.removeFirst();

            // tant que la coordonnee a deja ete jouee et qu'il reste des coordonnees dans la
                // collection
            } while (UtilitaireCollection.tableauContientCoord(listeCoupsJouees,point)
                        && listeCoordAdjacents.size() > 0);

            // s'il ne reste plus aucun point dans la collection
            if (listeCoordAdjacents.size()==0)

                //on obtient un coup aleatoirement qui n'a pas ete jouer
                point = UtilitaireCollection.obtenirCoupPasDejaJouer(listeCoupsJouees);
        }
        listeCoupsJouees.add(point);
        return point;
    }


    /**
     * fonction qui, selon la stratégie, fait des actions consequemment si le tir a touche a un
     * navire.
     * @param tir
     */
    public void aviserTir(Coord tir){

        /* STRATÉGIE
            La strategie ici est d'ajouter les cases adjacentes au tir entre en parametre.
            Les cases adjacentes en NORD,SUD,EST et OUEST sont donc ajoutees.
         */

        Coord pointTemporaire;

        pointTemporaire = new Coord();

        // rajouter les conditions afin que les points ne depassent pas la grille

        // construction de la coordonnee au NORD du tir
        pointTemporaire.ligne = tir.ligne + 1;
        pointTemporaire.colonne  = tir.colonne;
        if(!UtilitaireCollection.tableauContientCoord(listeCoupsJouees,pointTemporaire))
            listeCoordAdjacents.add(pointTemporaire);

        // construction de la coordonnee au SUD du tir
        pointTemporaire.ligne = tir.ligne - 1;
        if(!UtilitaireCollection.tableauContientCoord(listeCoupsJouees,pointTemporaire))
            listeCoordAdjacents.add(pointTemporaire);

        // construction de la coordonnee a l'OUEST du tir
        pointTemporaire.ligne = tir.ligne;
        pointTemporaire.colonne  = tir.colonne - 1;
        if(!UtilitaireCollection.tableauContientCoord(listeCoupsJouees,pointTemporaire))
            listeCoordAdjacents.add(pointTemporaire);

        // construction de la coordonnee a l'EST du tir
        pointTemporaire.colonne  = tir.colonne + 1;
        if(!UtilitaireCollection.tableauContientCoord(listeCoupsJouees,pointTemporaire))
            listeCoordAdjacents.add(pointTemporaire);

    }



}
