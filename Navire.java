import com.sun.corba.se.spi.transport.CorbaAcceptor;

import java.awt.*;
import java.util.ArrayList;

public class Navire {

    private final String NS = "NORD-SUD";
    private final String EW = "EST-OUEST";

    // les attributs necessaire au navire
    String nom;
    int longueur;
    Color couleur;
    ArrayList<Coord> coupsTouches = new ArrayList<Coord>();
    Coord coordDebut;
    Coord coordFin;
    String orientation;

    // vérifier s'il ne faut pas mettre les attributs de coordDebut et coordFin ici

    public Navire (String nom, Coord debut, Coord fin, Color couleur){

        // calcul des nombres de colonnes et de lignes du navire
        int nbColonne = fin.colonne - debut.colonne +1;
        int nbLigne = fin.ligne - debut.ligne +1;

        // gerer tous les exceptions possibles ici
        if (nbLigne > 1 && (debut.colonne != fin.colonne)){
            throw new IllegalArgumentException("Coordonnées NORD_SUD invalide");
        } else if (nbColonne > 1 && (debut.ligne != fin.ligne)){
            throw new IllegalArgumentException("Coordonnées EST_OUEST invalide");
        } else if ((debut.ligne > fin.ligne) || (debut.ligne > Constantes.TAILLE
                || fin.ligne > Constantes.TAILLE)){
            throw new IllegalArgumentException("Ligne invalide");
        } else if ((debut.colonne > fin.colonne) || (debut.colonne > Constantes.TAILLE
                || fin.colonne > Constantes.TAILLE)){
            throw new IllegalArgumentException("Colonne invalide");
        }

        this.nom = nom;
        this.coordDebut = debut;
        this.coordFin = fin;
        this.couleur = couleur;

        if (nbLigne > nbColonne){
            this.orientation = NS;
            this.longueur = nbLigne;
        } else {
            this.orientation = EW;
            this.longueur = nbColonne;
        }

    }

    public boolean estCoule(){

        // creation d'une nouvelle liste temporaire afin de storer les differentes cases de "touche"
        ArrayList<Coord> listeTemporaireCoupsTouche = new ArrayList<Coord>();

        for (Coord i : coupsTouches) {
            if (!listeTemporaireCoupsTouche.contains(i)) {
                listeTemporaireCoupsTouche.add(i);
            }
        }
        return listeTemporaireCoupsTouche.size() == this.longueur; // >= ou == ???
    }

    public boolean dejaRecuTir(Coord tir){

        return this.coupsTouches.contains(tir);
    }

    public boolean tirAtouche(Coord tir){

        boolean aToucheNavire = false;

        if (!this.estCoule()){
            if(!this.dejaRecuTir(tir)){
                //boucle de recherche
                    //si le tir touche au navire actuel (fonction privée)
                        this.coupsTouches.add(tir);
                        aToucheNavire = true;
            }
        }
        return aToucheNavire;
    }

    public boolean chevauche(Navire navire){

        /* peut être se servir de la fonction getTousLesCoordsNavire() ?
         il faut comparer tous les coordonnes une à une afin de savoir si les deux navires se
         croisent */


        return false;
    }

    /**
     * ce sous programme a pour but de trouver tous les coordonnes du navire.
     * @return un arraylist contenant tous les coordonnes du navire.
     *
     * fonction que j'ai decider de faire moi-mêmes puisque je sais que je pourrai m'en servir
     * dans les autres sous programmes. J.L.
     */
    private ArrayList<Coord> getTousLesCoordsNavire(){

        Coord coordTemporaire = new Coord();
        ArrayList<Coord> listeCoordNavire = new ArrayList<Coord>();

        // si le navire est verticale
        if (this.orientation.equals(NS)){


            for (int i = 0; i < this.longueur; i++){

                coordTemporaire.ligne = this.coordDebut.ligne + i;
                coordTemporaire.colonne = this.coordDebut.colonne;

                listeCoordNavire.add(coordTemporaire);
            }
        } else {
            for (int i = 0; i < this.longueur; i++){

                coordTemporaire.ligne = this.coordDebut.ligne;
                coordTemporaire.colonne = this.coordDebut.colonne + i;

                listeCoordNavire.add(coordTemporaire);
            }
        }
        return listeCoordNavire;
    }






}
