module src.kinosaalihaldur2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens src.kinosaalihaldur2 to javafx.fxml;
    exports src.kinosaalihaldur2;
}