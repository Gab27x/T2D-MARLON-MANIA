package com.example.marlonmania.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

public class HelloController {


    @FXML
    private AnchorPane window;

    @FXML
    private Label gameName ;
    @FXML
    private Label implementationMenu ;
    @FXML
    private Label difficultyMenu ;

    @FXML
    private Button playButton ;

    @FXML
    private RadioButton easyButton ;
    @FXML
    private RadioButton difficultButton ;
    @FXML
    private RadioButton listButton ;
    @FXML
    private RadioButton matrixButton ;

    @FXML
    void onClickEasy(){
        difficultButton.setSelected(false);


    }
    @FXML
    void onClickDifficult(){
        easyButton.setSelected(false);


    }

    @FXML
    void onClickList(){
        matrixButton.setSelected(false);



    }
    @FXML
    void onClickMatrix(){
        listButton.setSelected(false);

    }
    @FXML
    void onClickPlay(){
        if (listButton.isSelected() || matrixButton.isSelected()) {

            if (difficultButton.isSelected() || easyButton.isSelected()) {
                gameName.setText("TODO BIEN");

            } else {
                showAlert("SELECT DIFFICULTY");
            }

        } else {
            showAlert("SELECT AN IMPLEMENTATION");
        }

    }

    private void showAlert(String mistake) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Error");
        alert.setContentText(mistake);
        alert.showAndWait();
    }

    @FXML
    public void initialize() {
        gameName.setText("MARLON MANIA");
        implementationMenu.setText("SELECT IMPLEMENTATION");
        difficultyMenu.setText("SELECT DIFFICULTY");

        // Comprobar si el AnchorPane no es nulo
/*        if (window != null) {
            // Agregar el controlador de eventos de cambio de tamaño
            addResizeListener();
        }*/
    }

/*    private void addResizeListener() {
        Stage stage = (Stage) window.getScene().getWindow();
        stage.widthProperty().addListener((observable, oldValue, newValue) -> {
            // Ajustar el contenido al nuevo ancho
            window.setPrefWidth(newValue.doubleValue());
        });

        stage.heightProperty().addListener((observable, oldValue, newValue) -> {
            // Ajustar el contenido al nuevo alto
            window.setPrefHeight(newValue.doubleValue());
        });
    }*/

    // Otros métodos y código del controlador
}



