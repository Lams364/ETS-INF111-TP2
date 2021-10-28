package partie1;

import java.awt.*;
import java.util.*;
import partie2.*;

/**
 * Classe qui construit une flotte de navires
 * Permet de generer des navires aleatoires et de les ajouter dans la liste de navires
 * 
 * @author Marc-Olivier Champagne
 * @author Jacob Lamarche
 * @author Daniel Ouambo
 *
 * @version : 21 octobre 2021
 */
public class Flotte {

	// Initialisation du Random
	public final Random rand = new Random();
	
	// partie1.Constantes utilises
    public final int POSITION_INVALIDE = -1;
    public final int NAVIRE_DEJA_SUR_PLACE = -2;
    public final int AUCUNE_ERREUR = 0;
    public final int NORD = 1;
    public final int SUD = 2;
    public final int EST = 3;
    public final int OUEST = 4;

    // Liste qui va contenir les navires de la flotte
    public ArrayList<Navire> collectionDeNavires = new ArrayList<>();

    
    /**
     * Constructeur qui va initaliser une flotte de 5 navires
     */
    public Flotte(){
        ArrayList<Navire> collectionDeNavires = new ArrayList<>();

        // établir la taille minimal du arraylist a 5 afin qu'il n'aille pas a s'agrandir
        collectionDeNavires.ensureCapacity(5);
    }

    /**
     * Verifie si un navire a deja recu le tir
     * @param tir : partie1.Coord du tir
     * @return boolean du coup deja recu
     */
    public boolean dejaRecuCoup(Coord tir){
    	
    	// Pour tous les navires de la flotte
        for (Navire navireCourant : collectionDeNavires) {
        	
        	// On appel la verification du navire selon le tir
            if (navireCourant.dejaRecuTir(tir)) return true;
        }
        return false;
    }

    /**
     * Verifie si tous les navires de la flotte sont coules
     */
    public boolean jeuTermine(){

    	// Pour tous les navires de la collection
        for (Navire navireCourant : collectionDeNavires) {
        	
        	// Si le navire n'est pas coule, la fonction retourne false
            if (!navireCourant.estCoule()) return false;
        }
        return true;
    }

    /**
     * Retourne un tableau de navire a partir de la flotte
     * @return partie1.Navire[]
     */
    public Navire[] getTabNavires(){
        return collectionDeNavires.toArray(new Navire[0]);
    }

    /**
     * Retourne si le tir a touche un des navires de la flotte
     * @param tir : partie1.Coord du tir
     * @return
     */
    public boolean leTirTouche(Coord tir){
    	
    	// Pour tous les navires de la flotte
        for (Navire navireCourant : collectionDeNavires) {
        	
        	// Si le navire est touche, retourne true
            if (navireCourant.tirAtouche(tir)) return true;
        }
        return false;
    }

