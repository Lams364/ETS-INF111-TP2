import java.awt.Color;
import java.util.ArrayList;

public class Navire {

    private final String NS = "NORD-SUD";
    private final String EW = "EST-OUEST";
    
	//Creation des attributs
	String nom;
	int longueur;
	Color couleur;
	ArrayList<Coord> coupsTouches = new ArrayList<Coord>();
	ArrayList<Coord> coordsCouvertes = new ArrayList<Coord>();
	String orientation; //Voir a transferer dans le constructeur si pas reutilise
	
	//Constructeur
	public Navire(String nom, Coord debut, Coord fin, Color couleur) {
		
		//Calcul des dimensions dans les deux directions
		int longueurLigne = fin.ligne - debut.ligne + 1;
		int longueurColonne = fin.colonne - debut.colonne + 1;
		
		//Lever les exceptions
		if (longueurLigne > 1 && (debut.colonne != fin.colonne)){
	            throw new IllegalArgumentException("Coordonnées NORD_SUD invalide");
			
			} else if (longueurColonne > 1 && (debut.ligne != fin.ligne)){
	            throw new IllegalArgumentException("Coordonnées EST_OUEST invalide");

	        } else if ((debut.ligne > fin.ligne) || (debut.ligne > Constantes.TAILLE
	                || fin.ligne > Constantes.TAILLE)){
	            throw new IllegalArgumentException("Ligne invalide");
	            
	        } else if ((debut.colonne > fin.colonne) || (debut.colonne > Constantes.TAILLE
	                || fin.colonne > Constantes.TAILLE)){
	            throw new IllegalArgumentException("Colonne invalide");
		
			}
		 
		if (longueurLigne > longueurColonne){
			orientation = NS;
			longueur = longueurLigne;
		} else {
			orientation = EW;
			longueur = longueurColonne;         
		}
		
		this.nom = nom;
		this.couleur = couleur;
		
		for(int i = 0; i < longueur; i++) {
			if(orientation == NS) {
				Coord tempCoord = new Coord(debut.ligne + i, debut.colonne);
				coordsCouvertes.add(tempCoord);
			} else {
				Coord tempCoord = new Coord(debut.ligne, debut.colonne + i);
				coordsCouvertes.add(tempCoord);
			}
		}
	}
	
	public boolean estCoule() {
		
		int coupsUniques = 0;
		
		for(int i = 0; i < coupsTouches.size() - 1; i++) {
			if(coupsTouches.get(i) != coupsTouches.get(i + 1)) {
				coupsUniques ++;
			}
		}
		
		return (coupsUniques == longueur);
	}
	
	public boolean dejaRecuTir(Coord Tir) {
		return (coupsTouches.contains(Tir));
	}
	
	public boolean tirATouche(Coord Tir) {
		
		boolean touche = false;
		
		if(this.estCoule() == false) {
			if(this.dejaRecuTir(Tir)) {
				if(this.positionTouche(Tir) == true) {
					coupsTouches.add(Tir);
					return true;
				}
			}
		}
		
		return touche;
	}
	
	public boolean chevauche(Navire navire) {
		
		boolean chevauchement = false;
		
		for(int i = 0; i < this.coordsCouvertes.size(); i++) {
			if(navire.coordsCouvertes.contains(this.coordsCouvertes.get(i))) {
				return true;
			}
		}
		
		return chevauchement;
	}
	
	private boolean positionTouche(Coord Tir) {
		return (coupsTouches.contains(Tir));
	}
}
