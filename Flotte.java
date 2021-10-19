import com.sun.xml.internal.bind.v2.TODO;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Flotte {

    public final int POSITION_INVALIDE = -1;
    public final int NAVIRE_DEJA_SUR_PLACE = -2;
    public final int AUCUNE_ERREUR = 0;
    public final Random rand = new Random();
    public final int NORD = 1;
    public final int SUD = 2;
    public final int EST = 3;
    public final int OUEST = 4;

    ArrayList<Navire> collectionDeNavires = new ArrayList<>();

    public Flotte(){
        ArrayList<Navire> collectionDeNavires = new ArrayList<>();
    }

    public Flotte(int aleatoire){
        genererPosNavireAleaInsererDsGrille();
    }

    public boolean dejaRecuCoup(Coord tir){

        for (Navire navireCourant : collectionDeNavires) {
            if (navireCourant.dejaRecuTir(tir)) return true;
        }
        return false;
    }

    public boolean jeuTermine(){

        for (Navire navireCourant : collectionDeNavires) {
            if (!navireCourant.estCoule()) return false;
        }
        return true;
    }

    public Navire[] getTabNavires(){
        return collectionDeNavires.toArray(new Navire[0]); // à confirmer
    }

    public boolean leTirTouche(Coord tir){
        for (Navire navireCourant : collectionDeNavires) {
            if (navireCourant.tirAtouche(tir)) return true;
        }
        return false;
    }

    private int ajouterNavire(Navire navire){

        for(Navire navireCourant : collectionDeNavires){
            if(navire.chevauche(navireCourant)) return NAVIRE_DEJA_SUR_PLACE;
        }
        if (!navire.estDansLaGrille()){
            return POSITION_INVALIDE;
        }
        collectionDeNavires.add(navire);
        return AUCUNE_ERREUR;
    }

    private Navire obtenirNavireAleatoire(String nom, int longueur, Color couleur){

        Coord coordDebut = new Coord();
        Coord coordFin = new Coord();
        int direction;
        boolean coordsSontValide  = false;
        Navire essaieNavire;

        while (!coordsSontValide){

            coordDebut = new Coord(rand.nextInt(10)+1,rand.nextInt(10) + 1);
            direction = rand.nextInt(4) + 1;
            int longueurAjuste = longueur - 1;

            switch (direction){
                case NORD : coordFin = new Coord(coordDebut.ligne + longueurAjuste,coordDebut.colonne);
                case SUD : coordFin = new Coord(coordDebut.ligne - longueurAjuste,coordDebut.colonne);
                case EST: coordFin = new Coord(coordDebut.ligne,coordDebut.colonne + longueurAjuste);
                case OUEST: coordFin = new Coord(coordDebut.ligne,coordDebut.colonne - longueurAjuste);
            }

            try{
                essaieNavire = new Navire(nom,coordDebut,coordFin,couleur);
                coordsSontValide = true;
            }catch (Exception e){
                coordsSontValide = false;
            }
        }
        essaieNavire = new Navire(nom,coordDebut,coordFin,couleur);
        return essaieNavire;
    }
    /*Une méthode void genererPosNavireAleaInsererDsGrille() qui ajoute un à un
    les navires dans la flotte. Il y a autant de boucles qu’il y a de navires. Une
    boucle se termine lorsque ajouterNavire retourne AUCUNE_ERREUR.*/
    private void genererPosNavireAleaInsererDsGrille(){

        int indicateurErreur;

        do {
            indicateurErreur = 1;
            indicateurErreur = ajouterNavire(obtenirNavireAleatoire(Constantes.DESTROYER,
                    Constantes.DESTROYER_LONGUEUR,Color.LIGHT_GRAY));
        }while(indicateurErreur != AUCUNE_ERREUR);

        do {
            indicateurErreur = 1;
            indicateurErreur = ajouterNavire(obtenirNavireAleatoire(Constantes.CUIRASSE,
                    Constantes.CUIRASSE_LONGUEUR,Color.LIGHT_GRAY));
        }while(indicateurErreur != AUCUNE_ERREUR);

        do {
            indicateurErreur = 1;
            indicateurErreur = ajouterNavire(obtenirNavireAleatoire(Constantes.SOUS_MARIN,
                    Constantes.SOUS_MARIN_LONGUEUR,Color.LIGHT_GRAY));
        }while(indicateurErreur != AUCUNE_ERREUR);

        do {
            indicateurErreur = 1;
            indicateurErreur = ajouterNavire(obtenirNavireAleatoire(Constantes.CROISEUR,
                    Constantes.CROISEUR_LONGUEUR,Color.LIGHT_GRAY));
        }while(indicateurErreur != AUCUNE_ERREUR);

        do {
            indicateurErreur = 1;
            indicateurErreur = ajouterNavire(obtenirNavireAleatoire(Constantes.PORTE_AVION,
                    Constantes.PORTE_AVION_LONGUEUR,Color.LIGHT_GRAY));
        }while(indicateurErreur != AUCUNE_ERREUR);

    }

    /*Une méthode static obtenirFlotteAleatoire() crée une flotte, génère la position des
    navires aléatoire (appel du SP précédent) et la retourne.*/
    public static Flotte obtenirFlotteAleatoire(){
        return new Flotte(0);
    }









}
