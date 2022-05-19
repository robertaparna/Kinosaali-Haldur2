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
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.List;


public class PiletiOstmine {
    private Scene stseen;
    private ObservableList<String> seanssideNimed = FXCollections.observableArrayList();

    private List<Seanss> seansid;

    private Seanss praeguneSeanss;


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
        tagasi.setOnAction(e -> {
            pealava.setScene(eelmine);
            if(praeguneSeanss != null) {
                praeguneSeanss.tühistaValitudKohad();
            }
        });

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

        //Choicebox
        List<String> kp = Rakendus.getKuupäevad();
        ObservableList<String> kuupaevad = FXCollections.observableArrayList();
        kuupaevad.addAll(kp);

        ChoiceBox<String> kuupaev = new ChoiceBox<>();
        kuupaev.setItems(kuupaevad);
        kuupaev.setStyle("-fx-background-color: rgba(197,0,0,0.81); -fx-border-color: WHITE;" +
                "-fx-alignment: center");

        HBox content = new HBox();
        VBox valikud = new VBox();
        ChoiceBox<String> pealkiri = new ChoiceBox<>();
        kuupaev.setOnAction(event -> {
            pealkiri.setItems(seaSeansid(kuupaev.getSelectionModel().getSelectedItem()));
            if (valikud.getChildren().size() > 2){
                valikud.getChildren().remove(2, 4);
            }
        });
        kuupaev.setPrefSize(200,30);
        kuupaev.setValue("Vali sobiv kuupäev");
        BorderPane borderPane1 = new BorderPane(null, null, hBox1, null, hBox);

        //"Osta" - pileti ostmise nupp
        Button osta = new Button("Osta piletid");
        osta.setAlignment(Pos.BOTTOM_LEFT);
        osta.setTextAlignment(TextAlignment.CENTER);
        osta.setStyle("-fx-background-color: #c50000; -fx-border-color:  WHITE; -fx-text-fill: WHITE");
        osta.setFont(Font.font("Bauhaus 93", 13));
        osta.setOnAction(event -> {
            try {
                praeguneSeanss.müüValitudKohad();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Tehing õnnestus!");
                alert.setHeaderText(null);
                alert.setContentText("Piletid müüdud!");
                kuupaev.setValue("Vali sobiv kuupäev");
                pealkiri.setValue("Vali sobiv seanss");
                alert.showAndWait();

                if(content.getChildren().size() == 2) {
                    content.getChildren().remove(1);
                }
                if (valikud.getChildren().size() > 2){
                    valikud.getChildren().remove(2, 4);
                }

            }
            catch (PoleValitudErind e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Tehing ei õnnestunud!");
                alert.setHeaderText(null);
                alert.setContentText("Pileti ostmiseks vali kohad");
                alert.showAndWait();
            }
        });

        valikud.setSpacing(10);
        valikud.getChildren().addAll(kuupaev, pealkiri);
        content.getChildren().add(valikud);


        pealkiri.setItems(seanssideNimed);
        pealkiri.setStyle("-fx-background-color: rgba(197,0,0,0.81); -fx-border-color: WHITE;" +
                "-fx-alignment: center");

        pealkiri.setOnAction(event -> {
            if(content.getChildren().size() == 2) {
                content.getChildren().remove(1);
            }
            content.getChildren().add(visuaalneKohaplaanNupud(pealkiri.getSelectionModel().getSelectedItem()));
            if(praeguneSeanss != null) {
                if (valikud.getChildren().size() > 2){
                    valikud.getChildren().remove(2, 4);
                }
                Text info = new Text(praeguneSeanss.toString());
                info.setFill(Color.WHITE);
                FlowPane flowPane = new FlowPane(info);
                flowPane.setStyle("-fx-background-color: rgba(197,0,0,0.45)");
                flowPane.setPrefSize(200,120);
                valikud.getChildren().addAll(flowPane, osta);
            }
        });
        pealkiri.setPrefSize(200,30);
        pealkiri.setValue("Vali sobiv seanss");


        //vBox
        VBox vBox = new VBox();
        vBox.setPrefSize(800, 500);
        vBox.setPadding(new Insets(15));
        content.setSpacing(20); //vahe choiceboxi ja kohaplaani vahel
        vBox.getChildren().addAll(borderPane1, content);

        //Lõpu asjad
        vBox.setBackground(bGround);
        vBox.maxHeight(20000);
        vBox.maxWidth(20000);
        VBox.setVgrow(hBox,Priority.ALWAYS);
        this.stseen = new Scene(vBox);
    }

    /**
     * @param kuupaev valitud kuupäev
     * @return Seansid, mis sellel kuupäeval on
     */
    public ObservableList<String> seaSeansid(String kuupaev) {
        ObservableList<String> seanssideNimed = FXCollections.observableArrayList();
        List<Seanss> seansidKuupaeval = Rakendus.valiSeanss(kuupaev);
        this.seansid = seansidKuupaeval;

        for (Seanss seanss : seansidKuupaeval) {
            seanssideNimed.add(seanss.getPealkiri());
        }
        return seanssideNimed;
    }

    public Scene getStseen() {return stseen;}

    /**
     * @param seansiNimi Valitud seansi nimi
     * @return valitud seansi kohaplaan kuvatakse ekraanile
     */
    public GridPane visuaalneKohaplaanNupud(String seansiNimi) {
        Seanss valitud = null;
        for (Seanss seanss : seansid) {
            if(seanss.getPealkiri().equals(seansiNimi)) {
                valitud = seanss;
                this.praeguneSeanss = seanss;
            }
        }

        GridPane visuaalneKohaplaan = new GridPane();

        //0-vaba 1-voetud 2-valitud
        if(valitud != null) {
            visuaalneKohaplaan.setAlignment(Pos.BASELINE_CENTER);
            visuaalneKohaplaan.setHgap(5);
            visuaalneKohaplaan.setVgap(5);
            visuaalneKohaplaan.setStyle("-fx-background-color: #FFFFFF00");
            visuaalneKohaplaan.setPadding(new Insets(0, 10,10, 10));

            //Tooltipid
            Tooltip tp1 = new Tooltip("See koht on vaba");
            Tooltip tp2 = new Tooltip("See koht on võetud");
            Tooltip tp3 = new Tooltip("Teie valitud koht");

            for (int i = 0; i < valitud.getKohaplaan().size(); i++) {
                for (int j = 0; j < valitud.getKohaplaan().get(0).size(); j++) {
                    Rectangle koht = new Rectangle(25,25);
                    if(valitud.kohtVaba(i, j)) {
                        koht.setFill(Color.LIMEGREEN);
                        Tooltip.install(koht, tp1);
                    }
                    else {
                        koht.setFill(Color.RED);
                        Tooltip.install(koht, tp2);
                    }
                    int finalI = i;
                    int finalJ = j;
                    Seanss finalValitud = valitud;
                    koht.setOnMouseClicked(event -> {
                        if(finalValitud.kohtVaba(finalI, finalJ)) {
                            koht.setFill(Color.PURPLE);
                            finalValitud.valiKoht(finalI, finalJ);
                            Tooltip.install(koht, tp3);
                        }
                        else if(finalValitud.getKohaplaan().get(finalI).get(finalJ) == 2) {
                            koht.setFill(Color.LIMEGREEN);
                            finalValitud.getKohaplaan().get(finalI).set(finalJ, 0);
                            Tooltip.install(koht, tp1);
                        }
                    });
                    visuaalneKohaplaan.add(koht, j, i);
                }
            }
        }
        return visuaalneKohaplaan;
    }
}