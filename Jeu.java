public class Jeu {

    private int largeur = 350;  //largeur du jeu
    private int hauteur = 480;  //hauteur du jeu
    private Bulle[][] groupeBulles = new Bulle[3][5];   //3 groupes de 5 bulles
    private int score;  //Score total équivalent au nombre d'animaux atteints
    private double intervalleTemps = 0; //Intervalle de temps auquel les bulles se renouvellent

    public void Jeu(){
        //Créer les bulles
        renouvelerBulle();

    }

    public void draw(){

    }

    public void update(){

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
