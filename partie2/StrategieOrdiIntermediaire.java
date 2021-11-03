package partie2;

import partie1.*;
import java.util.*;

/**
 * Cette classe simule la strategie d'un ordi intermediaire. La strategie est de retenir les 4
 * cases adjacentes (nord, sud, est, ouest) du tir qui a touche un navire
 *
 * @author Jacob Lamarche
 * @author Marc-Olivier Champagne
 * @author Daniel Ouambo
 *
 * @version Automne 2021
 */
public class StrategieOrdiIntermediaire {

    // collection dans laquelle on va enregistrer tous les coups qui ont ete joues
    protected ArrayList<Coord> listeCoupsJoues;

    // collection dans laquelle on enregistre tous les cases adjacentes au tirs qui ont touches
    protected LinkedList<Coord> listeCoordAdjacents;

    /**
     * Constructeur par defaut qui initialise les deux collections
     */
    public StrategieOrdiIntermediaire(){

        listeCoupsJoues = new ArrayList<>();
        listeCoordAdjacents = new LinkedList<>();

    }


    /**
     * fonction qui trouve un point dans la grille selon la strategie des cases adjacentes
     * @return
     */
    public Coord getTir(){

        /*
                                                *STRATEGIE*
        Le but de cette strategie est d'obtenir les cases adjacentes (nord, sud, est, ouest) au tir
        qui ont touches. A chaque fois, on les enregistres dans une collection. Donc, en premier
        lieu, la fonction verifie s'il y a des coups possibles dans la collection de cases
        adjacentes afin de les tirer en premier. S'il n'y a pas de cases dans la collection, on
        utilise la même strategie que StrategieOrdiDebutant.
         */

        Coord pointTir;

        // tant que la liste des cases adjacentes n'est pas vide
        while (!listeCoordAdjacents.isEmpty()){

            // le point a obtenir est le premier point de la liste
            pointTir = listeCoordAdjacents.getFirst();

            // si le point n'a pas deja ete tirer
            if (!UtilitaireCollection.tableauContientCoord(listeCoupsJoues,pointTir)) {

                // on l'ajoute a la collection de coups deja joues et on le retourne
            	listeCoupsJoues.add(new Coord(pointTir.ligne, pointTir.colonne));
            	return pointTir;
            }
            // on enleve le point de la liste afin de ne pas le tester a nouveau au prochain tour
            listeCoordAdjacents.removeFirst();
        }
        /*
        * si on est sorti de la boucle, c'est qu'il n'y a plus aucun point dans la liste
        * on retourne donc un tir aleatoire qui n'a pas deja ete tirer apres l'avoir ajoute a nos
        * tirs precedents
        */
        pointTir = UtilitaireCollection.obtenirCoupPasDejaJouer(listeCoupsJoues);
        listeCoupsJoues.add(new Coord(pointTir.ligne, pointTir.colonne));
        return pointTir;
    }

    /**
     * fonction qui, selon la strategie, fait des actions consequemment si le tir a touche a un
     * navire.
     * @param tir
     */
    public void aviserTir(Coord tir){

        /*                                *STRATEGIE*
            La strategie ici est d'ajouter les cases adjacentes au tir entre en parametre.
            Les cases adjacentes en NORD,SUD,EST et OUEST sont donc ajoutees.
         */

        Coord pointTemporaire = new Coord();

        // construction de la coordonnee au NORD du tir
        pointTemporaire.ligne = tir.ligne + 1;
        pointTemporaire.colonne  = tir.colonne;

            // appel de fonction afin de verifier certaines conditions
            ajouterTirDansCasesAdjacentes(pointTemporaire);

        // construction de la coordonnee au SUD du tir
        pointTemporaire.ligne = tir.ligne - 1;

            // appel de fonction afin de verifier certaines conditions
            ajouterTirDansCasesAdjacentes(pointTemporaire);

        // construction de la coordonnee a l'OUEST du tir
        pointTemporaire.ligne = tir.ligne;
        pointTemporaire.colonne  = tir.colonne - 1;

            // appel de fonction afin de verifier certaines conditions
            ajouterTirDansCasesAdjacentes(pointTemporaire);

        // construction de la coordonnee a l'EST du tir
        pointTemporaire.colonne  = tir.colonne + 1;

            // appel de fonction afin de verifier certaines conditions
            ajouterTirDansCasesAdjacentes(pointTemporaire);


    }

    /**
     * cette fonction permet de verifier que le tir n'a pas deja ete joue et qu'il est dans la
     * grille, ensuite, il ajoute le tir a la collection
     * @param tir
     */
    private void ajouterTirDansCasesAdjacentes(Coord tir){
        if(!UtilitaireCollection.tableauContientCoord(listeCoupsJoues,tir)
                && UtilitaireFonctions.coordEstDansLaGrille(tir))
            listeCoordAdjacents.add(new Coord(tir.ligne, tir.colonne));
    }





}
