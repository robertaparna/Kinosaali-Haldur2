package src.kinosaalihaldur2;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PiletiOstmine extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Group juur = new Group();
        Scene scene = new Scene(juur, 1000, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
