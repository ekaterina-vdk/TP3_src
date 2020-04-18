import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Poisson extends Animaux {
    private Image[] animalPossible = new Image[]{
            new Image("/00.png"),
            new Image("/01.png"),
            new Image("/02.png"),
            new Image("/03.png"),
            new Image("/04.png"),
            new Image("/05.png"),
            new Image("/06.png"),
            new Image("/07.png")
    };

    public Poisson(double largeurJeu, double hauteurJeu){
        super(largeurJeu, hauteurJeu);
        int choix = (int)(Math.random()*this.animalPossible.length - 1);
        this.animalActuel = animalPossible[choix];


        this.vx = 100 * Math.pow(this.niveau, 1/3.0) + 200; //Formule de la vitesse en x donnée dans l'énoncé
        if(x != 0){ this.vx *= -1;} //Si le poisson part de la droite, il a une vitesse vers la gauche
        this.vy = (Math.random()*(200 - 100) + 100) * -1; //Vitesse initiale en y se situe entre 100 et 200 vers le haut
        this.ay = 100;  //Accélération initiale en y est de 100 vers le bas
    }

    @Override
    public void update(double dt) {
        this.vy += dt * this.ay;
        this.x += dt * this.vx;
        this.y += dt * this.vy;
    }
}
