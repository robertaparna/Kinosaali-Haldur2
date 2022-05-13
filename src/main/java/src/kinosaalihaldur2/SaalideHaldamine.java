package src.kinosaalihaldur2;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;


public class SaalideHaldamine {
    private final BorderPane borderPane;
    private static Stage pealava;
    private Group juur;

    public static Stage getStage() {
        return pealava;
    }

    //juure puudumine on probleem

    public SaalideHaldamine(){
        //main asjad
        juur = new Group();
        borderPane = new BorderPane();
        borderPane.setPadding(new Insets(15));
        borderPane.setPrefSize(600,400);
        borderPane.setStyle("-fx-background-color: #626266");

        //"Tagasi" nupp - okei siin tekib probleem, sest pealava jms ei ole
        Button tagasi = new Button();
        tagasi.setText("Tagasi");
        tagasi.setStyle("-fx-background-color: gray; -fx-border-color: black;-fx-text-fill: Yellow");
        tagasi.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        tagasi.setAlignment(Pos.TOP_LEFT);
        tagasi.setOnAction(e -> {
            Algus algusesseTagasi = new Algus();
            pealava.getScene().setRoot(algusesseTagasi.getJuur());
        });


        //tekstikast ja hbox
        HBox hBox = new HBox();
        hBox.setSpacing(10);
        hBox.setPrefSize(600,400);
        hBox.setPadding(new Insets(5));
        Text tekst = new Text("Saalide haldamine");
        tekst.setTextAlignment(TextAlignment.RIGHT);
        tekst.setFont(Font.font(null, FontWeight.BOLD, 20));
        hBox.getChildren().addAll(tagasi,tekst);

        //sulgemise nupp
        Button exit = new Button("Välju");
        exit.setOnAction((ActionEvent event) -> Platform.exit());
        exit.setPrefSize(60, 30);
        exit.setStyle("-fx-background-color: red; -fx-border-color:  black");
        exit.setFont(Font.font("Verdana", FontWeight.BOLD, 10));
        HBox hBox1 = new HBox(exit);
        hBox1.setPrefSize(100,100);
        hBox1.setAlignment(Pos.TOP_RIGHT);

        //vbox
        VBox vBox = new VBox();
        vBox.setPrefSize(600, 400);
        vBox.setPadding(new Insets(15));


        //Lõpu asjad
        vBox.getChildren().add(hBox1);
        vBox.getChildren().add(hBox);
        juur.getChildren().addAll(borderPane, vBox);
    }

    public Group getJuur() {
        return juur;
    }
}
