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

        // Visualizar los nodos del grafo en el Group
        for (ListVertex<String> vertex : vertices) {
            double posX = vertex.getPosX() * 55;
            double posY = vertex.getPosY() * 55;

            Circle circle = new Circle(posX, posY, vertex.getRadius());
            circle.setFill(Color.LIGHTBLUE);
            graphGroup.getChildren().add(circle);
            // Agregar texto dentro del círculo

            Text text = new Text(vertex.getValue());

            // Ajusta la posición del texto para que esté centrado en la mitad del círculo
            text.setX(posX - text.getLayoutBounds().getWidth() / 2);
            text.setY(posY + text.getLayoutBounds().getHeight() / 4);

            graphGroup.getChildren().add(text);


            // Dibujar líneas desde este nodo a sus nodos adyacentes
            for (ListEdge<String> edge : vertex.getEdges()) {
                double endX = edge.getRightVertex().getPosX() * 55;
                double endY = edge.getRightVertex().getPosY() * 55;

                // Calcular la dirección y la distancia desde el centro del círculo hasta el borde
                double dirX = endX - posX;
                double dirY = endY - posY;
                double length = Math.sqrt(dirX * dirX + dirY * dirY);

                // Calcular los puntos de inicio y fin en el borde del círculo
                double startXAdjusted = posX + (dirX / length) * vertex.getRadius();
                double startYAdjusted = posY + (dirY / length) * vertex.getRadius();
                double endXAdjusted = endX - (dirX / length) * edge.getRightVertex().getRadius();
                double endYAdjusted = endY - (dirY / length) * edge.getRightVertex().getRadius();

                Line line = new Line(startXAdjusted, startYAdjusted, endXAdjusted, endYAdjusted);
                line.setStrokeWidth(3.0);
                graphGroup.getChildren().add(line);
            }
        }
    }



}
