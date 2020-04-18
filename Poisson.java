import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Poisson extends Animaux {
    private Image animalActuel;
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
        int choix = (int)(Math.random()*this.animalPossible.length);
        System.out.println(choix);
        this.animalActuel = animalPossible[choix];


        this.y = Math.random()*((4/5*this.hauteurJeu - 1/5*this.hauteurJeu) + 1) + 1/5*this.hauteurJeu; //Hauteur initiale se situe entre le 1/5 et le 4/5 de le hauteur du jeu
        this.vx = 100 * Math.pow(this.niveau, 1/3) + 200;
        this.x = Math.random()*(this.largeurJeu + 1); //Position initiale x est à gauche ou à droite du jeu
        if(x != 0){ this.vx *= -1;} //Si le poisson part de la droite, il a une vitesse vers la gauche
        this.vy = (Math.random()*((200 - 100) + 1) + 100) * -1; //Vitesse initiale en y se situe entre 100 et 200 vers le haut
        this.ay = 100;  //Accélération initiale en y est de 100 vers le bas
    }

    @Override
    public void draw(GraphicsContext context) {
        context.clearRect(0,0, this.largeurJeu, this.hauteurJeu);
        context.drawImage(this.animalActuel, this.x, this.y, this.largeur, this.hauteur);
    }

    @Override
    public void update(double dt) {
        this.vy += dt * this.ay;
        this.x += dt * this.vx;
        this.y += dt * this.vy;
    }
}
