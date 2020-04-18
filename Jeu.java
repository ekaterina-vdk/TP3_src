import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.util.LinkedList;

public class Jeu {

    private int largeur = 640;  //largeur du jeu
    private int hauteur = 480;  //hauteur du jeu
    private Bulle[][] groupeBulles = new Bulle[3][5];   //3 groupes de 5 bulles
    private LinkedList<Animaux> animaux; //Tout les animaux qui sont sur le jeu
    private int score;  //Score total équivalent au nombre d'animaux atteints
    private double intervalleBulle = 0; //Intervalle de temps auquel les bulles se renouvellent
    private double intervallePoisson = 0;  //Intervalle de temps auquel les poissons se créent
    private double intervalleSpeciaux = 0; //Intervalle de temps auquel les crabes et les étoiles se créent
    private int niveau;

    public Jeu(){
        //Créer les bulles
        renouvelerBulle();

        //Mettre un premier poisson
        //Poisson premierPoisson = new Poisson(this.largeur, this.hauteur);
        //animaux.add(premierPoisson);
        animaux = new LinkedList<>();

        //Initialisation du score
        this.score = 0;

        //Initialisation du niveau
        this.niveau = 0;

    }

    public void draw(GraphicsContext etageScore, GraphicsContext etageBulle, GraphicsContext etageBalle, GraphicsContext etageAnimaux){
        //Afficher le score
        etageScore.clearRect(0, 0, this.largeur, this.hauteur);
        etageScore.setFill(Color.WHITE);
        etageScore.setFont(Font.font("Purisa", 20));
        etageScore.setTextAlign(TextAlignment.CENTER);
        etageScore.fillText(this.score + "", this.largeur/2, 30);

        //Afficher les bulles
        for(int i = 0; i < groupeBulles.length; i++) {
            for (int j = 0; j < groupeBulles[i].length; j++) {
                groupeBulles[i][j].draw(etageBulle);
            }
        }

        //Afficher la balle
        //balle.draw(etageBalle);

        //Afficher les animaux
        etageAnimaux.clearRect(0, 0, this.largeur, this.hauteur);
        if(animaux.size() > 0) {
            for (Animaux ani : animaux) {
                ani.draw(etageAnimaux);
                //System.out.println("Dessin fait");
            }
        }

    }

    public void update(double dt, GraphicsContext etageBulle, GraphicsContext etageBalle, GraphicsContext etageAnimaux){
        //Mise à jour des coordonnées des bulles
        etageBulle.clearRect(0,0, this.largeur, this.hauteur);
        double dernierY = 480;
        for(int i = 0; i < groupeBulles.length; i++) {
            for (int j = 0; j < groupeBulles[i].length; j++) {
                groupeBulles[i][j].update(dt);
                if(groupeBulles[i][j].getY() < dernierY){
                    dernierY = groupeBulles[i][j].getY();
                }
            }
        }
        intervalleBulle += dt;

        //Après 3 secondes, on change les 3 groupes de bulles
        if(intervalleBulle >= 3){
            renouvelerBulle();
            intervalleBulle = 0;
        }


        if(animaux.size() > 0) {
            //Mise à jour des coordonnées des animaux
            for (Animaux ani : animaux) {
                //System.out.println("Mise à jour " + ani.getX() + "," + ani.getY());
                ani.update(dt);
            }

            //Enlever un animal s'il est en dehors de l'écran
            for(Animaux ani : animaux){
                if(ani.getX() > this.largeur || ani.getX() < 0 || ani.getY() > this.hauteur || ani.getY() < 0){
                    //System.out.println("Supression " + ani.getX() + "," + ani.getY());
                    animaux.remove(ani);
                }
            }
        }
        intervallePoisson += dt;
        intervalleSpeciaux += dt;

        //Ajout d'un poisson au 3 secondes et d'une étoile ou d'un crabe aux 5 secondes
        if(intervallePoisson >= 3){
            Poisson nouveauPoisson = new Poisson(this. largeur, this.hauteur);
            //System.out.println("Création Poisson " + nouveauPoisson.getX() + "," + nouveauPoisson.getY());
            animaux.addFirst(nouveauPoisson);
            intervallePoisson = 0;
        }
        if(intervalleSpeciaux >= 5){
            double probabilité = Math.random();
            Animaux nouveauSpecial;
            if(probabilité < 0.5){
                nouveauSpecial = new Etoile(this.largeur, this.hauteur);
            }
            else{
                nouveauSpecial = new Crabe(this.largeur, this.hauteur);
            }
            //System.out.println("Création Spécial " + nouveauSpecial.getX() + "," + nouveauSpecial.getY());
            animaux.addFirst(nouveauSpecial);
            intervalleSpeciaux = 0;
        }



    }

    //Changer les groupes de bulles pour des nouvelles
    public void renouvelerBulle(){
        for(int i = 0; i < groupeBulles.length; i++) {
            int basex = (int) (Math.random() * (largeur + 1)); //le groupe de bulle va se situer aléatoirement sur la largeur du jeu
            for (int j = 0; j < groupeBulles[i].length; j++) {    //On initialise les 5 bulles et leur x
                groupeBulles[i][j] = new Bulle(this.largeur, this.hauteur);
                groupeBulles[i][j].setX(basex + Math.round(Math.random()) * 20);
            }
        }
    }

}
