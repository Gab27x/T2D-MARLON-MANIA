module com.example.marlonmania {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.marlonmania to javafx.fxml;
    exports com.example.marlonmania;
    exports com.example.marlonmania.Controllers;
    exports com.example.marlonmania.model;
    opens com.example.marlonmania.Controllers to javafx.fxml;
}