package src.kinosaalihaldur2;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class Algus extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage peaLava) {
        Group juur = new Group();
        Scene stseen = new Scene(juur, 600, 400);

        //taustapilt
        //ma sain siit selle -> https://www.delftstack.com/howto/java/javafx-background-image/
        Image img = new Image("https://entrepreneurship.babson.edu/wp-content/uploads/2020/10/Movie-1200-630.jpg");
        BackgroundImage bImg = new BackgroundImage(img,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT);
        Background bGround = new Background(bImg);

        //sulgemise nupp
        Button exit = new Button("Välju");
        exit.setOnAction((ActionEvent event) -> Platform.exit());
        exit.setPrefSize(60, 30);
        exit.setStyle("-fx-background-color: red; -fx-border-color:  black");
        exit.setFont(Font.font("Verdana", FontWeight.BOLD, 10));


        //Piletite ostmise nupp
        Button osta = new Button("Piletite " + "\n" + "ostmine");
        osta.setPrefSize(80, 35);
        osta.setStyle("-fx-background-color: gray; -fx-border-color: black");
        osta.setFont(Font.font("Verdana", FontWeight.BOLD, 10));
        osta.setTextAlignment(TextAlignment.CENTER);

        //Saalide haldamise nupp
        Button saalHalda = new Button("Saalide " + "\n" + "haldamine");
        saalHalda.setPrefSize(80, 35);
        saalHalda.setStyle("-fx-background-color: gray; -fx-border-color: black");
        saalHalda.setFont(Font.font("Verdana", FontWeight.BOLD, 10));
        saalHalda.setTextAlignment(TextAlignment.CENTER);

        //Kinokava haldamise nupp
        Button kinoHalda = new Button("Kinokava" + "\n" + "haldamine");
        kinoHalda.setPrefSize(80, 35);
        kinoHalda.setStyle("-fx-background-color: gray; -fx-border-color: black");
        kinoHalda.setFont(Font.font("Verdana", FontWeight.BOLD, 10));
        kinoHalda.setTextAlignment(TextAlignment.CENTER);

        //Salvestamise nupp
        Button salvesta = new Button("Salvesta");
        salvesta.setPrefSize(80, 35);
        salvesta.setStyle("-fx-background-color: gray; -fx-border-color: black");
        salvesta.setFont(Font.font("Verdana", FontWeight.BOLD, 10));

        //hbox
        HBox hBox = new HBox(exit);
        hBox.setPrefSize(100,100);
        hBox.setAlignment(Pos.TOP_RIGHT);

        //vbox
        VBox vBox = new VBox();
        vBox.setPrefSize(600,400); //panin prg, et see on sama suur kui juur
        vBox.setPadding(new Insets(15));

        //Gridpane
        GridPane gridpane = new GridPane();
        gridpane.setHgap(25);
        gridpane.setVgap(25);
        gridpane.setPrefSize(600,400);//panin prg, et see on sama suur kui juur
        gridpane.add(osta, 0,0);
        gridpane.add(saalHalda, 1,0);
        gridpane.add(kinoHalda, 0,1);
        gridpane.add(salvesta, 1,1);
        gridpane.setAlignment(Pos.CENTER);

        vBox.getChildren().add(hBox);
        vBox.getChildren().add(gridpane);
        vBox.setBackground(bGround);


        juur.getChildren().add(vBox);
        peaLava.setScene(stseen);
        peaLava.show();
    }
}
