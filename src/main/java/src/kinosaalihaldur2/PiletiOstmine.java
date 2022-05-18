package src.kinosaalihaldur2;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;


public class PiletiOstmine {
    private Scene stseen;
    private ObservableList<String> seanssideNimed = FXCollections.observableArrayList();


    public PiletiOstmine(Stage pealava, Scene eelmine) {

        //Taust
        Image img = new Image("https://www.marketplace.org/wp-content/uploads/2019/03/GettyImages-465453328.jpg?fit=1800%2C1000");
        BackgroundImage bImg = new BackgroundImage(img,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, false, true));
        Background bGround = new Background(bImg);

        //"Tagasi" minemise nupp
        Button tagasi = new Button();
        tagasi.setText("Tagasi");
        tagasi.setTextAlignment(TextAlignment.CENTER);
        tagasi.setStyle("-fx-background-color: #c50000; -fx-border-color:  WHITE; -fx-text-fill: WHITE");
        tagasi.setFont(Font.font("Bauhaus 93", 13));
        tagasi.setOnAction(e -> pealava.setScene(eelmine));

        //tekstikast ja hbox
        HBox hBox = new HBox();
        hBox.setSpacing(10);
        hBox.setPadding(new Insets(5));
        Text tekst = new Text("Piletite ostmine");
        tekst.setFont(Font.font("Bauhaus 93",  24));
        tekst.setFill(Color.WHITE);
        tekst.setLineSpacing(5);
        hBox.getChildren().addAll(tagasi, tekst);
        hBox.setAlignment(Pos.TOP_LEFT);

        Region vahe = new Region();
        HBox.setHgrow(vahe, Priority.ALWAYS);

        //"Välju" nupp
        Button exit = new Button("Välju");
        exit.setOnAction((ActionEvent event) -> Platform.exit());
        exit.setPrefSize(60, 30);
        exit.setStyle("-fx-background-color: #c50000; -fx-border-color:  black");
        exit.setFont(Font.font("Bauhaus 93", 12));
        HBox hBox1 = new HBox(exit);
        hBox1.setPrefSize(100,50); //see mõjutab choiceboxi kaugust tekstist praegu
        hBox1.setAlignment(Pos.TOP_RIGHT);

        //CHOICEBOX
        List<String> kp = Rakendus.getKuupäevad();
        ObservableList<String> kuupaevad = FXCollections.observableArrayList();
        kuupaevad.addAll(kp);

        ChoiceBox<String> kuupaev = new ChoiceBox<>();
        kuupaev.setItems(kuupaevad);
        kuupaev.setStyle("-fx-background-color: rgba(197,0,0,0.81); -fx-border-color: WHITE;" +
                "-fx-alignment: center");

        ChoiceBox<String> pealkiri = new ChoiceBox<>();
        kuupaev.setOnAction(event -> {
            pealkiri.setItems(seaSeansid(kuupaev.getSelectionModel().getSelectedItem()));
        });
        kuupaev.setPrefSize(200,30);
        kuupaev.setValue("Vali sobiv kuupäev");
        BorderPane borderPane1 = new BorderPane(null, null, hBox1, null, hBox);



        pealkiri.setItems(seanssideNimed);
        pealkiri.setStyle("-fx-background-color: rgba(197,0,0,0.81); -fx-border-color: WHITE;" +
                "-fx-alignment: center");

//        pealkiri.setOnAction(event -> valitudKuupaev.set(kuupaev.getSelectionModel().getSelectedItem()));
        pealkiri.setPrefSize(200,30);
        pealkiri.setValue("Vali sobiv seanss");


        HBox content = new HBox();
        VBox valikud = new VBox();

        Button osta = new Button("Osta piletid");
        osta.setAlignment(Pos.BOTTOM_LEFT);
        osta.setTextAlignment(TextAlignment.CENTER);
        osta.setStyle("-fx-background-color: #c50000; -fx-border-color:  WHITE; -fx-text-fill: WHITE");
        osta.setFont(Font.font("Bauhaus 93", 13));

        valikud.setSpacing(10);

        valikud.getChildren().addAll(kuupaev, pealkiri, osta);
        content.getChildren().add(valikud);

        //VBOX
        VBox vBox = new VBox();
        vBox.setPrefSize(600, 400);
        vBox.setPadding(new Insets(15));
        vBox.getChildren().addAll(borderPane1, content);

        //Lõpu asjad
        vBox.setBackground(bGround);
        vBox.maxHeight(20000);
        vBox.maxWidth(20000);
        VBox.setVgrow(hBox,Priority.ALWAYS);
        this.stseen = new Scene(vBox);
    }

    public ObservableList<String> seaSeansid(String kuupaev) {
        ObservableList<String> seanssideNimed = FXCollections.observableArrayList();
        List<Seanss> seansidKuupaeval = Rakendus.valiSeanss(kuupaev);

        for (Seanss seanss : seansidKuupaeval) {
            seanssideNimed.add(seanss.getPealkiri());
        }
        return seanssideNimed;
    }

    public Scene getStseen() {
        return stseen;
    }

    public GridPane visuaalneKohaplaan() {
        GridPane gp =new GridPane();
        return gp;
    }
}