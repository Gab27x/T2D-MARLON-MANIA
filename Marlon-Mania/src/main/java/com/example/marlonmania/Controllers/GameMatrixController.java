package com.example.marlonmania.Controllers;

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

import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class GameMatrixController implements Initializable {

    private int level;

    private String nickName;


    @FXML
    private Label nickNameLabel;

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

    private MatrixGame matrixGraphGame;
    final int gridSpacing = 55;
    final int gridRows = 8;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.matrixGraphGame = new MatrixGame(nickNameLabel.getText());
        displayGraph();
        setFonts();
    }

    @FXML
    public void onClickVertical() {
        horizontal.setSelected(false);
        circular.setSelected(false);
        empty.setSelected(false);
    }

    @FXML
    public void onClickHorizontal() {
        vertical.setSelected(false);
        circular.setSelected(false);
        empty.setSelected(false);
    }

    @FXML
    public void onClickCircular() {
        horizontal.setSelected(false);
        vertical.setSelected(false);
        empty.setSelected(false);
    }

    @FXML
    public void onClickEmpty() {
        horizontal.setSelected(false);
        vertical.setSelected(false);
        circular.setSelected(false);
    }


    @FXML
    public void onClickSimulate() {
        matrixGraphGame.simulate();
    }


    public void setNickName(String nickName){
        matrixGraphGame.setNickName(nickName);

    }




    private void displayGraph() {
        MatrixVertex<String>[] vertices = matrixGraphGame.getMatrixGraph().getVertices();

        if (vertices.length == 0) {
            System.out.println("La lista de nodos está vacía.");
            return;
        }



        for (MatrixVertex<String> vertex : vertices) {
            if (vertex != null) {
                int row = vertex.getPosY();
                int col = vertex.getPosX();

                double posX = col * gridSpacing;
                double posY = (gridRows - 1 - row) * gridSpacing;

                Circle circle = new Circle(posX, posY, vertex.getRadius());
                circle.setFill(Color.LIGHTBLUE);
                graphGroup.getChildren().add(circle);

                String state = switch (vertex.getState()) {
                    case EMPTY -> "X";
                    case VERTICAL -> "||";
                    case HORIZONTAL -> "=";
                    case END -> "D";
                    case START -> "F";
                    case CONNECTOR -> "0";
                };

                Text text = new Text(state);
                if (!"X".equals(state)) {
                    text.setFont(Font.font("", FontWeight.BOLD, 12));
                }
                text.setX(posX - text.getLayoutBounds().getWidth() / 2);
                text.setY(posY + text.getLayoutBounds().getHeight() / 4);
                graphGroup.getChildren().add(text);


                int index;
                for(int x = 0 ;x < 8; x++){

                    for (int y = 0 ; y < 7; y++){

                        index =  matrixGraphGame.getMatrixGraph().searchVertexIndex(x + "," + y);
                        MatrixVertex<String> v = matrixGraphGame.getMatrixGraph().obtainVertex(index);

                        index =  matrixGraphGame.getMatrixGraph().searchVertexIndex(x + "," + (y+1));

                        MatrixVertex<String> end =  matrixGraphGame.getMatrixGraph().obtainVertex(index);

                        addLine(v,end);




                    }
                }


                for(int y = 0 ;y < 8; y++){

                    for (int x = 0 ; x < 7; x++){

                        index =  matrixGraphGame.getMatrixGraph().searchVertexIndex(x + "," + y);
                        MatrixVertex<String> v = matrixGraphGame.getMatrixGraph().obtainVertex(index);

                        index =  matrixGraphGame.getMatrixGraph().searchVertexIndex((x+1) + "," + y);

                        MatrixVertex<String> end =  matrixGraphGame.getMatrixGraph().obtainVertex(index);

                        addLine(v,end);




                    }
                }

            }
        }
    }


    private void addLine(MatrixVertex<String> vertex, MatrixVertex<String> end) {
        int vertexRow = vertex.getPosY();
        int vertexCol = vertex.getPosX();

        int endRow = end.getPosY();
        int endCol = end.getPosX();

        double vertexX = vertexCol * gridSpacing;
        double vertexY = (gridRows - 1 - vertexRow) * gridSpacing;

        double endX = endCol * gridSpacing;
        double endY = (gridRows - 1 - endRow) * gridSpacing;

        double dirX = endX - vertexX;
        double dirY = endY - vertexY;
        double length = Math.sqrt(dirX * dirX + dirY * dirY);

        double startXAdjusted = vertexX + (dirX / length) * vertex.getRadius();
        double startYAdjusted = vertexY + (dirY / length) * vertex.getRadius();
        double endXAdjusted = endX - (dirX / length) * end.getRadius();
        double endYAdjusted = endY - (dirY / length) * end.getRadius();

        Line line = new Line(startXAdjusted, startYAdjusted, endXAdjusted, endYAdjusted);
        line.setStrokeWidth(3.0);

        double textX = ((startXAdjusted + endXAdjusted) / 2) + 2;
        double textY = ((startYAdjusted + endYAdjusted) / 2) - 2;

        // Usar el nuevo método getEdgeWeight
        int weightValue = matrixGraphGame.getMatrixGraph().getEdgeWeight(vertex, end);

        Text weightText = new Text(textX, textY, String.valueOf(weightValue));
        weightText.setFill(Color.BLACK);

        if(level == 1){

            graphGroup.getChildren().addAll(line, weightText);
        }else{
            graphGroup.getChildren().add(line);
        }
    }


    private void addLine2(MatrixVertex<String> vertex, MatrixVertex<String> end){

        int row = vertex.getPosY();
        int col = vertex.getPosX();

        double posX = col * gridSpacing;
        double posY = (gridRows - 1 - row) * gridSpacing;

        double endX = end.getPosX() * gridSpacing;
        double endY = (gridRows - 1 - end.getPosY()) * gridSpacing;

        double dirX = endX - posX;
        double dirY = endY - posY;
        double length = Math.sqrt(dirX * dirX + dirY * dirY);

        double startXAdjusted = posX + (dirX / length) * vertex.getRadius();
        double startYAdjusted = posY + (dirY / length) * vertex.getRadius();
        double endXAdjusted = endX - (dirX / length) * end.getRadius();
        double endYAdjusted = endY - (dirY / length) * end.getRadius();

        Line line = new Line(startXAdjusted, startYAdjusted, endXAdjusted, endYAdjusted);
        line.setStrokeWidth(3.0);

        double textX = ((startXAdjusted + endXAdjusted) / 2) + 2;
        double textY = ((startYAdjusted + endYAdjusted) / 2) - 2;

        Text weightText = new Text(textX, textY, String.valueOf(matrixGraphGame.getMatrixGraph().getMatrix()[vertex.getPosY()][vertex.getPosX()+1]));
        weightText.setFill(Color.BLACK);
        graphGroup.getChildren().addAll(line, weightText);

    }


