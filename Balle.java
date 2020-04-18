import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Balle extends Elements {

    public Balle(double largeurJeu, double hauteurJeu, double xSouris, double ySouris){
        super(largeurJeu, hauteurJeu);
        this.largeur = 50;
        this.couleur = Color.rgb(0, 0, 0);

        this.x = xSouris - this.largeur/2;
        this.y = ySouris - this.largeur/2;
        this.vx = 300;  //Taille de la balle diminue à cette vitesse

    }

    @Override
    public void draw(GraphicsContext context) {
        context.setFill(this.couleur);
        context.fillOval(this.x, this.y, this.largeur, this.largeur);
    }

    @Override
    public void update(double dt) {
        this.largeur -= dt * this.vx;

    }
}
