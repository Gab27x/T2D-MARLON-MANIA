package com.example.marlonmania.Controllers;

import com.example.marlonmania.MainApplication;
import com.example.marlonmania.model.Player;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class RankingController implements Initializable {

    @FXML
    private AnchorPane mainPane;

    @FXML
    private Label rankingTable;

    @FXML
    private TableView<Player> playerTableView;

    @FXML
    private TableColumn<Player, String> nickName2;
    @FXML
    private TableColumn<Player, Double> score;
    @FXML
    private Button menu;

    @FXML
    private Button exit;




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        nickName2.setCellValueFactory(new PropertyValueFactory<>("nickName"));
        score.setCellValueFactory(new PropertyValueFactory<>("score"));

        playerTableView.setItems(ControllerPlayers.getInstance().getPlayers());
/*        setFonts();*/


    }
    @FXML
    public void onClickExit(){
        MainApplication.closeWindow((Stage)mainPane.getScene().getWindow());

    }
    @FXML
    public void onClickMenu(){
        MainApplication.openWindow("menu.fxml");
        onClickExit();

    }


    private void setFonts() {

        InputStream is = getClass().getResourceAsStream("/Font/f1.ttf");


        if (is != null) {
            // Resto del código para cargar la fuente y aplicarla
            Font myFont = Font.loadFont(is,12);
            Font titleFont = Font.font(myFont.getFamily(), 20.0);

            rankingTable.setFont(titleFont);





        } else {
            System.err.println("No se pudo cargar el InputStream de la fuente");


        }

    }
}
