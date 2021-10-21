import java.awt.*;
import java.util.ArrayList;

/**
 * Classe qui construit un navire avec les informations fournies.
 * Permet de verifier ses coordonnees pour assurer qu'il est dans la grille, sans superposition.
 * 
 * @author Marc-Olivier Champagne
 * @author Jacob Lamarche
 * @author Daniel Ouambo
 *
 * @version : 21 octobre 2021
 */
public class Navire {

	//Constantes de direction possible d'un navire
    private final String NS = "NORD-SUD";
    private final String EO = "EST-OUEST";

    // les attributs necessaire au navire
    String nom;
    int longueur;
    Color couleur;
    ArrayList<Coord> coupsTouches = new ArrayList<Coord>();
    String orientation;
    ArrayList<Coord> listeCoordonnes = new ArrayList<Coord>();

    
    /**
     * Navire: constructeur par paramètre d'un navire. Remplis aussi les cases concernées.
     * @param nom: nom du type de navire
     * @param debut: Coord qui représente la premiere valeur du navire en partant du coin 
     * haut-gauche
     * @param fin: Coord qui représente l'autre extremite du navire
     * @param couleur: couleur utilise pour represente le navire
     */
    public Navire (String nom, Coord debut, Coord fin, Color couleur){

        // calcul des nombres de colonnes et de lignes du navire
        int nbColonne = fin.colonne - debut.colonne +1;
        int nbLigne = fin.ligne - debut.ligne +1;

        // Les messages d’exception possibles sont :
        
        // Le nombre de lignes est plus grand que 1 et les colonnes sont différentes
        // entre le debut et la fin.
        if (nbLigne > 1 && (debut.colonne != fin.colonne)){
            throw new IllegalArgumentException("Coordonnees NORD_SUD invalide");
             
        // Le nombre de colonnes est plus grand que 1 et les lignes sont différentes
        // entre le début et la fin.
        } else if (nbColonne > 1 && (debut.ligne != fin.ligne)){
            throw new IllegalArgumentException("Coordonnees EST_OUEST invalide");

        // La ligne n’est pas dans l’intervalle de la grille ou la ligne de début est plus
        // grande que la ligne de fin.
        } else if ((debut.ligne > fin.ligne) || (debut.ligne >= Constantes.TAILLE
                || fin.ligne >= Constantes.TAILLE)){
            throw new IllegalArgumentException("Ligne invalide");
        
        // La colonne n’est pas dans l’intervalle de la grille ou la colonne de début est
        // plus grande que la colonne de fin.
        } else if ((debut.colonne > fin.colonne) || (debut.colonne >= Constantes.TAILLE
                || fin.colonne >= Constantes.TAILLE)){
            throw new IllegalArgumentException("Colonne invalide");
        }

        // Copie des attributs directs
        this.nom = nom;
        this.couleur = couleur;

        // Copie de l'orientation et de la longueur selon la direction du navire
        if (nbLigne > nbColonne){
            orientation = NS;
            longueur = nbLigne;
        } else {
            orientation = EO;
            longueur = nbColonne;
        }

        // Pour toute la longueur du navire,
        for (int i=0;i<longueur;i++){
        	
        	// Dependamment de son orientation, on remplis son ArrayList avec les points couverts
            if(orientation.equals(NS)){
                Coord coordTemporaire = new Coord(debut.ligne + i,debut.colonne);
                listeCoordonnes.add(coordTemporaire);
            }else{
                Coord coordTemporaire = new Coord(debut.ligne,debut.colonne + i);
                listeCoordonnes.add(coordTemporaire);
            }
        }
    }

    
    /**
     * estCoule: retourne vrai si le navire est coule
     * @return: boolean navire coule ou non
     */
    public boolean estCoule(){

        // creation d'une nouvelle liste temporaire afin d'enregistrer les differentes 
    	// cases de "touche"
        ArrayList<Coord> listeTemporaireCoupsTouche = new ArrayList<Coord>();
        
        // Pour tous les coups touches du navire, on ajoute ceux uniques a la liste temporaire
        for (Coord i : coupsTouches) {
            if (!listeTemporaireCoupsTouche.contains(i)) {
                listeTemporaireCoupsTouche.add(i);
            }
        }
        
        // On evalue la quantite de coups dans la liste temporaire compare a la longueur du navire
        return listeTemporaireCoupsTouche.size() == longueur; // >= ou == ???
    }

    /**
     * dejaRecuTir: retourne vrai si la coordonnée recue a deja touche au navire.
     * @param tir: Coord du tir
     * @return: boolean de l'existence du tir sur une Coord du navire
     */
    public boolean dejaRecuTir(Coord tir){
        return coupsTouches.contains(tir);
    }

    /**
     * tirAtouche: retourne vrai si la coordonnee recue touche au navire actuel (this) et faux 
     * autrement (implique une boucle de recherche). Retient aussi la coordonnee si elle l’a touche
     * @param tir: Coord du tir
     * @return: boolean de la presence du tir sur une Coord du navire 
     */
    public boolean tirAtouche(Coord tir){

        boolean aToucheNavire = false;

        //Si le navire n’est pas coule
        if (!estCoule()){
        	
        	// Si le navire actuel n’a pas deja recu ce tir
            if(!dejaRecuTir(tir)){
            		
            	// Si le tir touche au navire actuel 
            	if(positionTouche(tir)) {
            		
            		// On retient la coordonnée dans la collection et on met le boolean a vrai
            		coupsTouches.add(tir);
                    aToucheNavire = true;
                }
            }
        }
        return aToucheNavire;
    }
    
    /**
     * chevauche: retourne vrai si une des positions du navire recu touche a une des positions 
     * du navire actuel (en ligne ou en colonne).
     * @param navireAutre: Navire auquel verifie s'il existe un chevauchement
     * @return boolean du chevauchement
     */
    public boolean chevauche(Navire navireAutre){
    	
    	// Pour tous les Coord des deux navires
        for (Coord pointNavireAutre : navireAutre.listeCoordonnes){
            for (Coord pointThis : this.listeCoordonnes){
            	
            	// On retourne vrai uniquement si des points sont identiques
                if(pointNavireAutre.equals(pointThis)) return true;
            }
         }
        return false;
    }

    /**
     * positionTouche: retourne vrai si la coordonnee du tir est entre le debut et la fin du navire
     * @param tir : Coord du tir
     * @return boolean 
     */
    private boolean positionTouche(Coord tir){
        return listeCoordonnes.contains(tir);
    }

    /**
     * estDansLaGrille: verifie si tous les points du navire sont dans la grille
     * @return boolean de la position des points dans la grille
     */
    public boolean estDansLaGrille(){

    	// Pour tous les points couverts par le navire
        for (Coord point : listeCoordonnes){
        	
        	// Retourne faux si les points depasses les bornes
            if (point.colonne > Constantes.TAILLE || point.ligne > Constantes.TAILLE) return false;
        }
        return true;
    }

    /**
     * toString: Transforme les informations en String
     * @return String contenant un message avec les informations du navire
     */
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
