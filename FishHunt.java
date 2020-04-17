//Ekaterina Van de Kerckhove, 20100493
//Leslie Moranta,

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class FishHunt extends Application {
    private int largeur = 640;  //Largeur du jeu
    private int hauteur = 480;  //Hauteur du jeu
    private static boolean mort;   //État de la méduse (morte ou non)

    public static void main(String[] args){ launch(args); }
    // ekat est miam
    @Override
    public void start(Stage primaryStage) {
            commencerJeu(primaryStage);
    }

    //Scène d'accueil
    public void commencerJeu(Stage primaryStage){
        mort = false;

        //Créer l'interface graphique
        Pane root = setscene(primaryStage);
        afficherLogo(root);
        // ceci est un test

        //System.out.println(scene.getX() + "," + scene.getY());
        //Canvas canvaFond = new Canvas(this.largeur, this.hauteur);

        //root.getChildren().add(canvaFond);

        //Présenter le fond
        //GraphicsContext fond = canvaFond.getGraphicsContext2D();
        //fond.clearRect(0 ,0, largeur, hauteur);
        //fond.setFill(Color.rgb(0, 0, 100));
        //fond.fillRect(0, 0, largeur, hauteur);

        //Présenter le logo




        //Présenter les 2 boutons
    /*
        Button boutonJeu = new Button("Nouvelle partie!");
        Button boutonScore = new Button("Meilleurs scores");

        HBox groupeBoutons = new HBox();
        groupeBoutons.getChildren().add(boutonJeu);
        groupeBoutons.getChildren().add(boutonScore);
        groupeBoutons.setAlignment(Pos.CENTER);

        root.getChildren().add(groupeBoutons);

     */
    /*
        boutonJeu.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                jouer();
            }
        });

        boutonScore.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                meilleursScores();
            }
        });
     */

        //Afficher le tout

    }
    public Pane setscene(Stage primaryStage){
        Pane root = new Pane();
        Scene scene = new Scene(root, this.largeur, this.hauteur);
        Canvas canvaFond = new Canvas(this.largeur, this.hauteur);

        root.getChildren().add(canvaFond);

        //Présenter le fond
        GraphicsContext fond = canvaFond.getGraphicsContext2D();
        fond.clearRect(0 ,0, largeur, hauteur);
        fond.setFill(Color.rgb(0, 0, 139));
        fond.fillRect(0, 0, largeur, hauteur);

        primaryStage.setTitle("Fish Hunt");
        primaryStage.setScene(scene);
        primaryStage.show();
        return root;
    }

    public void afficherLogo(Pane root){
        ImageView image = new ImageView();
        Image logo = new Image("/logo.png");
        image.setImage(logo);
        image.setX(170);
        image.setY(75);
        //image.setFitHeight(200);
        image.setFitWidth(300);
        image.setPreserveRatio(true);   //Conserver le ratio
        root.getChildren().add(image);
    }

    //Scène du jeu
    public void jouer(){
        //Détecter la souris
        //Détecter le clavie pour le debug
        //Afficher score
        //Afficher poissons morts
        if(mort){
            //recommencerJeu();
        }

    }

    //Scène des meilleurs scores
    public void meilleursScores(){
        //Afficher la liste des scores
        //Permettre l'entrée d'un nouveau score
        //Bouton ajouter score

    }

    public void recommencerJeu(Stage stage){
        commencerJeu(stage);
    }

    public static void setMorte(boolean état) {
        mort = état;
    }
}
