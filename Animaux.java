import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class Animaux extends Elements {

    protected int niveau;
    protected Image animalActuel;

    public Animaux(double largeurJeu, double hauteurJeu){
        super(largeurJeu, hauteurJeu);
        this.largeur = 70;
        this.hauteur = 70;

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
                Math.ceil(x) + this.largeur < Math.floor(balle.x)
                        || Math.floor(balle.x + balle.getLargeur()) < Math.ceil(this.x)
                        // L'animal et la balle sont un en haut de l'autre
                        || Math.ceil(y) + this.hauteur < Math.floor(balle.y)
                        || Math.floor(balle.y + balle.getHauteur()) < Math.ceil(this.y));
    }

    //Pas certaine d'en avoir de besoin
    public boolean testCollision(Balle balle) {

        return true;
    }

}
