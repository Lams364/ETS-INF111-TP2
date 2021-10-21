import com.sun.tools.internal.jxc.ap.Const;

import javax.swing.*;

public class Test {

    public static void main(String[] args) {

        Flotte flotteAleatoire = Flotte.obtenirFlotteAleatoire();

        for(Navire navireCourant : flotteAleatoire.collectionDeNavires){
            System.out.println(navireCourant.toString());
        }

        GrilleGui grille = new GrilleGui(Constantes.TAILLE+1,Constantes.TAILLE+1,
                Constantes.COULEUR_TEXTE
                ,Constantes.COULEUR_FOND, Constantes.OPTIONS, GrilleGui.DISPOSE);

        UtilitaireGrilleGui.pause(100);
        UtilitaireGrilleGui.montrerFlotte(flotteAleatoire,grille);

    }




}
