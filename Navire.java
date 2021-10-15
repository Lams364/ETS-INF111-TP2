import java.awt.*;
import java.util.ArrayList;

public class Navire {

    // les attributs necessaire au navire
    String nom;
    int longueur;
    Color couleur;
    ArrayList<Coord> coupsTouches = new ArrayList<Coord>();

    public Navire (String nom, Coord debut, Coord fin, Color couleur){

        // calcul des nombres de colonnes et de lignes du navire
        int nbColonne = fin.colonne - debut.colonne +1;
        int nbLigne = fin.ligne - debut.ligne +1;

        // gerer tous les exceptions possibles ici

        if (nbLigne > 1 && (debut.colonne != fin.colonne)){
            throw new IllegalArgumentException("Coordonnées NORD_SUD invalide");
        } else if (nbColonne > 1 && (debut.ligne != fin.ligne)){
            throw new IllegalArgumentException("Coordonnées EST_OUEST invalide");
        } else if (){
            throw new IllegalArgumentException("Ligne invalide");
        } else if (){
            throw new IllegalArgumentException("Colonne invalide");
        }




    }











}
