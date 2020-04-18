import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Etoile extends Animaux {

    public Etoile(double largeurJeu, double hauteurJeu){
        super(largeurJeu, hauteurJeu);
        this.animalActuel = new Image("/star.png");


        this.vx = 100 * Math.pow(this.niveau, 1/3.0) + 200; //Formule de la vitesse en x donnée dans l'énoncé
        if(x != 0){ this.vx *= -1;} //Si le poisson part de la droite, il a une vitesse vers la gauche
        this.vy = 50; //Amplitude d'oscillation
        this.ax = 0;
        this.ay = 0;
    }

    @Override
    public void update(double dt) {


        this.x += dt * this.vx;
        //this.y =
    }
}
