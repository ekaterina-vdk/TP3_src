public abstract class Animaux extends Elements {

    public Animaux(double largeurJeu, double hauteurJeu){
        super(largeurJeu, hauteurJeu);
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
