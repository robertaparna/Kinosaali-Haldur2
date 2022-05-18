package src.kinosaalihaldur2;

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
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class KinokavaHaldamine {
    private final Scene stseen;

    public KinokavaHaldamine(Stage pealava, Scene eelmine) {

        //Taust
        Image img = new Image("https://www.visittallinn.ee/static/files/010/t2_apollo_cinema_solaris_in_tallinnestonia.jpg");
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
        tagasi.setStyle("-fx-background-color: #f85700; -fx-border-color: WHITE;-fx-text-fill: WHITE");
        tagasi.setFont(Font.font("Bauhaus 93", 13));
        tagasi.setOnAction(e -> pealava.setScene(eelmine));

        //tekstikast ja hbox
        HBox hBox = new HBox();
        hBox.setSpacing(10);
        hBox.setPadding(new Insets(5));
        Text tekst = new Text("Kinokava haldamine");
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
        hBox1.setPrefSize(100, 100);
        hBox1.setAlignment(Pos.TOP_RIGHT);

        BorderPane borderPane1 = new BorderPane(null, null, hBox1, null, hBox);

        //VBOX
        VBox vBox = new VBox();
        vBox.setPrefSize(600, 400);
        vBox.setPadding(new Insets(15));
        vBox.getChildren().addAll(borderPane1);

        //Lõpu asjad
        vBox.setBackground(bGround);
        vBox.maxHeight(20000);
        vBox.maxWidth(20000);
        VBox.setVgrow(hBox,Priority.ALWAYS);
        this.stseen = new Scene(vBox);
    }

    public Scene getStseen() {
        return stseen;

    }
}

