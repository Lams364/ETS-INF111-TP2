import java.util.ArrayList;

public class Flotte {

    ArrayList<Navire> collectionDeNavires = new ArrayList<>();

    public Flotte(){
        ArrayList<Navire> collectionDeNavires = new ArrayList<>();
    }

    public boolean dejaRecuCoup(Coord tir){

        for (Navire navireCourant : this.collectionDeNavires) {
            if (navireCourant.dejaRecuTir(tir)) return true;
        }
        return false;
    }

    public boolean jeuTermine(){

        for (Navire navireCourant : this.collectionDeNavires) {
            if (!navireCourant.estCoule()) return false;
        }
        return true;
    }

    public Navire[] getTabNavires(){
        return this.collectionDeNavires.toArray(new Navire[0]);//à  confirmer
    }

    public boolean leTirTouche(Coord tir){
        for (Navire navireCourant : this.collectionDeNavires) {
            if (navireCourant.tirAtouche(tir)) return true;
        }
        return false;
    }

    private int ajouterNavire(Navire navire){



    }



}
