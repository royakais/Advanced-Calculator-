module com.example.oopfinal {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.oopfinal to javafx.fxml;
    exports com.example.oopfinal;
}