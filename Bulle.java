import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Bulle extends Elements {

    //Constructeur
    public Bulle(double largeurJeu, double hauteurJeu){
        super(largeurJeu, hauteurJeu);
        this.largeur = (int)(Math.random()*((40 - 10) + 1)) + 10; //Valeur aléaroire en 10 et 40px
        this.vy = (int)(Math.random()*((450 - 350) + 1)) + 350; //Valeur aléaroire en 350 et 450px
        this.y = this.hauteurJeu;
        this.couleur = Color.rgb(0, 0, 255, 0.4);
    }

    @Override
    public void draw(GraphicsContext context) {
        context.setFill(this.couleur);
        context.fillOval(x, y, this.largeur * 2, this. largeur * 2);
    }

    @Override
    public void update(double dt){
        this.y = this.y - dt*this.vy;
    }
}
