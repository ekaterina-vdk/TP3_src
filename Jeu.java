import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.util.LinkedList;

public class Jeu {

    private int largeur = 640;  //largeur du jeu
    private int hauteur = 480;  //hauteur du jeu
    private Bulle[][] groupeBulles = new Bulle[3][5];   //3 groupes de 5 bulles
    private LinkedList<Animaux> animaux; //Tout les animaux qui sont sur le jeu
    private LinkedList<Balle> balles; //Toutes les balles qui sont lancées
    private int score;  //Score total équivalent au nombre d'animaux atteints

    private int nbrVies; //Nombre de vie restant
    private Image imageVies; //Poisson représentant une vie

    private double intervalleBulle = 0; //Intervalle de temps auquel les bulles se renouvellent
    private double intervallePoisson = 0;  //Intervalle de temps auquel les poissons se créent
    private double intervalleSpeciaux = 0; //Intervalle de temps auquel les crabes et les étoiles se créent

    private double intervalleNiveau = 3; //Chaque niveau est affiché pendant 3 secondes
    private int niveau; //Niveau où on est rendu
    private boolean affichageNiveau; //Affichage du niveau est activé ou non

    public Jeu(){
        //Créer les bulles
        renouvelerBulle();

        //Initialisation des listes chaînées
        animaux = new LinkedList<>();
        balles = new LinkedList<>();

        //Initialisation du score et du nombre de vies
        this.score = 0;
        this.nbrVies = 3;
        imageVies = new Image("/00.png");

        //Initialisation du niveau
        this.niveau = 1;
        affichageNiveau = true;
    }

    public void draw(GraphicsContext etageScore, GraphicsContext etageBulle, GraphicsContext etageBalle, GraphicsContext etageAnimaux){
        //Afficher le score
        etageScore.clearRect(0, 0, this.largeur, this.hauteur);
        etageScore.setFill(Color.WHITE);
        etageScore.setFont(Font.font("Purisa", 20));
        etageScore.setTextAlign(TextAlignment.CENTER);
        etageScore.fillText(this.score + "", this.largeur/2, 30);

        //Afficher le nombre de vies
        for(int i = 0; i < nbrVies; i++) {
            etageScore.drawImage(imageVies, 245 + (i*50), 40, 40, 40);
        }

        //Afficher le niveau
        if(affichageNiveau){
            if(nbrVies == 0){   //On est mort, donc on affiche Game Over
                afficherNiveau(etageScore, "Game Over");
                if(intervalleNiveau < 0){ Controleur.setMort(true); }   //Après 3 secondes, on passe à l'écran des meilleurs scores
            }
            else if(intervalleNiveau >= 0){ //On passe au prochain niveau
                afficherNiveau(etageScore, "Level " + niveau);
            }
            else{ //Si le 3 secondes est passé, on enlève l'affichage du niveau et on reset l'intervalle
                affichageNiveau = false;
                intervalleNiveau = 3;
            }
        }

        //Afficher les bulles
        etageBulle.clearRect(0,0, this.largeur, this.hauteur);
        for(int i = 0; i < groupeBulles.length; i++) {
            for (int j = 0; j < groupeBulles[i].length; j++) {
                groupeBulles[i][j].draw(etageBulle);
            }
        }

        //Afficher la balle
        if(balles.size() > 0) {
            for (Balle b : balles) {
                b.draw(etageBulle);
            }
        }

        //Afficher les animaux
        etageAnimaux.clearRect(0, 0, this.largeur, this.hauteur);
        if(animaux.size() > 0) {
            for (Animaux ani : animaux) {
                ani.draw(etageAnimaux);
                //System.out.println("Dessin fait");
            }
        }

    }

    public void update(double dt){
        if(affichageNiveau){    //Si le niveau est actuellement affiché, on décrémente le chronomètre
            intervalleNiveau -= dt;
        }

        //Mise à jour des coordonnées des bulles
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
                ani.update(dt);
            }

            //Enlever un animal s'il est en dehors de l'écran
            for(Animaux ani : animaux){
                if(ani.getX() > this.largeur || ani.getX() < ani.getLargeur()*-1 || ani.getY() > this.hauteur || ani.getY() < ani.getHauteur()*-1){
                    animaux.remove(ani);
                    nbrVies --;
                }
            }
        }
        intervallePoisson += dt;
        intervalleSpeciaux += dt;

        //Ajout d'un poisson au 3 secondes et d'une étoile ou d'un crabe aux 5 secondes (seulement si on n'affiche pas le niveau)
        if(!affichageNiveau) {
            if (intervallePoisson >= 3) {
                Poisson nouveauPoisson = new Poisson(this.largeur, this.hauteur, this.niveau);
                animaux.addFirst(nouveauPoisson);
                intervallePoisson = 0;
            }
            if (intervalleSpeciaux >= 5 && this.niveau > 1) {   //Les crabes et étoiles apparaissent juste à partir du 2ième niveau
                double probabilité = Math.random();
                Animaux nouveauSpecial;
                if (probabilité < 0.5) {
                    nouveauSpecial = new Etoile(this.largeur, this.hauteur, this.niveau);
                } else {
                    nouveauSpecial = new Crabe(this.largeur, this.hauteur, this.niveau);
                }
                animaux.addFirst(nouveauSpecial);
                intervalleSpeciaux = 0;
            }
        }

        if(balles.size() > 0){
            //Mise à jour des coordonnées des balles
            for(Balle b : balles){
                b.update(dt);
            }

            //Enlever une balle si elle n'est plus existante
            for(Balle b : balles){
                if(b.getLargeur() < -50){ balles.remove(b); }
            }
        }

        //Tester les collisions, s'il y en a une, retirer l'animal du jeu
        for(Balle b : balles){
            for(Animaux a : animaux){
                if(a.intersection(b) && b.getLargeur() <= 0){
                    score ++;
                    if(score % 5 == 0){ //Si on a atteint 5 poissons depuis le début du niveau, on passe au prochain
                        niveau ++;
                        affichageNiveau = true;
                        animaux.clear();    //On enlève les animaux actuellement sur le jeu
                    }
                    animaux.remove(a);  //On retire l'animal atteint
                }
            }
        }

        if(nbrVies == 0){
            affichageNiveau = true; //Lorsqu'on meurt, on veut afficher Game Over
            animaux.clear();  //On enlève les animaux actuellement sur le jeu
        }
    }

    public void lancer(double x, double y){
        Balle nouvelleBalle = new Balle(this.largeur, this.hauteur, x, y);
        balles.addFirst(nouvelleBalle);
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

    public void afficherNiveau(GraphicsContext etageScore, String texte){
        etageScore.setFill(Color.WHITE);
        etageScore.setFont(Font.font("Purisa", 50));
        etageScore.setTextAlign(TextAlignment.CENTER);
        etageScore.fillText(texte, this.largeur/2, this.hauteur/2);
    }
}