    /**
     * Ajoute un navire seulement si les coordonnees du navire sont valides
     * @param navire : navire a ajoute
     * @return int de l'erreur
     */
    public int ajouterNavire(Navire navire){

    	// Pour tous les navires de la flotte
        for(Navire navireCourant : collectionDeNavires){
        	
        	// S'il y a du chevauchement, retourne le code d'erreur
            if(navire.chevauche(navireCourant)) return NAVIRE_DEJA_SUR_PLACE;
        }
        
        // Si le navire n'est pas dans la grille, retourne le code d'erreur
        if (!navire.estDansLaGrille()) return POSITION_INVALIDE;

        // Ajout du navire � la flotte et retour d'aucune erreur
        collectionDeNavires.add(navire);
        return AUCUNE_ERREUR;
    }
    /**
     * Retourne un navire dont les coordonnees ont ete generees aleatoirement, validees et ordonnees
     * @param nom : String contenant le nom du navire
     * @param longueur : int longueur desire du navire
     * @param couleur : Color couleur desire du navire
     * @return Un navire cree aleatoirement
     */
    private Navire obtenirNavireAleatoire(String nom, int longueur, Color couleur){

        boolean coordsSontValide  = false;
        
    	// Variables initiales pour transfert au navire et instanciation d'un navire
        Coord coordDebut;
        Coord coordFin;
        int direction;
        Navire essaieNavire;
        
        // On entre dans la boucle une premiere fois
        do{
        	// On cree une partie1.Coord initiale aleatoire qui ira dans une direction aleatoire aussi
        	// La longueur est ajustee pour ne pas inclure la partie1.Coord de debut
            coordDebut = new Coord(rand.nextInt(10),rand.nextInt(10));
            direction = rand.nextInt(4) + 1;
            int longueurAjuste = longueur - 1;
            
            // Dependemment de la direction, on attribue une ligne ou colonne adittionee a la
            // longueur ajustee et la ligne ou coulonne qui ne change pas
            switch (direction){
                case NORD : coordFin =
                		new Coord(coordDebut.ligne + longueurAjuste,coordDebut.colonne);
                    break;
                    
                case SUD : coordFin = 
                		new Coord(coordDebut.ligne - longueurAjuste,coordDebut.colonne);
                    break;
                    
                case EST: coordFin = 
                		new Coord(coordDebut.ligne,coordDebut.colonne + longueurAjuste);
                    break;
                    
                case OUEST: coordFin =
                		new Coord(coordDebut.ligne,coordDebut.colonne - longueurAjuste);
                    break;
                    
                // Valeur par d�faut
                default : coordFin = new Coord();
            }
            
            // On cree un navire avec les informations creees
            try{
                essaieNavire = new Navire(nom,coordDebut,coordFin,couleur);
                coordsSontValide = true;
                
            // Si la classe partie1.Navire ne fonctionne pas, on attribue false a la variable
            }catch (IllegalArgumentException e){
                coordsSontValide = false;
            }
            
        // Si la variable est fausse, donc si la classe partie1.Navire a rencontree un probleme, alors
        // on repete la boucle
        }while(!coordsSontValide);
        
        // On cree un navire avec les informations valides et on le retourne
        essaieNavire = new Navire(nom,coordDebut,coordFin,couleur);
        return essaieNavire;
    }

    /**
     *  Ajoute un � un les navires dans la flotte. Il y a autant de boucles qu�il y a de navires. 
	 *	Une boucle se termine lorsque ajouterNavire retourne AUCUNE_ERREUR..
     */
    private void genererPosNavireAleaInsererDsGrille(){
    	
    	// Creation d'une variable pour entreposer l'indicateur
        int indicateurErreur;
        
        /*
         * Les cinq blocs suivants servent a produire un navire valide
         * Ils sont executes une premiere fois pour avoir un navire initiale
         * Si l'indicateur revient avec une erreur, la creation est repetee
         * Le navire cree comporte les informations relatives a chacun des navires et une couleur
         * que nous avons selectionnee aleatoirement
         */
        
        // Boucle du destroyer
        do {
            indicateurErreur = ajouterNavire(obtenirNavireAleatoire(Constantes.DESTROYER,
                    Constantes.DESTROYER_LONGUEUR,Color.LIGHT_GRAY));
        }while(indicateurErreur != AUCUNE_ERREUR);

        // Boucle du cuirasse
        do {
            indicateurErreur = ajouterNavire(obtenirNavireAleatoire(Constantes.CUIRASSE,
                    Constantes.CUIRASSE_LONGUEUR,Color.DARK_GRAY));
        }while(indicateurErreur != AUCUNE_ERREUR);

        // Boucle du sous-marin
        do {
            indicateurErreur = ajouterNavire(obtenirNavireAleatoire(Constantes.SOUS_MARIN,
                    Constantes.SOUS_MARIN_LONGUEUR,Color.ORANGE));
        }while(indicateurErreur != AUCUNE_ERREUR);

        // Boucle du croiseur
        do {
            indicateurErreur = ajouterNavire(obtenirNavireAleatoire(Constantes.CROISEUR,
                    Constantes.CROISEUR_LONGUEUR,Color.PINK));
        }while(indicateurErreur != AUCUNE_ERREUR);

        // Boucle du porte avion
        do {
            indicateurErreur = ajouterNavire(obtenirNavireAleatoire(Constantes.PORTE_AVION,
                    Constantes.PORTE_AVION_LONGUEUR,Color.MAGENTA));
        }while(indicateurErreur != AUCUNE_ERREUR);

    }

    /**
     * Cr�e une flotte, g�n�re la position des navires al�atoire (appel du SP pr�c�dent) et
     * la retourne.
     * @return partie1.Flotte cree
     */
    public static Flotte obtenirFlotteAleatoire(){

    	// Creation de la flotte, appel du sous programme pour donner les positions et retour
        Flotte flotte = new Flotte();
        flotte.genererPosNavireAleaInsererDsGrille();
        return flotte;
    }









}
