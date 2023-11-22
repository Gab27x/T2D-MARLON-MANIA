package com.example.marlonmania.Controllers;

import com.example.marlonmania.MainApplication;
import com.example.marlonmania.model.Player;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {


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
    private TableView<Player> playerTableView;

    @FXML
    private TableColumn<Player, String> nickName;
    @FXML
    private TableColumn<Player, Double> score;
    @FXML
    private TextArea enterNickname;


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
        if ((listButton.isSelected() || matrixButton.isSelected()) && !enterNickname.getText().isEmpty()) {

            if (difficultButton.isSelected() || easyButton.isSelected()) {

                MainApplication.openWindow("game-view.fxml");



            } else {
                showAlert("SELECT DIFFICULTY");
            }

        } else if (enterNickname.getText().isEmpty()) {
            showAlert("ENTER NICKNAME");


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



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gameName.setText("MARLON MANIA");
        implementationMenu.setText("SELECT IMPLEMENTATION");
        difficultyMenu.setText("SELECT DIFFICULTY");
        nickName.setCellValueFactory(new PropertyValueFactory<>("NickName"));
        score.setCellValueFactory(new PropertyValueFactory<>("Score"));

        playerTableView.setItems(ControllerPlayers.getInstance().getPlayers());

    }
}