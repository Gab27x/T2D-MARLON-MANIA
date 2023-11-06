package com.example.marlonmania.Controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

public class HelloController {


    @FXML
    private AnchorPane window;

    @FXML
    private Label gameName;
    @FXML
    private Label implementationMenu;
    @FXML
    private Label difficultyMenu;

    @FXML
    private Button playButton;

    @FXML
    private RadioButton easyButton;
    @FXML
    private RadioButton difficultButton;
    @FXML
    private RadioButton listButton;
    @FXML
    private RadioButton matrixButton;

    @FXML
    void onClickEasy() {
        difficultButton.setSelected(false);
    }

    @FXML
    void onClickDifficult() {
        easyButton.setSelected(false);
    }

    @FXML
    void onClickList() {
        matrixButton.setSelected(false);
    }

    @FXML
    void onClickMatrix() {
        listButton.setSelected(false);
    }

    @FXML
    void onClickPlay() {
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

    }



}