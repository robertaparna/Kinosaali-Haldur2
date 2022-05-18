package src.kinosaalihaldur2;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;


public class SaalideHaldamine {
    private Scene stseen;

    public SaalideHaldamine(Stage pealava, Scene eelmine){

        //Taust
        Image img = new Image("https://entrepreneurship.babson.edu/wp-content/uploads/2020/10/Movie-1200-630.jpg");
        BackgroundImage bImg = new BackgroundImage(img,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, false, true));
        Background bGround = new Background(bImg);


        //tagasi nupp
        Button tagasi = new Button();
        tagasi.setText("Tagasi");
        tagasi.setStyle("-fx-background-color: #2baafd; -fx-border-color: WHITE;-fx-text-fill: WHITE");
        tagasi.setFont(Font.font("Bauhaus 93", 13));
        tagasi.setAlignment(Pos.TOP_LEFT);
        tagasi.setOnAction(e -> pealava.setScene(eelmine));

        //Tekst ja hbox
        HBox hBox = new HBox();
        hBox.setSpacing(10);
        hBox.setPadding(new Insets(5));
        Text tekst = new Text("SAALIDE HALDAMINE");
        tekst.setFont(Font.font("Bauhaus 93",  24));
        tekst.setFill(Color.WHITE);
        tekst.setLineSpacing(5);
        hBox.getChildren().addAll(tagasi,tekst);
        hBox.setAlignment(Pos.TOP_LEFT);

        Region vahe = new Region();
        HBox.setHgrow(vahe, Priority.ALWAYS);

        //sulgemise nupp
        Button exit = new Button("Välju");
        exit.setOnAction((ActionEvent event) -> Platform.exit());
        exit.setPrefSize(60, 30);
        exit.setStyle("-fx-background-color: #c50000; -fx-border-color:  black");
        exit.setFont(Font.font("Bauhaus 93", 12));
        HBox hBox1 = new HBox(exit);
        hBox1.setPrefSize(100,50); //see mõjutab choiceboxi kaugust tekstist praegu
        hBox1.setAlignment(Pos.TOP_RIGHT);

        BorderPane borderPane1 = new BorderPane(null, null, hBox1, null, hBox);

        //Vbox
        VBox vBox = new VBox();
        vBox.setPrefSize(600, 400);
        vBox.setPadding(new Insets(15));
        vBox.setSpacing(10);


        //ChoiceBox
        ObservableList<String> saalideNimed = FXCollections.observableArrayList();
        for (Saal saal : Rakendus.getSaalid()) {
            saalideNimed.add(saal.getNimi());
        }
        AtomicReference<String> valitudSaal = new AtomicReference<>("");
        System.out.println(saalideNimed);
        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        choiceBox.setItems(saalideNimed);
        choiceBox.setStyle("-fx-background-color: rgba(43,170,253,0.76); -fx-border-color: WHITE;" +
                "-fx-alignment: center");
        choiceBox.setPrefSize(200,30);
        choiceBox.setValue("Vali sobiv saal");

        vBox.setBackground(bGround);
        vBox.maxHeight(20000);
        vBox.maxWidth(20000);
        VBox.setVgrow(hBox,Priority.ALWAYS);
        vBox.getChildren().add(borderPane1);
        vBox.getChildren().add(choiceBox);

        choiceBox.setOnAction(event -> {
            valitudSaal.set(choiceBox.getSelectionModel().getSelectedItem());
            Saal valitud = null;
            if(vBox.getChildren().size() == 3){
                vBox.getChildren().remove(2);
            }
            for (Saal saal : Rakendus.getSaalid()) {
                if(saal.getNimi().equals(valitudSaal.toString())){
                    valitud = saal;
                }
            }
            vBox.getChildren().add(visuaalneKohaplaan(valitud.getKohaplaan()));
        });

        //Lõpu asjad

        this.stseen = new Scene(vBox, 600, 400);

    }


    public Scene getStseen() {
        return stseen;
    }
    public GridPane visuaalneKohaplaan(List<List<Integer>> kohaplaan) {
        GridPane visuaalneKohaplaan = new GridPane();
        visuaalneKohaplaan.setAlignment(Pos.BASELINE_CENTER);
        visuaalneKohaplaan.setHgap(5);
        visuaalneKohaplaan.setVgap(5);
        visuaalneKohaplaan.setBackground(new Background(new BackgroundFill(Color.BLACK, new CornerRadii(5), null)));
        visuaalneKohaplaan.setPadding(new Insets(10));

        for (int i = 0; i < kohaplaan.size(); i++) {
            for (int j = 0; j < kohaplaan.get(0).size(); j++) {
                Rectangle koht = new Rectangle(25, 25, Color.GREEN);
                visuaalneKohaplaan.add(koht, j, i);
            }
        }
        return visuaalneKohaplaan;
    }

}
