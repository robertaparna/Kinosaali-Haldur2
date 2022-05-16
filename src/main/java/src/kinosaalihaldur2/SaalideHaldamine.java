package src.kinosaalihaldur2;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;


public class SaalideHaldamine {
    private Scene stseen;

    public SaalideHaldamine(Stage pealava, Scene eelmine, List<Saal> saalid){

        //tagasi nupp
        Button tagasi = new Button();
        tagasi.setText("Tagasi");
        tagasi.setStyle("-fx-background-color: gray; -fx-border-color: black;-fx-text-fill: Yellow");
        tagasi.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        tagasi.setAlignment(Pos.TOP_LEFT);
        tagasi.setOnAction(e -> {
            pealava.setScene(eelmine);
        });


        //tekstikast ja hbox
        HBox hBox = new HBox();
        hBox.setSpacing(10);
        hBox.setPadding(new Insets(5));
        Text tekst = new Text("Saalide haldamine");
        tekst.setTextAlignment(TextAlignment.RIGHT);
        tekst.setFont(Font.font(null, FontWeight.BOLD, 20));
        hBox.getChildren().addAll(tagasi,tekst);
//        hBox.setPrefSize(300,30);
        hBox.setAlignment(Pos.TOP_LEFT);

        Region vahe = new Region();
        HBox.setHgrow(vahe, Priority.ALWAYS);
        //sulgemise nupp
        Button exit = new Button("Välju");
        exit.setOnAction((ActionEvent event) -> Platform.exit());
        exit.setPrefSize(60, 30);
        exit.setStyle("-fx-background-color: red; -fx-border-color:  black");
        exit.setFont(Font.font("Verdana", FontWeight.BOLD, 10));
        HBox hBox1 = new HBox(exit);
        hBox1.setPrefSize(100,100);
        hBox1.setAlignment(Pos.TOP_RIGHT);

        BorderPane borderPane1 = new BorderPane(null, null, hBox1, null, hBox);
        //vbox
        VBox vBox = new VBox();
        vBox.setPrefSize(600, 400);
        vBox.setPadding(new Insets(15));
        vBox.setStyle("-fx-background-color: #626266");

        //ChoiceBox
        ObservableList<String> saalideNimed = FXCollections.observableArrayList();
        for (Saal saal : saalid) {
            saalideNimed.add(saal.getNimi());
        }
        AtomicReference<String> valitudSaal = new AtomicReference<>("");
        System.out.println(saalideNimed);
        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        choiceBox.setItems(saalideNimed);
        choiceBox.setStyle("-fx-background-color: grey; -fx-border-color: black;" +
                "-fx-alignment: center");

        choiceBox.setOnAction(event -> valitudSaal.set(choiceBox.getSelectionModel().getSelectedItem()));

        //Lõpu asjad
//        vBox.getChildren().add(hBox1);
        vBox.getChildren().add(borderPane1);
        vBox.getChildren().add(choiceBox);
        this.stseen = new Scene(vBox);

    }

    public Group getJuur() {
        return juur;
    }
}
