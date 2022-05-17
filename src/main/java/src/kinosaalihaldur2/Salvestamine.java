package src.kinosaalihaldur2;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Salvestamine {

    private Scene stseen;
    List<String> salvestusfailid = new ArrayList<>();

    private final String salvestusfailidNimi = "seis.bin";
    public Scene getStseen() {
        return stseen;
    }

    public Salvestamine(Stage pealava, Scene eelmine) {



        try(DataInputStream dis = new DataInputStream(new FileInputStream(salvestusfailidNimi))) {
            int pikkus = dis.readInt();
            for (int i = 0; i < pikkus; i++) {
                salvestusfailid.add(dis.readUTF());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

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
        Text tekst = new Text("Salvestamine");
        tekst.setFont(Font.font(null, FontWeight.BOLD, 20));
        hBox.getChildren().addAll(tagasi,tekst);
        hBox.setAlignment(Pos.TOP_LEFT);

        Button exit = new Button("Välju");
        exit.setOnAction((ActionEvent event) -> Platform.exit());
        exit.setPrefSize(60, 30);
        exit.setStyle("-fx-background-color: red; -fx-border-color:  black");
        exit.setFont(Font.font("Verdana", FontWeight.BOLD, 10));
        HBox hBox1 = new HBox(exit);
        hBox1.setPrefSize(100,100);
        hBox1.setAlignment(Pos.TOP_RIGHT);




        Button laeFailist = new Button("Lae failist");
        laeFailist.setPrefSize(100, 30);
        laeFailist.setStyle("-fx-background-color: red; -fx-border-color:  black");
        laeFailist.setFont(Font.font("Verdana", FontWeight.BOLD, 10));
        laeFailist.setOnAction(event -> {
            ChoiceDialog<String> laeNimi = new ChoiceDialog<>("", salvestusfailid);
            laeNimi.setTitle("Laadimine");
            laeNimi.setHeaderText("");
            laeNimi.setContentText("Sisesta failinimi, millest soovid kino laadida: ");
            laeNimi.show();
        });

        Button salvestaFaili = new Button("Salvesta faili");
        salvestaFaili.setPrefSize(100, 30);
        salvestaFaili.setStyle("-fx-background-color: red; -fx-border-color:  black");
        salvestaFaili.setFont(Font.font("Verdana", FontWeight.BOLD, 10));
        salvestaFaili.setOnAction(event -> {

            TextInputDialog salvestaNimi = new TextInputDialog();
            salvestaNimi.setTitle("Salvestamine");
            salvestaNimi.setHeaderText("");
            salvestaNimi.setContentText("Sisesta failinimi, kuhu soovid kino hetkeseisu salvestada: ");
            Optional<String> vastus = salvestaNimi.showAndWait();
                vastus.ifPresent(failinimi -> {
                    try {
                        lisaSalvestusfail(failinimi);
                    }
                    catch (IOException e){
                        throw new RuntimeException(e);
                    }
                });
        });


        HBox nupud = new HBox(laeFailist, salvestaFaili);
        nupud.setAlignment(Pos.BASELINE_CENTER);
        nupud.setSpacing(10);
        nupud.setPadding(new Insets(30, 0,0,0));

        BorderPane borderPane1 = new BorderPane(null, null, hBox1, null, hBox);
        BorderPane borderPane = new BorderPane(nupud, null, null, null, null);
        //vbox
        VBox vBox = new VBox();
        vBox.setPrefSize(600, 400);
        vBox.setPadding(new Insets(15));
        vBox.setStyle("-fx-background-color: #626266");

        vBox.getChildren().addAll(borderPane1, borderPane);
        this.stseen = new Scene(vBox);


    }

    private void lisaSalvestusfail(String vastus) throws IOException {
        File uusSalvestusFail = new File(vastus + ".bin");
        if(!uusSalvestusFail.createNewFile()) {
            throw new RuntimeException("fail on juba olemas");
        }else{
            salvestusfailid.add(vastus + ".bin");
            uuendaFail();
        }
    }

    private void uuendaFail() {
        try(DataOutputStream dos = new DataOutputStream(new FileOutputStream(salvestusfailidNimi))) {
            List<String> salvestusfailidkoopia = List.copyOf(salvestusfailid);
            for (String s : salvestusfailidkoopia) {
                File fail = new File(s);
                if(fail.exists()){
                    System.out.println("olemas");
                }
                else {
                    salvestusfailid.remove(s);
                    System.out.println("pole olemas");
                }
            }
            dos.writeInt(salvestusfailid.size());
            for (String s : salvestusfailid) {
                dos.writeUTF(s);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
