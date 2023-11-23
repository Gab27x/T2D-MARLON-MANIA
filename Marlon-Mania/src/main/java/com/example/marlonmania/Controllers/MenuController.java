package com.example.marlonmania.Controllers;

import com.example.marlonmania.MainApplication;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuController implements Initializable {

    @FXML
    private AnchorPane mainPane;

    @FXML
    private Label title;
    @FXML
    private Button newGame;
    @FXML
    private Button ranking;
    @FXML
    private Button exit;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setFonts();
    }


    @FXML
    public void onClickNewGame(){
        MainApplication.openWindow("new-Game-Setup.fxml");
        onClickExit();



    }
    @FXML
    public void onClickRanking(){
        MainApplication.openWindow("ranking.fxml");
        onClickExit();


    }
    @FXML
    public void onClickExit(){
        MainApplication.closeWindow((Stage)mainPane.getScene().getWindow());

    }


    private void setFonts() {

        InputStream is = getClass().getResourceAsStream("/Font/f1.ttf");

        if (is != null) {
            // Resto del código para cargar la fuente y aplicarla
            Font myFont = Font.loadFont(is, 12.0);
            Font titleFont = Font.font(myFont.getFamily(), 20.0);

            title.setFont(titleFont);
            newGame.setFont(myFont);
            ranking.setFont(myFont);
            exit.setFont(myFont);

        } else {
            System.err.println("No se pudo cargar el InputStream de la fuente");


        }

    }

    private void showAlert(String mistake) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Error");
        alert.setContentText(mistake);
        alert.showAndWait();
    }

}
