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
    ArrayList<Coord> listeCoordonnes = new ArrayList<Coord>();

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
        this.couleur = couleur;
        coordDebut = debut;
        coordFin = fin;

        if (nbLigne > nbColonne){
            orientation = NS;
            longueur = nbLigne;
        } else {
            orientation = EW;
            longueur = nbColonne;
        }
        listeCoordonnes  = getCoordonnes();

    }

    public boolean estCoule(){

        // creation d'une nouvelle liste temporaire afin de storer les differentes cases de "touche"
        ArrayList<Coord> listeTemporaireCoupsTouche = new ArrayList<Coord>();

        for (Coord i : coupsTouches) {
            if (!listeTemporaireCoupsTouche.contains(i)) {
                listeTemporaireCoupsTouche.add(i);
            }
        }
        return listeTemporaireCoupsTouche.size() == longueur; // >= ou == ???
    }

    public boolean dejaRecuTir(Coord tir){

        return coupsTouches.contains(tir);
    }

    public boolean tirAtouche(Coord tir){

        boolean aToucheNavire = false;

        if (!estCoule()){
            if(!dejaRecuTir(tir)){
                    if(positionTouche(tir)) {
                        coupsTouches.add(tir);
                        aToucheNavire = true;
                    }
            }
        }
        return aToucheNavire;
    }
    

    public boolean chevauche(Navire navireAutre){

        //pour tous les points dans la liste coordsNavireAutre
        for (Coord point : navireAutre.listeCoordonnes){
            //si le point est trouvé dans la liste des points, c'est qu'il se chevauche.
            //on retourne donc true.
            if (this.listeCoordonnes.contains(point)) return true;
        }
        return false;
    }

    /**
     * ce sous programme a pour but de trouver tous les coordonnes du navire.
     * @return un arraylist contenant tous les coordonnes du navire.
     *
     * fonction que j'ai decider de faire moi-mêmes puisque je sais que je pourrai m'en servir
     * dans les autres sous programmes. J.L.
     */
    private ArrayList<Coord> getCoordonnes(){

        Coord coordTemporaire = new Coord();
        ArrayList<Coord> listeCoordNavire = new ArrayList<Coord>();

        // si le navire est verticale
        if (orientation.equals(NS)){


            for (int i = 0; i < longueur; i++){

                coordTemporaire.ligne = coordDebut.ligne + i;
                coordTemporaire.colonne = coordDebut.colonne;

                listeCoordNavire.add(coordTemporaire);
            }
        } else {
            for (int i = 0; i < longueur; i++){

                coordTemporaire.ligne = coordDebut.ligne;
                coordTemporaire.colonne = coordDebut.colonne + i;

                listeCoordNavire.add(coordTemporaire);
            }
        }
        return listeCoordNavire;
    }

    private boolean positionTouche(Coord tir){

        return listeCoordonnes.contains(tir);
    }

    public boolean estDansLaGrille(){
        return (coordDebut.colonne <= Constantes.TAILLE && coordDebut.ligne <= Constantes.TAILLE
                && coordFin.colonne <= Constantes.TAILLE && coordFin.ligne <= Constantes.TAILLE);
    }

    public String toString(){

        String message = new String();

        message += "Nom : " + nom;
        message += "\nLongueur : " + longueur;
        message += "\nOrientation : " + orientation;
        message += "\nCoordonneDebut : " + coordDebut;
        message += "\nCoordonneFin : " + coordFin;
        message += "\nListe de coups touches : " + coupsTouches.toString()+"\n\n";




        return message;
    }




}
