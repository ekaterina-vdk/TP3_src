import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class Animaux extends Elements {

    protected int niveau;
    protected Image animalActuel;

    public Animaux(double largeurJeu, double hauteurJeu){
        super(largeurJeu, hauteurJeu);
        this.largeur = 100;
        this.hauteur = 100;

        this.y = Math.random() *(4/5.0*this.hauteurJeu - 1/5.0*this.hauteurJeu)+ 1/5.0*this.hauteurJeu; //Hauteur initiale se situe entre le 1/5 et le 4/5 de le hauteur du jeu
        this.x = Math.round(Math.random())*this.largeurJeu; //Position initiale x est à gauche ou à droite du jeu
    }

    @Override
    public void draw(GraphicsContext context) {
        context.drawImage(this.animalActuel, this.x, this.y, this.largeur, this.hauteur);
    }

    //Déterminer si l'animal se trouve en instersection avec une balle (peu importe par quel côté)
    public boolean intersection(Balle balle) {
        return !( // L'animal et la balle sont côte à côte
                Math.ceil(this.x) + this.largeur < Math.floor(balle.x)
                        || Math.floor(balle.getX() + balle.getLargeur()) < Math.ceil(this.x)
                        // L'animal et la balle sont un en haut de l'autre
                        || Math.ceil(this.y) + this.hauteur < Math.floor(balle.getY())
                        || Math.floor(balle.getY() + balle.getHauteur()) < Math.ceil(this.y));
    }
}
