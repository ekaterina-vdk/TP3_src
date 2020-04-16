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
    }

    @Override
    public void draw(GraphicsContext context) {
        context.clearRect(0,0, this.largeurJeu, this.hauteurJeu);
        context.drawImage(this.animalActuel, this.x, this.y, this.largeur, this.hauteur);
    }

    @Override
    public void update(double dt) {

    }
}
