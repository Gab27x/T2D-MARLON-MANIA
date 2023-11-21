package com.example.marlonmania.Controllers;

import com.example.marlonmania.MainApplication;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerNewGameSetup implements Initializable {

    @FXML
    private AnchorPane mainPane;
    @FXML
    private Label title;

    @FXML
    private Label implementationMenu;
    @FXML
    private Label difficultyMenu;
    @FXML
    private Label nickName;

    @FXML
    private TextArea enterNickname;
    @FXML
    private RadioButton easyButton;
    @FXML
    private RadioButton difficultButton;
    @FXML
    private RadioButton listButton;
    @FXML
    private RadioButton matrixButton;
    @FXML
    private Button play;


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
    void onClickPlay(){
        if( (easyButton.isSelected() || difficultButton.isSelected()) &&
                (matrixButton.isSelected() || listButton.isSelected()) &&
                enterNickname.getText() != null){
            // FIXME
            // implementation: matrix 0 - list 1
            // difficulty: easy 0 - difficult 1

            MainApplication.openGameWindow("game-view.fxml", enterNickname.getText());
            MainApplication.closeWindow((Stage)mainPane.getScene().getWindow());
        }


    }


    private void setFonts() {

        InputStream is = getClass().getResourceAsStream("/Font/f1.ttf");

        if (is != null) {
            // Resto del código para cargar la fuente y aplicarla
            Font myFont = Font.loadFont(is, 12.0);
            Font titleFont = Font.font(myFont.getFamily(), 20.0);

            title.setFont(titleFont);
            nickName.setFont(myFont);
            implementationMenu.setFont(myFont);
            listButton.setFont(myFont);
            matrixButton.setFont(myFont);
            difficultyMenu.setFont(myFont);
            easyButton.setFont(myFont);
            difficultButton.setFont(myFont);
            play.setFont(myFont);



        } else {
            System.err.println("No se pudo cargar el InputStream de la fuente");


        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setFonts();
    }
}
