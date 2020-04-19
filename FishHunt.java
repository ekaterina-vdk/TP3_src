//Ekaterina Van de Kerckhove, 20100493
//Leslie Moranta,

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;
import java.util.*;

public class FishHunt extends Application {
    private int largeur = 640;  //Largeur du jeu
    private int hauteur = 480;  //Hauteur du jeu
    private static boolean mort;   //État de la méduse (morte ou non)
    private static int dernierScore; //Dernier score obtenu
    private Controleur controleur;
    private Scene sceneJouer;

    public static void main(String[] args){ launch(args); }

    @Override
    public void start(Stage primaryStage) { commencerJeu(primaryStage); }

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
        image.setPreserveRatio(true);
        vbox.getChildren().add(image);

        // Vbox pour contenir les boutons
        VBox vb_boutons = new VBox(10);
        vb_boutons.setAlignment(Pos.CENTER);
        vbox.getChildren().add(vb_boutons);

        // Bouton Jeu
        Button boutonJeu = new Button();
        boutonJeu.setText("Nouvelle partie!");
        boutonJeu.setPrefWidth(200);
        vb_boutons.getChildren().add(boutonJeu);
        boutonJeu.setOnAction(new EventHandler<ActionEvent>() { //Si on clique dessus, on passe à la scène du jeu
            @Override public void handle(ActionEvent e) {
                jouer(primaryStage);
            }
        });

        // Bouton Score
        Button boutonScore = new Button();
        boutonScore.setText("Meilleurs scores");
        boutonScore.setPrefWidth(200);
        vb_boutons.getChildren().add(boutonScore);
        boutonScore.setOnAction(new EventHandler<ActionEvent>() {   //Si on clique dessus, on passe à la scène des meilleurs scores
            @Override public void handle(ActionEvent e) {
                meilleursScores(primaryStage, -1);  //Entrée d'un score de -1 pour ne pas pouvoir entrer de nouveau score
            }
        });

