//Ekaterina Van de Kerckhove, 20100493
//Leslie Moranta,

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class FishHunt extends Application {
    private int largeur = 640;  //Largeur du jeu
    private int hauteur = 480;  //Hauteur du jeu
    private static boolean mort;   //État de la méduse (morte ou non)

    public static void main(String[] args){ launch(args); }

    @Override
    public void start(Stage primaryStage) {
            jouer(primaryStage);
    }

    //Scène d'accueil
    public void commencerJeu(Stage primaryStage){
        mort = false;
        // test
        //Créer l'interface graphique
        Pane root = setScene(primaryStage);

        //Présenter le logo
        ImageView image = new ImageView();
        Image logo = new Image("/logo.png");
        image.setImage(logo);
        image.setX(170);
        image.setY(75);
        image.setFitWidth(300);
        image.setPreserveRatio(true);   //Conserver le ratio
        root.getChildren().add(image);


        //Présenter les 2 boutons

        Button boutonJeu = new Button("Nouvelle partie!");
        Button boutonScore = new Button("Meilleurs scores");

        //VBox groupeBoutons = new VBox();
        //groupeBoutons.getChildren().add(boutonJeu);
        //groupeBoutons.getChildren().add(boutonScore);

        root.getChildren().add(boutonJeu);

        boutonJeu.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                System.out.println("Je veux jouer");
                //jouer();
            }
        });
/*
        boutonScore.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                meilleursScores();
            }
        });
     */

    }

    //Scène du jeu
    public void jouer(Stage primaryStage){

        //Créer l'interface graphique
        Pane root = setScene(primaryStage);
        Canvas canvaScore = new Canvas(this.largeur, this.hauteur);
        Canvas canvaBulle = new Canvas(this.largeur, this.hauteur);
        Canvas canvaBalle = new Canvas(this.largeur, this.hauteur);
        Canvas canvaAnimaux = new Canvas(this.largeur, this.hauteur);

        root.getChildren().add(canvaScore);
        root.getChildren().add(canvaBulle);
        root.getChildren().add(canvaBalle);
        root.getChildren().add(canvaAnimaux);

        GraphicsContext etageScore = canvaScore.getGraphicsContext2D();
        GraphicsContext etageBulle = canvaBulle.getGraphicsContext2D();
        GraphicsContext etageBalle = canvaBalle.getGraphicsContext2D();
        GraphicsContext etageAnimaux = canvaAnimaux.getGraphicsContext2D();

        Controleur controleur = new Controleur();

        //Animation centrale du jeu
        AnimationTimer timer = new AnimationTimer() {
            private long lastTime = 0;  //Temps au dernier frame

            @Override
            public void handle(long now) {
                if (lastTime == 0) {
                    lastTime = now;
                    return;
                }

                double deltaTime = (now - lastTime) * 1e-9;

                //On demande au contrôleur de mettre à jour les éléments du jeu et de les dessiner sur la fenêtre de jeu
                controleur.updateCoordonnees(deltaTime, etageBulle, etageBalle, etageAnimaux);
                controleur.drawJeu(etageScore, etageBulle, etageBalle, etageAnimaux);

                //Détecter le clavier pour le debug
                root.setOnKeyPressed((event) -> {
                       if (event.getCode() == KeyCode.H || event.getCode() == KeyCode.J || event.getCode() == KeyCode.K || event.getCode() == KeyCode.L) {
                           controleur.debug(event.getCode());
                       }
                });

                //Si on est mort, on veut arrêter la partie en cours et recommencer le jeu
                if(mort){
                    this.stop();
                    recommencerJeu(primaryStage);
                }

                lastTime = now;
            }
        };
        timer.start();

        //Lancer des balles

        root.setOnMouseClicked((event) -> {
            //controleur.lancer(event.getX(), event.getY());
        });


        //Faire bouger la cible en fonction de la souris
        ImageView image = new ImageView();
        Image logo = new Image("/cible.png");
        int cote = 30;
        image.setImage(logo);
        image.setFitWidth(cote);
        image.setPreserveRatio(true);   //Conserver le ratio
        root.getChildren().add(image);

        root.setOnMouseMoved((event) -> {
            image.setX(event.getX() - cote/2);
            image.setY(event.getY() - cote/2);
        });


        //Afficher score

        //Afficher poissons morts


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

    public Pane setScene(Stage primaryStage){
        //Créer l'interface graphique
        Pane root = new Pane();
        Scene scene = new Scene(root, this.largeur, this.hauteur);
        Canvas canvaFond = new Canvas(this.largeur, this.hauteur);

        root.getChildren().add(canvaFond);

        //Présenter le fond
        GraphicsContext fond = canvaFond.getGraphicsContext2D();
        fond.clearRect(0 ,0, largeur, hauteur);
        fond.setFill(Color.rgb(0, 0, 139));
        fond.fillRect(0, 0, largeur, hauteur);

        //Afficher la scène
        primaryStage.setTitle("Fish Hunt");
        primaryStage.setScene(scene);
        primaryStage.show();

        return root;
    }
}
