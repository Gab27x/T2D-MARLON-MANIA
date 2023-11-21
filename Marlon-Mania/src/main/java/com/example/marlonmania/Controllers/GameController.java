package com.example.marlonmania.Controllers;

import com.example.marlonmania.model.Game;
import com.example.marlonmania.model.ListGraph;
import com.example.marlonmania.model.ListVertex;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GameController implements Initializable {

    @FXML
    private Label nickNameLabel = new Label();

    @FXML
    private Group graphGroup;

    private Game newGame;

    public void setPlayerName(String nickName) {
        nickNameLabel.setText(nickName);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.newGame = new Game();
        newGame.initGrafo();

        displayGraph();
    }

    private void displayGraph() {
        ArrayList<ListVertex<String>> vertices = newGame.getListGraph();
        int i = 1;
        // Visualizar los nodos del grafo en el Group
        for (ListVertex<String> vertex : vertices) {
            System.out.println(i);
            double posX = vertex.getPosX();
            double posY = vertex.getPosY();

            Circle circle = new Circle(posX, posY, vertex.getRadius());
            circle.setFill(Color.BLUE);

            graphGroup.getChildren().add(circle);
            i++;
        }
    }

}
