import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;

public class Controleur {

    Jeu jeu;

    public Controleur() {
        jeu = new Jeu();
    }

    //Mise à jour de l'affichage du jeu
    void drawJeu(GraphicsContext etageScore, GraphicsContext etageBulle, GraphicsContext etageAnimaux){
        jeu.draw(etageScore, etageBulle, etageAnimaux);
    }

    //Mise à jour des coordonnées des éléments sur le jeu
    void updateCoordonnees(double deltaTime){ jeu.update(deltaTime); }

    //Lancer une balle suite au clic de souris
    void lancer(double x, double y){
        jeu.lancer(x, y);
    }

    //Activer une fonctions du debugage
    void debug(KeyCode lettre){ jeu.debug(lettre); }

    static void setMort(boolean etat){
        FishHunt.setMorte(etat);
    }
    static void setDernierScore(int score){ FishHunt.setDernierScore(score);}

    //Appel au serveur pour multijoueur
    void Start_Server(int port) {
        Runnable server = new Gestion_serveur(port);
        new Thread(server).start();
    }
}
