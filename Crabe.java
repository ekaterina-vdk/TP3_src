import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Crabe extends Animaux {

    private double intervalleTemps;
    private boolean inverse;

    public Crabe(double largeurJeu, double hauteurJeu){
        super(largeurJeu, hauteurJeu);
        this.animalActuel = new Image("/crabe.png");

        intervalleTemps = 0;
        inverse = false;

        this.vx = 1.3 * (100 * Math.pow(this.niveau, 1/3.0) + 200); //Formule de la vitesse en x donnée dans l'énoncé
        if(x != 0){ this.vx *= -1;} //Si le poisson part de la droite, il a une vitesse vers la gauche
        this.vy = 0;
        this.ax = 0;
        this.ay = 0;
    }

    @Override
    public void update(double dt) {
        intervalleTemps += dt;
        if(intervalleTemps > 0.75){
            this.vx *= -1;
            intervalleTemps = 0;
            inverse = false;
        }
        else if(intervalleTemps > 0.5 && inverse == false){
            this.vx *= -1;
            inverse = true;
        }


        this.x += dt * this.vx;
    }
}
