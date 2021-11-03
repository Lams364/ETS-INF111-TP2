public class Test {

    public static void main(String[] args) {

        Flotte flotteAleatoire = new Flotte();
        flotteAleatoire = Flotte.obtenirFlotteAleatoire();
        for(Navire navireCourant : flotteAleatoire.collectionDeNavires){

            System.out.println(navireCourant.toString());
        }

        UtilitaireGrilleGui UGrille = new UtilitaireGrilleGui();


    }


}