/*
    private void displayGraph() {
        MatrixVertex<String>[] vertices = matrixGraphGame.getMatrixGraph().getVertices();

        int m = 55;

        for(MatrixVertex<String> vertex : vertices){
            System.out.println(vertex.getValue());
            Circle circle = new Circle(vertex.getPosX()* m,vertex.getPosY()*m,vertex.getRadius());
            circle.setFill(Color.LIGHTBLUE);
            graphGroup.getChildren().add(circle);

        }



*/
/*        System.out.println(vertices.length);
        for (MatrixVertex<String> v  :vertices) {
            System.out.println(v.getValue());

        }*//*


    }
*/


    /*private void displayGraph() {
        MatrixVertex<String>[] vertices = matrixGraphGame.getMatrixGraph().getVertices();

        if (vertices.length == 0) {
            System.out.println("La lista de nodos está vacía.");
            return;
        }

        int gridSpacing = 55;
        int gridRows = 8;

        for (MatrixVertex<String> vertex : vertices) {
            if (vertex != null) {  // Verificar si el vértice no es nulo
                int row = vertex.getPosY();
                int col = vertex.getPosX();

                double posX = col * gridSpacing;
                double posY = (gridRows - 1 - row) * gridSpacing;

                Circle circle = new Circle(posX, posY, vertex.getRadius());
                circle.setFill(Color.LIGHTBLUE);
                graphGroup.getChildren().add(circle);

                String state = "nop";
                switch (vertex.getState()) {
                    case EMPTY -> state = "X";
                    case VERTICAL -> state = "||";
                    case HORIZONTAL -> state = "=";
                    case END -> state = "D";
                    case START -> state = "F";
                    case CONNECTOR -> state = "0";
                }

                Text text = new Text(state);
                if (!text.getText().equals("X")) {
                    text.setFont(Font.font("", FontWeight.BOLD, 12));
                }
                text.setX(posX - text.getLayoutBounds().getWidth() / 2);
                text.setY(posY + text.getLayoutBounds().getHeight() / 4);
                graphGroup.getChildren().add(text);

                for (int i = 0; i < vertices.length; i++) {
                    if (vertices[i] != null && matrixGraphGame.getMatrixGraph().getMatrix()[vertex.getPosX()][i] != 0) {
                        MatrixVertex<String> neighbor = vertices[i];

                        double endX = neighbor.getPosX() * gridSpacing;
                        double endY = (gridRows - 1 - neighbor.getPosY()) * gridSpacing;

                        double dirX = endX - posX;
                        double dirY = endY - posY;
                        double length = Math.sqrt(dirX * dirX + dirY * dirY);

                        double startXAdjusted = posX + (dirX / length) * vertex.getRadius();
                        double startYAdjusted = posY + (dirY / length) * vertex.getRadius();
                        double endXAdjusted = endX - (dirX / length) * vertex.getRadius();
                        double endYAdjusted = endY - (dirY / length) * vertex.getRadius();

                        Line line = new Line(startXAdjusted, startYAdjusted, endXAdjusted, endYAdjusted);
                        line.setStrokeWidth(3.0);

                        double textX = ((startXAdjusted + endXAdjusted) / 2) + 2;
                        double textY = ((startYAdjusted + endYAdjusted) / 2) - 2;

                        Text weightText = new Text(textX, textY, String.valueOf(matrixGraphGame.getMatrixGraph().getMatrix()[vertex.getPosX()][i]));
                        weightText.setFill(Color.BLACK);
                        graphGroup.getChildren().addAll(line, weightText);
                    }
                }
            }
        }
    }*/

    public void setNickNameLabel(String nickName) {
        nickNameLabel.setText("Player: " + nickName);
    }

    private void setFonts() {
        InputStream is = getClass().getResourceAsStream("/Font/f1.ttf");

        if (is != null) {
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
        } else {
            System.err.println("No se pudo cargar el InputStream de la fuente");
        }
    }
    public void setLevel(int level){
        this.level = level;
        System.out.println(level);
        graphGroup.getChildren().clear();
        displayGraph();

    }

    @FXML
    public void onClickAddPipe() {
        if (choiceBoxX.getValue() != null && choiceBoxY.getValue() != null &&
                (circular.isSelected() || vertical.isSelected() || horizontal.isSelected() || empty.isSelected())) {
            String vertex = choiceBoxX.getValue() + "," + choiceBoxY.getValue();

            if (circular.isSelected()) {
                matrixGraphGame.addPipe(vertex, State.CONNECTOR);
            } else if (vertical.isSelected()) {
                matrixGraphGame.addPipe(vertex, State.VERTICAL);
            } else if (horizontal.isSelected()) {
                matrixGraphGame.addPipe(vertex, State.HORIZONTAL);
            } else if (empty.isSelected()) {
                matrixGraphGame.addPipe(vertex, State.EMPTY);
            }

            graphGroup.getChildren().clear();
            displayGraph();
        } else {
            System.err.println("MALPARIDO TE FALTA ALGO");
        }

    }

}