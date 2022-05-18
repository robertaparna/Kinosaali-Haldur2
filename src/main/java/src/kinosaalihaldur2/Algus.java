package src.kinosaalihaldur2;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Algus extends Application {
    private static Stage pealava;
    private Scene stseen;

    public static Stage getStage() {
        return pealava;
    }

    public static void main(String[] args) {
        launch(args);
    }

    private List<Saal> saalid = new ArrayList<>();

    public List<Saal> getSaalid() {
        return saalid;
    }

    public void setSaalid(List<Saal> saalid) {
        this.saalid = saalid;
    }

    @Override
    public void start(Stage peaLava) {
        Rakendus.saalidAlgus();
        pealava = peaLava;

        //taustapilt
        //ma sain siit selle -> https://www.delftstack.com/howto/java/javafx-background-image/
        Image img = new Image("https://data.whicdn.com/images/311663681/original.jpg");
        BackgroundImage bImg = new BackgroundImage(img,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, false, true));
        Background bGround = new Background(bImg);

        //sulgemise nupp
        Button exit = new Button("Välju");
        exit.setOnAction((ActionEvent event) -> Platform.exit());
        exit.setPrefSize(60, 30);
        exit.setStyle("-fx-background-color: #f83a00; -fx-border-color:  black");
        exit.setFont(Font.font("Bauhaus 93", 12));


        //Piletite ostmise nupp
        Button osta = new Button("Piletite " + "\n" + "ostmine");
        osta.setPrefSize(100, 45);
        osta.setStyle("-fx-background-color: #f85700; -fx-border-color: #ffffff");
        osta.setFont(Font.font("Bauhaus 93", 12));
        osta.setTextFill(Color.WHITE);
        osta.setTextAlignment(TextAlignment.CENTER);
        osta.setOnAction(e -> {
            PiletiOstmine pilet = new PiletiOstmine(pealava, stseen);
            peaLava.setScene(pilet.getStseen());
        });

        //Saalide haldamise nupp
        Button saalHalda = new Button("Saalide " + "\n" + "haldamine");
        saalHalda.setPrefSize(100, 45);
        saalHalda.setStyle("-fx-background-color: #f85700; -fx-border-color: #ffffff");
        saalHalda.setFont(Font.font("Bauhaus 93",  12));
        saalHalda.setTextFill(Color.WHITE);
        saalHalda.setTextAlignment(TextAlignment.CENTER);
        saalHalda.setOnAction(e -> {
            SaalideHaldamine saal = new SaalideHaldamine(pealava, stseen);
            peaLava.setScene(saal.getStseen());
        });

        //Kinokava haldamise nupp
        Button kinoHalda = new Button("Kinokava" + "\n" + "haldamine");
        kinoHalda.setPrefSize(100, 45);
        kinoHalda.setStyle("-fx-background-color: #f85700; -fx-border-color: #ffffff");
        kinoHalda.setFont(Font.font("Bauhaus 93", 12));
        kinoHalda.setTextFill(Color.WHITE);
        kinoHalda.setTextAlignment(TextAlignment.CENTER);
        kinoHalda.setOnAction(e -> {
            KinokavaHaldamine kino = new KinokavaHaldamine(pealava, stseen);
            peaLava.setScene(kino.getStseen());
        });

        //Salvestamise nupp
        Button salvesta = new Button("Salvesta");
        salvesta.setPrefSize(100, 45);
        salvesta.setStyle("-fx-background-color: #f85700; -fx-border-color: #ffffff");
        salvesta.setFont(Font.font("Bauhaus 93", 12));
        salvesta.setTextFill(Color.WHITE);
        salvesta.setOnAction( event -> {
            Salvestamine salvestamine =  new Salvestamine(pealava, stseen);
            pealava.setScene(salvestamine.getStseen());
        });


        //hbox
        HBox hBox = new HBox(exit);
        hBox.setPrefSize(100,100);
        hBox.setAlignment(Pos.TOP_RIGHT);

        //vbox
        VBox vBox = new VBox();
        vBox.setPrefSize(600,400); //panin prg, et see on sama suur kui juur
        vBox.autosize();
        vBox.setPadding(new Insets(15));

        //Gridpane
        GridPane gridpane = new GridPane();
        gridpane.setHgap(25);
        gridpane.setVgap(25);
        //gridpane.setPrefSize(600,400);//panin prg, et see on sama suur kui juur
        gridpane.add(osta, 0,0);
        gridpane.add(saalHalda, 1,0);
        gridpane.add(kinoHalda, 0,1);
        gridpane.add(salvesta, 1,1);
        gridpane.setAlignment(Pos.CENTER);


        vBox.getChildren().add(hBox);
        vBox.getChildren().add(gridpane);
        vBox.setBackground(bGround);
        vBox.maxHeight(20000);
        vBox.maxWidth(20000);
        VBox.setVgrow(gridpane, Priority.ALWAYS);

        this.stseen = new Scene(vBox, 600, 400);

        peaLava.setScene(stseen);
        peaLava.show();
    }
}