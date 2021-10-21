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

        if (nbLigne > nbColonne){
            orientation = NS;
            longueur = nbLigne;
        } else {
            orientation = EW;
            longueur = nbColonne;
        }

        for (int i=0;i<longueur;i++){

            if(orientation.equals(NS)){
                Coord coordTemporaire = new Coord(debut.ligne + i,debut.colonne);
                listeCoordonnes.add(coordTemporaire);
            }else{
                Coord coordTemporaire = new Coord(debut.ligne,debut.colonne + i);
                listeCoordonnes.add(coordTemporaire);
            }
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

        for (Coord pointNavireAutre : navireAutre.listeCoordonnes){
            for (Coord pointThis : this.listeCoordonnes){
                if(pointNavireAutre.equals(pointThis)) return true;
            }
         }
        return false;
    }


    private boolean positionTouche(Coord tir){

        return listeCoordonnes.contains(tir);
    }

    public boolean estDansLaGrille(){

        for (Coord point : listeCoordonnes){
            if (point.colonne > Constantes.TAILLE || point.ligne > Constantes.TAILLE) return false;
        }
        return true;
    }


    public String toString(){

        String message = new String();

        message += "Nom : " + nom;
        message += "\nLongueur : " + longueur;
        message += "\nOrientation : " + orientation;
        message += "\nCoordonneDebut : " + listeCoordonnes.get(0);
        message += "\nCoordonneFin : " + listeCoordonnes.get(longueur - 1);
        message += "\nListe de coups touches : " + coupsTouches.toString();
        message += "\nCouleur : " + couleur+"\n\n";

        return message;
    }




}
