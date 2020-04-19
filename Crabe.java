import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Crabe extends Animaux {

    private double tempsEcoule; //Temps écoulé depuis le dernier cycle de mouvement
    private boolean inverse;    //Mouvement vers l'arrière (le crabe recule) si true

    public Crabe(double largeurJeu, double hauteurJeu, int niveau){
        super(largeurJeu, hauteurJeu, niveau);
        this.animalActuel = new Image("/crabe.png");

        tempsEcoule = 0;
        inverse = false;

        this.vx = 1.3 * (100 * Math.pow(niveau, 1/3.0) + 200); //Formule de la vitesse en x donnée dans l'énoncé
        if(x != 0){ this.vx *= -1;} //Si le poisson part de la droite, il a une vitesse vers la gauche
        this.vy = 0;
        this.ax = 0;
        this.ay = 0;
    }

    @Override
    public void update(double dt) {
        tempsEcoule += dt;

        if(tempsEcoule > 0.75){
            this.vx *= -1;
            tempsEcoule = 0;
            inverse = false;
        }
        else if(tempsEcoule > 0.5 && inverse == false){
            this.vx *= -1;
            inverse = true;
        }

        this.x += dt * this.vx;
    }
}
