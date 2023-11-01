package com.example.marlonmania.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;

public class HelloController {




    @FXML
    private Label gameName ;


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




/*
    @FXML
    public void initialize() {
        gameName.setText("Hola");
    }
*/


}
