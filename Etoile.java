import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Etoile extends Animaux {

    private double tempsEcoule; //Temps écoulé depuis le début de la création de l'étoile
    private double dy; //Composante sinusoïdale du mouvement en y
    private double yMoyen;  //Y central
    private double frequence;   //Fréquence du mouvement
    private double amplitude;   //Amplitude du mouvement

    public Etoile(double largeurJeu, double hauteurJeu, int niveau){
        super(largeurJeu, hauteurJeu, niveau);
        this.animalActuel = new Image("/star.png");

        tempsEcoule = 0;

        this.yMoyen = this.y;
        this.vx = 100 * Math.pow(niveau, 1/3.0) + 200; //Formule de la vitesse en x donnée dans l'énoncé
        if(x != 0){ this.vx *= -1;} //Si le poisson part de la droite, il a une vitesse vers la gauche
        this.vy = 0;
        this.ax = 0;
        this.ay = 0;

        this.amplitude = 50;
        this.frequence = 1;
    }

    @Override
    public void update(double dt) {
        tempsEcoule += dt;

        this.dy = this.amplitude * Math.sin(tempsEcoule * 2 * Math.PI / this.frequence);

        this.x += dt * this.vx;
        this.y = this.yMoyen + this.dy;
    }
}
