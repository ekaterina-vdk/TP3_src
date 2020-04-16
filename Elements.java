import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class Elements {
    protected double x, y, vx, vy, ax, ay, largeur, hauteur, largeurJeu, hauteurJeu;
    protected Color couleur;

    public Elements(double largeurJeu, double hauteurJeu){
        this.largeurJeu = largeurJeu;
        this.hauteurJeu = hauteurJeu;
    }

    //Dessin de l'élément sur le context graphique
    public abstract void draw(GraphicsContext context);

    //Mise à jour des coordonnées de l'élément
    public abstract void update(double dt);

    //Getters et setters
    public double getX() { return x; }
    public void setX(double x) { this.x = x; }

    public double getY() { return y; }
    public void setY(double y) { this.y = y; }

    public double getVx() { return vx; }
    public void setVx(double vx) { this.vx = vx; }

    public double getVy() { return vy; }
    public void setVy(double vy) { this.vy = vy; }

    public double getAx() { return ax; }
    public void setAx(double ax) { this.ax = ax; }

    public double getAy() { return ay; }
    public void setAy(double ay) { this.ay = ay; }

    public double getLargeur() { return largeur; }
    public void setLargeur(double largeur) { this.largeur = largeur; }

    public double getHauteur() { return hauteur; }
    public void setHauteur(double hauteur) { this.hauteur = hauteur; }

    public Color getCouleur() { return couleur; }
    public void setCouleur(Color couleur) { this.couleur = couleur; }
}

