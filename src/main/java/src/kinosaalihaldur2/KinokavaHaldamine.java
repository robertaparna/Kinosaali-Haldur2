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

import java.util.List;
import java.util.Optional;

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

        //vBox
        VBox vBox = new VBox();
        vBox.setPrefSize(800, 500);
        vBox.setPadding(new Insets(15));
        vBox.getChildren().addAll(borderPane1);

        VBox vBox1 = new VBox();

        ObservableList<String> liigid = FXCollections.observableArrayList();
        liigid.addAll("Dokumentaalfilm", "Mängufilm", "Õudusfilm");

        //Choicebox
        ChoiceBox<String> filmiliik = new ChoiceBox<>(liigid);
        filmiliik.setStyle("-fx-background-color: #f85700; -fx-border-color: WHITE;" +
                "-fx-alignment: center");
        filmiliik.setPrefSize(250,30);
        filmiliik.setValue("Mis liiki filmi soovid kavasse lisada?");
        filmiliik.setOnAction(valik -> {
            avaVorm(filmiliik.getSelectionModel().getSelectedItem());
        });
        vBox1.getChildren().add(filmiliik);
        vBox1.setAlignment(Pos.BASELINE_CENTER);
        vBox.getChildren().add(vBox1);

        //Lõpu asjad
        vBox.setBackground(bGround);
        vBox.maxHeight(20000);
        vBox.maxWidth(20000);
        VBox.setVgrow(hBox,Priority.ALWAYS);
        this.stseen = new Scene(vBox);
    }

    /**
     * @param selectedItem valitud žanr
     * Pärast valikut kuvatakse ekraanile "ankeet", kus saab välja täita
     * ja seansi lisada
     */

    private void avaVorm(String selectedItem) {
        if (selectedItem.equals("Dokumentaalfilm")) {
            Dialog<List<String>> vorm = new Dialog<>();
            vorm.setTitle("Dokumetaalfilmi lisamine");
            vorm.setHeaderText(null);
            ButtonType salvestaNupp = new ButtonType("Lisa film", ButtonBar.ButtonData.OK_DONE);
            vorm.getDialogPane().getButtonTypes().addAll(salvestaNupp, ButtonType.CANCEL);
            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 150, 10, 10));

            TextField pealkiri = new TextField();
            pealkiri.setPromptText("Pealkiri");

            ObservableList<String> saalideNimed = FXCollections.observableArrayList();
            for (Saal saal : Rakendus.getSaalid()) {
                saalideNimed.add(saal.getNimi());
            }

            ChoiceBox<String> saal = new ChoiceBox<>(saalideNimed);
            DatePicker datePicker = new DatePicker();
            TextField kellaaeg = new TextField();
            kellaaeg.setPromptText("hh:mm");

            TextField kestus = new TextField();
            kestus.setPromptText("Kestus minutites");
            TextField teema = new TextField();
            teema.setPromptText("Filmi teema");
            TextField tegijad = new TextField();
            tegijad.setPromptText("Filmi tegijad");

            grid.add(new Label("Pealkiri:"), 0,0);
            grid.add(pealkiri, 1, 0);
            grid.add(new Label("Kuupäev:"), 0,1);
            grid.add(datePicker, 1, 1);
            grid.add(new Label("Algab kell:"), 0,2);
            grid.add(kellaaeg, 1, 2);
            grid.add(new Label("Kestus:"), 0,3);
            grid.add(kestus, 1, 3);
            grid.add(new Label("Teema:"), 0,4);
            grid.add(teema, 1, 4);
            grid.add(new Label("Tegijad:"), 0,5);
            grid.add(tegijad, 1, 5);
            grid.add(new Label("Saalis:"), 0,6);
            grid.add(saal, 1, 6);

            vorm.getDialogPane().setContent(grid);

            vorm.setResultConverter(nupp -> {
                if (nupp == salvestaNupp) {
                    return List.of(pealkiri.getText(), datePicker.getValue().toString(), kellaaeg.getText(), kestus.getText(), saal.getSelectionModel().getSelectedItem(), tegijad.getText(), teema.getText());
                }
                else {
                    return null;
                }
            });

            Optional<List<String>> uusFilm = vorm.showAndWait();

            uusFilm.ifPresent(this::lisaDokumentaal);


        } else if (selectedItem.equals("Mängufilm")) {
            Dialog<List<String>> vorm = new Dialog<>();
            vorm.setTitle("Mängufilmi lisamine");
            vorm.setHeaderText(null);
            ButtonType salvestaNupp = new ButtonType("Lisa film", ButtonBar.ButtonData.OK_DONE);
            vorm.getDialogPane().getButtonTypes().addAll(salvestaNupp, ButtonType.CANCEL);
            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 150, 10, 10));

            TextField pealkiri = new TextField();
            pealkiri.setPromptText("Pealkiri");

            ObservableList<String> saalideNimed = FXCollections.observableArrayList();
            for (Saal saal : Rakendus.getSaalid()) {
                saalideNimed.add(saal.getNimi());
            }

            ChoiceBox<String> saal = new ChoiceBox<>(saalideNimed);
            DatePicker datePicker = new DatePicker();
            TextField kellaaeg = new TextField();
            kellaaeg.setPromptText("hh:mm");

            TextField kestus = new TextField();
            kestus.setPromptText("Kestus minutites");
            TextField zanr = new TextField();
            zanr.setPromptText("Filmi žanr");
            TextField naitlejad = new TextField();
            naitlejad.setPromptText("Filmi naitlejad");

            grid.add(new Label("Pealkiri:"), 0,0);
            grid.add(pealkiri, 1, 0);
            grid.add(new Label("Kuupäev:"), 0,1);
            grid.add(datePicker, 1, 1);
            grid.add(new Label("Algab kell:"), 0,2);
            grid.add(kellaaeg, 1, 2);
            grid.add(new Label("Kestus:"), 0,3);
            grid.add(kestus, 1, 3);
            grid.add(new Label("Filmi žanr:"), 0,4);
            grid.add(zanr, 1, 4);
            grid.add(new Label("Filmi naitlejad:"), 0,5);
            grid.add(naitlejad, 1, 5);
            grid.add(new Label("Saalis:"), 0,6);
            grid.add(saal, 1, 6);

            vorm.getDialogPane().setContent(grid);

            vorm.setResultConverter(nupp -> {
                if (nupp == salvestaNupp) {
                    return List.of(pealkiri.getText(), saal.getSelectionModel().getSelectedItem(), zanr.getText(), naitlejad.getText(), datePicker.getValue().toString(), kellaaeg.getText(), kestus.getText());
                }
                else {
                    return null;
                }
            });

            Optional<List<String>> uusFilm = vorm.showAndWait();

            uusFilm.ifPresent(this::lisaMangufilm);

        } else {
            Dialog<List<String>> vorm = new Dialog<>();
            vorm.setTitle("Õudusfilmi lisamine");
            vorm.setHeaderText(null);
            ButtonType salvestaNupp = new ButtonType("Lisa film", ButtonBar.ButtonData.OK_DONE);
            vorm.getDialogPane().getButtonTypes().addAll(salvestaNupp, ButtonType.CANCEL);
            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 150, 10, 10));

            TextField pealkiri = new TextField();
            pealkiri.setPromptText("Pealkiri");

            ObservableList<String> saalideNimed = FXCollections.observableArrayList();
            for (Saal saal : Rakendus.getSaalid()) {
                saalideNimed.add(saal.getNimi());
            }

            ChoiceBox<String> saal = new ChoiceBox<>(saalideNimed);
            DatePicker datePicker = new DatePicker();
            TextField kellaaeg = new TextField();
            kellaaeg.setPromptText("hh:mm");

            TextField kestus = new TextField();
            kestus.setPromptText("Kestus minutites");
            TextField zanr = new TextField();
            zanr.setPromptText("Filmi žanr");
            TextField naitlejad = new TextField();
            naitlejad.setPromptText("Filmi naitlejad");
            TextField vanusepiirang = new TextField();
            vanusepiirang.setPromptText("Vanusepiirang");

            grid.add(new Label("Pealkiri:"), 0,0);
            grid.add(pealkiri, 1, 0);
            grid.add(new Label("Kuupäev:"), 0,1);
            grid.add(datePicker, 1, 1);
            grid.add(new Label("Algab kell:"), 0,2);
            grid.add(kellaaeg, 1, 2);
            grid.add(new Label("Kestus:"), 0,3);
            grid.add(kestus, 1, 3);
            grid.add(new Label("Filmi žanr:"), 0,4);
            grid.add(zanr, 1, 4);
            grid.add(new Label("Filmi naitlejad:"), 0,5);
            grid.add(naitlejad, 1, 5);
            grid.add(new Label("Saalis:"), 0,6);
            grid.add(saal, 1, 6);
            grid.add(new Label("Vanusepiirang:"), 0,7);
            grid.add(vanusepiirang, 1, 7);

            vorm.getDialogPane().setContent(grid);

            vorm.setResultConverter(nupp -> {
                if (nupp == salvestaNupp) {
                    return List.of(pealkiri.getText(), saal.getSelectionModel().getSelectedItem(), zanr.getText(), naitlejad.getText(), datePicker.getValue().toString(), kellaaeg.getText(), kestus.getText(), vanusepiirang.getText());
                }
                else {
                    return null;
                }
            });

            Optional<List<String>> uusFilm = vorm.showAndWait();
            uusFilm.ifPresent(this::lisaOudusfilm);
        }
    }

    private void lisaOudusfilm(List<String> andmed) {
        String saalinimi = andmed.get(1);
        Saal lisatav = null;
        for (Saal saal : Rakendus.getSaalid()) {
            if (saal.getNimi().equals(saalinimi)) {
                lisatav = saal;
            }
        }
        try{
            new Oudusfilm(andmed.get(0), lisatav, andmed.get(2), andmed.get(3), Integer.parseInt(andmed.get(7)), andmed.get(4), andmed.get(5), Integer.parseInt(andmed.get(6)));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Lisatud!");
            alert.setHeaderText(null);
            alert.setContentText("Filmi lisamine õnnestus!");
            alert.showAndWait();
        }
        catch (AegHoivatudErind e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Filmi ei lisatud");
            alert.setHeaderText(null);
            alert.setContentText("Selleks ajaks on saal juba broneeritud, palun proovi uuesti");
            alert.showAndWait();
        }
        catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Filmi ei lisatud");
            alert.setHeaderText(null);
            alert.setContentText("Filmi lisamisel tekkis viga, palun proovi uuesti");
            alert.showAndWait();
        }
    }

    private void lisaMangufilm(List<String> andmed) {
        String saalinimi = andmed.get(1);
        Saal lisatav = null;
        for (Saal saal : Rakendus.getSaalid()) {
            if (saal.getNimi().equals(saalinimi)) {
                lisatav = saal;
            }
        }
        try{
            new Mangufilm(andmed.get(0), lisatav, andmed.get(2), andmed.get(3), andmed.get(4), andmed.get(5), Integer.parseInt(andmed.get(6)));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Lisatud!");
            alert.setHeaderText(null);
            alert.setContentText("Filmi lisamine õnnestus!");
            alert.showAndWait();
        }
        catch (AegHoivatudErind e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Filmi ei lisatud");
            alert.setHeaderText(null);
            alert.setContentText("Selleks ajaks on saal juba broneeritud, palun proovi uuesti");
            alert.showAndWait();
        }
        catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Filmi ei lisatud");
            alert.setHeaderText(null);
            alert.setContentText("Filmi lisamisel tekkis viga, palun proovi uuesti");
            alert.showAndWait();
        }
    }

    private void lisaDokumentaal(List<String> andmed) {
        String saalinimi = andmed.get(4);
        Saal lisatav = null;
        for (Saal saal : Rakendus.getSaalid()) {
            if (saal.getNimi().equals(saalinimi)) {
                lisatav = saal;
            }
        }
        try{
            new Dokumentaalfilm(andmed.get(0), andmed.get(1), andmed.get(2), Integer.parseInt(andmed.get(3)), lisatav, andmed.get(5), andmed.get(6));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Lisatud!");
            alert.setHeaderText(null);
            alert.setContentText("Filmi lisamine õnnestus!");
            alert.showAndWait();
        }
        catch (AegHoivatudErind e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Filmi ei lisatud");
            alert.setHeaderText(null);
            alert.setContentText("Selleks ajaks on saal juba broneeritud, palun proovi uuesti");
            alert.showAndWait();
        }
        catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Filmi ei lisatud");
            alert.setHeaderText(null);
            alert.setContentText("Filmi lisamisel tekkis viga, palun proovi uuesti");
            alert.showAndWait();
        }
    }
    public Scene getStseen() {return stseen;}
}

