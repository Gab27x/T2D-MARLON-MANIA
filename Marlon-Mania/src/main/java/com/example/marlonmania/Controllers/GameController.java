package com.example.marlonmania.Controllers;

import com.example.marlonmania.model.Game;
import com.example.marlonmania.model.ListEdge;
import com.example.marlonmania.model.ListGraph;
import com.example.marlonmania.model.ListVertex;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

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
  /*      newGame.initGrafoDescendente();*/

        displayGraph();
    }

    private void displayGraph() {
    ArrayList<ListVertex<String>> vertices = newGame.getListGraph();

    if (vertices.isEmpty()) {
        System.out.println("La lista de nodos está vacía.");
        return;
    }

    // Ajustes para la distribución de la cuadrícula
    int gridSpacing = 55; // Espaciado entre nodos
    int gridRows = 8;

    // Visualizar los nodos del grafo en el Group
    for (ListVertex<String> vertex : vertices) {
        int row = vertex.getPosY();
        int col = vertex.getPosX();

        double posX = col * gridSpacing;
        double posY = (gridRows - 1 - row) * gridSpacing; // Invertir las coordenadas y

        Circle circle = new Circle(posX, posY, vertex.getRadius());
        circle.setFill(Color.LIGHTBLUE);
        graphGroup.getChildren().add(circle);

        // Agregar texto dentro del círculo
        Text text = new Text(vertex.getValue());
        text.setX(posX - text.getLayoutBounds().getWidth() / 2);
        text.setY(posY + text.getLayoutBounds().getHeight() / 4);
        graphGroup.getChildren().add(text);

        // Dibujar líneas desde este nodo a sus nodos adyacentes
        for (ListEdge<String> edge : vertex.getEdges()) {
            int endRow = edge.getRightVertex().getPosY();
            int endCol = edge.getRightVertex().getPosX();

            double endX = endCol * gridSpacing;
            double endY = (gridRows - 1 - endRow) * gridSpacing; // Invertir las coordenadas y

            // Calcular la dirección y la distancia desde el centro del círculo hasta el borde
            double dirX = endX - posX;
            double dirY = endY - posY;
            double length = Math.sqrt(dirX * dirX + dirY * dirY);

            // Calcular los puntos de inicio y fin en el borde del círculo
            double startXAdjusted = posX + (dirX / length) * vertex.getRadius();
            double startYAdjusted = posY + (dirY / length) * vertex.getRadius();
            double endXAdjusted = endX - (dirX / length) * vertex.getRadius();
            double endYAdjusted = endY - (dirY / length) * vertex.getRadius();

            Line line = new Line(startXAdjusted, startYAdjusted, endXAdjusted, endYAdjusted);
            line.setStrokeWidth(3.0);
            graphGroup.getChildren().add(line);
        }
    }
}


}