        // Bouton multijoueur
        Button boutonMulti = new Button();
        boutonMulti.setText("Multijoueur");
        boutonMulti.setPrefWidth(200);
        vb_boutons.getChildren().add(boutonMulti);
        boutonMulti.setOnAction(new EventHandler<ActionEvent>() {   //Si on clique dessus, on passe à la scène multijoueur
            @Override public void handle(ActionEvent e) {
                System.out.println("Jouer multijoueur");
                multijouer_menu(primaryStage);
            }
        });
    }

    //Scène du jeu
    public void jouer(Stage primaryStage) {

        //Créer l'interface graphique
        Pane root = Initi_scene(primaryStage);
        Canvas canvaScore = new Canvas(this.largeur, this.hauteur);
        Canvas canvaBulle = new Canvas(this.largeur, this.hauteur);
        Canvas canvaAnimaux = new Canvas(this.largeur, this.hauteur);

        root.getChildren().add(canvaScore);
        root.getChildren().add(canvaBulle);
        root.getChildren().add(canvaAnimaux);

        GraphicsContext etageScore = canvaScore.getGraphicsContext2D();
        GraphicsContext etageBulle = canvaBulle.getGraphicsContext2D();
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
                controleur.updateCoordonnees(deltaTime);
                controleur.drawJeu(etageScore, etageBulle, etageAnimaux);

                //Détecter le clavier pour les options de debug
                sceneJouer.setOnKeyPressed((event) -> {
                    if (event.getCode() == KeyCode.H || event.getCode() == KeyCode.J || event.getCode() == KeyCode.K || event.getCode() == KeyCode.L) {
                        controleur.debug(event.getCode());
                    }
                });

                //Si on est mort, on veut arrêter la partie en cours et passer au tableau des meilleurs scores
                if (mort) {
                    this.stop();
                    meilleursScores(primaryStage, dernierScore);
                }

                lastTime = now;
            }
        };
        timer.start();

        //Lancer des balles lorsqu'on clique
        root.setOnMouseClicked((event) -> {
            controleur.lancer(event.getX(), event.getY());
        });

        //Faire bouger la cible en fonction de la souris
        ImageView image = new ImageView();
        Image logo = new Image("/cible.png");
        int cote = 50;
        image.setImage(logo);
        image.setFitWidth(cote);
        image.setPreserveRatio(true);
        root.getChildren().add(image);

        root.setOnMouseMoved((event) -> {   //Placer le centre de la cible sur le curseur
            image.setX(event.getX() - cote / 2);
            image.setY(event.getY() - cote / 2);
        });
    }

    //Scène des meilleurs scores
    public void meilleursScores(Stage stage, int score){
        //Créer l'interface graphique
        Pane root = Initi_scene(stage);
        VBox vbox = new VBox(20);
        vbox.setPrefSize(this.largeur, this.hauteur);
        vbox.setAlignment(Pos.CENTER);
        root.getChildren().add(vbox);

        //Afficher le titre
        Text texte = new Text("Meilleurs scores");
        texte.setFill(Color.WHITE);
        texte.setFont(Font.font("Purissa", 35));
        vbox.getChildren().add(texte);

        // Vbox pour contenir les boutons
        VBox vb_boutons = new VBox(10);
        vb_boutons.setPrefWidth(this.largeur);
        vb_boutons.setAlignment(Pos.BOTTOM_CENTER);

        //Lecture du fichier
        LinkedList<Object> resultats = lireFichier();   //Ensemble des noms et de leurs scores du top 10
        int positionScore = -1; //Position où sera inséré le nouveau score s'il y a lieu
        int compteur = 0; //Combien de fois le score est plus petit qu'un autre enregistré

        //Recherche de la position de la personne par rapport aux scores enregistrés dans le top 10
        for(int i = 1; i < resultats.size(); i += 2){
            if(score <= (int)resultats.get(i)){
                compteur++;
            }
        }
        positionScore = compteur * 2 + 1;
        if(compteur == resultats.size()/2 && resultats.size() < 20){   //Si le score est le plus petit, mais qu'il reste de la place dans le tableau, on l'ajoute
            positionScore = resultats.size() + 1;
        }

        //Possibilité d'ajouter un score, seulement s'il est plus grand qu'un d'entre eux ou qu'il y a de la place
        if(positionScore >=0 && positionScore < resultats.size() && score >=0) {
            //HBox pour l'opération d'ajouter son score
            HBox hb_ajout = new HBox(5);
            hb_ajout.setAlignment(Pos.CENTER);
            hb_ajout.setPrefWidth(this.largeur);
            vb_boutons.getChildren().add(hb_ajout);

            //Début de l'instruction
            Label debutPhrase = new Label("Votre nom :");
            debutPhrase.setTextFill(Color.rgb(255, 255, 255));

            //Espace pour que l'utilisateur entre son nom
            TextField entreeNom = new TextField("");

            //Fin de l'instruction
            Label finPhrase = new Label("a fait " + score + " points!");
            finPhrase.setTextFill(Color.rgb(255, 255, 255));

            //Bouton Ajouter
            Button boutonAjout = new Button();
            boutonAjout.setText("Ajouter!");
            int finalPositionScore = positionScore;
            boutonAjout.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    resultats.add(finalPositionScore - 1, entreeNom.getText()); //On ajoute le nom de la personne
                    resultats.add(finalPositionScore, score);   //On ajouter son score

                    if(resultats.size() > 20){ //Si en ajoutant le score, on dépasse le top 10, on doit enlever le dernier nom et le score associé
                        resultats.removeLast();
                        resultats.removeLast();
                    }
                    updateFichier(resultats);   //Mise à jour du fichier
                    recommencerJeu(stage);  //Retour au menu principal
                }
            });

            hb_ajout.getChildren().add(debutPhrase);
            hb_ajout.getChildren().add(entreeNom);
            hb_ajout.getChildren().add(finPhrase);
            hb_ajout.getChildren().add(boutonAjout);

        }

        //Bouton pour retourner au menu
        Button boutonMenu = new Button();
        boutonMenu.setText("Menu");
        vb_boutons.getChildren().add(boutonMenu);
        boutonMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                recommencerJeu(stage);
            }
        });

        //Afficher la liste de score dans un tableau
        ObservableList<String> affichage = FXCollections.observableArrayList(
                afficherResultat(resultats, 0), afficherResultat(resultats, 2), afficherResultat(resultats, 4),
                afficherResultat(resultats, 6), afficherResultat(resultats, 8), afficherResultat(resultats, 10),
                afficherResultat(resultats, 12), afficherResultat(resultats, 14), afficherResultat(resultats, 16),
                afficherResultat(resultats, 18));
        ListView<String> listView = new ListView<String>(affichage);
        listView.setFixedCellSize(24);
        listView.setMaxSize(540, 242);

        //Ajouter la liste et les boutons
        vbox.getChildren().add(listView);
        vbox.getChildren().add(vb_boutons);
    }

    //Scène multijoueur
    public void multijouer_menu(Stage primaryStage){
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

    public void recommencerJeu(Stage stage){ commencerJeu(stage); }

    public static void setMorte(boolean etat) { mort = etat; }

    public static void setDernierScore(int score) {dernierScore = score;}

    //Méthode pour initialiser une scène
    public Pane Initi_scene(Stage primaryStage){
        //Créer l'interface graphique
        Pane root = new Pane();
        Scene scene = new Scene(root, this.largeur, this.hauteur);
        sceneJouer = scene;
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

    //Méthode pour lire le fichier des meilleurs scores
    public LinkedList<Object> lireFichier(){
        LinkedList<Object> resultats = new LinkedList<>();  //Liste contenant tous les noms et scores enregistrés

        try {
            FileReader fr = new FileReader("src/Scores.txt");
            BufferedReader reader = new BufferedReader(fr);

            String ajout;
            int i = 0;
            while ((ajout = reader.readLine()) != null){    //On passe au travers le fichier
                resultats.add(i, ajout);    //Ajouter le nom de la personne
                resultats.add(i + 1, Integer.parseInt(reader.readLine()));  //Ajouter son score
                i += 2;
            }
            reader.close();
        } catch (IOException ex) {
            System.out.println("Erreur à l’ouverture du fichier");
        }

        return resultats;
    }

    //Méthode pour mettre à jour le fichier des meilleurs scores
    public void updateFichier(LinkedList<Object> resultats){
        try {
            FileWriter fw = new FileWriter("src/Scores.txt");
            BufferedWriter writer = new BufferedWriter(fw);

            for(int i = 0; i < resultats.size(); i++) {
                writer.append(resultats.get(i).toString());
                if(i != resultats.size() - 1) {  //On rajoute une ligne après seulement si ce n'est pas le dernier élément
                    writer.newLine();
                }
            }
            writer.close();
        } catch (IOException ex) {
            System.out.println("Erreur à l’écriture du fichier");
        }
    }

    //Méthode pour afficher la position, le nom et le score de chaque personne enregistrée dans le top 10
    public String afficherResultat(LinkedList<Object> resultats, int i){
        int position = i/2 + 1;
        String nomScore;

        if(i < resultats.size()){ nomScore = "#" + position + " - " + resultats.get(i) + " - " + resultats.get(i + 1); }
        else{ nomScore = ""; }  //S'il n'y a pas de score à cet index, on retourne une String vide

        return nomScore;
    }
}
