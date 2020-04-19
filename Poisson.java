import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

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

    public Poisson(double largeurJeu, double hauteurJeu, int niveau){
        super(largeurJeu, hauteurJeu, niveau);
        int choix = (int)(Math.random()*this.animalPossible.length - 1); //Image choisie aléatoirement parmis les choix

        int r = (int)(Math.random() * 255); //Composante rouge aléatoire de la couleur du poisson
        int g = (int)(Math.random() * 255); //Composante verte aléatoire de la couleur du poisson
        int b = (int)(Math.random() * 255); //Composante bleue aléatoire de la couleur du poisson
        this.couleur = Color.rgb(r, g, b).brighter();

        //Initialisation de la couleur
        this.animalActuel = ImageHelpers.colorize(animalPossible[choix], this.couleur);

        this.vx = 100 * Math.pow(niveau, 1/3.0) + 200; //Formule de la vitesse en x donnée dans l'énoncé
        if(x != 0){
            this.vx *= -1; //Si le poisson part de la droite, il a une vitesse vers la gauche
            this.animalActuel = ImageHelpers.flop(this.animalActuel);   //Inversé l'image pour qu'elle soit dans le sens de la direction
        }
        this.vy = (Math.random()*(200 - 100) + 100) * -1; //Vitesse initiale en y se situe entre 100 et 200 vers le haut
        this.ax = 0;
        this.ay = 100;  //Accélération initiale en y est de 100 vers le bas
    }

    @Override
    public void update(double dt) {
        this.vy += dt * this.ay;
        this.x += dt * this.vx;
        this.y += dt * this.vy;
    }
}
