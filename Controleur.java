import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;

public class Controleur {

    Jeu jeu;

    public Controleur() {
        jeu = new Jeu();
    }

    //Mise à jour de l'affichage du jeu
    void drawJeu(GraphicsContext etageScore, GraphicsContext etageBulle, GraphicsContext etageBalle, GraphicsContext etageAnimaux){
        jeu.draw(etageScore, etageBulle, etageBalle, etageAnimaux);

    }

    //Mise à jour des coordonnées des éléments sur le jeu
    void updateCoordonnees(double deltaTime, GraphicsContext etageBulle, GraphicsContext etageBalle, GraphicsContext etageAnimaux){
        jeu.update(deltaTime, etageBulle, etageBalle, etageAnimaux);

    }

    void lancer(double x, double y){
        jeu.lancer(x, y);
    }

    //Le tableau des meilleurs scores doit être afficher
    void drawScore(){

    }

    //Un score doit être ajouter
    void updateScore(){

    }

    //Activer une fonctions du debugage
    void debug(KeyCode lettre){

    }

    static void setMort(boolean état){
        FishHunt.setMorte(état);
    }

}
