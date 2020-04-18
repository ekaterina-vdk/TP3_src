//Ekaterina Van de Kerckhove, 20100493
//Leslie Moranta,

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
    private Controleur controleur;
    public static void main(String[] args){ launch(args); }

    @Override
    public void start(Stage primaryStage) {
            commencerJeu(primaryStage);
    }

    //Scène d'accueil
    public void commencerJeu(Stage primaryStage){
        mort = false;
        this.controleur = new Controleur();
        //Créer l'interface graphique
        Pane root = Initi_scene(primaryStage);

        // Insérer Vbox dans le Panel pour le placement
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        int logo_width = 300;
        double padding_sides = (largeur - logo_width)/2;
        vbox.setPadding(new Insets(50, padding_sides, 0, padding_sides));
        vbox.setSpacing(50);
        root.getChildren().add(vbox);

        //Présenter le logo
        ImageView image = new ImageView();
        Image logo = new Image("/logo.png");
        image.setImage(logo);
        image.setFitWidth(logo_width);
        image.setPreserveRatio(true);   //Conserver le ratio
        vbox.getChildren().add(image);


        //Présenter les 2 boutons
        // Vbox specific au boutons
        VBox vb_bouttons = new VBox();
        vb_bouttons.setAlignment(Pos.CENTER);
        vb_bouttons.setSpacing(10);
        vbox.getChildren().add(vb_bouttons);
        // Bouton jeu
        Button boutonJeu = new Button();
        boutonJeu.setText("Nouvelle partie!");
        boutonJeu.setPrefWidth(200);
        vb_bouttons.getChildren().add(boutonJeu);
        boutonJeu.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                jouer(primaryStage);
            }
        });

        // Bouton score
        Button boutonScore = new Button();
        boutonScore.setText("Meilleurs scores");
        boutonScore.setPrefWidth(200);
        vb_bouttons.getChildren().add(boutonScore);
        boutonScore.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                System.out.println("Je veux voir les scores");
                //jouer();
            }
        });
        // Bouton multijoueur
        Button boutonmulti = new Button();
        boutonmulti.setText("Multijouer");
        boutonmulti.setPrefWidth(200);
        vb_bouttons.getChildren().add(boutonmulti);
        boutonmulti.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                System.out.println("Jouer multijoueur");
                Multijouer_menu(primaryStage);
            }
        });




    }


    public void Multijouer_menu(Stage primaryStage){
        //Créer l'interface graphique
        Pane root = Initi_scene(primaryStage);

        // Insérer Vbox dans le Panel pour le placement des autres layouts
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(200, 210, 50, 200));
        vbox.setSpacing(20);
        root.getChildren().add(vbox);

        // Vbox pour les entrées utilisateur
        VBox vbox_entree = new VBox();
        vbox_entree.setAlignment(Pos.CENTER);
        vbox_entree.setSpacing(10);
        vbox.getChildren().add(vbox_entree);
        // Hbox pour le port
        HBox hb_port = new HBox();
        hb_port.setAlignment(Pos.CENTER);
        hb_port.setSpacing(10);
        vbox_entree.getChildren().add(hb_port);

        Label label_port = new Label("Port:");
        label_port.setTextFill(Color.rgb(255,255,255));
        label_port.setPrefWidth(75);

        TextField textField_port = new TextField ("6868");

        hb_port.getChildren().add(label_port);
        hb_port.getChildren().add(textField_port);
        // Hbox pour l'addresse IP
        HBox hb_IP = new HBox();
        hb_IP.setAlignment(Pos.CENTER);
        hb_IP.setSpacing(10);
        vbox_entree.getChildren().add(hb_IP);

        Label label_IP = new Label("Adresse IP:");
        label_IP.setTextFill(Color.rgb(255,255,255));
        label_IP.setPrefWidth(75);

        TextField textField_IP = new TextField ("127.0.0.1");

        hb_IP.getChildren().add(label_IP);
        hb_IP.getChildren().add(textField_IP);

        // Bouton pour heberger
        Button bouton_heberger = new Button();
        bouton_heberger.setText("Héberger");
        bouton_heberger.setPrefWidth(200);
        vbox.getChildren().add(bouton_heberger);
        bouton_heberger.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                System.out.println("Héberger");
                int port = Integer.valueOf(textField_port.getText()); //TODO verification entree utilisateur
                controleur.Start_Server(port);
            }
        });
        // Bouton pour rejoindre
        Button bouton_rejoindre = new Button();
        bouton_rejoindre.setText("Rejoindre");
        bouton_rejoindre.setPrefWidth(200);
        vbox.getChildren().add(bouton_rejoindre);
        bouton_rejoindre.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                System.out.println("Rejoindre");
            }
        });
    }
    //Scène du jeu
    public void jouer(Stage primaryStage) {

        //Créer l'interface graphique
        Pane root = Initi_scene(primaryStage);
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
                if (mort) {
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
            image.setX(event.getX() - cote / 2);
            image.setY(event.getY() - cote / 2);
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

    public Pane Initi_scene(Stage primaryStage){
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
