import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Etoile extends Animaux {

    public Etoile(double largeurJeu, double hauteurJeu){
        super(largeurJeu, hauteurJeu);
        this.animalActuel = new Image("/star.png");


        if(x != 0){ this.vx *= -1;} //Si le poisson part de la droite, il a une vitesse vers la gauche
    }

    @Override
    public void update(double dt) {

    }
}
