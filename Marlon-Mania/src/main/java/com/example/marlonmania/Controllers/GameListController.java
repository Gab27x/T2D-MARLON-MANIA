package com.example.marlonmania.Controllers;

import com.example.marlonmania.MainApplication;
import com.example.marlonmania.model.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GameListController implements Initializable {

    @FXML
    private Label nickNameLabel;

    @FXML
    private Label implementation;

    @FXML
    private Label title;
    @FXML
    private Label enterX;
    @FXML
    private Label enterY;
    @FXML
    private Label selectPipe;

    @FXML
    private Group graphGroup;
    @FXML
    private ChoiceBox<String> choiceBoxX;
    @FXML
    private ChoiceBox<String> choiceBoxY;

    @FXML
    private Button addPipe;

    @FXML
    private Button simulate;

    @FXML
    private RadioButton vertical;
    @FXML
    private RadioButton horizontal;
    @FXML
    private RadioButton circular;
    @FXML
    private RadioButton empty;

    private int level ;


    private ListGame newListGame;




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        this.newListGame = new ListGame(nickNameLabel.getText());

        displayGraph();

        setFonts();

    }



    @FXML
    public void onClickVertical(){
        horizontal.setSelected(false);
        circular.setSelected(false);
        empty.setSelected(false);
    }
    @FXML
    public void onClickHorizontal(){
        vertical.setSelected(false);
        circular.setSelected(false);
        empty.setSelected(false);
    }
    @FXML
    public void onClickCircular(){
        horizontal.setSelected(false);
        vertical.setSelected(false);
        empty.setSelected(false);
    }

    @FXML
    public void onClickEmpty(){
        horizontal.setSelected(false);
        vertical.setSelected(false);
        circular.setSelected(false);
    }


    @FXML
    public void onClickSimulate(){
        int wins = ControllerPlayers.getInstance().getPlayers().size();

        newListGame.simulate();

        if(wins != ControllerPlayers.getInstance().getPlayers().size()){
            MainApplication.closeWindow((Stage)graphGroup.getScene().getWindow());

        }
    }

    public void setLevel(int level){
        this.level = level;
        newListGame.setLevel(level);
        System.out.println(level);


        graphGroup.getChildren().clear();
        displayGraph();


    }

    private void displayGraph() {
    ArrayList<ListVertex<String>> vertices = newListGame.getListGraph();

    System.out.println("display" + this.level);

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
        String state = "nop";
        switch (vertex.getState()){
            case EMPTY -> {state = "X" ;}
            case VERTICAL -> {state = "||" ;}
            case HORIZONTAL -> {state = "="; }
            case END -> {state = "D";}
            case START -> {state = "F";}
            case CONNECTOR -> {state = "0";}
        }

        Text text = new Text(state);
        if(!text.getText().equals("X")){
            text.setFont(Font.font("", FontWeight.BOLD,12));

        }
        text.setX(posX - text.getLayoutBounds().getWidth() / 2);
        text.setY(posY + text.getLayoutBounds().getHeight() / 4);

        graphGroup.getChildren().add(text);

        // Dibujar líneas desde este nodo a sus nodos adyacentes
        switch (this.level){

            case 0->{

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

            case 1->{
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

                // Crear la línea
                Line line = new Line(startXAdjusted, startYAdjusted, endXAdjusted, endYAdjusted);
                line.setStrokeWidth(3.0);

                // Crear el Text con el valor del peso y ajustar su posición
                double textX = ((startXAdjusted + endXAdjusted) / 2) + 2 ;
                double textY = ((startYAdjusted + endYAdjusted) / 2) - 2 ;

                Text weightText = new Text(textX, textY, String.valueOf(edge.getWeight()));
                weightText.setFill(Color.BLACK); // Color del texto

                // Agregar la línea y el Text al graphGroup
                graphGroup.getChildren().addAll(line, weightText);
                }



            }



        }




/*


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

            // Crear la línea
            Line line = new Line(startXAdjusted, startYAdjusted, endXAdjusted, endYAdjusted);
            line.setStrokeWidth(3.0);

            // Crear el Text con el valor del peso y ajustar su posición
            double textX = (startXAdjusted + endXAdjusted) / 2;
            double textY = (startYAdjusted + endYAdjusted) / 2;

            Text weightText = new Text(textX, textY, String.valueOf(edge.getWeight()));
            weightText.setFill(Color.BLACK); // Color del texto

            // Agregar la línea y el Text al graphGroup
            graphGroup.getChildren().addAll(line, weightText);
        }

        */



        }
    }


    public void setNickNameLabel(String nickName){
        nickNameLabel.setText("Player: "+ nickName);
        newListGame.setNickName(nickName);

    }
    private void setFonts() {

        InputStream is = getClass().getResourceAsStream("/Font/f1.ttf");



        if (is != null) {
            // Resto del código para cargar la fuente y aplicarla
            Font myFont = Font.loadFont(is, 12.0);
            Font titleFont = Font.font(myFont.getFamily(), 20.0);
            Font midFont = Font.font(myFont.getFamily(), 14.0);
            nickNameLabel.setFont(midFont);
            title.setFont(titleFont);
            vertical.setFont(myFont);
            horizontal.setFont(myFont);
            circular.setFont(myFont);
            addPipe.setFont(myFont);
            simulate.setFont(myFont);
            enterX.setFont(myFont);
            enterY.setFont(myFont);
            selectPipe.setFont(myFont);
            empty.setFont(myFont);
            implementation.setFont(myFont);

        } else {
            System.err.println("No se pudo cargar el InputStream de la fuente");


        }

    }

    @FXML
    public void onClickAddPipe(){
        if( choiceBoxX.getValue() != null && choiceBoxY.getValue() != null &&
                (circular.isSelected() || vertical.isSelected() || horizontal.isSelected() || empty.isSelected())){
            String vertex = choiceBoxX.getValue() + "," + choiceBoxY.getValue();

            if(circular.isSelected()){
                /*newGame.addPipe(vertex, State.CONNECTOR);*/
                newListGame.addPipe(vertex, State.CONNECTOR);

            } else if (vertical.isSelected()) {
                /*newGame.addPipe(vertex, State.VERTICAL);*/
                newListGame.addPipe(vertex, State.VERTICAL);

            } else if (horizontal.isSelected()) {
               /* newGame.addPipe(vertex, State.HORIZONTAL);*/
                newListGame.addPipe(vertex, State.HORIZONTAL);

            } else if (empty.isSelected()) {
                /*newGame.addPipe(vertex, State.EMPTY);*/
                newListGame.addPipe(vertex, State.EMPTY);
            }

            graphGroup.getChildren().clear();
            displayGraph();

        }
        else {
            System.err.println(" TE FALTA ALGO");
        }

    }

}
