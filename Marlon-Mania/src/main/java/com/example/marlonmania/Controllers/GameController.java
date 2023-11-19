package com.example.marlonmania.Controllers;

import com.example.marlonmania.MainApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {

    private static GameController instance;
    public static GameController getInstance(){

        if(instance == null){
            instance = new GameController();

        }

        return instance;
    }


    private Label nickNameLabel = new Label();


    public void setPlayerName(String nickName) {
        nickNameLabel.setText(nickName);


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
