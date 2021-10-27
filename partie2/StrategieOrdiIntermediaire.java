package partie2;

import partie1.*;
import java.util.*;

public class StrategieOrdiIntermediaire {

    private ArrayList<Coord> listCoups;
    private LinkedList<Coord> listCoordAdjacents;

    /**
     * Constructeur par défaut
     */
    public StrategieOrdiIntermediaire(){

        listCoups = new ArrayList<>();
        listCoordAdjacents = new LinkedList<>();

    }


    /**
     * fonction qui trouve un point dans la grille selon la statégie des cases adjacentes
     * @return
     */
    public Coord getTir(){

        Coord pointTir = new Coord();

        // tant que la liste des cases adjacentes n'est pas vide
        while (listCoordAdjacents.size() > 0){

            // le point a tester est le premier point de la liste
            pointTir = listCoordAdjacents.getFirst();

            // si le point n'a pas deja ete tirer
            if (!UtilitaireCollection.tableauContientCoord(listCoups,pointTir)) {
                return pointTir;
            }
            // on enleve le point de la liste afin de ne pas le tester a nouveau au prochain tour
            listCoordAdjacents.removeFirst();
        }
        // si on est sorti de la boucle, c'est qu'il n'y a plus aucun point dans la liste
        // on retourne donc un tir aleatoire qui n'a pas deja ete tirer
        pointTir = UtilitaireCollection.obtenirCoupPasDejaJouer(listCoups);
        return pointTir;
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
        if(!UtilitaireCollection.tableauContientCoord(listCoups,pointTemporaire))
            listCoordAdjacents.add(pointTemporaire);

        // construction de la coordonnee au SUD du tir
        pointTemporaire.ligne = tir.ligne - 1;
        if(!UtilitaireCollection.tableauContientCoord(listCoups,pointTemporaire))
            listCoordAdjacents.add(pointTemporaire);

        // construction de la coordonnee a l'OUEST du tir
        pointTemporaire.ligne = tir.ligne;
        pointTemporaire.colonne  = tir.colonne - 1;
        if(!UtilitaireCollection.tableauContientCoord(listCoups,pointTemporaire))
            listCoordAdjacents.add(pointTemporaire);

        // construction de la coordonnee a l'EST du tir
        pointTemporaire.colonne  = tir.colonne + 1;
        if(!UtilitaireCollection.tableauContientCoord(listCoups,pointTemporaire))
            listCoordAdjacents.add(pointTemporaire);

    }





}
